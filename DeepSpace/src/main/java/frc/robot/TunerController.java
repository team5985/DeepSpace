package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Controls the tuning mode for bringing up the robot or adjusting values.
 */
public class TunerController {
    public TunerController() {

    }

    public void runTuner() {
        SmartDashboard.getNumber("Wrist Max Power", 0.0);
        // Constants.kBobcatJointMaxOutput = SmartDashboard.getNumber("Bobcat Max Power", 0.0);

        
    }
}