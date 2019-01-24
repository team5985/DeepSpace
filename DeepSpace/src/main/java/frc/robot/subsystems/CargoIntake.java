package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import frc.lib.SquareRootControl;
import frc.robot.Constants;

public class CargoIntake extends Subsystem {
    private WPI_TalonSRX wristMotor;
    private double feedBack = 0;
    private double angle = 0;
    public SquareRootControl wristMotorControl;
    private double velocity = 0;
    private double stowedAngle = 0;
    private double highAngle = 60;
    private double midAngle = 90;
    private double lowAngle = 120;

    public enum IntakePositions {
        STOWED,
        HIGH,
        MID,
        LOW,
    }

//**set to angles 30 degrees down, 30 degrees up, centre and stowed for picking up balls */
    public boolean actionMoveTo(IntakePositions position) {
        switch (position){
            case STOWED:
                setPosition(0);
                if (stowedAngle >= getPosition() - 2 && stowedAngle <= getPosition() + 2){
                    return true;
                } else {
                    return false;
                }
            case HIGH:
                setPosition(60);
                if (highAngle >= getPosition() - 2 && highAngle <= getPosition() + 2){
                    return true;
                } else {
                    return false;
                }
            case MID:
                setPosition(90);
                if (midAngle >= getPosition() - 2 && midAngle <= getPosition() + 2){
                    return true;
                } else {
                    return false;
                }
            case LOW:
                setPosition(120);
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