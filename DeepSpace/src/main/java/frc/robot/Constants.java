package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;
import com.sun.tools.classfile.StackMapTable_attribute.stack_map_frame;

public class Constants {
	// CAN IDs
    public static final int kLeftDriveACanId = 1;
    public static final int kLeftDriveBCanId = 2;
    public static final int kLeftDriveCCanId = 3;

    public static final int kRightDriveACanId = 4;
    public static final int kRightDriveBCanId = 5;
    public static final int kRightDriveCCanId = 6;

    // Elevator TalonSRX
    public static final int kTalonElevatorLeftCanId = 7;
    public static final int kTalonElevatorRightCanId = 8;
    //cargo
    public static final int kTalonCargoWristCanId = 9;
    
    //hatch
    public static final int kTalonBobcatJointCanId = 10; //for raising and lowering hatch

    // RoboRIO DIO Ports
    public static final int kDriveLeftEncoderAPort = 0;
    public static final int kDriveLeftEncoderBPort = 1;
    public static final int kDriveRightEncoderAPort = 2;
    public static final int kDriveRightEncoderBPort = 3;

    // Driverstation Ports
    public static final int kJoystickPort = 0;
    public static final int kXboxPort = 1;

    // Encoder Constants
    public static final int kEncoderPpr = 1024;  // Number of pulses per revolution of the encoder. Settable by the DIP switches on the AMT-103, should be checked.

    // Drivetrain Constants
    public static IdleMode kDriveIdleMode = IdleMode.kBrake;

	public static final boolean kLeftDriveEncoderPhase = false; // false = not inverted, true = inverted
    public static final boolean kRightDriveEncoderPhase = false; // TODO: Check this
    public static final boolean kTalonCargoIntakeEncoderPhase = true; //TODO: Check
    public static final boolean kTalonBobcatJointEncoderPhase = true; //TODO: check     for hatch raising and lowering

	public static final boolean kLeftDriveMotorPhase = false; // false = not inverted, true = inverted
    public static final boolean kRightDriveMotorPhase = true;

    public static double kDriveMaxRotationalAccel = 0.0;  // TODO: Test this
    public static double kDriveMaxRotationalVel = 0.0;
    public static double kDriveTopSpeed = 3.7795;

    public static double kDriveGyroTurnKf = 1 / kDriveMaxRotationalVel;  // Feedforward for power / turn rate
    public static double kDriveGyroTurnKp = 0.0;  // Compensation for error
    public static double kDriveGyroTurnGain = 1.0;  // K gain for square root controller.
    public static double kDriveGyroTurnThresh = 3.0;  // In degrees.
    public static double kDriveGyroRateThresh = 3.0;  // In deg/s.

	public static final double kDrivePowerKf = 1 / kDriveTopSpeed;

    public static double kDriveWheelDiameter = 6.0;
	public static final double kDriveEncoderDistancePerPulse = (kDriveWheelDiameter * Math.PI) / kEncoderPpr;  // Metres per pulse

    // Gain Constants
    public static final double kGainGyroDriveTurn = 1;

    // Tilt Compensation Constants
    public static final double kRollErrorMax = 5;
    public static final double kRollErrorMin = kRollErrorMax * -1;

    public static final double kPitchErrorMax = 5;
    public static final double kPitchErrorMin = kPitchErrorMax * -1;

    public static final double kYawErrorMax = 5;
    public static final double kYawErrorMin = kYawErrorMax * -1;

    // PCM Solenoid Ports
    public static final int kHatchRightPcmPort = 0;
    public static final int kHatchLeftPcmPort = 1;
    public static final int kElevatorSolenoidControllerPcmPort = 2;
    public static final byte kSolenoidMantisChannel = 3;

    // CargoIntake
    public static final int kVictorCargoIntakeLeftPwmPort = 0;   // ??????????
    public static final int kVictorCargoIntakeRightPwmPort = 1;  //????????????????

    // CargoWrist Contants
    /**degrees per second per second */
    public static final double kCargoIntakeMaxAccelerationDegrees = 1000;       // do calculations, placeholders (double max speed (not true value))
    public static final double kCargoIntakeMaxSpeed = 518.4;
    public static final double kCargoIntakeGain = 1;  //placeholder

    //Bobcat squareroot compensation constants
    public static final double kBobcatJointMotorMaxAccelerationDegrees = 300;       // do calculations, placeholders (double max speed (not true value))
    public static final double kBobcatJointMotorMaxSpeed = 169.2;  //different degree number to cargo + gearing
    public static final double kBobCatJointMotorGain = 1;  //placeholder
    // Field measurements (angles are positive as getAutoDetectTargetCrossError() requires it)
	public static final double kVisionTargetSideNearAngle = 0;
	public static final double kVisionTargetSideLeftAngle = 90;
	public static final double kVisionTargetSideRightAngle = 270;
	public static final double kVisionTargetRocketFarLeftAngle = 208.75;
	public static final double kVisionTargetRocketFarRightAngle = 151.25;
	public static final double kVisionTargetRocketNearLeftAngle = 331.25;
    public static final double kVisionTargetRocketNearRightAngle = 28.75;
    
    //other
    public static final double kCountsToDegrees = 0.087890625;
}
