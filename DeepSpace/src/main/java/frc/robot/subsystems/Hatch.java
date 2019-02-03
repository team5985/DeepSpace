package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;

public class Hatch extends Subsystem {
    boolean extendBeak = true;
    
    public static Hatch hatchInstance = null;

    Solenoid hatchPopLeft;
    Solenoid hatchPopRight;
    Solenoid beakSolenoid;
    
    public static Hatch getInstance() {
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
    public void setPosition(boolean position){
        hatchPopLeft.set(position);
        hatchPopRight.set(position);
    }

    /**
     * Sets the beak to deploy or retract.
     * @param position True deploys.
     */
    public void moveBeak(boolean position){
        beakSolenoid.set(position);
        extendBeak = position;
    }

    public boolean getBeakPosition(){
        return extendBeak;
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
        beakSolenoid = new Solenoid(Constants.kBeakPlusCargoSolenoidChannel);
    }
}
