package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Bobcat;
import frc.robot.subsystems.Bobcat.ArmPositions;
import frc.robot.subsystems.CargoIntake;
import frc.robot.subsystems.CargoIntake.*;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hatch;


/**
 * Manager of robot controls and movement during teleop period.
 */
public class TeleopController {
    DriverControls _controls;
    CargoIntake _cargo;
    Drive _drive;
    Climber _climb;
    Bobcat _bobcat;
    Hatch _hatch;

    States robotState;
    boolean cargoMode = true;
    public static TeleopController teleopInstance = null;
    CargoWristAngleStates cargoWristAngleState;
    BobcatStates bobcatState;
    HatchStates hatchState;
    ElevatorStates elevatorState;

Timer gameTimer = new Timer();
    public enum States {
        AUTO,
        TELEOP,
        VISION,
        HAB,
        VICTORY,
    }
    public enum CargoWristAngleStates{
        LOW,
        MID,
        HIGH,
        STOWED,
    }
    public enum BobcatStates{
        LOW_CARGO,
        MID_CARGO,
        HIGH_CARGO,
        CARGOSHIP_CARGO,
        LOW_HATCH,
        MID_HATCH,
        HIGH_HATCH,
        STOWED,
    }
    public enum HatchStates{
        BEAK_OUT,
        BEAK_IN,
    }
    public enum ElevatorStates{
        Extended,
        Retracted,
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
        _hatch = Hatch.getInstance();
    }

    public void callStateMachines(){
        stateMachine();
        wristAngleStateMachine();
        bobcatHeightStateMachine();
        hatchStateMachine();
        elevatorStateMachine();
    }

    //State Machine
    public void stateMachine() {
        switch (robotState) {
                case TELEOP:
                    trVision();
                    runTeleop();
                    trHab();
                    break;
                case VISION:
                    stVision();
                    break;
                case HAB:
                    stHab();
                    trVictory();
                    break;
                case VICTORY:
                    stVictory();
                    break;
                default:
                    break;
        }
    }

    public void wristAngleStateMachine(){
        switch(cargoWristAngleState){
            case LOW:
            cargoWristAngleLowState();
            case MID:
            cargoWristAngleMidState();
            case HIGH:
            cargoWristAngleHighState();
            case STOWED:
            cargoWristAngleStowedState();
            default:
            cargoWristAngleStowedState();
        }
    }

    public void bobcatHeightStateMachine(){
        switch(bobcatState){
            case LOW_CARGO:
            bobcatCargoLowRocketState();
            case MID_CARGO:
            bobcatCargoMidRocketState();
            case HIGH_CARGO:
            bobcatCargoHighRocketState();
            case CARGOSHIP_CARGO:
            bobcatCargoShipRocketState();
            case LOW_HATCH:
            bobcatHatchLowRocketState();
            case MID_HATCH:
            bobcatHatchMidRocketState();
            case HIGH_HATCH:
            bobcatHatchHighRocketState();
            case STOWED:
            bobcatStowedState();
            default:
            bobcatStowedState();

        }
    }

    public void hatchStateMachine(){
        switch(hatchState){
            case BEAK_OUT:
            hatchOutState();
            case BEAK_IN:
            hatchInState();
            default:
            hatchInState();
        }
    }

    public void elevatorStateMachine(){
        switch(elevatorState){
            case Extended:
            elevatorExtendedState();
            case Retracted:
            elevatorRetractedState();
            default:
            elevatorRetractedState();
        }
    }

    //angle states
    private void cargoWristAngleLowState(){
        _cargo.actionMoveTo(IntakePositionsCargo.LOW);
    }
    private void cargoWristAngleMidState(){
        _cargo.actionMoveTo(IntakePositionsCargo.MID);
    }
    private void cargoWristAngleHighState(){
        _cargo.actionMoveTo(IntakePositionsCargo.HIGH);
    }
    private void cargoWristAngleStowedState(){
        _cargo.actionMoveTo(IntakePositionsCargo.STOWED);
    }

    //bobcat states
    private void bobcatCargoLowRocketState(){
        _bobcat.actionMoveTo(ArmPositions.LOW_CARGO);
    }
    private void bobcatCargoMidRocketState(){
        _bobcat.actionMoveTo(ArmPositions.MID_CARGO);
    }
    private void bobcatCargoHighRocketState(){
        _bobcat.actionMoveTo(ArmPositions.HIGH_CARGO);
    }
    private void bobcatCargoShipRocketState(){
        _bobcat.actionMoveTo(ArmPositions.CARGOSHIP_BALL_POSITION);
    }
    private void bobcatHatchLowRocketState(){
        _bobcat.actionMoveTo(ArmPositions.LOW_HATCH);   
    }
    private void bobcatHatchMidRocketState(){
        _bobcat.actionMoveTo(ArmPositions.MID_HATCH); 
    }
    private void bobcatHatchHighRocketState(){
        _bobcat.actionMoveTo(ArmPositions.HIGH_HATCH); 
    }
    private void bobcatStowedState(){
        _bobcat.actionMoveTo(ArmPositions.DOWN); 
    }

