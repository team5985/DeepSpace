package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;

public class Hatch extends Subsystem {

    public Hatch hatchInstance = null;
    Solenoid hatchPopLeft;
    Solenoid hatchPopRight;
    
    public Hatch getInstance() {
        if (hatchInstance == null) {
            hatchInstance = new Hatch();
        }
        return hatchInstance;
    }

    private Hatch() {

    }
    public boolean zeroPosition(){
        
    }
    public double getPosition(){
        
    }
    void configSensors() {
		
    }
    void configActuators(){
        hatchPopRight = new Solenoid(Constants.kHatchRightPcmPort);
        hatchPopLeft = new Solenoid(Constants.kHatchLeftPcmPort);
    }
    
}
