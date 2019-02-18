/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

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
  Vision _vision = Vision.getInstance();

  DriverControls _controls = new DriverControls();
  
  Compressor comp;

  @Override
  public void robotInit() {
    comp = new Compressor(Constants.kPcmCanId);
    comp.setClosedLoopControl(true);
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    // if (driverControls.getStickInterupt() == false){
      
    // }
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    teleopController.callStateMachines();
    teleopController.callDrive();

    // _vision.updateVision();
    // SmartDashboard.putNumber("Vision Angle", _vision.getAngle());   
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }
}
