package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import frc.lib.Calcs;
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

	public boolean elevatorCompletedExtend = false;
	public boolean elevatorCompletedRetract = true;

	private Solenoid mantisSolenoid;

	private WPI_TalonSRX elevator; //sensor
	private WPI_TalonSRX talonLeft;
	private WPI_TalonSRX talonRight;

	private VictorSP mantisLeft;
	private VictorSP mantisRight;

	public static Climber climberInstance = null;

	private static AHRS imu;

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
		boolean completed = Calcs.isWithinThreshold(height, getPosition(), Constants.kElevatorHeightTolerance);
		double pitch = imu.getPitch();  // Where positive is tipping back
		double feedforward = Math.min(Constants.kElevatorLiftFeedforward, Constants.kElevatorHoldingPower);
		double power = feedforward + (Constants.kElevatorTiltCompGain * pitch);  // Set the tilt compensation gain to 0 to remove software levelling
		talonLeft.set(ControlMode.PercentOutput, power);
		return completed;
	}

	void configActuators() {
		talonLeft = new WPI_TalonSRX(Constants.kTalonElevatorLeftCanId);
		talonLeft.setInverted(Constants.kTalonElevatorDirection);  //TODO: check

		talonRight = new WPI_TalonSRX(Constants.kTalonElevatorLeftCanId);
		talonRight.setInverted(Constants.kTalonElevatorDirection);  //TODO: check
		talonRight.follow(talonLeft);

		mantisLeft = new VictorSP(Constants.kVictorMantisLeftPwnPort);
		mantisLeft.setInverted(Constants.kVictorMantisDirection);  //TODO: check
		
		mantisRight = new VictorSP(Constants.kVictorMantisRightPwmPort);
		mantisRight.setInverted(!Constants.kVictorMantisDirection);
		mantisSolenoid = new Solenoid(Constants.kSolenoidMantisChannel);
	}

	void configSensors() {
		elevator = new WPI_TalonSRX(Constants.kTalonElevatorLeftCanId);   //same as talonleft (encoder plugged into left TalonSRX)
		imu = Drive.getInstance().getImuInstance();
	}

	/**
	 * Gets the estimated height of the elevators.
	 * @return Height in metres.
	 */
	public double getPosition() {
		return elevator.getSelectedSensorPosition() * 0.000244140625; //change values when robot built
	}

	public boolean zeroPosition() {
		setMantisPosition(false); //TODO: elevator

		return true;
	}
}
