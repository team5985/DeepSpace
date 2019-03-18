/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  TeleopController teleopController = TeleopController.getInstance();
  AutoController autoController = AutoController.getInstance();
  // Vision _vision = Vision.getInstance();

  DriverControls _controls = new DriverControls();
  
  Compressor comp;

  boolean autoInterrupt = false;

  @Override
  public void robotInit() {
    comp = new Compressor(Constants.kPcmCanId);
    comp.setClosedLoopControl(true);

    CameraServer.getInstance().startAutomaticCapture();    
  }

  @Override
  public void robotPeriodic() {
    // _vision.updateVision();
    // SmartDashboard.putBoolean("Target Lock", _vision.getDataIsValid());
    // SmartDashboard.putNumber("Vision Angle", _vision.getAngle());

    SmartDashboard.putBoolean("Game Piece Mode", teleopController.getGamePieceMode());  // True = ball mode
  }

  @Override
  public void autonomousInit() {
    teleopController.resetAllSensors();
    autoController.initialiseAuto();
    autoInterrupt = false;
    teleopController.matchStartConfig();
  }

  @Override
  public void autonomousPeriodic() {
    if (autoController.exit() || _controls.getStickInterupt()) {
      autoInterrupt = true;
    }

    if (!autoInterrupt) {
      autoController.runAuto();
    } else {
      teleopController.callStateMachines();
    }
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    teleopController.callStateMachines();
  }
}
