package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Bobcat;
import frc.robot.subsystems.Bobcat.IntakePositions;
import frc.robot.subsystems.CargoIntake;
import frc.robot.subsystems.CargoIntake.*;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;


/**
 * Manager of robot controls and movement during teleop period.
 */
public class TeleopController {
    DriverControls _controls;
    CargoIntake _cargo;
    Drive _drive;
    Climber _climb;
    Bobcat _bobcat;

    States robotState;
    boolean cargoMode = true;
    public static TeleopController teleopInstance = null;

Timer gameTimer = new Timer();
    public enum States {
        AUTO,
        TELEOP,
        VISION,
        HAB,
        VICTORY,
    }

    public static TeleopController getInstance() {
        if (teleopInstance == null) {
            teleopInstance = new TeleopController();
        }
        return teleopInstance;
    }

    private TeleopController() {
        _drive = Drive.getInstance();
        _controls = DriverControls.getInstance();
        _cargo = CargoIntake.getInstance();
        _climb = Climber.getInstance();
        _bobcat = Bobcat.getInstance();
    }

    //State Machine
        
public void stateMachine() {
    switch (robotState) {
            case TELEOP:
                trVision();
                runTeleop();
                trHab();
            case VISION:
                stVision();
            case HAB:
                stHab();
                trVictory();
            case  VICTORY:
                stVictory();
                //HORAY 5985
            break;
            default:
    }
    }
        //tr for transition
        private void trVision() {
            if(_controls.getThumbPress() == true) {
                robotState = States.TELEOP;
            }
        } 
        private void trHab() {
            if((_controls.getButtonElevatorExtend())){
                robotState = States.HAB;
            }
        }  
        public void trVictory() {
            if(_controls.getButtonPress12()) {
                //why
            }
        }

        //States
        public void callDrive() {
            _drive.arcadeDrive(_controls.getDrivePower(), _controls.getDriveSteering(), _controls.getDriveThrottle());
            if (robotState != States.HAB){
                _drive.testTip();
            }    
        }
        public void stVision() {
            //I've got my i on you
        }
        public void stHab() {
            if (_climb.elevatorCompletedExtend == false){
                _climb.elevatorMove(true);
            }
            else{
                if (_controls.getButtonElevatorRetract()){
                    _climb.elevatorMove(false);
                }
            }
        }
        public void stEnd() {
            //The end is here! ARRGH
        }
        public void stVictory() {
            //AND THAT'S A WIN FOR TEAM 5985!!!!!!!!!!!!!!!!!!!!!
        }

        public void gamePieceMode() {
            if (_controls.getButtonPress3()) {
                cargoMode = true;
            } 
            else if (_controls.getButtonPress4()) {
                cargoMode = false;
            }
        }
        public boolean getGamePieceMode() {
            return cargoMode;
        }

    /**
     * To be called by Robot.teleopPeriodic() to run the teleop controller during the teleop mode.
     */
    public void runTeleop() {
        _drive.arcadeDrive(_controls.getDrivePower(), _controls.getDriveSteering(), _controls.getDriveThrottle());

        if (getGamePieceMode()){
            if(_controls.getButtonPressWristUp()){               //set buttons for 30 degrees down and up and mid
                _cargo.actionMoveTo(IntakePositionsCargo.HIGH);
            }
            if (_controls.getButtonPressWristMid()){
                _cargo.actionMoveTo(IntakePositionsCargo.MID);
                //TODO: if up shoot, if detect ball go mid
            }
            if (_controls.getPressCargoWristDown()){
                _cargo.actionMoveTo(IntakePositionsCargo.LOW);   //TODO: change so no need to hold buttons
            }
            if (_controls.getPressStowCargo()){
                _cargo.actionMoveTo(IntakePositionsCargo.STOWED);  //TODO: light sensor stuff
            }
    }
    if (getGamePieceMode()){
        if (_controls.getPressLowRocketPosition()){
            _bobcat.actionMoveTo(IntakePositions.LOW_CARGO);
        }
        if (_controls.getPressMidRocketPosition()){
            _bobcat.actionMoveTo(IntakePositions.MID_CARGO);  //TODO: fix mixture with shooting and positions
        }
        if (_controls.getPressHighRocketPosition()){
            _bobcat.actionMoveTo(IntakePositions.HIGH_CARGO);
        }
        if (_controls.getPressLowRocketPosition()){
            _bobcat.actionMoveTo(IntakePositions.CARGOSHIP_BALL_POSITION);
        }
    }
    else if (getGamePieceMode() == false){
        _cargo.actionMoveTo(IntakePositionsCargo.STOWED);
        _cargo.setIntakeMode(IntakeModesCargo.OFF);
        if (_controls.getPressLowRocketPosition()){
            _bobcat.actionMoveTo(IntakePositions.LOW_HATCH);
        }
        if (_controls.getPressMidRocketPosition()){
            _bobcat.actionMoveTo(IntakePositions.MID_HATCH);
        }
        if (_controls.getPressHighRocketPosition()){
            _bobcat.actionMoveTo(IntakePositions.HIGH_HATCH);
        }
    }
    //put stow here
    if (_controls.getPressStowCargo()){
        _bobcat.actionMoveTo(IntakePositions.DOWN);
        _cargo.actionMoveTo(IntakePositionsCargo.STOWED);
        _cargo.setIntakeMode(IntakeModesCargo.OFF);
    }
    if (_controls.getPressShootCargo()){
        _cargo.setIntakeMode(IntakeModesCargo.SHOOT);  //TODO: light sensor stuff
    } 
}
}

   
    


