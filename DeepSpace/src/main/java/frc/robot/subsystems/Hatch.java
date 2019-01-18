package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Hatch {
    public Hatch hatchInstance = null;

    public Hatch getInstance() {
        if (hatchInstance == null) {
            hatchInstance = new Hatch();
        }
        return hatchInstance;
    }

    WPI_TalonSRX wrist;

    private Hatch() {
        
    }
    public void getWristAngle() {
        
    }
}