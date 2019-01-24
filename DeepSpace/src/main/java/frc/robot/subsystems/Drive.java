package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import frc.lib.SquareRootControl;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;

/**
 * Drivetrain class. Contains actions and functions to operate the drivetrain.
 * Sensors:
 * - 2 Drive Encoders (AMT-103)
 * - NavX
 * Actuators:
 * - 2*2 CANSparkMax (REV NEOs)
 * @author Zac Hah
 *
 */
public class Drive extends Subsystem {
	public boolean zeroPosition(){
		return false;
	}
	/**
	 * returns z angle of Interial Measurement Unit
	 */
	public double getPosition(){
		return imu.getYaw();
	}
	// Subsystems are singletons (only one instance of this class is possible)
    public static Drive driveInstance;

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
    
    // Class declarations
    private CANSparkMax leftDriveA; // Master
	private CANSparkMax leftDriveB; // Slave
	
	private CANSparkMax rightDriveA; // Master
	private CANSparkMax rightDriveB; // Slave

	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private AHRS imu; // Inertial Measurement Unit (navx)

	Joystick stick;
	int reverse = 1;
	private boolean robotTipped = false;
	double oldTime = 0;
	double newTime = 0;

	SquareRootControl gyroTurnController;
  
	/**
	 * Initialise drivetrain
	 */
    private Drive() {
    	configActuators();
		configSensors();
		
		gyroTurnController = new SquareRootControl(Constants.kDriveMaxRotationalAccel, Constants.kDriveMaxRotationalVel, Constants.kDriveGyroTurnGain);
	}

	// Drive-Control
	public void arcadeDrive(double power, double steering, double throttle) {
		if (robotTipped == false){
			if (stick.getRawButtonPressed(7)) {
				reverse *= -1;
			}
			double leftPower = (power + steering) * throttle * reverse;
			double rightPower = (power - steering) * throttle * reverse;
			setMotors(leftPower, rightPower);
		}

	}

	public void testTip(){
		double roll = imu.getRoll(); // returns -180 to 180 degress    (xx)
		double threshold = 10.0f;
		if(roll > threshold || roll < -threshold){
			newTime = DriverStation.getInstance().getMatchTime();
			if (3 <= newTime - oldTime ){
			robotTipped = true;
			if (roll > threshold){
				setMotors(-0.5, -0.5);
			}
			//TODO: fix
			if (roll < -threshold){
				setMotors(0.5, 0.5);
			}
			} else {
				robotTipped = false;
			}
			//TODO: test 
		}
		else{
			robotTipped = false;
			oldTime = DriverStation.getInstance().getMatchTime();
		}

	}

	// Function to drive in a straight line
	public void straightDrive(double power, int direction, double throttle) {
		reverse = direction;
		arcadeDrive(power, 0 , throttle);
		
	}

	/**
	 * Turn the robot on the spot with square root ramping based on gyro heading.
	 * @param gain Gain to use for square root ramping.
	 * @param targetHeading Heading to aim at, in degrees.
	 * @param maxRate Coast rotational rate, in deg/s.
	 * @return True when heading and rate within target threshold.
	 */
	public boolean actionGyroTurn(double gain, double targetHeading, int maxRate) {
		double currentHeading = imu.getYaw();
		double currentRate = imu.getRate();

		gyroTurnController.configK(gain);
		gyroTurnController.configMaxSpeed(maxRate);
		double rate = gyroTurnController.run(currentHeading, targetHeading);

		double steering = (rate * Constants.kDriveGyroTurnKf) + (rate - currentRate) * Constants.kDriveGyroTurnKp;  // TODO: Find feedforward and tune compensation
		arcadeDrive(0.0, steering, 1.0);

		return (Math.abs(targetHeading - currentHeading) <= Constants.kDriveGyroTurnThresh) && (Math.abs(currentRate) <= Constants.kDriveGyroRateThresh);
	}
		
	//Driver Heading Assist
	public void headingAssist(double speed, double adjustAmmount) {
	/** double speed, double adjustAmmount*/
		float yaw = imu.getYaw();
		if(yaw != 0) {
			if((imu.getYaw()) > 0) {
				arcadeDrive(0.0, (adjustAmmount * -1), speed);
			}
			else {
				arcadeDrive(0.0, adjustAmmount, speed);
			}	
		}
	}
    
    /**
     * Set the power of both sides of the drivetrain individually. Ranges from -1 to 1.
     * @param leftPower
     * @param rightPower
     */
    public void setMotors(double leftPower, double rightPower) {
    	leftDriveA.set(leftPower);
    	rightDriveA.set(rightPower);
    }
    
    @Override
    void configActuators() {
    	// Initialise motor controllers
		leftDriveA = new CANSparkMax(Constants.kLeftDriveACanId, MotorType.kBrushless);
		leftDriveB = new CANSparkMax(Constants.kLeftDriveBCanId, MotorType.kBrushless);
		
		rightDriveA = new CANSparkMax(Constants.kRightDriveACanId, MotorType.kBrushless);
		rightDriveB = new CANSparkMax(Constants.kRightDriveBCanId, MotorType.kBrushless);
		
		// Set follower controllers
		leftDriveB.follow(leftDriveA);
		rightDriveB.follow(rightDriveA);
		
		// Set brake/coast
		leftDriveA.setIdleMode(Constants.kDriveIdleMode);
		leftDriveB.setIdleMode(Constants.kDriveIdleMode);
		
		rightDriveA.setIdleMode(Constants.kDriveIdleMode);
		rightDriveB.setIdleMode(Constants.kDriveIdleMode);
		
		// Invert right side
		leftDriveA.setInverted(Constants.kLeftDriveMotorPhase);
		rightDriveA.setInverted(Constants.kRightDriveMotorPhase);

		// Set current limit to PDP fuses
		leftDriveA.setSmartCurrentLimit(40);
		rightDriveA.setSmartCurrentLimit(40);
	}
		
	@Override
	void configSensors() {
		imu = new AHRS(SPI.Port.kMXP); // Must be over SPI so the JeVois can communicate through UART Serial.
		
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