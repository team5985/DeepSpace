package frc.robot.auto;

import frc.robot.AutoController.AutoSelection;
import frc.robot.AutoController.StartSelection;
import frc.robot.subsystems.Drive;

public class DefaultAuto extends AutoMode {
    private String name = "Default";
    private AutoSelection autoType = AutoSelection.DEFAULT;
    private StartSelection startPosition = StartSelection.DEFAULT;

    public boolean runStep(int step) {
        boolean stepComplete = false;
        
        /** Each step's code runs within each case. Each step's code runs concurrently, and the step moves on when stepComplete returns true. */
        switch (step) {
            case 0:
            // stepComplete = Drive.getInstance().actionSensorDrive(1.0, 0.0, 2.0);
            break;
        }
        
        return stepComplete;
    }
}