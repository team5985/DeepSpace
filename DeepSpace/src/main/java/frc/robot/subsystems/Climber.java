package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

public abstract class Climber {

boolean encoderElev = true;
boolean encoderArm = true;
private AHRS imu;
	
private void mantisArmManual(Boolean direction) {
/**Boolean direction-True = Up */
boolean completed = false;
	if(direction == true){
		//Arm Up
		completed = true;
	}else {
		//Arm down
		completed = true;
	}
}
private void elevatorAMove(Boolean direction) {

	/**Boolean direction-True = Up */
	if(direction == true){
		//Arm Up
	}else {
		//Arm down
	}
}

private void elevatorBMove(Boolean direction) {

	/**Boolean direction-True = Up */
	if(direction == true){
		//Arm Up
	}else {
		//Arm down
	}
}



public boolean elevatorManual(boolean direction) {
		/**Boolean direction-True = Up */
	boolean completed = false;
	if (encoderElev == false) {
		if (direction = true)
			if ((imu.getRoll()) <= -5.0) {
				elevatorBMove(true);
				completed = true;
			}else if ((imu.getRoll()) >= 5.0) {
				elevatorAMove(true);
				completed = true;
			}else {
				elevatorAMove(true);
				elevatorAMove(true);
				completed = true;
			}
		}else if (direction = false) {
			if ((imu.getRoll()) <= -5.0) {
				elevatorBMove(false);
				completed = true;
			}else if ((imu.getRoll()) >= 5.0) {
				elevatorAMove(false);
				completed = true;
			}else {
				elevatorAMove(false);
				elevatorAMove(false);
				completed = true;  
			}
	}	return completed;}		
	
	public boolean elevatorPitch() {
		boolean completedElev = false;
		boolean completedProc = false;

		boolean base = true;
		if ((imu.getPitch()) <= -5.0) {
			completedElev = (elevatorManual(false));
		}else if ((imu.getPitch()) >= 5.0); {
			completedElev = (elevatorManual(false));
		}else {
			
		}
	return base == completedElev == completedProc;
	

	}
	public boolean climb(boolean direction) {
		boolean base = true;
		if (encoderArm = false) {
			boolean completedArm = (mantisArmManual(direction));
		}
		boolean competedElev = (elevatorPitch());
		return base == completedArm == completedElev;

	}
}	


	


 
