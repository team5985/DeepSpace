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
    public static final int kTalonElevatorLeftCanId = 7;
    public static final int kTalonElevatorRightCanId = 8;
    //cargo
    public static final int kTalonCargoIntakeCanId = 9;
    //hatch
    public static final int kTalonBobcatJointCanId = 10; //for raising and lowering hatch

    // Driverstation Ports
    public static final int kJoystickPort = 0;
    
    // Drivetrain Constants
    public static NeutralMode kDriveNeutralMode = NeutralMode.Brake;
    
	public static final boolean kLeftDriveEncoderPhase = false; // false = not inverted, true = inverted
    public static final boolean kRightDriveEncoderPhase = false; // TODO: Check this
    public static final boolean kTalonCargoIntakeEncoderPhase = true; //TODO: Check 
    public static final boolean kTalonBobcatJointEncoderPhase = true; //TODO: check     for hatch raising and lowering
	
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
    public static final int kHatchRightPcmPort = 2;
    public static final int kHatchLeftPcmPort = 3;

    // CargoIntake Contants
    /**degrees per second per second */
    public static final double kCargoIntakeMaxAccelerationDegrees = 1000;       // do calculations, placeholders (double max speed (not true value))
    public static final double kCargoIntakeMaxSpeed = 518.4;
    public static final double kCargoIntakeGain = 1;  //placeholder

    //Bobcat squareroot compensation constants
    public static final double kBobcatJointMotorMaxAccelerationDegrees = 300;       // do calculations, placeholders (double max speed (not true value))
    public static final double kBobcatJointMotorMaxSpeed = 169.2;  //different degree number to cargo + gearing
    public static final double kBobCatJointMotorGain = 1;  //placeholder
}
