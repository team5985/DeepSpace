package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.Constants;
import jdk.jfr.Percentage;

public class Climber extends Subsystem {

	public boolean elevatorCompletedExtend = false;
	public boolean elevatorCompletedRetract = true;

	private AHRS imu;
	private Solenoid mantisSolenoid;

	private WPI_TalonSRX elevator; //sensor (left)
	private WPI_TalonSRX talonLeft;
	private WPI_TalonSRX talonRight;

	private VictorSP mantisLeft;
	private VictorSP mantisRight;

	public static Climber climberInstance = null;


/**true = out, false = in */
	public static Climber getInstance(){
		if (climberInstance == null){
			climberInstance = new Climber();
		}
		return climberInstance;
	}
	private void setMantisPosition(Boolean direction) {
		if(direction == true){
			mantisSolenoid.set(true);
			mantisLeft.set(1);
			mantisRight.set(1);
		}else {
			mantisSolenoid.set(false);
			mantisLeft.set(0);
			mantisLeft.set(0);
		}
	}
	private void checkCompleted(){
		if (getPosition() >= 18){
			elevatorCompletedExtend = true;
		}
		else{
			elevatorCompletedExtend = false;
		}
		if (getPosition() <= 2){
			elevatorCompletedRetract = true;
		}
		else{
			elevatorCompletedRetract = false;
		}
	}

	/**
	 * Set the Mantis Arms position.
	 * @param direction where true is extended and false is retracted.
	 */
	private boolean manualExtendMantisArms(boolean direction) {
		boolean completed = false;
		if(direction == true){
			completed = true;
		} else {
			//Arm down
			completed = true;
		}
		return completed;
	}

	/**Boolean direction-True = Up */
	public boolean elevatorMove(boolean direction) {
		if (direction = true) {
			setMantisPosition(true);
			if (imu.getPitch() <= 0) {        //TILT CONTROL
				elevatorMoveUP(1);
				return elevatorCompletedExtend;
			}
			else {
				elevatorMoveUP((5-imu.getPitch())*0.2);
				return elevatorCompletedExtend;
			}
		}
		else {                                // move down
			setMantisPosition(false);
			if ((imu.getPitch()) >= 0) {         //TODO: fix jittering
				elevatorMoveDown(-1);
				return elevatorCompletedRetract;
			}
			else {
				elevatorMoveUP((-5-imu.getPitch())*0.2);
				return elevatorCompletedRetract;
			}
		}
	}

	private void elevatorMoveUP(double percent){
		talonLeft.set(ControlMode.PercentOutput, percent);
		talonRight.set(ControlMode.PercentOutput, percent);
	}
	private void elevatorMoveDown(double percent){
		talonLeft.set(ControlMode.PercentOutput, percent);  //check negatives
		talonRight.set(ControlMode.PercentOutput, percent);          //TODO change to make less jittery (proportional)
	}

	void configActuators() {
		talonLeft = new WPI_TalonSRX(Constants.kTalonElevatorLeftCanId);
		talonRight = new WPI_TalonSRX(Constants.kTalonElevatorLeftCanId);
		mantisLeft = new VictorSP(Constants.kVictorMantisLeftPwnPort);
		mantisLeft.setInverted(false);										//TODO: check
		mantisRight = new VictorSP(Constants.kVictorMantisRightPwmPort);
		mantisRight.setInverted(false);										//TODO: check
		mantisSolenoid = new Solenoid(Constants.kSolenoidMantisChannel);
	}

	void configSensors() {
		elevator = new WPI_TalonSRX(Constants.kTalonElevatorLeftCanId);   //same as talonleft (encoder plugged into left TalonSRX)
	}

	/**in inches */
	double getPosition() {
		return elevator.getSelectedSensorPosition() * 0.000244140625; //change values when robot built
	}

	boolean zeroPosition() {
		setMantisPosition(false); //TODO: elevator
		return true;
	}
}
