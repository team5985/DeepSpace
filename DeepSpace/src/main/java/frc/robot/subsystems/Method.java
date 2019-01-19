package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;

public abstract class Method {
	
	public boolean methodStickInterupt() {
		boolean end = false;
		Stick = new Joystick(0);

		if(Stick.getX >= 0.7) {
			end = true;
		}else if(Stick.getX <= -0.7) {
			end = true;
		}else if(Stick.getY >= 0.7) {
			end = true;
		}else if(Stick.getY <= -0.7) {
			end = true;
		}else if(Stick.getZ >= 0.7) {
			end = true;
		}else if(Stick.getZ <= -0.7) {
			end = true;
		}
	
	return end;
	}




}