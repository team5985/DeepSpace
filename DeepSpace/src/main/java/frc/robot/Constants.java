package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

public class Constants {
    // CAN IDs
    public static final int kRightDriveACanId = 1;
    public static final int kRightDriveBCanId = 2;

    public static final int kLeftDriveACanId = 3;
    public static final int kLeftDriveBCanId = 4;

    public static final int kTalonElevatorMasterCanId = 5;   //Also elevator encoder
    public static final int kTalonElevatorSlaveCanId = 6;
    
    public static final int kTalonCargoWristCanId = 4;
    
public static final int kTalonBobcatJointCanId = 7; //for raising and lowering hatch

    public static final int kPcmCanId = 61;

    // RoboRIO DIO Ports
    public static final int kDriveLeftEncoderAPort = 0;
    public static final int kDriveLeftEncoderBPort = 1;
    public static final int kDriveRightEncoderAPort = 2;
    public static final int kDriveRightEncoderBPort = 3;

    public static final int kBobcatHallEffectPort = 4;
    public static final int kIntakeHallEffectPort = 5;
    
    public static final int kBobcatEncoderPortA = 6;
    public static final int kBobcatEncoderPortB = 7;
    
    // RoboRIO PWM Ports
    public static final int kVictorCargoIntakeLeftPwmPort = 0;
    public static final int kVictorCargoIntakeRightPwmPort = 1;
    public static final int kVictorMantisRightPwmPort = 2;
    public static final int kVictorMantisLeftPwmPort = 3;

    // PCM Solenoid Ports
    public static final int kHatchPopperForwardPcmPort = 0;
    public static final int kHatchPopperReversePcmPort = 1;
    public static final int kSolenoidMantisChannel = 3;
    public static final int kBeakPlusCargoSolenoidChannel = 4;

    // Driverstation Ports
    public static final int kJoystickPort = 0;
    public static final int kXboxPort = 1;

    // Encoder Constants
    public static final int kCuiEncoderPpr = 2048;  // Number of pulses per revolution of the encoder. Settable by the DIP switches on the CUI AMT-103, should be checked.
    public static final int kCuiEncoderCpr = kCuiEncoderPpr * 4;  // Number of counts per revolution of the encoder.
    public static final double kCuiCountsToDegrees = kCuiEncoderCpr / 360;

    // Drivetrain Constants
    public static IdleMode kDriveIdleMode = IdleMode.kCoast;

	public static final boolean kLeftDriveEncoderPhase = false; // false = not inverted, true = inverted
    public static final boolean kRightDriveEncoderPhase = false; // TODO: Check this

	public static final boolean kLeftDriveMotorPhase = false; // false = not inverted, true = inverted

    public static double kDriveMaxRotationalAccel = 0.0;  // TODO: Test this
    public static double kDriveMaxRotationalVel = 0.0;
    public static double kDriveGyroTurnKf = 1 / kDriveMaxRotationalVel;  // Feedforward for power / turn rate
    public static double kDriveGyroTurnKp = 0.0;  // Compensation for error
    public static double kDriveGyroTurnGain = 1.0;  //TODO: K gain for square root controller.
    public static double kDriveGyroTurnThresh = 3.0;  // In degrees.
    public static double kDriveGyroRateThresh = 3.0;  // In deg/s.
    
	public static final double kDrivePowerKf = 1 / 3.6;
    public static final double kPGainGyroDriveTurn = 0.008;  // In % per degree (Not square root controller!: proportional gain)  TODO: Test
    
	public static final double kDriveMaxDriveAccel = 3.29;  // m/s/s
	public static final double kDriveMaxDriveVel = 3.29;  // m/s
	public static final double kDriveEncoderDriveGain = 1.0;  // TODO: Used in square root controller

    public static double kDriveWheelDiameter = 6.0;
    public static final double kDriveEncoderDistancePerPulse = (kDriveWheelDiameter * Math.PI) / kCuiEncoderCpr;  // Metres per pulse
    
    public static final double kDriveTipThreshold = 25.0;  // Degrees before activating tip protection
    public static final double kDriveTipCorrectionPower = 0.4;  // Amount of power to drive when running tip protection.

    // Climber constants
    // public static final double kElevatorHoldingPower = 0.17;  // TODO: Check all of these
	// public static final double kElevatorLiftFeedforward = 1.0;
	public static final double kElevatorLiftDistGain = 10;  // TODO:
	public static final double kElevatorTiltCompGain = 0.05;  // TODO: Motor controller units (-1:1) per degree  
    public static final double kElevatorHeightTolerance = 0.05;  // Metres
    public static final double kElevatorClimbHeight = 0.4826;

    public static final double kElevatorSpoolDiam = 0.00762;  // In Metres!
    public static final double kElevatorDistancePerPulse = (Math.PI * Constants.kElevatorSpoolDiam) / Constants.kCuiEncoderCpr;  // Metres per pulse

	public static final double kElevatorMaxOutput = 0.0; // TODO
    public static final boolean kTalonElevatorDirection = false;
	public static final boolean kVictorMantisDirection = false;

    // Hatch Constants
	public static final int kHatchPopperDelay = 0;

    // CargoWrist Contants
    /**degrees per second per second */
    public static final double kCargoWristMaxAccelerationDegrees = 48.6;       // do calculations, placeholders (double max speed (not true value))
    public static final double kCargoWristMaxSpeed = 97.2;
    public static final double kCargoWristGain = 1.0;  // Square root controller gain TODO: 
    public static final double kCargoWristAngleTolerance = 2;
	public static final boolean kWristMotorDirection = true;
    public static final boolean kTalonCargoIntakeEncoderPhase = true; //TODO: Check

    public static double kWristMaxOutput = 0.0;  //TODO

    // Cargo Intake Constants
    public static final boolean kVictorCargoIntakeDirection = false;

    // Bobcat Constants
    public static final double kBobcatJointMotorMaxAccelerationDegrees = 300;       // do calculations, placeholders (double max speed (not true value))
    public static final double kBobcatJointMotorMaxSpeed = 169.2;  //different degree number to cargo + gearing
    public static final double kBobcatJointMotorGain = 1.0;  // Square root controller gain TODO: placeholder
    public static final double kBobcatJointKv = 1 / 169.2;  // deg/s^-1

    public static final double kBobcatJointAngleTolerance = 2;  // +/- Degrees

    public static final boolean kBobcatJointDirection = true;
    public static final boolean kBobcatJointEncoderPhase = true; //TODO: check     for bobcat raising and lowering
    
    public static final double kBobcatJointRampRate = 0; // Seconds to full power
	public static final double kBobcatJointMaxOutput = 0.0;  // TODO
    
    // Field measurements (angles are positive as getAutoDetectTargetCrossError() requires it)
	public static final double kVisionTargetSideNearAngle = 0;
	public static final double kVisionTargetSideLeftAngle = 90;
	public static final double kVisionTargetSideRightAngle = 270;
	public static final double kVisionTargetRocketFarLeftAngle = 208.75;
	public static final double kVisionTargetRocketFarRightAngle = 151.25;
	public static final double kVisionTargetRocketNearLeftAngle = 331.25;
    public static final double kVisionTargetRocketNearRightAngle = 28.75;
}
