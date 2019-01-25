package frc.robot;

import frc.robot.DriverControls;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive; 



/**
 * Manager of robot controls and movement during teleop period.
 */
public class TeleopController {
    Climber Climber = new Climber();
    DriverControls DriverControls = new DriverControls();
    boolean hatchMode = true;
    public static TeleopController teleopInstance = null;

    public TeleopController getInstance() {
        if (teleopInstance == null) {
            teleopInstance = new TeleopController();
        }
        return teleopInstance;
    }

    DriverControls _controls;
    Drive _drive;

    private TeleopController() {
        _drive = Drive.getInstance();
    }

    public void startClimb() {

        if (DriverControls.getButtonPress8() && DriverControls.getButtonPress11()) {
            // Activates 'Climb" by pressing button8 && button11
            Climber.mantisArmManual(true);
            Climber.elevatorMoveUP();
    }
}


    /**
     * To be called by Robot.teleopPeriodic() to run the teleop controller during the teleop mode.
     */
    public void runTeleop() {
        _drive.arcadeDrive(_controls.getDrivePower(), _controls.getDriveSteering(), _controls.getDriveThrottle());

        
        
        if (DriverControls.getButtonPress3()) {
            hatchMode = true;
        } else if (DriverConrols.getRawButtonPress4()) {
            hatchMode = false;
        }

          public boolean getOMMode() {
            @return "True for Hatch mode";
            return hatchMode;
        }

    }

   
    

}
