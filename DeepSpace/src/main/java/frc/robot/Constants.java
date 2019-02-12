package frc.robot;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.revrobotics.CANSparkMax.IdleMode;

public class Constants {
	// CAN IDs
    public static final int kLeftDriveACanId = 3;
    public static final int kLeftDriveBCanId = 2;
    // public static final int kLeftDriveCCanId = 3;

    public static final int kRightDriveACanId = 1;
    public static final int kRightDriveBCanId = 0;
    // public static final int kRightDriveCCanId = 6;

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
    
    // RoboRIO PWM Ports
    public static final int kVictorCargoIntakeLeftPwmPort = 0;
    public static final int kVictorCargoIntakeRightPwmPort = 1;
    public static final int kVictorMantisRightPwmPort = 2;
    public static final int kVictorMantisLeftPwmPort = 3;

    // Driverstation Ports
    public static final int kJoystickPort = 0;
    public static final int kXboxPort = 1;

    // Encoder Constants
    public static final int kCuiEncoderPpr = 1024;  // Number of pulses per revolution of the encoder. Settable by the DIP switches on the CUI AMT-103, should be checked.
    public static final double kCuiCountsToDegrees = kCuiEncoderPpr / 360;

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
    public static final double kGainGyroDriveTurn = 0;  // TODO
    
	public static final double kDriveMaxDriveAccel = 0;  // m/s/s TODO: Find this
	public static final double kDriveMaxDriveVel = 3.6;  // m/s
	public static final double kDriveEncoderDriveGain = 0;  // TODO: Used in square root controller

    public static double kDriveWheelDiameter = 6.0;
    public static final double kDriveEncoderDistancePerPulse = (kDriveWheelDiameter * Math.PI) / kCuiEncoderPpr;  // Metres per pulse
    
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
    public static final double kElevatorDistancePerPulse = (Math.PI * Constants.kElevatorSpoolDiam) / Constants.kCuiEncoderPpr;  // Metres per pulse

	public static final double kElevatorMaxOutput = 0.0; // TODO
    public static final boolean kTalonElevatorDirection = false;
	public static final boolean kVictorMantisDirection = false;
    
    // Tilt Compensation Constants
    public static final double kRollErrorMax = 5;
    public static final double kRollErrorMin = kRollErrorMax * -1;

    public static final double kPitchErrorMax = 5;
    public static final double kPitchErrorMin = kPitchErrorMax * -1;

    public static final double kYawErrorMax = 5;
    public static final double kYawErrorMin = kYawErrorMax * -1;

    // PCM Solenoid Ports
    public static final int kHatchPopperPcmPort = 0;
    public static final int kSolenoidMantisChannel = 3;
    public static final int kBeakPlusCargoSolenoidChannel = 4;

    // CargoIntake
    public static final boolean kVictorCargoIntakeDirection = false;

    // CargoWrist Contants
    /**degrees per second per second */
    public static final double kCargoWristMaxAccelerationDegrees = 48.6;       // do calculations, placeholders (double max speed (not true value))
    public static final double kCargoWristMaxSpeed = 97.2;
    public static final double kCargoWristGain = 1;  //TODO: placeholder
    public static final double kCargoWristAngleTolerance = 2;
	public static final boolean kWristMotorDirection = true;
    public static final boolean kTalonCargoIntakeEncoderPhase = true; //TODO: Check

    public static double kWristMaxOutput = 0.0;  //TODO

    //Bobcat Constants
    public static final double kBobcatJointMotorMaxAccelerationDegrees = 300;       // do calculations, placeholders (double max speed (not true value))
    public static final double kBobcatJointMotorMaxSpeed = 169.2;  //different degree number to cargo + gearing
    public static final double kBobCatJointMotorGain = 1;  //TODO: placeholder
    public static final double kBobcatJointKv = 1 / 169.2;  // deg/s^-1

    public static final double kBobcatJointAngleTolerance = 2;  // +/- Degrees

    public static final boolean kBobcatJointDirection = true;
    public static final boolean kTalonBobcatJointEncoderPhase = true; //TODO: check     for hatch raising and lowering
    
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
