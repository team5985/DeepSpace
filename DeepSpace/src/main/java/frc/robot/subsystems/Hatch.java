package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;
import frc.robot.DriverControls;

public class Hatch extends Subsystem {
    boolean extendBeak = true;
    DriverControls DriverControls = new DriverControls();
    Constants Constants = new Constants();
    public Hatch hatchInstance = null;
    Solenoid hatchPopLeft;
    Solenoid hatchPopRight;
    Solenoid beakSolenoid;
    
    public Hatch getInstance() {
        if (hatchInstance == null) {
            hatchInstance = new Hatch();
        }
        return hatchInstance;
    }

    private Hatch() {
        configActuators();
        configSensors();
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
        beakSolenoid = new Solenoid(Constants.kbeakSolenoidChannel);
    }
    
}
