package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.lib.SquareRootControl;
import frc.robot.Constants;
import frc.robot.DriverControls;

public class Bobcat extends Subsystem {
    boolean hatchCollected = false;
    DriverControls DriverControls = new DriverControls();
    public SquareRootControl jointMotorControl;
    public WPI_TalonSRX jointMotor;
    private double velocity = 0;
    private double feedback = 0;
    private double lowAngleHatch = 10; //TODO: Test and change value
    private double midAngleHatch = 45; //TODO: Test and change value
    private double highAngleHatch = 90; //TODO: Test and change value
    private double lowAngleCargo = 10; //TODO: Test and change value
    private double midAngleCargo = 45; //TODO: Test and change value
    private double highAngleCargo = 90; //TODO: Test and change value
    private double stowedAngle = 0; //TODO: Test and change value
    public static Bobcat bobcatInstance;

    public Bobcat getInstance() {
        if (bobcatInstance == null) {
            bobcatInstance = new Bobcat();
        }
        return bobcatInstance;
    }
    private Bobcat(){
        jointMotorControl = new SquareRootControl(Constants.kBobcatJointMotorMaxAccelerationDegrees, Constants.kBobcatJointMotorMaxSpeed, Constants.kBobCatJointMotorGain);   
    }
    
    public void bobcatPickup() {
        if (DriverControls.getButtonPressBobcatUp()) {
            if (getPosition() > 2 || getPosition() < 2) {
                actionMoveTo(IntakePositions.DOWN);
            }
            setAngle(stowedAngle + 1);
            actionMoveTo(IntakePositions.LOW_HATCH);
        }
    }   

    public enum IntakePositions {
        DOWN,
        HIGH_HATCH,
        MID_HATCH,
        LOW_HATCH,
        HIGH_CARGO,
        MID_CARGO,
        LOW_CARGO,
    }
    public boolean actionMoveTo(IntakePositions positions){
        switch(positions){
            case DOWN:
                setAngle(stowedAngle);
                if (stowedAngle >= getPosition() - 2 && stowedAngle <= getPosition() + 2) {
                    boolean returnValue = true;
                    return returnValue;
                } else {
                    boolean returnValue = false;
                    return returnValue;
                }
            case LOW_HATCH:
                setAngle(lowAngleHatch);
                if (lowAngleHatch >= getPosition() - 2 && lowAngleHatch <= getPosition() + 2) {
                    boolean returnValue = true;
                    return returnValue;
                } else {
                    boolean returnValue = false;
                    return returnValue;
                }
            case MID_HATCH:
                setAngle(midAngleHatch);
                if (midAngleHatch >= getPosition() - 2 && midAngleHatch <= getPosition() + 2) {
                    boolean returnValue = true;
                    return returnValue;
                } else {
                    boolean returnValue = false;
                    return returnValue;
                }
            case HIGH_HATCH:
                setAngle(highAngleHatch);
                    if (highAngleHatch >= getPosition() - 2 && highAngleHatch <= getPosition() + 2) {
                        boolean returnValue = true;
                        return returnValue;
                    } else {
                        boolean returnValue = false;
                        return returnValue;
                    }
            case LOW_CARGO:
                setAngle(lowAngleCargo);
                if (lowAngleCargo >= getPosition() - 2 && lowAngleCargo <= getPosition() + 2) {
                    boolean returnValue = true;
                    return returnValue;
                } else {
                    boolean returnValue = false;
                    return returnValue;
                }
            case MID_CARGO:
                setAngle(midAngleCargo);
                if (midAngleCargo >= getPosition() - 2 && midAngleCargo <= getPosition() + 2) {
                    boolean returnValue = true;
                    return returnValue;
                } else {
                    boolean returnValue = false;
                    return returnValue;
                }
            case HIGH_CARGO:
                setAngle(highAngleCargo);
                    if (highAngleCargo >= getPosition() - 2 && highAngleCargo <= getPosition() + 2) {
                        boolean returnValue = true;
                        return returnValue;
                    } else {
                        boolean returnValue = false;
                        return returnValue;
                    }
            default:
            boolean returnValue = false;
            return returnValue;
        }
    }

    public void setAngle(double desiredAngle){
        velocity = jointMotorControl.run(getPosition(), desiredAngle);
        jointMotor.set(ControlMode.Velocity, velocity);
    }
    void configSensors() {
		jointMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		jointMotor.setSensorPhase(Constants.kTalonBobcatJointEncoderPhase); 
    }
    void configActuators() {
		jointMotor = new WPI_TalonSRX(Constants.kTalonBobcatJointCanId);
    }
    public boolean zeroPosition(){
        return false;
    }
    public double getPosition(){ //angle
        feedback = jointMotor.getSelectedSensorPosition();
        return feedback * Constants.kCountsToDegrees;
    }
}