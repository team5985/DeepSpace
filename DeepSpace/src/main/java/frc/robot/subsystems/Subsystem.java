package frc.robot.subsystems;

public abstract class Subsystem {
    /**
     * Configure and initialise all the actuator classes associated with the subsystem.
     */
	abstract void configActuators();
	
	/**
	 * Configure and initialise all sensor classes associated with the subsystem.
	 */
	abstract void configSensors();
}