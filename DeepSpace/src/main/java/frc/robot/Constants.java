package frc.robot;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Constants {
	// CAN IDs
    public static final int kLeftDriveACanId = 1;
    public static final int kLeftDriveBCanId = 2;
    public static final int kLeftDriveCCanId = 3;
    
    public static final int kRightDriveACanId = 4;
    public static final int kRightDriveBCanId = 5;
    public static final int kRightDriveCCanId = 6;
    
    // Drivetrain constants
    public static NeutralMode kDriveNeutralMode = NeutralMode.Brake;
    
	public static final boolean kLeftDriveEncoderPhase = false; // false = not inverted, true = inverted
	public static final boolean kRightDriveEncoderPhase = false; // TODO: Check this
	
	public static final boolean kLeftDriveMotorPhase = false; // false = not inverted, true = inverted
	public static final boolean kRightDriveMotorPhase = true;
}