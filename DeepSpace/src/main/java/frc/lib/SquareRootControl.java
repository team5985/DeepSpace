package frc.lib;

/**
 * Simple feedback controller that accelerates, coasts, and decelerates.
 */
public class SquareRootControl {
    private double _maxAccel;
    private double _maxSpeed;
    private double _K;

    private double dt;

    // Ramp and coast speeds
    private double vUp;
    private double vCoast;
    private double vDown;

    /**
     * Initialise controller.
     * @param maximumAcceleration in units of m/s/s or deg/s/s
     * @param maximumSpeed in units of m/s or deg/s
     * @param K
     */
    public SquareRootControl(double maximumAcceleration, double maximumSpeed, double K) {
        _maxAccel = maximumAcceleration;
        _maxSpeed = maximumSpeed;
        _K = K;

        dt = 0;
    }

    /**
     * Configure K gain for square root deceleration.
     */
    public void configK(double K) {
        _K = K;
    }

    public void configMaxSpeed(double maximumSpeed) {
        _maxSpeed = maximumSpeed;
    }

    /**
     * Sets the recorded elapsed time to 0. Used for starting a new movement.
     */
    public void reset() {
        dt = 0;
    }

    /**
     * Run a controller step.
     * @param error
     * @return Speed in units or m/s or deg/s.
     */
    public double run(double error) {
        vUp = _maxAccel * dt;
        vCoast = _maxSpeed;
        vDown = _K * Math.sqrt(error);
        return Math.min(Math.min(vUp, vCoast), vDown); // Return lowest speed.
    }
    //TODO need to convert to volts???? advice??
}