package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.lib.Calcs;
import frc.lib.SquareRootControl;
import frc.robot.Constants;
import frc.robot.DriverControls;

public class Bobcat extends Subsystem {
    boolean hatchCollected = false;
    DriverControls driverControls = new DriverControls();
    private SquareRootControl jointMotorControl;
    private WPI_TalonSRX jointMotor;
    private DigitalInput hallEffect;

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
        if (!hallEffect.get()) {
            jointMotor.setSelectedSensorPosition(0);
        }

        switch(positions){
            case DOWN:
                return setAngle(stowedAngle);

            case LOW_HATCH:
                return setAngle(lowAngleHatch);

            case MID_HATCH:
                return setAngle(midAngleHatch);

            case HIGH_HATCH:
                return setAngle(highAngleHatch);

            case LOW_CARGO:
                return setAngle(lowAngleCargo);

            case MID_CARGO:
                return setAngle(midAngleCargo);

            case HIGH_CARGO:
                return setAngle(highAngleCargo);

            case CARGOSHIP_BALL_POSITION:
                return setAngle(cargoShipCargoAngle);

            default:
                return false;
        }
    }

    /**
     * Runs the square root controller to the desired angle.
     * @param desiredAngle In degrees, starting from bottom and increasing as the arm goes up.
     * @return True if the arm is within a certain tolerance of the desired angle.
     */
    public boolean setAngle(double desiredAngle){
        double velocity = jointMotorControl.run(getPosition(), desiredAngle);
        double power = Constants.kBobcatJointMotorMaxSpeed / velocity;
        jointMotor.set(ControlMode.PercentOutput, power);
        return Calcs.isWithinThreshold(getPosition(), desiredAngle, Constants.kBobcatJointAngleTolerance);
    }

    /**
     * Calculate the feedforward gain required to keep the arm level at steady state. Does not configure the Talon.
     * @return Units (-1:1)
     */
    // private double calculateHoldingFeedforward() {
    //     double horizDist = Constants.kBobcatPhysicalLength * Math.sin(Constants.kBobcatStowedPhysicalAngle + getPosition());
    //     double gravityTorque = Constants.kBobcatPhysicalWeight * horizDist;
    //     double feedforward = Constants.kBobcatJointMaxTorque / gravityTorque;
    //     return feedforward;
    // }

    /**
     * Drives the arm downwards slowly until the hall effect sensor gets triggered.
     * @return True when zeroed.
     */
    public boolean zeroPosition(){
        if (!hallEffect.get()) {  // When hall effect sensor is triggered
            jointMotor.setSelectedSensorPosition(0);
        }
        if (jointMotor.getSelectedSensorPosition() != 0) {
            jointMotor.set(ControlMode.PercentOutput, -0.2);
        } else {
            jointMotor.set(ControlMode.PercentOutput, 0.0);
            return true;
        }
        
        return false;
    }

    void configActuators() {
        jointMotor = new WPI_TalonSRX(Constants.kTalonBobcatJointCanId);
    }

    void configSensors() {
        hallEffect = new DigitalInput(Constants.kBobcatHallEffectPort);
        
		jointMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		jointMotor.setSensorPhase(Constants.kTalonBobcatJointEncoderPhase);
    }
    
    /**
     * Returns the angle of the bobcat.
     * @return Angle in degrees, bottom of movement is 0.
     */
    public double getPosition(){
        double feedback = jointMotor.getSelectedSensorPosition();
        return feedback * Constants.kCountsToDegrees;
    }
}