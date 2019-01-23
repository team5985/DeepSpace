package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Constants {
	// CAN IDs
    public static final int kLeftDriveACanId = 1;
    public static final int kLeftDriveBCanId = 2;
    public static final int kLeftDriveCCanId = 3;
    
    public static final int kRightDriveACanId = 4;
    public static final int kRightDriveBCanId = 5;
    public static final int kRightDriveCCanId = 6;

    //elevator TalonSRX 
    public static final int kTalonElevatorLeftCanId = 0;
    public static final int kTalonElevatorRightCanId = 1;

    // Driverstation Ports
    public static final int kJoystickPort = 0;
    public static final int kXboxPort = 1;
    
    // Drivetrain Constants
    public static NeutralMode kDriveNeutralMode = NeutralMode.Brake;
    
	public static final boolean kLeftDriveEncoderPhase = false; // false = not inverted, true = inverted
	public static final boolean kRightDriveEncoderPhase = false; // TODO: Check this
	
	public static final boolean kLeftDriveMotorPhase = false; // false = not inverted, true = inverted
    public static final boolean kRightDriveMotorPhase = true;

    public static final double kDriveMaxRotationalAccel = 0.0;  // TODO: Test this
    public static final double kDriveMaxRotationalVel = 0.0;
    public static final double kDriveGyroTurnKf = 1 / kDriveMaxRotationalVel;  // Feedforward for power / turn rate
    public static final double kDriveGyroTurnKp = 0.0;  // Compensation for error
    public static final double kDriveGyroTurnGain = 1.0;  // K gain for square root controller.
    public static final double kDriveGyroTurnThresh = 3.0;  // In degrees.
    public static final double kDriveGyroRateThresh = 3.0;  // In deg/s.
    	
    
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
}
