package frc.robot;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.ParseException;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class for handling the input of Vision data from the JeVois and parsing it into localisation data.
 * Only handles localisation (where the robot is) and not *how* to get to the target.
 */
public class Vision {
	public static Vision mVisionInstance;

	SerialPort mxp;

	double targetAngle;
	double targetDistance;
	boolean dataIsValid;
	boolean jevoisError;

	double[] fieldTargetAngles;

	public static Vision getInstance() {
		if (mVisionInstance == null) {
			mVisionInstance = new Vision();
		}
		return mVisionInstance;
	}

	private Vision() {
		mxp = new SerialPort(115200, SerialPort.Port.kMXP);

		targetAngle = 0.0;
		targetDistance = 0.0;
		dataIsValid = false;
		jevoisError = true;

		// Angles of the vision targets, organised into an array. Used for automatically selecting the target.
		fieldTargetAngles = new double[8];
		fieldTargetAngles[0] = Constants.kVisionTargetSideNearAngle;
		fieldTargetAngles[1] = Constants.kVisionTargetSideLeftAngle;
		fieldTargetAngles[2] = Constants.kVisionTargetSideRightAngle;
		fieldTargetAngles[3] = Constants.kVisionTargetRocketFarLeftAngle;
		fieldTargetAngles[4] = Constants.kVisionTargetRocketFarRightAngle;
		fieldTargetAngles[5] = Constants.kVisionTargetRocketNearLeftAngle;
		fieldTargetAngles[6] = Constants.kVisionTargetRocketNearLeftAngle;		
		fieldTargetAngles[7] = Constants.kVisionTargetLoadingStation;
	}

	public enum VisionTarget {
		SIDE_NEAR,
		SIDE_LEFT,
		SIDE_RIGHT,
		ROCKET_NEAR_LEFT,
		ROCKET_NEAR_RIGHT,
		ROCKET_FAR_LEFT,
		ROCKET_FAR_RIGHT,
	}

	/**
	 * Updates the known locations of the vision targets.
	 */
	public void updateVision() {
		targetAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);

		SmartDashboard.putNumber("X", targetAngle);
	}

	/**
	 * Returns distance the robot must travel perpendicular to the vision target in order to align with it. Does not update vision values.
	 * @param visionTargetAngle Angle the vision target faces, relative to the field.
	 * @param robotAngle Angle of the robot's gyro, relative to the field.
	 * @return Error in metres.
	 * @see updateVision()
	 */
	public double getTargetCrossError(double visionTargetAngle, double robotAngle) {
		double error = 0.0;
		double visionDist = getDistance();
		double visionAngle = getAngle();
		visionTargetAngle += 90;  // Current equation uses an angle in-line with the vision target. This converts from facing forward to what works in the calculation.
		error = visionDist * Math.sin(90-((robotAngle-180)-visionTargetAngle+visionAngle));

		return error;
	}

	/**
	 * Returns distance the robot must travel perpendicular to the vision target in order to align with it. Does not update vision values.
	 * @param visionTargetAngle Target to track.
	 * 
	 * @param robotAngle Angle of the robot's gyro, relative to the field.
	 * @return Error in metres.
	 * @see updateVision()
	 */
	public double getTargetCrossError(VisionTarget visionTargetAngle, double robotAngle) {
		return getTargetCrossError(targetNameToAngle(visionTargetAngle), robotAngle);
	}

	/**
	 * Returns distance the robot must travel perpendicular to the vision target in order to align with it. Does not update vision values.
	 * This method attemps to guess which target is being targeted based on the robot's orientation.
	 * @param robotAngle Angle of the robot's gyro, relative to the field.
	 * @return Error in metres.
	 * @see updateVision()
	 */
	public double getAutoDetectTargetCrossError(double robotAngle) {
		robotAngle %= 360;  // Ensuring the angle is within +360 and -360
		if (robotAngle < 0) { robotAngle += 360; }  // Ensuring angle is positive (positive clockwise)

		double distance = Math.abs(fieldTargetAngles[0] - robotAngle);
		int idx = 0;
		for(int c = 1; c < fieldTargetAngles.length; c++){
			double cdistance = Math.abs(fieldTargetAngles[c] - robotAngle);
			if(cdistance < distance){
				idx = c;
				distance = cdistance;
			}
		}
		double visionTargetAngle = fieldTargetAngles[idx];
		return getTargetCrossError(visionTargetAngle, robotAngle);
	}

	/**
	 * Returns known angle to the target. Does not update the value.
	 * @return Angle to target in degrees.
	 * @see updateVision()
	 */
	public double getAngle() {
		return targetAngle;
	}
	
	/**
	 * Returns known distance to the target. Does not update the value.
	 * @return Distance to target in metres.
	 * @see updateVision()
	 */
	public double getDistance() {
		return targetDistance;
	}

	/**
	 * @return True if the camera is detecting a valid target.
	 */
	public boolean getDataIsValid() {
		return dataIsValid;
	}

	/**
	 * @return True if there is no data coming in from the JeVois
	 */
	public boolean getJeVoisError() {
		return jevoisError;
	}

	/**
	 * Returns the angle of the vision target.
	 * @param target
	 * @return Angle of target, from the perspective of facing towards it, relative to the field, in degrees.
	 */
	public double targetNameToAngle(VisionTarget target) {
		double retval = -1;
		switch (target) {
			case SIDE_NEAR:
			retval = Constants.kVisionTargetSideNearAngle;
			break;

			case SIDE_LEFT:
			retval = Constants.kVisionTargetSideLeftAngle;
			break;

			case SIDE_RIGHT:
			retval = Constants.kVisionTargetSideRightAngle;
			break;

			case ROCKET_FAR_LEFT:
			retval = Constants.kVisionTargetRocketFarLeftAngle;
			break;

			case ROCKET_FAR_RIGHT:
			retval = Constants.kVisionTargetRocketFarRightAngle;
			break;

			case ROCKET_NEAR_LEFT:
			retval = Constants.kVisionTargetRocketNearLeftAngle;
			break;

			case ROCKET_NEAR_RIGHT:
			retval = Constants.kVisionTargetRocketNearRightAngle;
			break;
		}
		return retval;
	}
}
