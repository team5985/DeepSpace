package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.lib.SquareRootControl;
import frc.robot.Constants;
import frc.robot.DriverControls;

public class Bobcat extends Subsystem {
    boolean hatchCollected = false;
    DriverControls driverControls = new DriverControls();
    private SquareRootControl jointMotorControl;
    private WPI_TalonSRX jointMotor;
    private DigitalInput hallEffect;

    private double velocity = 0;
    private double feedback = 0;
     //TODO: Test and change value below
    private double lowAngleHatch = 10;
    private double midAngleHatch = 45;
    private double highAngleHatch = 90;
    private double lowAngleCargo = 10;
    private double midAngleCargo = 45;
    private double highAngleCargo = 90;
    private double cargoShipCargoAngle = 20;
    private double stowedAngle = 0;
    public static Bobcat bobcatInstance;

    public static Bobcat getInstance() {
        if (bobcatInstance == null) {
            bobcatInstance = new Bobcat();
        }
        return bobcatInstance;
    }
    private Bobcat(){
        jointMotorControl = new SquareRootControl(Constants.kBobcatJointMotorMaxAccelerationDegrees, Constants.kBobcatJointMotorMaxSpeed, Constants.kBobCatJointMotorGain); 
        configActuators();
        configSensors();  
    }
    
    public enum ArmPositions {
        DOWN,
        HIGH_HATCH,
        MID_HATCH,
        LOW_HATCH,
        HIGH_CARGO,
        MID_CARGO,
        LOW_CARGO,
        CARGOSHIP_BALL_POSITION;
    }

    public boolean actionMoveTo(ArmPositions positions){
        switch(positions){
            case DOWN:
                setAngle(stowedAngle);
                if (stowedAngle >= getPosition() - 2 && stowedAngle <= getPosition() + 2) {
                    return true;
                } else {
                    return false;
                }
            case LOW_HATCH:
                setAngle(lowAngleHatch);
                if (lowAngleHatch >= getPosition() - 2 && lowAngleHatch <= getPosition() + 2) {
                    return true;
                } else {
                    return false;
                }
            case MID_HATCH:
                setAngle(midAngleHatch);
                if (midAngleHatch >= getPosition() - 2 && midAngleHatch <= getPosition() + 2) {
                    return true;
                } else {
                    return false;
                }
            case HIGH_HATCH:
                setAngle(highAngleHatch);
                    if (highAngleHatch >= getPosition() - 2 && highAngleHatch <= getPosition() + 2) {
                        return true;
                    } else {
                        return false;
                    }
            case LOW_CARGO:
                setAngle(lowAngleCargo);
                if (lowAngleCargo >= getPosition() - 2 && lowAngleCargo <= getPosition() + 2) {
                    return true;
                } else {
                    return false;
                }
            case MID_CARGO:
                setAngle(midAngleCargo);
                if (midAngleCargo >= getPosition() - 2 && midAngleCargo <= getPosition() + 2) {
                    return true;
                } else {
                    return false;
                }
            case HIGH_CARGO:
                setAngle(highAngleCargo);
                    if (highAngleCargo >= getPosition() - 2 && highAngleCargo <= getPosition() + 2) {
                        return true;
                    } else {
                        return false;
                    }
            case CARGOSHIP_BALL_POSITION:
                setAngle(cargoShipCargoAngle);
                if (cargoShipCargoAngle >= getPosition() - 2 && cargoShipCargoAngle <= getPosition() + 2){
                    return true;
                } else{
                    return false;
                } 
            default:
            return false;
        }
    }

    public void setAngle(double desiredAngle){
        velocity = jointMotorControl.run(getPosition(), desiredAngle);
        jointMotor.set(ControlMode.Velocity, velocity);
    }

    /**
     * Drives the arm downwards slowly until the hall effect sensor gets triggered.
     * @return True when zeroed.
     */
    public boolean zeroPosition(){
        if (!hallEffect.get()) {  // When hall effect sensor is triggered
            jointMotor.setSelectedSensorPosition(0, 0, 0);
        }
        if (jointMotor.getSelectedSensorPosition() != 0) {
            jointMotor.set(ControlMode.PercentOutput, -0.2);
        } else {
            jointMotor.set(ControlMode.PercentOutput, 0.0);
            return true;
        }
        
        return false;
    }

    void configSensors() {
        hallEffect = new DigitalInput(Constants.kBobcatHallEffectPort);
        
		jointMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		jointMotor.setSensorPhase(Constants.kTalonBobcatJointEncoderPhase);
    }
    
    void configActuators() {
        jointMotor = new WPI_TalonSRX(Constants.kTalonBobcatJointCanId);
        jointMotor.config_kF(0, Constants.k);
    }
    
    public double getPosition(){ //angle
        feedback = jointMotor.getSelectedSensorPosition();
        return feedback * Constants.kCountsToDegrees;
    }
}