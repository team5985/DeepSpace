package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import frc.robot.Constants;

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
	// Subsystems are singletons (only one instance of this class is possible)
    public static Drive driveInstance;

    /**
     * Get instance of the drivetrain subsystem.
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
    
	/**
	 * Initialise drivetrain
	 */
    private Drive() {
    	configActuators();
		configSensors();
	}

	public void arcadeDrive(double power, double steering, double throttle) {
		double leftPower = steering;
		double rightPower = steering;
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
		
		// Reset to factory default, so we ensure all the settings are what's in this method
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
	//Gyro Turning
	public boolean actionGyroTurn(double gain, double degrees, int speed) {

		boolean completed = false;
		boolean dirLeft = true;
		double actualPos = imu.getYaw();

		
		
		var requiredMovement = (degrees - actualPos);
		double setMovement = gain * requiredMovement;

		
			if (setMovement < 0) {
				arcadeDrive(0.0, 1.0, speed);
				completed = true;
			}
			else if (setMovement > 0) {
				arcadeDrive(0.0, -1.0, speed);
				completed = true;
			}

			return completed;
		}
		
	//Driver Heading Assist
			public void headingAssist(double speed, double adjustAmmount) {
				float yaw = imu.getYaw();;
				if(yaw != 0) {
					if((imu.getYaw()) > 0) {
						arcadeDrive(0.0, (adjustAmmount * -1), speed);
					}
					else {
						arcadeDrive(0.0, adjustAmmount, speed);
					}

					
				}
			
			}

	@Override
	void configSensors() {
		imu = new AHRS(SPI.Port.kMXP); // Must be over SPI so the JeVois can communicate through UART Serial.
		
		leftDriveA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		leftDriveA.setSensorPhase(Constants.kLeftDriveEncoderPhase);
		
		rightDriveA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		rightDriveA.setSensorPhase(Constants.kRightDriveEncoderPhase);
	}
}