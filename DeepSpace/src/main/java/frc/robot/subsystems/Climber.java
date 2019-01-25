package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;
import jdk.jfr.Percentage;

public class Climber extends Subsystem {

	boolean elevatorCompletedExtend = false;
	boolean elevatorCompletedRetract = true;

	private AHRS imu;
	Solenoid mantisSolenoid;

	WPI_TalonSRX elevator; //sensor (left)
	WPI_TalonSRX talonLeft;
	WPI_TalonSRX talonRight;

/**true = out, false = in */
	private void setMantisPosition(Boolean direction) {
		if(direction == true){
			mantisSolenoid.set(true);
		}else {
			mantisSolenoid.set(false);
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

	public void elevatorMoveUP(double percent){
		talonLeft.set(ControlMode.PercentOutput, percent);
		talonRight.set(ControlMode.PercentOutput, percent);
	}
	public void elevatorMoveDown(double percent){
		talonLeft.set(ControlMode.PercentOutput, percent);  //check negatives
		talonRight.set(ControlMode.PercentOutput, percent);          //TODO change to make less jittery (proportional)
	}

	void configActuators() {
		talonLeft = new WPI_TalonSRX(Constants.kTalonElevatorLeftCanId);
		talonRight = new WPI_TalonSRX(Constants.kTalonElevatorLeftCanId);
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
