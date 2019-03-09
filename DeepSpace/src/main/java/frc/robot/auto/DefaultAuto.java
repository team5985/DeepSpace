package frc.robot.auto;

import frc.robot.AutoController.AutoSelection;
import frc.robot.AutoController.StartSelection;

public class DefaultAuto extends AutoMode {
    private String name = "Default";
    private AutoSelection autoType = AutoSelection.DEFAULT;
    private StartSelection startPosition = StartSelection.DEFAULT;

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
            exit = true;
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