package frc.util;

import frc.robot.Constants;

/**
 * Class that contains various calculations that can be used in different parts of the code.
 */
public class Calcs {
    public static long encoderToRevolutions(int input) {
        return (input / Constants.kCuiEncoderCpr);
    }

    /**
     * Returns true if the two values are within the threshold number away from each other.
     * @param input1
     * @param input2
     * @param threshold
     * @return True if input2 is +/- threshold of input1
     */
    public static boolean isWithinThreshold(double input1, double input2, double threshold) {
        return Math.abs(input1 - input2) < threshold;
    }
}