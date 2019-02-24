package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Encoder;

/**
 * Drivetrain class. Contains actions and functions to operate the drivetrain.
 * Sensors:
 * - 2 Drive Encoders (AMT-103)
 * - NavX
 * Actuators:
 * - 2*2 CANSparkMax (REV NEOs)
 */
public class Drive extends Subsystem {
	
	private CANSparkMax leftDriveA; // Master
	private CANSparkMax leftDriveB; // Slave
	
	private CANSparkMax rightDriveA; // Master
	private CANSparkMax rightDriveB; // Slave

	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private static AHRS _imu; // Inertial Measurement Unit (navx)

	Joystick stick;
	XboxController xBox = new XboxController(Constants.kXboxPort);
	int reverse = 1; // 1: Forward, -1: Backward
	double oldTime = 0;
	double newTime = 0;

	public static Drive driveInstance;

	public boolean zeroPosition(){
		_imu.zeroYaw();
		
		return false;
	}

	/**
	 * @deprecated
	 */
	public double getPosition(){
		return 0.0;
	}

	/**
	 * 
	 * @return Gyro angle in degrees from -180 to 180.
	 */
	public double getGyroYaw() {
		return _imu.getYaw();
	}

	// Subsystems are singletons (only one instance of this class is possible)
    /**
     * Get instance of the drive subsystem
     * @return Drive instance
     */
    public static Drive getInstance() {
    	if (driveInstance == null) {
			driveInstance = new Drive();
		}
		return driveInstance;
	}
  
	/**
	 * Initialise drivetrain
	 */
    private Drive() {
    	configActuators();
		configSensors();
	}

	// Drive-Control
	public void arcadeDrive(double power, double steering, double throttle) {
		double leftPower = (power + steering) * throttle * reverse;
		double rightPower = (power - steering) * throttle * reverse;
		setMotors(leftPower, rightPower);
	}

	/**
	 * Drives the robot, also including tip protection which overrides the parameter inputs.
	 * @param power
	 * @param steering
	 * @param throttle
	 */
	public void teleopDrive(double power, double steering, double throttle) {
		double pitch = _imu.getPitch(); // returns -180 to 180 degress
		double threshold = Constants.kDriveTipThreshold;
		if (pitch > threshold) {
			setMotors(-Constants.kDriveTipCorrectionPower, -Constants.kDriveTipCorrectionPower);
		} else if (pitch < -threshold) {
			setMotors(Constants.kDriveTipCorrectionPower, Constants.kDriveTipCorrectionPower);
		} else {
			arcadeDrive(power, steering, throttle);  // Normal driving.
		}
	}
	
	/**
	 * Turn the robot on the spot with square root ramping based on gyro heading.
	 * @param targetHeading Heading to aim at, in degrees.
	 * @param maxRate Coast rotational rate, in motor units. (1 is full throttle, 0 is no power.)
	 * @return True when heading and rate within target threshold.
	 */
	public boolean actionGyroTurn(double targetHeading, int maxRate) {
		double currentRate = _imu.getRate();
		double currentHeading = _imu.getYaw();
		double steering = Math.sqrt((targetHeading - currentHeading)) * Constants.kDriveGyroTurnK;
		arcadeDrive(0.0, steering, Math.abs(maxRate));

		return (Math.abs(targetHeading - currentHeading) <= Constants.kDriveGyroTurnThresh) && (Math.abs(currentRate) <= Constants.kDriveGyroRateThresh);
	}

	/**
	 * Drive at a given distance and gyro heading.
	 * @param speed Maximum speed in m/s
	 * @param targetHeading in degrees.
	 * @param distance in metres.
	 * @return True when driven to given distance, within a threshold. @see getEncoderWithinDistance()
	 */
	public boolean actionSensorDrive(double speed, double targetHeading, double distance) {
		double position = getAvgEncoderDistance();
		double drivePower = (distance - position) * Constants.kDriveEncoderDrivePGain;

		double currentHeading = _imu.getYaw();
		double steering = (targetHeading - currentHeading) * Constants.kDriveSensorDriveTurnKp;
		arcadeDrive(drivePower, steering, 1);

		return encoderIsWithinDistance(distance, 0.1);
	}
		
