package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

public class TiltProtection extends Subsystem {

private AHRS imu;


double roll = imu.getRoll();
double pitch = imu.getPitch();

    @Override
    void configActuators() {

    }

    @Override
    void configSensors() {
	
    }
}

/**  Pseudo Code (English version of Java)
*   Detect Tilt with *Roll* (detects side to side rocking, also known as tilt)
*
*   Tilt (other way) same amount of degrees tilted.
*
*   waitSeconds(Y)
*
*   if (roll <= X) {Reset Mechanism}
*
*/
