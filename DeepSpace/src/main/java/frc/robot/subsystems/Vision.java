package frc.robot.subsystems;

import java.util.Timer;
import edu.wpi.first.wpilibj.Joystick;


public abstract class Vision {
	Timer timer = new Timer();
	Stick = new Joystick(1);

	//Main vision class

	public boolean machineVision() {
			
			long startTime = System.currentTimeMillis();
			long endTime = 0;
			long elapsedTime = 0;
			boolean end = false;
			boolean interupt = false;
			boolean completed = false;
			while(end = false) {
				
				//vision code

				//end conditions
				elapsedTime = (endTime - startTime);
				//Time
				if(elapsedTime >= 20) {
					end = true;
				//Vision Button

				}else if(Stick.getButtonPressed(2)) {
					end = true;
				}//Violent Stick Movement
					Method.methodStickInterupt(end);
			
			}
			if(end = true) {
				completed = false;
			}else {
				completed = true;
			}

		return(completed);
	}
		
			
		
			
		}


}