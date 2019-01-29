package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Bobcat;
import frc.robot.subsystems.Bobcat.IntakePositions;
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
    CargoWristAngleStates cargoWristAngleStates;
    CargoWristPowerStates cargoWristPowerStates;
    BobcatStates bobcatStates;
    HatchStates hatchStates;
    ElevatorStates elevatorStates;

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
    public enum CargoWristPowerStates{
        GRAB,
        HOLD,
        SHOOT,
        OFF,
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
    wristPowerStateMachine();
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
                case VISION:
                    stVision();
                case HAB:
                    stHab();
                    trVictory();
                case VICTORY:
                    stVictory();
                default:
        }
    }
    public void wristAngleStateMachine(){
        switch(cargoWristAngleStates){
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
    public void wristPowerStateMachine(){
        switch(cargoWristPowerStates){
            case GRAB:
            cargoWristPowerGrabState();
            case HOLD:
            cargoWristPowerHoldState();
            case SHOOT:
            cargoWristPowerShootState();
            case OFF:
            cargoWristPowerOffState();
            default:
            cargoWristPowerOffState();
        }
    }
    public void bobcatHeightStateMachine(){
        switch(bobcatStates){
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
        switch(hatchStates){
            case BEAK_OUT:
            hatchOutState();
            case BEAK_IN:
            hatchInState();
            default:
            hatchInState();
        }
    }
    public void elevatorStateMachine(){
        switch(elevatorStates){
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

    //power states
    private void cargoWristPowerGrabState(){
        _cargo.setIntakeMode(IntakeModesCargo.GRAB);
    }
    private void cargoWristPowerHoldState(){
        _cargo.setIntakeMode(IntakeModesCargo.HOLD);
    }
    private void cargoWristPowerShootState(){
        _cargo.setIntakeMode(IntakeModesCargo.SHOOT);
    }
    private void cargoWristPowerOffState(){
        _cargo.setIntakeMode(IntakeModesCargo.OFF);
    }

    //bobcat states
    private void bobcatCargoLowRocketState(){
        _bobcat.actionMoveTo(IntakePositions.LOW_CARGO);
    }
    private void bobcatCargoMidRocketState(){
        _bobcat.actionMoveTo(IntakePositions.MID_CARGO);
    }
    private void bobcatCargoHighRocketState(){
        _bobcat.actionMoveTo(IntakePositions.HIGH_CARGO);
    }
    private void bobcatCargoShipRocketState(){
        _bobcat.actionMoveTo(IntakePositions.CARGOSHIP_BALL_POSITION);
    }
    private void bobcatHatchLowRocketState(){
        _bobcat.actionMoveTo(IntakePositions.LOW_HATCH);
    }
    private void bobcatHatchMidRocketState(){
        _bobcat.actionMoveTo(IntakePositions.MID_HATCH);
    }
    private void bobcatHatchHighRocketState(){
        _bobcat.actionMoveTo(IntakePositions.HIGH_HATCH);
    }
    private void bobcatStowedState(){
        _bobcat.actionMoveTo(IntakePositions.DOWN);
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
        _climb.elevatorMove(true);               //TODO: add mantis arms
    }
    private void elevatorRetractedState(){
        _climb.elevatorMove(false);
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
                elevatorStates = ElevatorStates.Extended;
            }
            else{
                if (_controls.getButtonElevatorRetract()){
                    elevatorStates = ElevatorStates.Retracted;
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
                    hatchStates = HatchStates.BEAK_IN;
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
                cargoWristAngleStates = CargoWristAngleStates.HIGH;
            }
            if (_controls.getButtonPressWristMid()){
                cargoWristAngleStates = CargoWristAngleStates.MID;
                //TODO: if up shoot, if detect ball go mid
            }
            if (_controls.getPressCargoWristDown()){
                cargoWristAngleStates = CargoWristAngleStates.LOW;   //TODO: change so no need to hold buttons
            }
            if (_controls.getPressStowCargo()){
                cargoWristAngleStates = CargoWristAngleStates.STOWED;  //TODO: light sensor stuff
            }
            if (_controls.getPressLowRocketPosition()){
                bobcatStates = BobcatStates.LOW_CARGO;
            }
            if (_controls.getPressMidRocketPosition()){
                bobcatStates = BobcatStates.MID_CARGO;  //TODO: fix mixture with shooting and positions
            }
            if (_controls.getPressHighRocketPosition()){
                bobcatStates = BobcatStates.HIGH_CARGO;
            }
            if (_controls.getPressXButton()){
                bobcatStates = BobcatStates.CARGOSHIP_CARGO;
            }
            if (_controls.getPressShootCargo()){
                hatchStates = HatchStates.BEAK_OUT;
                cargoWristPowerStates = CargoWristPowerStates.SHOOT;  //TODO: light sensor stuff
            }
            if (_controls.getCargoGrabPress()){
                cargoWristPowerStates = CargoWristPowerStates.GRAB;
                hatchStates = HatchStates.BEAK_IN;
            }
        }
        else if (getGamePieceMode() == false){
            cargoWristAngleStates = CargoWristAngleStates.STOWED;
            cargoWristPowerStates = CargoWristPowerStates.OFF;
            if (_controls.getPressLowRocketPosition()){
                bobcatStates = BobcatStates.LOW_HATCH;
            }
            if (_controls.getPressMidRocketPosition()){
                bobcatStates = BobcatStates.MID_HATCH;
            }
            if (_controls.getPressHighRocketPosition()){
                bobcatStates = BobcatStates.HIGH_HATCH;
            }
            if (_controls.getPressXButton()){
                if (_hatch.getBeakPosition()){
                    hatchStates = HatchStates.BEAK_IN;
                } else {
                    hatchStates = HatchStates.BEAK_OUT;
                }
            }
        }
        //put stow here
        if (_controls.getPressStowCargo()){
            bobcatStates = BobcatStates.STOWED;
            cargoWristAngleStates = CargoWristAngleStates.STOWED;
            cargoWristPowerStates = CargoWristPowerStates.OFF;
        }
    }
}
