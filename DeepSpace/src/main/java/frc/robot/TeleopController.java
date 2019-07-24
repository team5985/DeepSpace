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
import edu.wpi.first.networktables.*;


/**
 * Manager of robot controls and movement during teleop period.
 */
public class TeleopController {
    DriverControls _controls;
    Vision _vision;

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
    long newTime = 0;
    boolean finished = true;
    long oldTime = 0;    
    boolean testStart = false;  
    boolean doOne = false;
    boolean doTwo = false;    
    boolean doThree = false; 
    boolean doFour = false; 
    boolean doFive = false; 
    boolean doSix = false; 
    boolean doSeven = false;     

    private double limeLightDriveCommand = 0.0;
    private double limeLightSteerCommand = 0.0;

    double position = 0.0;

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
        MANUAL,
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
        MANUAL,
        CLIMB_ANGLE,
    }
    public enum HatchStates{
        HOLD_HATCH,
        STOW_HATCH,
        POP,
    }
    public enum ElevatorStates{
        STOWED,
        RISE,
        LEV2_RISE,
        TRANSITION,
        RETRACT,
        MANUAL,
    }

    public static TeleopController getInstance() {
        if (teleopInstance == null) {
            teleopInstance = new TeleopController();
        }
        return teleopInstance;
    }

    private TeleopController() {
        _controls = DriverControls.getInstance();
        _vision = Vision.getInstance();
        
        _drive = Drive.getInstance();
        _cargo = CargoIntake.getInstance();
        _climb = Climber.getInstance();
        _bobcat = Bobcat.getInstance();
        _hatch = Hatch.getInstance();

        robotState = States.TELEOP;
        cargoWristAngleState = CargoWristAngleStates.STOWED;
        bobcatState = BobcatStates.STOWED;
        hatchState = HatchStates.HOLD_HATCH;
        climberState = ElevatorStates.STOWED;
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
                // trVision();
                stTeleop();
                trHab();
                autoClimb();
                //String s = "test";
                //SmartDashboard.putString("Climber State", s);
                break;
            case VISION:
                stTeleop();
                stVision();
                trTeleop();
                break;
            case HAB:
                stHab();
                trTeleop();
                break;
            case VICTORY:
                
                trTeleop();
                break;
            default:
                break;
        }
    }

    public void wristAngleStateMachine(){
        SmartDashboard.putString("Wrist Angle", cargoWristAngleState.name());
        
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
            case MANUAL:
            cargoWristManualState();
            break;
            default:
            cargoWristAngleStowedState();
            break;
        }
    }

    public void bobcatHeightStateMachine(){
        SmartDashboard.putString("Bobcat State", bobcatState.name());
        
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
            case MANUAL:
            bobcatManualState();
            break;
            case CLIMB_ANGLE:
            bobcatClimbState();
            break;
            default:
            bobcatStowedState();
            break;
        }
    }

    public void hatchStateMachine(){
        SmartDashboard.putString("Hatch State", hatchState.name());
        
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
            case STOWED:
                break;
            case RISE:
                _climb.setClimbHeight(Constants.kElevatorClimbHeight);
                climberExtendedState();
                break;
            case LEV2_RISE:
                _climb.setClimbHeight(Constants.kElevatorLev2ClimbHeight);
                climberLev2ExtendedState();
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
    private void cargoWristManualState() {
        _cargo.manualMove(_controls.getManualCargoWrist());
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
    private void bobcatManualState() {
        _bobcat.manualMove(_controls.getManualBobcat());
    }
    private void bobcatClimbState() {
        _bobcat.actionMoveTo(ArmPositions.CLIMB_ANGLE);
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
        _climb.actionMoveTo(_climb.getClimbHeight());
    }

    private void climberLev2ExtendedState() {
        _climb.setMantisPosition(true);
        _climb.actionMoveTo(_climb.getClimbHeight());
    }

    /**
     * Transferring from supported by mantis arms to supported by drivebase and elevators. Raises the mantis arms while keeping the elevators down.
     */
    private void climberTransferState() {
        _climb.setMantisPosition(false);
        _climb.actionMoveTo(_climb.getClimbHeight());
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
        // if((_controls.getButtonPressVision() == true) && (_vision.getDataIsValid())) {
        //     robotState = States.VISION;
        // }
    }

    private void trHab() {
        if((_controls.getButtonPressSyncClimb() || _controls.getButtonPressSyncLev2Climb())){
            robotState = States.HAB;
        }
    }

    private void trTeleop() {
        if (_controls.getPressHatchMode() || _controls.getPressBallMode()) {
            robotState = States.TELEOP;
        }
    }



    public void autoClimb() {
        /*
        newTime = System.currentTimeMillis();
        position = _climb.getPosition();
        if(_controls.getButtonPress6()) {
            oldTime = System.currentTimeMillis();
            finished = false;    
        }      
        if (finished == false){
            if(newTime - oldTime < 0.5 * 1000){
                if(doOne == false){
                    climberState = ElevatorStates.RISE;
                    doOne = true;
                }
            }
            if(newTime - oldTime >=3.5 * 1000 && newTime - oldTime < 4 * 1000){
                if(doTwo == false){
                    doTwo = true;
                    _drive.teleopDrive(0.2, 1, 1);
                }    
            }
            if(newTime - oldTime >=4 * 1000 && newTime - oldTime < 4.5 * 1000){
                if(doThree == false){
                    doThree = true;
                    climberState = ElevatorStates.TRANSITION;
                }    
            }
            if(newTime - oldTime >=4.5 * 1000 && newTime - oldTime < 5 * 1000){
                if(doFour == false){
                    doFour = true;
                    _drive.teleopDrive(0.2, 1, 1);
                }    
            }
            if(newTime - oldTime >=5 * 1000 && newTime - oldTime < 5.5 * 1000){
                if(doFive == false){
                    doFive = true;
                    _drive.teleopDrive(-0.1, 1, 1);
                }    
            }
            if(newTime - oldTime >=5.5 * 1000 && newTime - oldTime < 7.5 * 1000){
                if(doSix == false){
                    doSix = true;
                    climberState = ElevatorStates.RETRACT;
                }    
            }
            if(newTime - oldTime >=7.5 * 1000 && newTime - oldTime < 8 * 1000){
                if(doSeven == false){
                    doSeven = true;
                    _drive.teleopDrive(0.2, 1, 1);
                }    
            }
            if(newTime - oldTime >= 8 * 1000){
                finished = true;
                doOne = false;
                doTwo = false;
                doThree = false;
                doFour = false;
                doFive = false;
                doSix = false;
                doSeven = false;
                }  
            }
            */
        }
        
    
    

    //States
    public void callDrive() {
        if (_controls.getPressSwitchDriveDirection()) {
            _drive.setReversed();
        }
        if ((robotState != States.HAB) || (robotState != States.VISION)){
            _drive.teleopDrive(_controls.getDrivePower(), _controls.getDriveSteering(), _controls.getDriveThrottle());
        }
    }
        /*
        if (_controls.getButtonVision()) {
            double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
            double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
            double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
            double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
            if(tv < 1){
                SmartDashboard.putString("Climber State", climberState.name());
                limeLightDriveCommand = 0.0;
                limeLightSteerCommand = 0.0;
            }
            else{
                limeLightSteerCommand = tx * Constants.kVisionSteer;
                limeLightDriveCommand = (Constants.kVisionDesiredArea - ta) * Constants.kVisionDrive;
                if (limeLightDriveCommand > Constants.kVisionMaxDrive){
                    limeLightDriveCommand = Constants.kVisionMaxDrive;
                }
                _drive.arcadeDrive(limeLightDriveCommand, limeLightSteerCommand, _controls.getDriveThrottle());
            }   
                //_drive.arcadeDrive(_controls.getDrivePower(), _vision.getAngle() * Constants.kVisionServoingGain * (_controls.getDrivePower() + 0.1), _controls.getDriveThrottle());
        }
    }
    */

    /**
     * Runs the vision assist code for the driver. DOES NOT UPDATE VISION TARGETING VALUES
     */
    public void stVision() {
        //I've got my i on you
        // double adjustment = _controls.getDriveSteering() * Constants.kVisionDriverAdjustmentGain;  // Adds a small amount to the target angle so the driver can adjust side to side
        // double targetAngle = 0.0;
        // if (_vision.getDataIsValid()) {
        //     _vision.getAngle();
        // } else {
        //     targetAngle = 0.0;
        // }
        // targetAngle += adjustment;

        // double steering = Constants.kVisionServoingGain * Math.sqrt(Math.abs(targetAngle));
        // if (targetAngle < 0) {
        //     steering *= -1;
        // }

        // _drive.teleopDrive(_controls.getDrivePower(), steering, _controls.getDriveThrottle());
    }

    public void stHab() {
        cargoWristAngleState = CargoWristAngleStates.STOWED;

        _climb.setMotors(_controls.getDrivePower());
        _drive.teleopDrive(_controls.getDrivePower(), _controls.getDriveSteering(), _controls.getDriveThrottle());

        
        if (_controls.getStowBobcat()){
            bobcatState = BobcatStates.STOWED;
        }
        if (_controls.getPressXButton()){
            bobcatState = BobcatStates.CLIMB_ANGLE;
        }
        // Zero the joints
        if (_controls.getButtonPressResetBobcat()) {
            _bobcat.resetSensors();
        }
        if (_controls.getButtonPressResetCargoWrist()) {
            _cargo.resetSensors();
        }

        // Run manual mode
        if (_controls.getManualBobcatMode()) {
            bobcatState = BobcatStates.MANUAL;
        }
        if (_controls.getManualCargoWristMode()) {
            cargoWristAngleState = CargoWristAngleStates.MANUAL;
        }
        
        // Climb
        if (_controls.getButtonReleaseSyncClimb()) {
            climberState = ElevatorStates.RISE;

        } else if (_controls.getButtonReleaseSyncLev2Climb()) {
            climberState = ElevatorStates.LEV2_RISE;

        } else if (_controls.getButtonPressMantisRetract()) {
            climberState = ElevatorStates.TRANSITION;

        }
        if (_controls.getButtonPressElevatorRetract()){
            climberState = ElevatorStates.RETRACT;
        }

        // if (_controls.stick.getRawButton(12)) {
        //     climberState = ElevatorStates.MANUAL;
        //     _climb.setElevatorMotors(1);
        // } else if (_controls.stick.getRawButton(11)) {
        //     climberState = ElevatorStates.MANUAL;
        //     _climb.setElevatorMotors(-1);
        // } else if (climberState == ElevatorStates.MANUAL) {
        //     _climb.setElevatorMotors(0);
        // }

        SmartDashboard.putString("Climber State", climberState.name());
    }

    public void setGamePieceMode() {
        if (_controls.getChangeGamePieceMode()){
            if (cargoMode == true){
                cargoMode = false;
                cargoWristAngleState = CargoWristAngleStates.STOWED;
            } else {
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
        callDrive();
        
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
                cargoWristAngleState = CargoWristAngleStates.MID;
            }
            if (_controls.getPressMidRocketPosition()){
                bobcatState = BobcatStates.MID_CARGO;
                cargoWristAngleState = CargoWristAngleStates.HIGH;
            }
            if (_controls.getPressHighRocketPosition()){
                bobcatState = BobcatStates.HIGH_CARGO;
                cargoWristAngleState = CargoWristAngleStates.HIGH;
            }
            if (_controls.getPressXButton()){
                bobcatState = BobcatStates.CARGOSHIP_CARGO;
                cargoWristAngleState = CargoWristAngleStates.HIGH;
            }

            if (_controls.getShootCargo()){
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
            _cargo.setIntakeMode(IntakeModesCargo.OFF);

            if (_controls.getPressLowRocketPosition()){
                bobcatState = BobcatStates.LOW_HATCH;
            }
            if (_controls.getPressMidRocketPosition()){
                bobcatState = BobcatStates.MID_HATCH;
            }
            if (_controls.getPressHighRocketPosition()){
                bobcatState = BobcatStates.HIGH_HATCH;
            }

            // Hatch beak toggle
            if (_controls.getTriggerPress()) {
                hatchState = HatchStates.POP;
            } else if (_controls.getTriggerRelease()) {
                hatchState = HatchStates.STOW_HATCH;
            } else if (_controls.getPressXButton()){
                if (_hatch.getBeakPosition()){
                    hatchState = HatchStates.STOW_HATCH;
                } else {
                    hatchState = HatchStates.HOLD_HATCH;
                }
            }
        }
        
        if (_controls.getStowBobcat()){
            bobcatState = BobcatStates.STOWED;
        }

        // Zero the joints
        if (_controls.getButtonPressResetBobcat()) {
            _bobcat.resetSensors();
        }
        if (_controls.getButtonPressResetCargoWrist()) {
            _cargo.resetSensors();
        }

        // Run manual mode
        if (_controls.getManualBobcatMode()) {
            cargoWristAngleState = CargoWristAngleStates.MANUAL;
            bobcatState = BobcatStates.MANUAL;
        }
    }

    /**
     * Set all the mechanim's sensor readings to 0 (without moving anything)
     */
    public void resetAllSensors() {  // TODO: Add elevators
        _drive.zeroPosition();
        _bobcat.resetSensors();
        _cargo.resetSensors();
    }

    public void matchStartConfig() {
        robotState = States.TELEOP;
        cargoWristAngleState = CargoWristAngleStates.STOWED;
        bobcatState = BobcatStates.STOWED;
        hatchState = HatchStates.HOLD_HATCH;
        climberState = ElevatorStates.STOWED;
    }
}
