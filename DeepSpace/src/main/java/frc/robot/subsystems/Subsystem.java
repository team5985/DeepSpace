package frc.robot.subsystems;

public abstract class Subsystem {
	/**
	 * Get the position of the mechanism's axis.
	 * @return Current position. Units will be in metres or degrees.
	 */
	abstract double getPosition();
	
	/**
	 * Zero the mechanism by running a zeroing routine.
	 * @return True when the sensor is zeroed.
	 */
	abstract boolean zeroPosition();
	
    /**
     * Configure and initialise all the actuator classes associated with the subsystem.
     */
	abstract void configActuators();
	
	/**
	 * Configure and initialise all sensor classes associated with the subsystem.
	 */
	abstract void configSensors();
}