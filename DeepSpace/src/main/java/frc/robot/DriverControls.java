package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class receives raw joystick input and contains methods for TeleopController to get.
 * The idea is that this class raises the button presses into conceptual "drive commands".
 */
public class DriverControls {
	public static DriverControls mDriverControlsInstance;
	
	public static DriverControls getInstance() {
		if (mDriverControlsInstance == null) {
			mDriverControlsInstance = new DriverControls();
		}
		return mDriverControlsInstance;
	}

    /**
     * Initialise the driver's controllers.
     */
    private DriverControls() {
		stick = new Joystick(Constants.kJoystickPort);
    }

	Joystick stick;

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

//Trigger

	public boolean getTriggerPress() {

		return stick.getTriggerPressed();
	}

	public boolean getTriggerRelease() {

		return stick.getTriggerReleased();
	}
//Thumb Button
	public boolean getThumbPress() {

		return stick.getRawButtonPressed(2);
	}
	public boolean getThumbRelease() {

		return stick.getRawButtonReleased(2);
	}
//Hatch Mode
	public boolean getButtonPress3() {

		return stick.getRawButtonPressed(3);
}

	public boolean getButtonRelease3() {

		return stick.getRawButtonReleased(3);
}
//Ball Mode
	public boolean getButtonPress4() {

		return stick.getRawButtonPressed(4);
}

	public boolean getButtonRelease4() {

	return stick.getRawButtonReleased(4);
}
//Wrist down/up
	public boolean getButtonPress5() {

		return stick.getRawButtonPressed(5);
}

	public boolean getButtonRelease5() {

		return stick.getRawButtonReleased(5);
}
//Begin HAB sequence
	public boolean getButtonPress6() {

		return stick.getRawButtonPressed(6);
}

	public boolean getButtonRelease6() {

		return stick.getRawButtonReleased(6);
}
//Reverse Direction
	public boolean getButtonPress7() {

		return stick.getRawButtonPressed(7);
}

	public boolean getButtonRelease7() {

		return stick.getRawButtonReleased(7);
}
//EM on/off
	public boolean getButtonPress10() {

		return stick.getRawButtonPressed(10);
}

	public boolean getButtonRelease10() {

		return stick.getRawButtonReleased(10);
}
//Elevators deployed
	public boolean getButtonPress8() {

		return stick.getRawButtonPressed(8);
}

	public boolean getButtonRelease8() {

		return stick.getRawButtonReleased(8);
}
//Mantis Arms deployed
	public boolean getButtonPress11() {

		return stick.getRawButtonPressed(11);
}

	public boolean getButtonRelease11() {

		return stick.getRawButtonReleased(11);
}
//Elevators stowed
	public boolean getButtonPressElevDown() {

		return (stick.getRawButtonPressed(8) && stick.getRawButtonPressed(9) == true);
	}
	public boolean getButtonReleaseElevDown() {

		return (stick.getRawButtonReleased(8) || stick.getRawButtonReleased(9) == true);
	}
//MA Stowed
	public boolean getButtonPressMADown() {

		return (stick.getRawButtonPressed(11) && stick.getRawButtonPressed(9) == true);
}
	public boolean getButtonReleaseMADown() {

		return (stick.getRawButtonReleased(11) || stick.getRawButtonReleased(9) == true);
}



	/**
	 * Get driver power command.
	 * @return Y from -1 to 1.
	 */
	public double getDrivePower() {
		return -stick.getY();
	}

	/**
	 * Get driver steering command.
	 * @return X from -1 to 1.
	 */
	public double getDriveSteering() {
		return stick.getX();
	}

	/**
	 * Get driver throttle command.
	 * @return Throttle from 0 to 1.
	 */
	public double getDriveThrottle() {
		return (-stick.getThrottle() + 1) / 2;
	}

	

}