    //hatch states
    private void hatchOutState(){
        _hatch.moveBeak(true);
    }
    private void hatchInState(){
        _hatch.moveBeak(false);
    }
    //elevator states
    private void elevatorExtendedState(){
        _climb.setMantisPosition(true);
        _climb.actionMoveTo(Constants.kElevatorClimbHeight);
    }
    private void elevatorRetractedState(){
        _climb.setMantisPosition(false);
        _climb.actionMoveTo(0.0);
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
            if (robotState != States.HAB){
                _drive.teleopDrive(_controls.getDrivePower(), _controls.getDriveSteering(), _controls.getDriveThrottle());
            }
        }

        public void stVision() {
            //I've got my i on you
        }

        public void stHab() {
            if (_climb.elevatorCompletedExtend == false){
                elevatorState = ElevatorStates.Extended;
            } else {
                if (_controls.getButtonElevatorRetract()){
                    elevatorState = ElevatorStates.Retracted;
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
            if (_controls.getChangeGamePieceMode()){
                if (cargoMode == true){
                    cargoMode = false;
                } else{
                    cargoMode = true;
                    hatchState = HatchStates.BEAK_IN;
                }
            }
            else {
                if (_controls.getPressHatchMode()) {
                    cargoMode = true;
                }
                else if (_controls.getPressBallMode()) {
                    cargoMode = false;
                }
            }
        }

        public boolean getGamePieceMode() {
            return cargoMode;
        }

    /**
     * To be called by Robot.teleopPeriodic() to run the teleop controller during the teleop mode.
     */
    public void runTeleop() {
        if (getGamePieceMode()){
            if(_controls.getButtonPressWristUp()){               //set buttons for 30 degrees down and up and mid
                cargoWristAngleState = CargoWristAngleStates.HIGH;
            }
            if (_controls.getButtonPressWristMid()){
                cargoWristAngleState = CargoWristAngleStates.MID;
                //TODO: if up shoot, if detect ball go mid
            }
            if (_controls.getPressCargoWristDown()){
                cargoWristAngleState = CargoWristAngleStates.LOW;   //TODO: change so no need to hold buttons
            }
            if (_controls.getPressStowCargo()){
                cargoWristAngleState = CargoWristAngleStates.STOWED;  //TODO: light sensor stuff
            }
            if (_controls.getPressLowRocketPosition()){
                bobcatState = BobcatStates.LOW_CARGO;
            }
            if (_controls.getPressMidRocketPosition()){
                bobcatState = BobcatStates.MID_CARGO;  //TODO: fix mixture with shooting and positions
            }
            if (_controls.getPressHighRocketPosition()){
                bobcatState = BobcatStates.HIGH_CARGO;
            }
            if (_controls.getPressXButton()){
                bobcatState = BobcatStates.CARGOSHIP_CARGO;
            }
            if (_controls.getShootCargo()){
                hatchState = HatchStates.BEAK_OUT;
                _cargo.setIntakeMode(IntakeModesCargo.SHOOT);
            }
            if (_controls.getCargoGrab()){
                _cargo.setIntakeMode(IntakeModesCargo.GRAB);
                hatchState = HatchStates.BEAK_IN;
            }
        }
        else if (getGamePieceMode() == false){
            cargoWristAngleState = CargoWristAngleStates.STOWED;
            if (_controls.getPressLowRocketPosition()){
                bobcatState = BobcatStates.LOW_HATCH;
            }
            if (_controls.getPressMidRocketPosition()){
                bobcatState = BobcatStates.MID_HATCH;
            }
            if (_controls.getPressHighRocketPosition()){
                bobcatState = BobcatStates.HIGH_HATCH;
            }
            if (_controls.getPressXButton()){
                if (_hatch.getBeakPosition()){
                    hatchState = HatchStates.BEAK_IN;
                } else {
                    hatchState = HatchStates.BEAK_OUT;
                }
            }
        }
        //put stow here
        if (_controls.getPressStowCargo()){
            bobcatState = BobcatStates.STOWED;
            cargoWristAngleState = CargoWristAngleStates.STOWED;
        }
    }
}
