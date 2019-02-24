package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.Calcs;
import frc.robot.Constants;

public class CargoIntake extends Subsystem {
    private WPI_TalonSRX wristMotor;
    private VictorSP leftIntakeMotor;
    private VictorSP rightIntakeMotor;
    private DigitalInput hallEffect;

    private double feedBack = 0; // 4096 cpr
    private double stowedAngle = 0;
    private double highAngle = 60;
    private double midAngle = 90;
    private double lowAngle = 115;
    private double grabIntakePercent = 0.3;
    private double holdIntakePercent = 0.0;
    private double shootIntakePercent = -1;
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
        } else if (mode == IntakeModesCargo.GRAB) {
            leftIntakeMotor.set(grabIntakePercent);
            rightIntakeMotor.set(grabIntakePercent);
        } else if (mode == IntakeModesCargo.HOLD) {
            leftIntakeMotor.set(holdIntakePercent);
            rightIntakeMotor.set(holdIntakePercent);
        } else if (mode == IntakeModesCargo.SHOOT) {
            leftIntakeMotor.set(shootIntakePercent);
            rightIntakeMotor.set(shootIntakePercent);
        } else  {
            leftIntakeMotor.set(0);
            rightIntakeMotor.set(0);
        }
    }

	public double getPosition(){
        feedBack = wristMotor.getSelectedSensorPosition();   //1024 Pulses per rotation -  4 counts per pulse   - 4096
        return (feedBack / 4096) * 360;
    }

    public double changeDegreesToCounts(double degrees){
        return degrees / Constants.kCuiCountsToDegrees;
    }

    public boolean setAngle(double angle){
        // double velocity = wristMotorControl.run(getPosition(), angle);
        // double power = velocity / Constants.kCargoWristMaxSpeed;

        double power = (angle - getPosition()) * Constants.kWristPGain;
        power -= calculateHoldingFeedforward();

        if ((getPosition() < 15) && (angle < 15)) {
            power = -0.1;
        }
        
        wristMotor.set(ControlMode.PercentOutput, power);

        // SmartDashboard.putNumber("CargoIntake Velocity", velocity);
        SmartDashboard.putNumber("CargoIntake Encoder", getPosition());
        SmartDashboard.putNumber("CargoIntake Set Angle", angle);
        SmartDashboard.putNumber("CargoIntake Power", power);

        return Calcs.isWithinThreshold(getPosition(), angle, Constants.kCargoWristAngleTolerance);
    }

    /**
     * Calculate the feedforward gain required to keep the intake level at steady state. Does not configure the Talon.
     * @return Units (-1:1)
     */
    private double calculateHoldingFeedforward() {
        double horizDist = Constants.kIntakePhysicalLength * Math.sin(Math.toRadians(Constants.kIntakeStowedPhysicalAngle + getPosition()));
        double gravityTorque = Constants.kIntakePhysicalWeight * horizDist;
        double feedforward = gravityTorque / Constants.kIntakeWristMaxTorque;
        return feedforward;
    }

    void configActuators(){
        wristMotor = new WPI_TalonSRX(Constants.kTalonCargoWristCanId);
        wristMotor.setInverted(Constants.kWristMotorDirection);  //TODO: check
        wristMotor.setNeutralMode(NeutralMode.Brake);
        
        wristMotor.configPeakCurrentLimit(0, 0);
        wristMotor.configContinuousCurrentLimit(30, 0);

        wristMotor.configPeakOutputForward(Constants.kWristMaxOutput);
        wristMotor.configPeakOutputReverse(-Constants.kWristMaxOutput);

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