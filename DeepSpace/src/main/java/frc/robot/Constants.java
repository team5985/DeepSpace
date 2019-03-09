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
    
    public static final int kTalonCargoWristCanId = 8;
    
    public static final int kTalonBobcatJointCanId = 7; //for raising and lowering bobcat

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
    public static final int kHatchPopperForwardPcmPort = 0;  // 0, 1 for comp robot, 4/5 for Pyxis
    public static final int kHatchPopperReversePcmPort = 1;
    public static final int kSolenoidMantisChannel = 3;
    public static final int kBeakPlusCargoSolenoidChannel = 6;  // TODO: Check

    // Driverstation Ports
    public static final int kJoystickPort = 0;
    public static final int kXboxPort = 1;

    // Encoder Constants
    public static final int kCuiEncoderPpr = 2048;  // Number of pulses per revolution of the encoder. Settable by the DIP switches on the CUI AMT-103, should be checked.
    public static final int kCuiEncoderCpr = kCuiEncoderPpr * 4;  // Number of counts per revolution of the encoder.
    public static final double kCuiCountsToDegrees = 360 / kCuiEncoderCpr;

    // Drivetrain Constants
    public static IdleMode kDriveIdleMode = IdleMode.kCoast;

	public static final boolean kLeftDriveEncoderPhase = false; // false = not inverted, true = inverted
    public static final boolean kRightDriveEncoderPhase = false; // Check this

	public static final boolean kLeftDriveMotorPhase = false; // false = not inverted, true = inverted

    public static double kDriveGyroTurnK = 0.06;  // Square root gain for turning on the spot
    public static double kDriveGyroTurnThresh = 3.0;  // In degrees.
    public static double kDriveGyroRateThresh = 3.0;  // In deg/s.
    
    public static final double kDriveSensorDriveTurnKp = 0.0075;  // In % per degree (Not square root controller!: proportional gain)
    
	public static final double kDriveMaxDriveAccel = 3.29;  // m/s/s
	public static final double kDriveMaxDriveVel = 3.29;  // m/s
	public static final double kDriveEncoderDrivePGain = 1.0;  // Proportional gain for drive speed to distance

    public static double kDriveWheelDiameter = 6.0;
    public static final double kDriveEncoderDistancePerPulse = (kDriveWheelDiameter * Math.PI) / kCuiEncoderCpr;  // Metres per pulse
    
    public static final double kDriveTipThreshold = 25.0;  // Degrees before activating tip protection
    public static final double kDriveTipCorrectionPower = 0.4;  // Amount of power to drive when running tip protection.

    public static double kDriveSquaredSteeringInputsExponent = 2.0;
    public static double kDriveSquaredPowerInputsExponent = 2.0;

    // Climber constants
    public static final double kElevatorLiftDistGain = 20;
	public static final double kElevatorTiltCompGain = 0.0;  // Motor controller units (-1:1) per degree  
    public static final double kElevatorHeightTolerance = 0.05;  // Metres
    public static final double kElevatorClimbHeight = 0.43;

    public static final double kElevatorSpoolDiam = 0.0191;  // In Metres!
    public static final double kElevatorDistancePerPulse = (Math.PI * Constants.kElevatorSpoolDiam) / Constants.kCuiEncoderCpr;  // Metres per pulse

	// public static final double kElevatorMaxOutput = 0.0;
    public static final boolean kTalonElevatorDirection = false;
	public static final boolean kVictorMantisDirection = false;

    // Hatch Constants
	public static final int kHatchPopperDelay = 0;

    // CargoWrist Contants
    /**degrees per second per second */
    public static final double kCargoWristMaxAccelerationDegrees = 100.0;       // do calculations, placeholders (double max speed (not true value))
    public static final double kCargoWristMaxSpeed = 97.2;
    public static final double kCargoWristAngleTolerance = 2;
	public static final boolean kWristMotorDirection = false;
    public static final boolean kTalonCargoIntakeEncoderPhase = true;

    public static final double kIntakePhysicalLength = 0.270; // Metres
	public static final double kIntakeStowedPhysicalAngle = 0.0; // Degrees
	public static final double kIntakePhysicalWeight = 32.0; // N
    public static final double kIntakeWristMaxTorque = 43.0; // Nm
    
	public static final double kWristPGain = 0.015;

    public static double kWristMaxUpOutput = 0.5;
    public static double kWristMaxDownOutput = 0.2;

    // Cargo Intake Constants
    public static final boolean kVictorCargoIntakeDirection = false;

    // Bobcat Constants
    public static final double kBobcatPhysicalLength = 1.0414;  // m
	public static final double kBobcatStowedPhysicalAngle = 0;  // deg
	public static final double kBobcatPhysicalWeight = 75;  // Newtons
	public static final double kBobcatJointMaxTorque = 520;  // Nm
    
    public static final double kBobcatJointPGain = 0.025;
    
    public static final double kBobcatJointAngleTolerance = 2;  // +/- Degrees

    public static final boolean kBobcatJointDirection = true;
    public static final boolean kBobcatJointEncoderPhase = true;
    
    public static final double kBobcatJointRampRate = 0.125; // Seconds to full power
    public static final double kBobcatJointMaxOutput = 0.8;
    public static final double kBobcatJointMaxDownwardsOutput = -0.25;

    // Vision constants
    public static final double kVisionDriverAdjustmentGain = 20; // Proportional, scales the joystick steering command and adds to the vision tracking target
	public static final double kVisionServoingGain = 0.06;  // Square root gain

    // Field measurements (angles are positive as getAutoDetectTargetCrossError() requires it)
	public static final double kVisionTargetSideNearAngle = 0;
	public static final double kVisionTargetSideLeftAngle = 90;
	public static final double kVisionTargetSideRightAngle = 270;
	public static final double kVisionTargetRocketFarLeftAngle = 208.75;
	public static final double kVisionTargetRocketFarRightAngle = 151.25;
	public static final double kVisionTargetRocketNearLeftAngle = 331.25;
    public static final double kVisionTargetRocketNearRightAngle = 28.75;
	public static final double kVisionTargetLoadingStation = 180;
	
}
