package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;

public class CargoIntake extends Subsystem {
    private WPI_TalonSRX wristMotor;
    private double feedBack = 0;
    private double angle = 0;
    public boolean zeroPosition(){
		return false;
	}
	/**
	 * returns z angle of Interial Measurement Unit
	 */
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
    public void lowerArms(){
        //if from start want to go - 120
        //if from up want to go - 60
    }
    public void raiseArms(){
        wristMotor.set(ControlMode.Position, 300);
    }
}   