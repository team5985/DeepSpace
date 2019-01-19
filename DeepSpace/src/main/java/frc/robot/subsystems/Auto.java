package frc.robot.subsystems;

import java.util.Timer;

public abstract class Auto {
   
	public void auto() {
	
		final long startTime = System.currentTimeMillis();
		long endTime = 0;
		final long maxTime = 15000;
		long elapsedTime = 0;
		boolean end = false;
		boolean completed = false;

		while(end = false) {
			elapsedTime = (endTime - startTime);
			//auto code

			//end conditions
			if(elapsedTime == maxTime) {
				end = false;
			} else if(Stick.getButtonPressed(8)){
				end = false;
			}
			Method.methodStickInterupt(end);
			
		}
	
	
	}
}