package frc.robot;

import java.util.Timer;
import edu.wpi.first.wpilibj.Joystick;


public class Vision {
	Timer timer = new Timer();
	Joystick stick = new Joystick(0);

	public Vision() {

	}

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

				}else if(stick.getRawButtonPressed(2)) {
					end = true;
				}//Violent Stick Movement
			
			}
			if(end = true) {
				completed = false;
			}else {
				completed = true;
			}

		return(completed);
	}

	public double getX() {
		return 0.0;
	}
}