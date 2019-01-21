package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

public class Climber extends Subsystem {

	boolean encoderElev = true;
	boolean encoderArm = true;
	private AHRS imu;
		
	/**
	 * Set the Mantis Arms position.
	 * @param direction where true is extended and false is retracted.
	 */
	private boolean manualExtendMantisArms(boolean direction) {
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
