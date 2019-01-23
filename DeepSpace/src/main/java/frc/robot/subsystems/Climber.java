package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;


public abstract class Climber {

boolean encoderElev = true;
boolean encoderArm = true;
private AHRS imu;
Solenoid sMantisArm = new Solenoid(Constants.kSolenidMantisChannel);
	
private void mantisArmManual(Boolean direction) {
/**Boolean direction-True = Up */
boolean completed = false;
	if(direction == true){
		sMantisArm.set(true);
		completed = true;
	}else {
		mantisArms.set(false);
		completed = true;
	}
}
private void elevatorAMove(Boolean direction) {

	/**Boolean direction-True = Up */
	if(direction == true){
		//Arm Up
	}else {
		//Arm down

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;

public class Climber extends Subsystem {

	boolean encoderElev = true;
	boolean encoderArm = true;
	private AHRS imu;

	Solenoid mantisLeft;
	Solenoid mantisRight;
	TalonSRX talonLeft;
	TalonSRX talonRight;
		
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

	/**
	 * Set the elevator 
	 * @param position
	 */
	private void actionMoveTo(double position) {
		/**Boolean direction-True = Up */
		if(direction == true){
			//Arm Up
		}else {
			//Arm down
		}
	}

	/**
	 * 
	 */
	public boolean elevatorManual(boolean direction) {
			/**Boolean direction-True = Up */
		boolean completed = false;
		if (encoderElev == false) {
			if (direction = true)
				if ((imu.getRoll()) <= -5.0) {
					elevatorMoveUP();
					completed = true;
				}else if ((imu.getRoll()) >= 5.0) {
					elevatorMoveUP();
					completed = true;            //TILT CONTROL
				}else {
					elevatorMoveUP();
					completed = true;                        
				}
			}else if (direction = false) {
				if ((imu.getRoll()) <= -5.0) {
					elevatorMoveDown();;
					completed = true;
				}else if ((imu.getRoll()) >= 5.0) {
					elevatorMoveDown();;
					completed = true;
				}else {
					elevatorMoveDown();
					completed = true;  
				}
		}	return completed;
	}		
		public void elevatorMoveUP(){
			talonLeft.set(ControlMode.PercentOutput, 1);
			talonRight.set(ControlMode.PercentOutput, 1);
		}
		public void elevatorMoveDown(){
			talonLeft.set(ControlMode.PercentOutput, -1);
			talonRight.set(ControlMode.PercentOutput, -1);          //TODO change to make less jittery (proportional)
		}

		public boolean elevatorPitch() {
			boolean completedElev = false;
			boolean completedProc = false;

			boolean base = true;
			if ((imu.getPitch()) <= -5.0) {
				completedElev = (elevatorManual(false));
			}else if ((imu.getPitch()) >= 5.0); {
				completedElev = (elevatorManual(false));
			}
		return base == completedElev == completedProc;
		

		}
		public boolean actionClimb(boolean direction) {
			boolean base = true;
			if (encoderArm = false) {
				//boolean completedArm = (mantisArmManual(direction));      add methods
			}
			boolean competedElev = (elevatorPitch());
			//return base == completedArm == completedElev;            add methods

	}

	void configActuators() {
		mantisLeft = new Solenoid(Constants.kMantisLeftPcmPort);
		mantisRight = new Solenoid(Constants.kMantisRightPcmPort);
		talonLeft = new TalonSRX(Constants.kTalonElevatorLeftCanId);
		talonRight = new TalonSRX(Constants.kTalonElevatorLeftCanId);
	}
}	
