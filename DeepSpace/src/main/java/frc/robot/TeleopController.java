package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.DriverControls;
import frc.robot.subsystems.Drive;

/**
 * Manager of robot controls and movement during teleop period.
 */
public class TeleopController {

    WPI_TalonSRX bobcat;
    WPI_TalonSRX encoderBobcat;

    public static TeleopController teleopInstance = null;

    public TeleopController getInstance() {
        if (teleopInstance == null) {
            teleopInstance = new TeleopController();
        }
        return teleopInstance;
    }

    DriverControls _controls;
    Drive _drive;

    public boolean heightControl(boolean on) {
        boolean overrided = false;
        if(on == false) {
            
        }else if(encoderBobcat.getSelectedSensorPosition >= Constants.kMaxHabEncoder) {
            //Fire foward torpedo tubes OR kill arm
        }
    }
    
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
