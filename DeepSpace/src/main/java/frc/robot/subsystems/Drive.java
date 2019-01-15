package frc.robot.subsystems;

import frc.robot.Constants;

public class Drive extends Subsystem {
    public static Drive driveInstance;

    public static Drive getInstance() {
		if (driveInstance == null) {
			driveInstance = new Drive();
		}
		return driveInstance;
	}
    
    private Drive() {
        // Initialisation
    }
}