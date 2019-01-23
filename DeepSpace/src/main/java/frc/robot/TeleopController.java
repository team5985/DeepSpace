package frc.robot;

import frc.robot.DriverControls;
import frc.robot.subsystems.Drive;

/**
 * Manager of robot controls and movement during teleop period.
 */
public class TeleopController {
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

    /**
     * To be called by Robot.teleopPeriodic() to run the teleop controller during the teleop mode.
     */
    public void runTeleop() {
        _drive.arcadeDrive(_controls.getDrivePower(), _controls.getDriveSteering(), _controls.getDriveThrottle());
    }
}
