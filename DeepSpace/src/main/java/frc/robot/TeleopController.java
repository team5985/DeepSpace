package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    boolean cargoMode = false;
    public static TeleopController teleopInstance = null;
    CargoWristAngleStates cargoWristAngleState;
    BobcatStates bobcatState;
    HatchStates hatchState;
    ElevatorStates climberState;

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
        HOLD_HATCH,
        STOW_HATCH,
        POP,
    }
    public enum ElevatorStates{
        RISE,
        TRANSITION,
        RETRACT,
    }

    public static TeleopController getInstance() {
        if (teleopInstance == null) {
            teleopInstance = new TeleopController();
        }
        return teleopInstance;
    }

    private TeleopController() {
        _controls = DriverControls.getInstance();
        
        _drive = Drive.getInstance();
        _cargo = CargoIntake.getInstance();
        _climb = Climber.getInstance();
        _bobcat = Bobcat.getInstance();
        _hatch = Hatch.getInstance();

        robotState = States.TELEOP;
        cargoWristAngleState = CargoWristAngleStates.STOWED;
        bobcatState = BobcatStates.STOWED;
        hatchState = HatchStates.STOW_HATCH;
        climberState = ElevatorStates.RETRACT;
    }

    public void callStateMachines(){
        stateMachine();
        wristAngleStateMachine();
        bobcatHeightStateMachine();
        hatchStateMachine();
        climberStateMachine();

        SmartDashboard.putString("Current State", robotState.name());
    }

    //State Machine
    public void stateMachine() {
        switch (robotState) {
            case TELEOP:
                trVision();
                stTeleop();
                trHab();
                break;
            case VISION:
                stVision();
                trTeleop();
                break;
            case HAB:
                stHab();
                trTeleop();
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
            break;
            case MID:
            cargoWristAngleMidState();
            break;
            case HIGH:
            cargoWristAngleHighState();
            break;
            case STOWED:
            cargoWristAngleStowedState();
            break;
            default:
            cargoWristAngleStowedState();
            break;
        }
    }

    public void bobcatHeightStateMachine(){
        switch(bobcatState){
            case LOW_CARGO:
            bobcatCargoLowRocketState();
            break;
            case MID_CARGO:
            bobcatCargoMidRocketState();
            break;
            case HIGH_CARGO:
            bobcatCargoHighRocketState();
            break;
            case CARGOSHIP_CARGO:
            bobcatCargoShipRocketState();
            break;
            case LOW_HATCH:
            bobcatHatchLowRocketState();
            break;
            case MID_HATCH:
            bobcatHatchMidRocketState();
            break;
            case HIGH_HATCH:
            bobcatHatchHighRocketState();
            break;
            case STOWED:
            bobcatStowedState();
            break;
            default:
            bobcatStowedState();
            break;
        }
    }

    public void hatchStateMachine(){
        switch(hatchState){
            case HOLD_HATCH:
            hatchHoldState();
            break;
            case STOW_HATCH:
            hatchStowState();
            break;
            case POP:
            hatchPopState();
            break;
            default:
            hatchStowState();
            break;
        }
    }

    public void climberStateMachine(){
        switch(climberState){
            case RISE:
                climberExtendedState();
                break;
            case TRANSITION:
                climberTransferState();
                break;
            case RETRACT:
                climberRetractState();
                break;
            default:
                climberRetractState();
                break;
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
    private void hatchHoldState(){
        _hatch.setPosition(true, false);
    }
    private void hatchStowState(){
        _hatch.setPosition(false, false);
    }
    private void hatchPopState() {
        _hatch.setPosition(false, true);
    }

    //elevator states
    /**
     * Extend both mantis arms and elevators
     */
    private void climberExtendedState(){
        _climb.setMantisPosition(true);
        _climb.actionMoveTo(Constants.kElevatorClimbHeight);
    }

    /**
     * Transferring from supported by mantis arms to supported by drivebase and elevators. Raises the mantis arms while keeping the elevators down.
     */
    private void climberTransferState() {
        _climb.setMantisPosition(false);
        _climb.actionMoveTo(0.0);
    }

    /**
     * Retracts all parts of the climber.
     */
    private void climberRetractState(){
        _climb.setMantisPosition(false);
        _climb.actionMoveTo(0.0);
    }

    //tr for transition
    private void trVision() {
        if(_controls.getThumbPress() == true) {
            robotState = States.VISION;
        }
    }
    private void trHab() {
        if((_controls.getButtonPressElevatorExtend())){
            robotState = States.HAB;
        }
    }

    private void trTeleop() {
        if (_controls.getThumbPress()) {
            robotState = States.TELEOP;
        }
    }
    
    public void trVictory() {
        // if(_controls.getButtonPress12()) {
            //why
        // }
    }

    //States
    public void callDrive() {
        if (_controls.getPressSwitchDriveDirection()) {
            _drive.setReversed();
        }
        if (robotState != States.HAB){
            _drive.teleopDrive(_controls.getDrivePower(), _controls.getDriveSteering(), _controls.getDriveThrottle());
        }
    }

    public void stVision() {
        //I've got my i on you

    }

    public void stHab() {
        bobcatState = BobcatStates.STOWED;
        cargoWristAngleState = CargoWristAngleStates.STOWED;

        _climb.setMotors(_controls.getDrivePower());
        _drive.teleopDrive(_controls.getDrivePower(), _controls.getDriveSteering(), _controls.getDriveThrottle());
        
        if (_controls.getReleaseElevatorExtend()) {
            climberState = ElevatorStates.RISE;

        } else if (_controls.getButtonPressElevatorRetract() && climberState == ElevatorStates.RISE){
            climberState = ElevatorStates.TRANSITION;

        } else if (_controls.getButtonPressElevatorRetract() && climberState == ElevatorStates.TRANSITION){
            climberState = ElevatorStates.RETRACT;
        }
    }

    public void stEnd() {
        //The end is here! ARRGH
    }

    public void stVictory() {
        //AND THAT'S A WIN FOR TEAM 5985!!!!!!!!!!!!!!!!!!!!!
    }

    public void setGamePieceMode() {
        if (_controls.getChangeGamePieceMode()){
            if (cargoMode == true){
                cargoMode = false;
                cargoWristAngleState = CargoWristAngleStates.STOWED;
            } else{
                cargoMode = true;
                hatchState = HatchStates.STOW_HATCH;
            }
        }
        else {
            if (_controls.getPressHatchMode()) {
                cargoMode = false;
            }
            else if (_controls.getPressBallMode()) {
                cargoMode = true;
            }
        }
    }

    public boolean getGamePieceMode() {
        return cargoMode;
    }

    public void stTeleop() {
        setGamePieceMode();
        
        if (getGamePieceMode() == true){  // Ball handling mode
            if(_controls.getButtonPressWristUp()){               //set buttons for 30 degrees down and up and mid
                cargoWristAngleState = CargoWristAngleStates.HIGH;
            }
            if (_controls.getButtonPressWristMid()){
                cargoWristAngleState = CargoWristAngleStates.MID;
            }
            if (_controls.getPressCargoWristDown()){
                cargoWristAngleState = CargoWristAngleStates.LOW;
            }
            if (_controls.getPressStowCargo()){
                cargoWristAngleState = CargoWristAngleStates.STOWED;
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
                hatchState = HatchStates.POP;
                _cargo.setIntakeMode(IntakeModesCargo.SHOOT);
            } else if (_controls.getCargoGrab()){
                _cargo.setIntakeMode(IntakeModesCargo.GRAB);
                hatchState = HatchStates.STOW_HATCH;
            } else {
                _cargo.setIntakeMode(IntakeModesCargo.HOLD);
                hatchState = HatchStates.STOW_HATCH;
            }
        }
        else if (getGamePieceMode() == false) {  // Hatch handling mode
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
            
            // Hatch popper
            if (_controls.getTrigger()) {
                int hatchPopDelayTimer = 0;
                if (_controls.getTriggerPress()) {
                    hatchPopDelayTimer = 0;
                }
                hatchPopDelayTimer++;
                
                // Returns the beak and activates the popper after a delay.
                if (hatchPopDelayTimer < Constants.kHatchPopperDelay) {
                    hatchState = HatchStates.STOW_HATCH;
                } else {
                    hatchState = HatchStates.POP;
                }
            }
            
            // Hatch beak toggle
            if (_controls.getPressXButton()){
                if (_hatch.getBeakPosition()){
                    hatchState = HatchStates.STOW_HATCH;
                } else {
                    hatchState = HatchStates.HOLD_HATCH;
                }
            }
        }
        //put stow here
        if (_controls.getStowBobcat()){
            bobcatState = BobcatStates.STOWED;
        }
    }
}
