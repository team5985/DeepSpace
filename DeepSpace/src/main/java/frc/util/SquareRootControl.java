package frc.util;

/**
 * Simple feedback controller that accelerates, coasts, and decelerates.
 * <b>Doesn't work, don't use.</b>
 */
@Deprecated
public class SquareRootControl {
    private double _maxAccel;
    private double _maxSpeed;
    private double _K;

    private double dt;

    // Ramp and coast speeds
    private double vUp;
    private double vCoast;
    private double vDown;

    double lastTarget = 0.0;

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
        vUp = 0;
    }

    /**
     * Run a controller step.
     * @param position Current position.
     * @param target Desired position.
     * @return Speed in units or m/s or deg/s.
     */
    public double run(double position, double target) {
        if (target != lastTarget) { // Starts new movement if commanded target is different.
            reset();
        }

        boolean forward = target > position;  // True when the direction is positive and false when negative

        double error = Math.abs(target - position);

        vUp = _maxAccel * dt;
        vCoast = _maxSpeed;
        vDown = _K * Math.sqrt(error);

        dt += 0.02;
        lastTarget = target;

        if (forward) {
            return Math.min(Math.min(vUp, vCoast), vDown); // Return lowest speed.e
        } else {
            return -Math.min(Math.min(vUp, vCoast), vDown); // Return lowest speed.
        }        
    }
}
