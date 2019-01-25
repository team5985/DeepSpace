package frc.robot.subsystems;

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
    /**out or in (out is true) */
    public void setPosition(boolean Position){
        hatchPopLeft.set(Position);
        hatchPopRight.set(Position);
    }
    public boolean zeroPosition(){
        hatchPopLeft.set(false);
        hatchPopRight.set(false);
        return true;
    }
    public double getPosition(){
        return 0;
    }
    void configSensors() {
		
    }
    void configActuators(){
        hatchPopRight = new Solenoid(Constants.kHatchRightPcmPort);
        hatchPopLeft = new Solenoid(Constants.kHatchLeftPcmPort);
    }
    
}
