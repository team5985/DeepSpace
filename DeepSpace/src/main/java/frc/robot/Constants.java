package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

public class Constants {
	// CAN IDs
    public static final int kLeftDriveACanId = 1;
    public static final int kLeftDriveBCanId = 2;
    public static final int kLeftDriveCCanId = 3;
    
    public static final int kRightDriveACanId = 4;
    public static final int kRightDriveBCanId = 5;
    public static final int kRightDriveCCanId = 6;

    // RoboRIO DIO Ports
    public static final int kDriveLeftEncoderAPort = 0;
	public static final int kDriveLeftEncoderBPort = 1;

    //elevator TalonSRX 
    public static final int kTalonElevatorLeftCanId = 7;
    public static final int kTalonElevatorRightCanId = 8;
    public static final int kTalonCargoIntakeCanId = 9;


    //Solenoid
    public static final byte kSolenoidMantisChannel = 0;

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
	
	public static final boolean kLeftDriveMotorPhase = false; // false = not inverted, true = inverted
    public static final boolean kRightDriveMotorPhase = true;

    public static double kDriveMaxRotationalAccel = 0.0;  // TODO: Test this
    public static double kDriveMaxRotationalVel = 0.0;
    public static double kDriveGyroTurnKf = 1 / kDriveMaxRotationalVel;  // Feedforward for power / turn rate
    public static double kDriveGyroTurnKp = 0.0;  // Compensation for error
    public static double kDriveGyroTurnGain = 1.0;  // K gain for square root controller.
    public static double kDriveGyroTurnThresh = 3.0;  // In degrees.
    public static double kDriveGyroRateThresh = 3.0;  // In deg/s.
    
    public static double kDriveWheelDiameter = 6.0;
	public static final double kDriveEncoderDistancePerPulse = (kDriveWheelDiameter * Math.PI) / kEncoderPpr;  // Metres per pulse
    
    // Tilt Compensation Constants
    public static final double kRollErrorMax = 5;
    public static final double kRollErrorMin = kRollErrorMax * -1;

    public static final double kPitchErrorMax = 5;
    public static final double kPitchErrorMin = kPitchErrorMax * -1;

    public static final double kYawErrorMax = 5;
    public static final double kYawErrorMin = kYawErrorMax * -1;   
    
    // PCM Solenoid Ports
    public static final int kMantisLeftPcmPort = 0;
    public static final int kMantisRightPcmPort = 1;  
  
    // CargoIntake Contants
    /**degrees per second per second */
    public static final double kCargoIntakeMaxAccelerationDegrees = 1000;       // do calculations, placeholders (double max speed (not true value))
    public static final double kCargoIntakeMaxSpeed = 518.4;
    public static final double kCargoIntakeGain = 1;  //placeholder
}
