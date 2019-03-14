package frc.robot.auto;

import frc.robot.AutoController.AutoSelection;
import frc.robot.AutoController.StartSelection;
import frc.robot.subsystems.*;
import frc.robot.subsystems.CargoIntake.IntakePositionsCargo;

public class RightToRight extends AutoMode {
    private String name = "Right To Right";
    private AutoSelection autoType = AutoSelection.RIGHT_CARGOSHIP;
    private StartSelection startPosition = StartSelection.RIGHT_LEV2;

    boolean exit = false;
    int timer = 0;
    
    public void init() {
        exit = false;
        timer = 0;
    }    

    public boolean runStep(int step) {
        boolean stepComplete = false;
        
        /** Each step's code runs within each case. Each step's code runs concurrently, and the step moves on when stepComplete returns true. */
        switch (step) {
            case 0:
            Drive.getInstance().gyroDrive(0.5, 0.0);
            CargoIntake.getInstance().actionMoveTo(IntakePositionsCargo.STOWED);
            Hatch.getInstance().setPosition(true, false);
            stepComplete = timer >= 75;
            break;
            case 1:
            Drive.getInstance().gyroDrive(0.3, 20.0);
            CargoIntake.getInstance().actionMoveTo(IntakePositionsCargo.STOWED);
            Hatch.getInstance().setPosition(true, false);
            stepComplete = timer >= 150;
            break;
            case 2:
            Drive.getInstance().gyroDrive(0.3, 0.0);
            CargoIntake.getInstance().actionMoveTo(IntakePositionsCargo.STOWED);
            Hatch.getInstance().setPosition(true, false);
            stepComplete = timer >= 220;
            break;
            case 3:
            Drive.getInstance().gyroDrive(-0.2, -90.0);
            CargoIntake.getInstance().actionMoveTo(IntakePositionsCargo.STOWED);
            Hatch.getInstance().setPosition(true, false);
            stepComplete = timer >= 233;
            break;
            case 4:
            Drive.getInstance().gyroDrive(0.0, -90.0);
            CargoIntake.getInstance().actionMoveTo(IntakePositionsCargo.STOWED);
            Hatch.getInstance().setPosition(true, false);
            exit = true;
            stepComplete = false;
            break;
        }
        
        timer ++;
        return stepComplete;
    }

    /**
     * Returns String of the name of the auto mode.
     * @return Name of auto mode.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns what the auto does as type AutoSelection.
     * @return Type of auto mode.
     */
    public AutoSelection getAutoType() {
        return autoType;
    }

    /**
     * Returns where the auto starts at as type StartSelection.
     * @return Starting position of auto mode.
     */
    public StartSelection getStartPosition() {
        return startPosition;
    }

    public boolean getExit() {
        return exit;
    }
}