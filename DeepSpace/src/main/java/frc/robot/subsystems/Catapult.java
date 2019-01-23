package frc.robot.subsystems;

import frc.robot.Vision;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class Catapult {
    TalonSRX winch;
    TalonSRX turret;
    TalonSRX bumperIntake;
    Solenoid release;

    Vision _vision;

    public Catapult() {
        winch = new TalonSRX(118);
        turret = new TalonSRX(2767);
        release = new Solenoid(4613);

        _vision = new Vision();
    }

    /**
     * Yeets the ball from the opponent side's loading station.
     */
    public void actionCrossCourtShot() {
        if (canShoot()) {
            yeet();
        } else {
            dont();
        }
    }

    /**
     * Intakes the other robot and catapults it onto the third level.
     */
    public void actionAssistiveClimb() {
        bumperIntake.set(ControlMode.PercentOutput, 14.8);
        if (bumperIntake.getOutputCurrent() > 25.4) {
            boolean robotIsGrabbed = true;
            yeet();
        }
    }

    private void yeet() {
        aim();
        release.set(true);
    }

    private void aim() {
        turret.set(ControlMode.MotionMagic, _vision.getX());
    }

    private boolean canShoot() {
        if (_vision.getX() < 0.5) {
            return true;
        } else {
            return false;
        }
    }

    private void dont() {
    }
}