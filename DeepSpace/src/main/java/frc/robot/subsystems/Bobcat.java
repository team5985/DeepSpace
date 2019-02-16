package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.Calcs;
import frc.lib.SquareRootControl;
import frc.robot.Constants;
import frc.robot.DriverControls;

public class Bobcat extends Subsystem {
    boolean hatchCollected = false;
    DriverControls driverControls = new DriverControls();
    private SquareRootControl jointMotorControl;
    private WPI_TalonSRX jointMotor;
    private Encoder jointEncoder;
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
        jointMotorControl = new SquareRootControl(Constants.kBobcatJointMotorMaxAccelerationDegrees, Constants.kBobcatJointMotorMaxSpeed, Constants.kBobcatJointMotorGain); 
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
            jointEncoder.reset();
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
        double power = velocity / Constants.kBobcatJointMotorMaxSpeed;
        jointMotor.set(power);
        SmartDashboard.putNumber("Bobcat Power", power);
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
            jointEncoder.reset();
            jointMotor.set(0.0);
            return true;
        } else {
            jointMotor.set(-0.1);
            return false;
        }
    }

    void configActuators() {
        jointMotor = new WPI_TalonSRX(Constants.kTalonBobcatJointCanId);
        jointMotor.configFactoryDefault();
        jointMotor.setInverted(Constants.kBobcatJointDirection);  //TODO: check
        
        // jointMotor.setSmartCurrentLimit(30);
        jointMotor.enableCurrentLimit(false);

        // jointMotor.setRampRate(Constants.kBobcatJointRampRate);
        jointMotor.configOpenloopRamp(Constants.kBobcatJointRampRate);
        jointMotor.configPeakOutputForward(Constants.kBobcatJointMaxOutput);
        jointMotor.configPeakOutputReverse(-Constants.kBobcatJointMaxOutput);
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
        return feedback * Constants.kCuiCountsToDegrees;
    }
}