	//Driver Heading Assist
	public void headingAssist(double speed, double adjustAmmount) {
	/** double speed, double adjustAmmount*/
		double yaw = _imu.getYaw();
		if(yaw != 0) {
			if((_imu.getYaw()) > 0) {
				arcadeDrive(0.0, (adjustAmmount * -1), speed);
			}
			else {
				arcadeDrive(0.0, adjustAmmount, speed);
			}	
		}
	}

	/**
	 * Sets the drivebase to switch where forwards is. @see arcadeDrive
	 */
	public void setReversed() {
		reverse *= -1;
	}

	/**
	 * Sets the drivebase to change where forwards is. 
	 * @param reversed True makes the robot drive backwards, false makes it drive forwards.
	 * @see arcadeDrive
	 */
	public void setReversed(boolean reversed) {
		if (reversed) {
			reverse = -1;
		} else {
			reverse = 1;
		}
	}

	/**
	 * Returns true if the average of the two encoders are within a certain range.
	 * @param distance is metres
	 * @param threshRange +/- in metres
	 * @return Boolean true when within range.
	 */
	public boolean encoderIsWithinDistance(double distance, double threshRange) {
		return Math.abs(distance - getAvgEncoderDistance()) < threshRange;
	}

	/**
	 * Returns the average of the two drive encoders.
	 * @return average distance since reset in metres.
	 */
	public double getAvgEncoderDistance() {
		return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2;
	}
    
    /**
     * Set the power of both sides of the drivetrain individually. Ranges from -1 to 1.
     * @param leftPower
     * @param rightPower
     */
    public void setMotors(double leftPower, double rightPower) {
		leftDriveA.set(leftPower);
		leftDriveB.set(leftPower);

		rightDriveA.set(rightPower);
		rightDriveB.set(rightPower);
	}
	
	public AHRS getImuInstance() {
		if (_imu == null) {
			_imu = new AHRS(SPI.Port.kMXP); // Must be over SPI so the JeVois can communicate through UART Serial.
		}
		return _imu;
	}
    
    @Override
    void configActuators() {
    	// Initialise motor controllers
		leftDriveA = new CANSparkMax(Constants.kLeftDriveACanId, MotorType.kBrushless);
		leftDriveB = new CANSparkMax(Constants.kLeftDriveBCanId, MotorType.kBrushless);
		
		rightDriveA = new CANSparkMax(Constants.kRightDriveACanId, MotorType.kBrushless);
		rightDriveB = new CANSparkMax(Constants.kRightDriveBCanId, MotorType.kBrushless);
		
		// Set brake/coast
		leftDriveA.setIdleMode(Constants.kDriveIdleMode);
		leftDriveB.setIdleMode(Constants.kDriveIdleMode);
		
		rightDriveA.setIdleMode(Constants.kDriveIdleMode);
		rightDriveB.setIdleMode(Constants.kDriveIdleMode);
		
		// Invert right side
		leftDriveA.setInverted(Constants.kLeftDriveMotorPhase);
		leftDriveB.setInverted(Constants.kLeftDriveMotorPhase);
		rightDriveA.setInverted(!Constants.kLeftDriveMotorPhase);
		rightDriveB.setInverted(!Constants.kLeftDriveMotorPhase);

		// Set current limit to PDP fuses
		leftDriveA.setSmartCurrentLimit(40);
		leftDriveB.setSmartCurrentLimit(40);
		rightDriveA.setSmartCurrentLimit(40);
		rightDriveB.setSmartCurrentLimit(40);
	}
		
	@Override
	void configSensors() {
		_imu = getImuInstance();
		
		leftEncoder = new Encoder(Constants.kDriveLeftEncoderAPort, Constants.kDriveLeftEncoderBPort, Constants.kLeftDriveEncoderPhase, EncodingType.k4X);
		leftEncoder.setMaxPeriod(0.1); 
		leftEncoder.setDistancePerPulse(Constants.kDriveEncoderDistancePerPulse);
		leftEncoder.setSamplesToAverage(8);

		rightEncoder = new Encoder(Constants.kDriveRightEncoderAPort, Constants.kDriveRightEncoderBPort, Constants.kRightDriveEncoderPhase, EncodingType.k4X);
		rightEncoder.setMaxPeriod(0.1); 
		rightEncoder.setDistancePerPulse(Constants.kDriveEncoderDistancePerPulse);
		rightEncoder.setSamplesToAverage(8);
	}
}