package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import frc.lib.Calcs;
import frc.lib.SquareRootControl;
import frc.robot.Constants;

public class CargoIntake extends Subsystem {
    private WPI_TalonSRX wristMotor;
    private VictorSP leftIntakeMotor;
    private VictorSP rightIntakeMotor;
    private DigitalInput hallEffect;

    private double feedBack = 0;
    public SquareRootControl wristMotorControl;
    private double stowedAngle = 0;
    private double highAngle = 60;
    private double midAngle = 90;
    private double lowAngle = 120;
    private double grabIntakePercent = 0.8;
    private double holdIntakePercent = 0.2;
    private double shootIntakePercent = 1;
    public static CargoIntake cargoInstance;

    public enum IntakePositionsCargo {
        STOWED,
        HIGH,
        MID,
        LOW,
    }

    public enum IntakeModesCargo {
        OFF,
        GRAB,
        HOLD,
        SHOOT,
    }

    public static CargoIntake getInstance(){
        if(cargoInstance == null){
            cargoInstance = new CargoIntake();
        } 
        return cargoInstance;
    }

    private CargoIntake(){
        wristMotorControl = new SquareRootControl(Constants.kCargoWristMaxAccelerationDegrees, Constants.kCargoWristMaxSpeed, Constants.kCargoWristGain); 
        configActuators();
        configSensors();  
    }
    
    /**
     * Set to angles 30 degrees down, 30 degrees up, centre and stowed for picking up balls.
     * @param position
     * @return True when action completed.
     */
    public boolean actionMoveTo(IntakePositionsCargo position) {
        if (!hallEffect.get()) {
            wristMotor.setSelectedSensorPosition(0);
        }
        
        switch (position){
            case STOWED:
                return setAngle(stowedAngle);
            case HIGH:
                return setAngle(highAngle);
            case MID:
                return setAngle(midAngle);
            case LOW:
                return setAngle(lowAngle);
            default:
                return false;
        }
    }

    public boolean zeroPosition(){
        if (!hallEffect.get()) {  // When hall effect sensor is triggered
            wristMotor.setSelectedSensorPosition(0);
        }
        if (wristMotor.getSelectedSensorPosition() != 0) {
            wristMotor.set(ControlMode.PercentOutput, -0.2);
        } else {
            wristMotor.set(ControlMode.PercentOutput, 0.0);
            return true;
        }
        
        return false;
    }
    
	/**
	 * Runs cargo intake motors
	 */
    public void setIntakeMode(IntakeModesCargo mode){
        if (mode == IntakeModesCargo.OFF) {
            leftIntakeMotor.set(0);
            rightIntakeMotor.set(0);
        }
        if (mode == IntakeModesCargo.GRAB) {
            leftIntakeMotor.set(grabIntakePercent);
            rightIntakeMotor.set(grabIntakePercent);
        }
        if (mode == IntakeModesCargo.HOLD) {
            leftIntakeMotor.set(holdIntakePercent);
            rightIntakeMotor.set(holdIntakePercent);
        }
        if (mode == IntakeModesCargo.SHOOT) {
            leftIntakeMotor.set(shootIntakePercent);     //dont know which negative which positive
            rightIntakeMotor.set(shootIntakePercent);
        } else  {
            leftIntakeMotor.set(0);
            rightIntakeMotor.set(0);
        }
    }

	public double getPosition(){
        feedBack = wristMotor.getSelectedSensorPosition();   //1024 Pulses per rotation -  4 counts per pulse   - 4096
        return feedBack * Constants.kCuiCountsToDegrees;
    }

    public double changeDegreesToCounts(double degrees){
        return degrees / Constants.kCuiCountsToDegrees;
    }

    public boolean setAngle(double angle){
        double velocity = wristMotorControl.run(getPosition(), angle);
        double power = Constants.kCargoWristMaxSpeed / velocity;
        wristMotor.set(ControlMode.PercentOutput, power);
        return Calcs.isWithinThreshold(getPosition(), angle, Constants.kCargoWristAngleTolerance);
    }

    /**
     * Calculate the feedforward gain required to keep the intake level at steady state. Does not configure the Talon.
     * @return Units (-1:1)
     */
    // private double calculateHoldingFeedforward() {
    //     double horizDist = Constants.kIntakePhysicalLength * Math.sin(Constants.kIntakeStowedPhysicalAngle + getPosition());
    //     double gravityTorque = Constants.kIntakePhysicalWeight * horizDist;
    //     double feedforward = Constants.kIntakeWristMaxTorque / gravityTorque;
    //     return feedforward;
    // }

    void configActuators(){
        wristMotor = new WPI_TalonSRX(Constants.kTalonCargoWristCanId);
        wristMotor.setInverted(Constants.kWristMotorDirection);  //TODO: check
        
        wristMotor.configPeakCurrentLimit(0, 0);
        wristMotor.configContinuousCurrentLimit(20, 0);

        wristMotor.configPeakOutputForward(Constants.kWristMaxOutput);
        wristMotor.configPeakOutputReverse(Constants.kWristMaxOutput);

        leftIntakeMotor = new VictorSP(Constants.kVictorCargoIntakeLeftPwmPort);
        leftIntakeMotor.setInverted(Constants.kVictorCargoIntakeDirection);
        rightIntakeMotor = new VictorSP(Constants.kVictorCargoIntakeRightPwmPort);
        rightIntakeMotor.setInverted(!Constants.kVictorCargoIntakeDirection);
    } 
   
    void configSensors() {
        wristMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        wristMotor.setSensorPhase(Constants.kTalonCargoIntakeEncoderPhase); 

        hallEffect = new DigitalInput(Constants.kIntakeHallEffectPort);
    }
}   