package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

public abstract class Climber {

boolean encoder = true;

private AHRS imu;
	
private void mantisArmManual(Boolean direction) {
/**Boolean direction-True = Up */
	if(direction == true){
		//Arm Up
	}else {
		//Arm down
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
	if (encoder == false) {
		if (direction = true)
			if ((imu.getYaw) <= -5) {
				elevatorBMove(true);
			}else if ((imu.getYaw) <= -5) {
				elevatorAMove(true);
			}else {
				elevatorAMove(true);
				elevatorAMove(true);
			}
		}else if (direction = false) {
			if ((imu.getYaw) <= -5) {
				elevatorBMove(false);
			}else if ((imu.getYaw) <= -5) {
				elevatorAMove(false);
			}else {
				elevatorAMove(false);
				elevatorAMove(false);
			}
	}else if {
		
		
	}	

}
