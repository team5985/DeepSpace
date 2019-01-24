package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.lib.SquareRootControl;
import frc.robot.Constants;

public class Bobcat extends Subsystem {
    public SquareRootControl jointMotorControl;
    public WPI_TalonSRX jointMotor;
    private double velocity = 0;
    private double feedback = 0;
    private double lowAngle = 0;
    private double midAngle = 0;
    private double highAngle = 0;
    private double stowedAngle = 0;
    private Bobcat(){
        jointMotorControl = new SquareRootControl(Constants.kBobcatJointMotorMaxAccelerationDegrees, Constants.kBobcatJointMotorMaxSpeed, Constants.kBobCatJointMotorGain);   
    }

    public enum IntakePositions {
        DOWN,
        HIGH,
        MID,
        LOW,
    }
    public boolean actionMoveTo(IntakePositions positions){
        switch(positions){
            case DOWN:
                setAngle(stowedAngle);
                if (stowedAngle >= getPosition() - 2 && stowedAngle <= getPosition() + 2){
                    return true;
                } else{
                    return false;
                }
            case LOW:
                setAngle(lowAngle);
                if (lowAngle >= getPosition() - 2 && lowAngle <= getPosition() + 2){
                    return true;
                } else{
                    return false;
                }
            case MID:
                setAngle(0);
                if (midAngle >= getPosition() - 2 && midAngle <= getPosition() + 2){
                    return true;
                } else{
                    return false;
                }
            case HIGH:
                setAngle(0);
                    if (highAngle >= getPosition() - 2 && highAngle <= getPosition() + 2){
                        return true;
                    } else{
                        return false;
                    }
            default:
                return false;
        }
    }

    public void setAngle(double desiredAngle){
        velocity = jointMotorControl.run(getPosition(), desiredAngle);
        jointMotor.set(ControlMode.Velocity, velocity);
    }
    void configSensors() {
		jointMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		jointMotor.setSensorPhase(Constants.kTalonBobcatJointEncoderPhase); 
    }
    void configActuators() {
		jointMotor = new WPI_TalonSRX(Constants.kTalonBobcatJointCanId);
    }
    public boolean zeroPosition(){
        return false;
    }
    public double getPosition(){ //angle
        feedback = jointMotor.getSelectedSensorPosition();
        return feedback * 0.087890625;
    }
}