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

  
  public long encoderToRevolutions(int input) {
    return (input / 4 / 1024); //TODO: Verify 1024
  }
  
   
  
   Joystick stick;

  Vision machineVision;
  Drive drive;
  boolean hatchMode = true;
  
  @Override
  public void robotInit() {
    stick = new Joystick(0);
    machineVision = new Vision();
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

    if (stick.getRawButtonPressed(2)) {
      machineVision.machineVision(); 
    }
      Drive.getInstance().testTip();
    if (stick.getRawButtonPressed(3)) {
      hatchMode = true;
    }else if (stick.getRawButtonPressed(4)) {
      hatchMode = false;
    }

    }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }
}
