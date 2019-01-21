package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class receives raw joystick input and contains methods for TeleopController to get.
 * The idea is that this class raises the button presses into conceptual "drive commands".
 */
public class DriverControls {
    Joystick stick;

    /**
     * Initialise the driver's controllers.
     */
    DriverControls() {
        stick = new Joystick(Constants.kJoystickPort);
    }

    /**
     * Returns true if the joystick has been jerked beyond 0.7.
     * @return Driver interrupt command.
     */
    public boolean getStickInterupt() {
		boolean end = false;

		if(stick.getX() >= 0.7) {
			end = true;
		} else if(stick.getX() <= -0.7) {
			end = true;
		} else if(stick.getY() >= 0.7) {
			end = true;
		} else if(stick.getY() <= -0.7) {
			end = true;
		} else if(stick.getZ() >= 0.7) {
			end = true;
		} else if(stick.getZ() <= -0.7) {
			end = true;
		}
	
	    return end;
	}
}