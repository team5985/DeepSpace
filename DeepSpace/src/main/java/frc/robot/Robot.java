/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;


import org.junit.runner.Description;
import org.junit.runners.Parameterized.Parameters;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Vision;
import frc.robot.subsystems.Drive;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  TeleopController teleopController = TeleopController.getInstance();

  
  public long encoderToRevolutions(int input) {
    return (input / 4 / 1024); //TODO: Verify 1024
  }
  
   
  
   Joystick stick;

  Vision machineVision;
  Drive drive;
  boolean hatchMode = true;
  


  @Override
  public void robotInit() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    teleopController.stateMachine();
    teleopController.callDrive();
    teleopController.getGamePieceMode();
    teleopController.setState();
    teleopController.wristAngleStateMachine();
    teleopController.wristPowerStateMachine();
    teleopController.bobcatHeightStateMachine();
    teleopController.hatchStateMachine();
    teleopController.elevatorStateMachine();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }
}
