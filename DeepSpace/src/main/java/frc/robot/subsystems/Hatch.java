package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants;

public class Hatch extends Subsystem {
    boolean extendBeak = false;
    
    public static Hatch hatchInstance = null;

    DoubleSolenoid popSolenoid;
    DoubleSolenoid beakSolenoid;
    
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
    public void setPosition(boolean beakPosition, boolean popPosition){
        if (popPosition) {
            popSolenoid.set(Value.kForward);
        } else {
            popSolenoid.set(Value.kReverse);
        }

        if (beakPosition) {
            beakSolenoid.set(Value.kForward);
        } else {
            beakSolenoid.set(Value.kReverse);
        }
        
    }

    /**
     * Sets the beak to deploy or retract.
     * @param position True deploys.
     */
    // public void moveBeak(boolean position){
    //     beakSolenoid.set(position);
    //     extendBeak = position;
    // }

    public boolean getBeakPosition(){
        if (beakSolenoid.get() == Value.kForward) {
            return true;
        } else {
            return false;
        }
    }

    public boolean zeroPosition(){
        setPosition(false, false);
        return true;
    }

    public double getPosition(){
        return 0;
    }

    void configSensors() {
    }

    void configActuators(){
        popSolenoid = new DoubleSolenoid(Constants.kPcmCanId, Constants.kHatchPopperForwardPcmPort, Constants.kHatchPopperReversePcmPort);
        beakSolenoid = new DoubleSolenoid(Constants.kPcmCanId, Constants.kBeakPlusCargoSolenoidChannel, 5);
    }
}
