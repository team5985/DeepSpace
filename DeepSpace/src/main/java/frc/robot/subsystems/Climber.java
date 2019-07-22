package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.util.Calcs;
import frc.robot.Constants;

/**
 * Mantis Arms & Elevators.
 * Sensors:
 * - 1 Encoder (AMT-103)
 * - Gyro from Drive (NavX)
 * Actuators:
 * - 2 TalonSRX (Elevator 775s)
 * - 1 Solenoid (plumbed to both mantis arm cylinders)
 */
public class Climber extends Subsystem {
	private Solenoid mantisSolenoid;

	private WPI_TalonSRX masterTalon;
	private WPI_TalonSRX slaveTalon;

	private VictorSP mantisLeft;
	private VictorSP mantisRight;

	public static Climber climberInstance = null;

	private Encoder encoder;
	private static AHRS imu;
	
	private double _climbHeight = 0.0;

	private Climber(){
		configActuators();
		configSensors();
	}

	public static Climber getInstance(){
		if (climberInstance == null){
			climberInstance = new Climber();
		}
		return climberInstance;
	}

	/**
	 * Sets the solenoid to extend or retract the mantis arms.
	 * @param direction True is extend, false is retract.
	 */
	public void setMantisPosition(Boolean direction) {
		if(direction == true){
			mantisSolenoid.set(true);
		} else {
			mantisSolenoid.set(false);
		}
	}

	/**
	 * Move the elevators to a given height, also using the gyro to attempt to keep level.
	 * @param height in metres, where 0 is the starting configuration and increases as the elevators extend.
	 * @return True when close to the target height.
	 */
	public boolean actionMoveTo(double height) {
		double pitch = imu.getPitch();  // Where positive is tipping back
		double encoderBasedPower = (height - getPosition()) * Constants.kElevatorLiftDistGain;
		double power = encoderBasedPower + (Constants.kElevatorTiltCompGain * pitch);  // Set the tilt compensation gain to 0 to remove software levelling
		masterTalon.set(ControlMode.PercentOutput, power);
		slaveTalon.set(ControlMode.PercentOutput, -power);

		SmartDashboard.putNumber("Elevator Position", getPosition());
		SmartDashboard.putNumber("Elevator Power", power);
		return Calcs.isWithinThreshold(height, getPosition(), Constants.kElevatorHeightTolerance);
	}

	public void setElevatorMotors(double power) {
		masterTalon.set(ControlMode.PercentOutput, power);
		slaveTalon.set(ControlMode.PercentOutput, -power);
	}

	public void setMotors(double power) {
		mantisLeft.set(-power);
		mantisRight.set(-power);

		SmartDashboard.putNumber("Mantis Wheels Power", power);
	}

	void configActuators() {
		masterTalon = new WPI_TalonSRX(Constants.kTalonElevatorMasterCanId);
		masterTalon.configFactoryDefault();
		masterTalon.setNeutralMode(NeutralMode.Coast);
		masterTalon.setInverted(false);

		masterTalon.configOpenloopRamp(0.5);
		// talonLeft.configPeakCurrentLimit(0, 0);
		// talonLeft.configContinuousCurrentLimit(20, 0);

		// talonLeft.configPeakOutputForward(Constants.kElevatorMaxOutput);
        // talonLeft.configPeakOutputReverse(-Constants.kElevatorMaxOutput);

		slaveTalon = new WPI_TalonSRX(Constants.kTalonElevatorSlaveCanId);
		slaveTalon.configFactoryDefault();
		slaveTalon.setNeutralMode(NeutralMode.Coast);
		slaveTalon.setInverted(false);

		slaveTalon.configOpenloopRamp(0.5);
		
		mantisLeft = new VictorSP(Constants.kVictorMantisLeftPwmPort);
		mantisLeft.setInverted(Constants.kVictorMantisDirection);  //TODO: check
		
		mantisRight = new VictorSP(Constants.kVictorMantisRightPwmPort);
		mantisRight.setInverted(!Constants.kVictorMantisDirection);
		mantisSolenoid = new Solenoid(Constants.kPcmCanId, Constants.kSolenoidMantisChannel);
	}

	void configSensors() {
		encoder = new Encoder(Constants.kElevatorEncoderPortA, Constants.kElevatorEncoderPortB, Constants.kElevatorJointEncoderPhase, EncodingType.k4X);
		imu = Drive.getInstance().getImuInstance();
	}

	/**
	 * Gets the estimated height of the elevators.
	 * @return Height in metres.
	 */
	public double getPosition() {
		return encoder.getRaw() * Constants.kElevatorDistancePerPulse;
	}

	public boolean zeroPosition() {
		setMantisPosition(false);
		actionMoveTo(0.0);
		return false;
	}

	public void setClimbHeight(double height) {
		_climbHeight = height;
	}

	public double getClimbHeight() {
		return _climbHeight;
	}
}
