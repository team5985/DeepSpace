package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * This class receives raw joystick and Xbox input and contains methods for TeleopController to get.
 * The idea is that this class raises the button presses into conceptual "drive commands".
 */
public class DriverControls {
	Joystick stick;
	XboxController xBox;
	boolean end = false;
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
    public DriverControls() {
		stick = new Joystick(Constants.kJoystickPort);
		xBox = new XboxController(Constants.kXboxPort);
    }

    /**
     * Returns true if the joystick has been jerked beyond 0.7.
     * @return Driver interrupt command.
     */
    public boolean getStickInterupt() {

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
		} else if(xBox.getX(Hand.kLeft) < -0.7){
			end = true;
		} else if(xBox.getX(Hand.kLeft) > 0.7){
			end = true;
		} else if(xBox.getX(Hand.kRight) < -0.7){
			end = true;
		} else if(xBox.getX(Hand.kRight) > 0.7){
			end = true;
		} else if(xBox.getY(Hand.kLeft) < -0.7){
			end = true;
		} else if(xBox.getY(Hand.kLeft) > 0.7){
			end = true;
		} else if(xBox.getY(Hand.kRight) < -0.7){
			end = true;
		} else if(xBox.getY(Hand.kRight) > 0.7){
			end = true;
		}
	    return end;
	}

	/**
	 * Get driver power command.
	 * @return Y from -1 to 1.
	 */
	public double getDrivePower() {
		return stick.getY();
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

	public boolean getPressSwitchDriveDirection() {
		return stick.getRawButtonPressed(7);
	}
	
//Trigger
	public boolean getTrigger() {
		return stick.getRawButton(1);
	}
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
	public boolean getPressHatchMode() {
		 return stick.getRawButtonPressed(3);
}
	public boolean getButtonRelease3() {
		return stick.getRawButtonReleased(3);
}
//Ball Mode
	public boolean getPressBallMode() {
		return stick.getRawButtonPressed(4);
	}
	public boolean getChangeGamePieceMode(){ //xbox
		return xBox.getBackButtonPressed();
	}

	public boolean getButtonRelease4() {
	return stick.getRawButtonReleased(4);
	}
	//??
	public boolean getButtonPress5() {
		return stick.getRawButtonPressed(5);
	}
	public boolean getButtonRelease5() {
		return stick.getRawButtonReleased(5);
	}
	//?
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
	//free
	public boolean getButtonPress10() {
		return stick.getRawButtonPressed(10);
	}

	public boolean getButtonRelease10() {
		return stick.getRawButtonReleased(10);
	}
	//Elevators extend
	public boolean getButtonPressElevatorExtend() {
		return stick.getRawButtonPressed(8);
	}

	public boolean getReleaseElevatorExtend() {
		return stick.getRawButtonReleased(8);
	}
	//Elevators retract
	public boolean getButtonPressElevatorRetract() {
		return stick.getRawButtonPressed(11);
	}

	public boolean getReleaseElevatorRetract() {
		return stick.getRawButtonReleased(11);
	}
	//VICTORY
	public boolean getButtonPress12() {
		return stick.getRawButtonPressed(12);
	}
	public boolean getButtonRelease12() {
		return stick.getRawButtonReleased(12);
	}
	/** 
	* Xbox Controller Buttons
	*/
	// low rocket bobcat position
	public boolean getPressLowRocketPosition() {
		return (xBox.getAButtonPressed());
	}
	public boolean getReleaseLowRocketPosition() {
		return (xBox.getAButtonReleased());
	}
	// mid rocket bobcat position
	public boolean getPressMidRocketPosition() {
		return (xBox.getBButtonPressed());
	}
	public boolean getReleaseMidRocketPosition() {
		return (xBox.getBButtonReleased());
	}
	// high rocket bobcat position
	public boolean getPressHighRocketPosition() {
		return (xBox.getYButtonPressed());
	}
	public boolean getReleaseHighRocketPosition() {
		return (xBox.getYButtonReleased());
	}
	// cargoship height (for ball) bobcat position
	public boolean getPressXButton() {
		return (xBox.getXButtonPressed());
	}
	public boolean getReleaseXButton() {
		return (xBox.getXButtonReleased());
	}
	public boolean getShootCargo() {
		return (xBox.getStartButton());
	}
	public boolean getReleaseShootCargo() {
		return (xBox.getStartButtonReleased());
	}

	public boolean getStowBobcat() {
		return xBox.getTriggerAxis(Hand.kRight)  < 0.75;
	}

/* Cargo Controls */
	public boolean getPressStowCargo() {
		return (xBox.getTriggerAxis(Hand.kLeft) > 0.5);
	}
	public boolean getReleaseStowCargo() {
		return (xBox.getTriggerAxis(Hand.kLeft) <= 0.75 && xBox.getTriggerAxis(Hand.kLeft) >= 0.25 );
	}

/* Wrist Controls */
	public boolean getPressCargoWristDown() {
		return (xBox.getTriggerAxis(Hand.kRight) > 0.5);
	}
	public boolean getReleaseCargoWristDown() {
		return (xBox.getTriggerAxis(Hand.kRight) < 0.75 && xBox.getTriggerAxis(Hand.kRight) > 0.25);
	}
// Wrist Down
	public boolean getButtonPressWristMid() {
		return (xBox.getBumperPressed(Hand.kLeft));
	}
	public boolean getButtonReleaseWristMid() {
		return (xBox.getBumperReleased(Hand.kLeft));
	}
// Wrist Up
	public boolean getButtonPressWristUp() {
		return (xBox.getBumperPressed(Hand.kRight));
	}
	public boolean getButtonReleaseWristUp() {
		return (xBox.getBumperReleased(Hand.kRight));
	}
	public boolean getCargoGrab(){
		return (xBox.getStickButton(Hand.kRight));
	}
	public boolean leftStickPress(){
		return (xBox.getStickButtonPressed(Hand.kLeft));
	}

	

	//TODO: add two buttons for 30 degrees down and up for cargo intake angles and mid

}