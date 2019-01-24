package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.lib.SquareRootControl;
import frc.robot.Constants;

public class CargoIntake extends Subsystem {
    private WPI_TalonSRX wristMotor;
    private double feedBack = 0;
    private double angle = 0;
    public SquareRootControl wristMotorControl;
    double velocity = 0;

    public enum IntakePositions {
        STOWED,
        HIGH,
        MID,
        LOW,
    }

    public boolean actionMoveTo(IntakePositions position) {
        switch (position){
            case STOWED:
                setPosition(0);
                if (0 >= getPosition() - 2 && 0 <= getPosition() + 2){
                    return true;
                } else {
                    return false;
                }
            case HIGH:
                setPosition(60);
                if (60 >= getPosition() - 2 && 60 <= getPosition() + 2){
                    return true;
                } else {
                    return false;
                }
            case MID:
                setPosition(90);
                if (90 >= getPosition() - 2 && 90 <= getPosition() + 2){
                    return true;
                } else {
                    return false;
                }
            case LOW:
                setPosition(120);
                if (120 >= getPosition() - 2 && 120 <= getPosition() + 2){
                    return true;
                } else {
                    return false;
                }
            default:
                return true;
        }
    }

    public boolean zeroPosition(){
		return false;
	}
	/**
	 * returns z angle of Interial Measurement Unit
	 */
    private CargoIntake(){
        wristMotorControl = new SquareRootControl(Constants.kCargoIntakeMaxAccelerationDegrees, Constants.kCargoIntakeMaxSpeed, Constants.kCargoIntakeGain);   
    }
	public double getPosition(){
        feedBack = wristMotor.getSelectedSensorPosition();   //1024 Pulses per rotation -  4 counts per pulse   - 4096
        angle = feedBack * 0.087890625;
        return angle;
    }
    void configSensors() {
		wristMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		wristMotor.setSensorPhase(Constants.kTalonCargoIntakeEncoderPhase); 
    }
    void configActuators(){
        wristMotor = new WPI_TalonSRX(Constants.kTalonCargoIntakeCanId);
    }
    public double changeDegreesToCounts(double degrees){
        return degrees / 0.087890625;
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