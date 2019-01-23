package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import frc.lib.SquareRootControl;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * Drivetrain class. Contains actions and functions to operate the drivetrain.
 * Sensors:
 * - 2 Drive Encoders (AMT-103)
 * - NavX
 * Actuators:
 * - 3*2 WPI_TalonSRX (MiniCIMs)
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
    private WPI_TalonSRX leftDriveA; // Master
	private WPI_TalonSRX leftDriveB; // Slave
	private WPI_TalonSRX leftDriveC; // Slave
	
	private WPI_TalonSRX rightDriveA; // Master
	private WPI_TalonSRX rightDriveB; // Slave
	private WPI_TalonSRX rightDriveC; // Slave
	
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
	 * Turn the robot on the spot based on gyro heading.
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
    	leftDriveA.set(ControlMode.PercentOutput, leftPower);
    	rightDriveA.set(ControlMode.PercentOutput, rightPower);
    }
    
    @Override
    void configActuators() {
    	// Initialise motor controllers
		leftDriveA = new WPI_TalonSRX(Constants.kLeftDriveACanId);
		leftDriveB = new WPI_TalonSRX(Constants.kLeftDriveBCanId);
		leftDriveC = new WPI_TalonSRX(Constants.kLeftDriveCCanId);
		
		rightDriveA = new WPI_TalonSRX(Constants.kRightDriveACanId);
		rightDriveB = new WPI_TalonSRX(Constants.kRightDriveBCanId);
		rightDriveC = new WPI_TalonSRX(Constants.kRightDriveCCanId);
		
		// Reset to factory default, so we ensure all the settings are what's in this method.
		leftDriveA.configFactoryDefault();
		leftDriveB.configFactoryDefault();
		leftDriveC.configFactoryDefault();
		
		rightDriveA.configFactoryDefault();
		rightDriveB.configFactoryDefault();
		rightDriveC.configFactoryDefault();
		
		// Set follower controllers
		leftDriveB.follow(leftDriveA);
		leftDriveC.follow(leftDriveA);
		
		rightDriveB.follow(rightDriveA);
		rightDriveC.follow(rightDriveA);
		
		// Set brake/coast
		leftDriveA.setNeutralMode(Constants.kDriveNeutralMode);
		leftDriveB.setNeutralMode(Constants.kDriveNeutralMode);
		leftDriveC.setNeutralMode(Constants.kDriveNeutralMode);
		
		rightDriveA.setNeutralMode(Constants.kDriveNeutralMode);
		rightDriveB.setNeutralMode(Constants.kDriveNeutralMode);
		rightDriveC.setNeutralMode(Constants.kDriveNeutralMode);
		
		// Invert right side NOTE: this will make the right controllers green when driving forward
		leftDriveA.setInverted(Constants.kLeftDriveMotorPhase);
		rightDriveA.setInverted(Constants.kRightDriveMotorPhase);
	}

	@Override
	void configSensors() {
		imu = new AHRS(SPI.Port.kMXP); // Must be over SPI so the JeVois can communicate through UART Serial.
		
		leftDriveA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		leftDriveA.setSensorPhase(Constants.kLeftDriveEncoderPhase);
		
		rightDriveA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		rightDriveA.setSensorPhase(Constants.kRightDriveEncoderPhase);

		stick = new Joystick(1);
	}
}