package frc.robot;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.DriverControls;
import frc.robot.subsystems.Drive; 



/**
 * Manager of robot controls and movement during teleop period.
 */
public class TeleopController {

DriverControls thumbPress = new DriverControls();
Timer gameTimer = new Timer();
    public enum States {
        IDLE,
        AUTO,
        TELEOP,
        VISION,
        END,
        HAB,
        VICTORY,
    }

    boolean hatchMode = true;
    public static TeleopController teleopInstance = null;

    public TeleopController getInstance() {
        if (teleopInstance == null) {
            teleopInstance = new TeleopController();
        }
        return teleopInstance;
    }

    DriverControls _controls;
    Drive _drive;

    States robotState;

    private TeleopController() {
        _drive = Drive.getInstance();
    }

    //State Machine
        
public void stateMachine() {
    switch (robotState) {

            case IDLE:
                stIdle();
                trDrive();
            break;
            defult TELEOP:
                stDrive();
                trEnd();
                trVision();
            break;
            case VISION:
                stVision();
                trDrive();
                trEnd();
            break;
            case END:
                stEnd();
                trVictory();
                trHab();
            break;
            case HAB:
                stHab();
                trVictory();
            break;
            case  VICTORY:
                stVictory();
                //HORAY 5985
            break;
    }
    }

        //Raw Transitions 
        
        private void trDriveRaw() {
            robotState = States.TELEOP;
        }
        private void trIdleRaw() {
            robotState = States.IDLE;
        }
        private void trVisionRaw() {
            robotState = States.VISION;
        }
        private void trEndRaw() {
            robotState = States.END;
        }
        private void trHABRaw() {
            robotState = States.HAB;
        }
        private void trVictoryRaw() {
            robotState = States.VICTORY;
        }

        //ADV Transitions

        private void trVision() {
            if(thumbPress.getThumbPress() == true) {
                trVisionRaw();
            }
        } 
        private void trHab() {
            if(thumbPress.getButtonPress6()) {
                trHABRaw();
            }
        }  
        private void trEnd() {
            if(gameTimer.getMatchTime() <= 30) {
                trEndRaw();
            }
        }
        private void trDrive() {
            trDriveRaw();
        }
        public void trVictory() {
            if(thumbPress.getButtonPress12()) {
                trVictoryRaw();
            }
        }

        //States
        public void stDrive() {
            //VroomVroom
        }
        public void stVision() {
            //I've got my i on you
        }
        public void stHab() {
            //Home at last
        }
        public void stEnd() {
            //The end is here! ARRGH
        }
        public void stVictory() {
            //AND THAT'S A WIN FOR TEAM 5985!!!!!!!!!!!!!!!!!!!!!
        }
        public void stIdle() {
            //The idle moooooooooooooooooooooooooooooooooooooooo
        }





        
    
    
    /**
     * To be called by Robot.teleopPeriodic() to run the teleop controller during the teleop mode.
     */
    public void runTeleop() {
        _drive.arcadeDrive(_controls.getDrivePower(), _controls.getDriveSteering(), _controls.getDriveThrottle());

        
        
        if (DriverControls.getButtonPress3()) {
            hatchMode = true;
          }else if (DriverConrols.getRawButtonPress4()) {
            hatchMode = false;
          }

          public boolean getOMMode() {
            @return "True for Hatch mode";
            return hatchMode;
          }

    }

   
    


