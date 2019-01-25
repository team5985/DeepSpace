package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;

import frc.lib.SquareRootControl;
import frc.robot.Constants;

public class CargoIntake extends Subsystem {
    private WPI_TalonSRX wristMotor;
    private VictorSP leftIntakeMotor;
    private VictorSP rightIntakeMotor;
    private double feedBack = 0;
    private double angle = 0;
    public SquareRootControl wristMotorControl;
    private double velocity = 0;
    private double stowedAngle = 0;
    private double highAngle = 60;
    private double midAngle = 90;
    private double lowAngle = 120;
    private double grabIntakePercent = 0.8;
    private double holdIntakePercent = 0.2;
    private double shootIntakePercent = 1;

    public enum IntakePositions {
        STOWED,
        HIGH,
        MID,
        LOW,
    }
    public enum IntakeModes {
        OFF,
        GRAB,
        HOLD,
        SHOOT,
    }
    
    public boolean actionSetMode(IntakeModes mode){
        switch (mode){
            case OFF:
                setIntakeMode(mode);
                return true;
            case GRAB:
                setIntakeMode(mode);
                return true;
            case HOLD:
                setIntakeMode(mode);
                return true;
            case SHOOT:
                setIntakeMode(mode);
                return true;
            default:
                return false;
        }
    }
//**set to angles 30 degrees down, 30 degrees up, centre and stowed for picking up balls */
    public boolean actionMoveTo(IntakePositions position) {
        switch (position){
            case STOWED:
                setPosition(stowedAngle);
                if (stowedAngle >= getPosition() - 2 && stowedAngle <= getPosition() + 2){
                    return true;
                } else {
                    return false;
                }
            case HIGH:
                setPosition(highAngle);
                if (highAngle >= getPosition() - 2 && highAngle <= getPosition() + 2){
                    return true;
                } else {
                    return false;
                }
            case MID:
                setPosition(midAngle);
                if (midAngle >= getPosition() - 2 && midAngle <= getPosition() + 2){
                    return true;
                } else {
                    return false;
                }
            case LOW:
                setPosition(lowAngle);
                if (lowAngle >= getPosition() - 2 && lowAngle <= getPosition() + 2){
                    return true;
                } else {
                    return false;
                }
            default:
                return true;
        }
    }

    public boolean zeroPosition(){
		return false; //TODO: fix
	}
	/**
	 * returns z angle of Interial Measurement Unit
	 */
    public void setIntakeMode(IntakeModes mode){
        if (mode == IntakeModes.OFF) {
            leftIntakeMotor.set(0);
            rightIntakeMotor.set(0);
        }
        if (mode == IntakeModes.GRAB) {
            leftIntakeMotor.set(grabIntakePercent);
            rightIntakeMotor.set(grabIntakePercent);
        }
        if (mode == IntakeModes.HOLD) {
            leftIntakeMotor.set(holdIntakePercent);
            rightIntakeMotor.set(holdIntakePercent);
        }
        if (mode == IntakeModes.SHOOT) {
            leftIntakeMotor.set(shootIntakePercent);     //dont know which negative which positive
            rightIntakeMotor.set(shootIntakePercent);
        }
    }

    private CargoIntake(){
        wristMotorControl = new SquareRootControl(Constants.kCargoIntakeMaxAccelerationDegrees, Constants.kCargoIntakeMaxSpeed, Constants.kCargoIntakeGain);   
    }
	public double getPosition(){
        feedBack = wristMotor.getSelectedSensorPosition();   //1024 Pulses per rotation -  4 counts per pulse   - 4096
        return feedBack * Constants.kCountsToDegrees;
    }
    void configSensors() {
		wristMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		wristMotor.setSensorPhase(Constants.kTalonCargoIntakeEncoderPhase); 
    }
    void configActuators(){
        wristMotor = new WPI_TalonSRX(Constants.kTalonCargoWristCanId);
        leftIntakeMotor= new VictorSP(Constants.kVictorCargoIntakeLeftPwmPort);
        leftIntakeMotor.setInverted(true);
        rightIntakeMotor = new VictorSP(Constants.kVictorCargoIntakeRightPwmPort);
        rightIntakeMotor.setInverted(false);
    }
    public double changeDegreesToCounts(double degrees){
        return degrees / Constants.kCountsToDegrees;
    }
    public void setPosition(double angle){
        velocity = wristMotorControl.run(getPosition(), angle);
        wristMotor.set(ControlMode.Velocity, velocity);
    }
    


    //public void lowerArms(){
      //  velocity = wristMotorControl.run(getPosition(), 120);
        //wristMotor.set(ControlMode.Velocity, velocity);
    //}
    //public void raiseArms(){
      //  wristMotor.set(ControlMode.Position, 300);
   // }
}   