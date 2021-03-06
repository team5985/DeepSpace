package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.Calcs;
import frc.robot.Constants;
import frc.robot.DriverControls;

public class Bobcat extends Subsystem {
    boolean hatchCollected = false;
    DriverControls driverControls = new DriverControls();
    private CANSparkMax jointMotor;
    private Encoder jointEncoder;
    private DigitalInput hallEffect;

    private double lowAngleHatch = 8;
    private double midAngleHatch = 55;
    private double highAngleHatch = 92;

    private double lowAngleCargo = 36;
    private double midAngleCargo = 70;
    private double highAngleCargo = 105;
    private double cargoShipCargoAngle = 48;

    private double climbAngle = 38;

    private double stowedAngle = 0;
    public static Bobcat bobcatInstance;

    public static Bobcat getInstance() {
        if (bobcatInstance == null) {
            bobcatInstance = new Bobcat();
        }
        return bobcatInstance;
    }

    private Bobcat(){
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
        CARGOSHIP_BALL_POSITION,
        CLIMB_ANGLE,
    }

    public boolean actionMoveTo(ArmPositions positions){
        if (!hallEffect.get()) {
            jointEncoder.reset();
        }

        switch(positions){
            case DOWN:
                return setAngle(stowedAngle, false);

            case LOW_HATCH:
                return setAngle(lowAngleHatch, true);

            case MID_HATCH:
                return setAngle(midAngleHatch, true);

            case HIGH_HATCH:
                return setAngle(highAngleHatch, true);

            case LOW_CARGO:
                return setAngle(lowAngleCargo, true);

            case MID_CARGO:
                return setAngle(midAngleCargo, true);

            case HIGH_CARGO:
                return setAngle(highAngleCargo, true);

            case CARGOSHIP_BALL_POSITION:
                return setAngle(cargoShipCargoAngle, true);

            case CLIMB_ANGLE:
                return setAngle(climbAngle, true);

            default:
                return false;
        }
    }

    /**
     * Runs the square root controller to the desired angle.
     * @param desiredAngle In degrees, starting from bottom and increasing as the arm goes up.
     * @param useHoldingFf Enables / disables the calculations for holding power. (see calculateHoldingFeedforward()) (deprecated)
     * @return True if the arm is within a certain tolerance of the desired angle.
     */
    public boolean setAngle(double desiredAngle, boolean useHoldingFf){
        // double velocity = jointMotorControl.run(getPosition(), desiredAngle);
        // double power = velocity / Constants.kBobcatJointMotorMaxSpeed;

        double power = (desiredAngle - getPosition()) * Constants.kBobcatJointPGain;

        // if (useHoldingFf) {
        //     power += calculateHoldingFeedforward();
        // }

        
        
        manualMove(power);

        SmartDashboard.putNumber("Bobcat Target", desiredAngle);
        SmartDashboard.putNumber("Bobcat Encoder", getPosition());
        SmartDashboard.putNumber("Bobcat Power", power);
        return Calcs.isWithinThreshold(getPosition(), desiredAngle, Constants.kBobcatJointAngleTolerance);
    }

    /**
     * Manually set the power of the bobcat. +1 goes up, -1 goes down.
     * @param power
     */
    public void manualMove(double power) {
        if (power < Constants.kBobcatJointMaxDownwardsOutput) {
            power = Constants.kBobcatJointMaxDownwardsOutput;
            if (getPosition() <3) {
                power = 0.0;
            }
        } else if (power > Constants.kBobcatJointMaxOutput) {
            power = Constants.kBobcatJointMaxOutput;
        }

        jointMotor.set(power);
    }

    /**
     * Calculate the feedforward gain required to keep the arm level at steady state. Does not configure the Talon.
     * @return Units (-1:1)
     */
    private double calculateHoldingFeedforward() {
        double horizDist = Constants.kBobcatPhysicalLength * Math.sin(Math.toRadians(Constants.kBobcatStowedPhysicalAngle + getPosition()));
        double gravityTorque = Constants.kBobcatPhysicalWeight * horizDist;
        double feedforward = gravityTorque / Constants.kBobcatJointMaxTorque;
        return feedforward;
    }

    /**
     * Drives the arm downwards slowly until the hall effect sensor gets triggered.
     * @return True when zeroed.
     */
    public boolean zeroPosition(){
        if (!hallEffect.get()) {  // When hall effect sensor is triggered
            jointEncoder.reset();
            jointMotor.set(0.0);
            return true;
        } else {
            jointMotor.set(-0.1);
            return false;
        }
    }

    public void resetSensors() {
        jointEncoder.reset();
    }

    void configActuators() {
        jointMotor = new CANSparkMax(Constants.kTalonBobcatJointCanId, MotorType.kBrushless);
        jointMotor.setIdleMode(IdleMode.kBrake);
        // jointMotor.configFactoryDefault();
        jointMotor.setInverted(Constants.kBobcatJointDirection);
        
        jointMotor.setSmartCurrentLimit(40);
        // jointMotor.enableCurrentLimit(false);

        jointMotor.setOpenLoopRampRate(Constants.kBobcatJointRampRate);
        // jointMotor.configOpenloopRamp(Constants.kBobcatJointRampRate);
        // jointMotor.configPeakOutputForward(Constants.kBobcatJointMaxOutput);
        // jointMotor.configPeakOutputReverse(0.0);
    }

    void configSensors() {
        hallEffect = new DigitalInput(Constants.kBobcatHallEffectPort);
        jointEncoder = new Encoder(Constants.kBobcatEncoderPortA, Constants.kBobcatEncoderPortB, Constants.kBobcatJointEncoderPhase, EncodingType.k4X);
    }
    
    /**
     * Returns the angle of the bobcat.
     * @return Angle in degrees, bottom of movement is 0.
     */
    public double getPosition(){
        double feedback = jointEncoder.getRaw();
        return (feedback / 8192) * 360;
    }
}