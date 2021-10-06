package com.jher.nid_aux_histoires.service.tool;

import java.security.SecureRandom;
import java.util.Random;

public class RNG {
	private static final Random random = new SecureRandom();

	/**
	 * Get a random number between 0 (included) and max (excluded)
	 *
	 * @param max : the maximum value the random number can take
	 * @return a random number
	 */
	public static int getRandBelow(int max) {
		return random.nextInt(max);
	}

	/**
	 * Get a random number between min and max (both included)
	 *
	 * @param min : the minimum value the random number can take
	 * @param max : the maximum value the random number can take
	 * @return a random number
	 */
	public static double getRandomIntoInterval(double min, double max) {
		return min + (max - min) * random.nextDouble();
	}

	/**
	 * Generates a number that kind of follow the normal law.
	 * 
	 * @param max    : the max number that can be generated
	 * @param force  : greater = makes the extreme numbers closer to max/2
	 * @param smooth : makes the result closer to the linear function 0 - max
	 * @return the result of the random variable
	 */
	public static long useRandomVariable(int force, int max, double smooth) {
		int power = 2 * force + 1;
		double offset_x = -0.5;
		double offset_y = -Math.pow(offset_x, power);
		int up_to_max = (int) ((1 / (Math.pow(-offset_x, power) + offset_y)) * max);

		double randDouble = random.nextDouble();
		double proba = (Math.pow(randDouble + offset_x, power) + offset_y) * up_to_max;

		double linear_0_max = randDouble * max;
		return Math.round((proba + linear_0_max * smooth) / (smooth + 1));
	}
}
