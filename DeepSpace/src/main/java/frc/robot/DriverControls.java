package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

		SmartDashboard.setDefaultNumber("Power Gain", 2.0);
		SmartDashboard.setDefaultNumber("Steering Gain", 2.0);
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
		double power = -stick.getY();
		Constants.kDriveSquaredPowerInputsExponent = SmartDashboard.getNumber("Power Gain", 2.0);
		return Math.pow(Math.abs(power), Constants.kDriveSquaredPowerInputsExponent) * Math.signum(power);
	}

	/**
	 * Get driver steering command.
	 * @return X from -1 to 1.
	 */
	public double getDriveSteering() {
		double steering = stick.getX();
		Constants.kDriveSquaredSteeringInputsExponent = SmartDashboard.getNumber("Steering Gain", 2.0);
		return Math.pow(Math.abs(steering), Constants.kDriveSquaredSteeringInputsExponent) * Math.signum(steering);
	}

	/**
	 * Get driver throttle command.
	 * @return Throttle from 0 to 1.
	 */
	public double getDriveThrottle() {
		return (-stick.getThrottle() + 1) / 2;
	}

	/**
	 * button 7
	 */
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
		return stick.getRawButtonPressed(4) || getCargoGrab();
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

	public boolean getButtonPressVision() {
		return stick.getRawButtonPressed(5);
	}

	// Syncroned (semi-auto) climb
	public boolean getButtonPressSyncClimb() {
		return stick.getRawButtonPressed(8);
	}

	public boolean getButtonReleaseSyncClimb() {
		return stick.getRawButtonReleased(8);
	}

	//Elevators extend
	public boolean getButtonPressElevatorExtend() {
		return stick.getRawButtonPressed(11);
	}

	public boolean getReleaseElevatorExtend() {
		return stick.getRawButtonReleased(11);
	}

	//Elevators retract
	public boolean getButtonPressElevatorRetract() {
		return stick.getRawButtonPressed(12);
	}

	public boolean getReleaseElevatorRetract() {
		return stick.getRawButtonReleased(12);
	}
		
	// Mantis Arms
	public boolean getButtonPressMantisExtend() {
		return stick.getRawButtonPressed(9);
	}

	public boolean getButtonPressMantisRetract() {
		return stick.getRawButtonPressed(10);
	}

	
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
		return (xBox.getXButtonPressed() || getThumbPress());
	}
	public boolean getReleaseXButton() {
		return (xBox.getXButtonReleased() || getThumbRelease());
	}
	public boolean getShootCargo() {
		return (xBox.getBumper(Hand.kRight) || stick.getTrigger());
	}
	public boolean getReleaseShootCargo() {
		return (xBox.getBumperReleased(Hand.kRight));
	}

	public boolean getStowBobcat() {
		return xBox.getY(Hand.kLeft)  > 0.75;
	}

	// Manual joint control controls
	/**
	 * @return True while the button for manually controlling both joints is being pressed
	 */
	public boolean getManualMode() {
		return xBox.getBumper(Hand.kLeft);
	}	

	/**
	 * @return Power for bobcat to drive at from -1 to +1
	 */
	public double getManualBobcat() {
		return -xBox.getY(Hand.kLeft);
	}

	/**
	 * @return Power for cargo wrist to drive from -1 to +1
	 */
	public double getManualCargoWrist() {
		return xBox.getY(Hand.kRight);
	}

	/**
	 * @return True if the reset button was pressed since last checked
	 */
	public boolean getButtonPressResetBobcat() {
		return xBox.getStickButtonPressed(Hand.kLeft);
	}

	/**
	 * @return True if the reset button was pressed since last checked
	 */
	public boolean getButtonPressResetCargoWrist() {
		return xBox.getStickButtonPressed(Hand.kRight);
	}

	/* Cargo Controls */
	public boolean getPressStowCargo() {
		return (xBox.getPOV(0) == 0);
	}
	// public boolean getReleaseStowCargo() {
	// 	return (xBox.getTriggerAxis(Hand.kLeft) <= 0.75 && xBox.getTriggerAxis(Hand.kLeft) >= 0.25 );
	// }

/* Wrist Controls */
	public boolean getPressCargoWristDown() {
		return (xBox.getPOV(0) == 180);
	}
	// public boolean getReleaseCargoWristDown() {
	// 	return (xBox.getTriggerAxis(Hand.kRight) < 0.75 && xBox.getTriggerAxis(Hand.kRight) > 0.25);
	// }
// Wrist Down
	public boolean getButtonPressWristMid() {
		return (xBox.getPOV(0) == 270);
	}
	// public boolean getButtonReleaseWristMid() {
	// 	return (xBox.getBumperReleased(Hand.kLeft));
	// }
// Wrist Up
	public boolean getButtonPressWristUp() {
		return (xBox.getPOV(0) == 90);
	}
	// public boolean getButtonReleaseWristUp() {
	// 	return (xBox.getBumperReleased(Hand.kRight));
	// }
	public boolean getCargoGrab(){
		return (xBox.getTriggerAxis(Hand.kRight) > 0.5);
	}
	public boolean leftStickPress(){
		return (xBox.getStickButtonPressed(Hand.kLeft));
	}
}
