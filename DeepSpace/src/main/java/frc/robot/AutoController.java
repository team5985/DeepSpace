package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auto.*;

public class AutoController {
	public static AutoController instance;

	public static AutoController getInstance() {
		if (instance == null) {
			instance = new AutoController();
		}
		return instance;
	}

	public AutoMode[] autoModes = new AutoMode[] {
		new DefaultAuto(),
	};

	public enum StartSelection {
		DEFAULT,
		LEFT_LEV1,
		CENTRE_LEV1,
		RIGHT_LEV1,
		LEFT_LEV2,
		RIGHT_LEV2,
	}

	public enum AutoSelection {
		DEFAULT,
		
	}

	SendableChooser<AutoSelection> autoSelector;
	AutoSelection selectedAuto;
	SendableChooser<StartSelection> positionSelector;
	StartSelection selectedPosition;
	AutoMode runningAuto;
	int currentStep;

	private AutoController() {
		autoSelector = new SendableChooser<AutoSelection>();
		
		// Add each auto to the dashboard dropdown menu
		for (AutoSelection auto : AutoSelection.values()) {
			autoSelector.addOption(auto.name(), auto);
		}
		autoSelector.setDefaultOption(AutoSelection.DEFAULT.name(), AutoSelection.DEFAULT);

		SmartDashboard.putData(autoSelector);
	}

	public void initialiseAuto() {
		selectedPosition = positionSelector.getSelected();		
		selectedAuto = autoSelector.getSelected();
		runningAuto = evaluateAutoSelection(selectedAuto, selectedPosition);

		currentStep = 0;
	}

	public void runAuto() {
		boolean stepComplete = runningAuto.runStep(currentStep);
		if (stepComplete) {
			currentStep++;
		}
	}

	private AutoMode evaluateAutoSelection(AutoSelection autoType, StartSelection start) {
		AutoMode retval = null;
		
		for (AutoMode mode : autoModes) {
			if (mode.getAutoType() == autoType && mode.getStartPosition() == start) {
				return mode;
			}
		}
		
		return retval;
	}
}
