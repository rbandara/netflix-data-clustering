package org.nc.util;

import java.util.Random;

/**
 * @author rbandara
 *         A class containing utility methods
 */
public class RandomUtil {

    private static Random randomGenerator = new Random();

    /**
     * @param maxValue maximum value that can be in the array
     * @param length   length of the array
     * @return an array with given length and a given maximum valuejhyWNBXxx
     */
    public static int[] getRandomArray(int maxValue, int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = randomGenerator.nextInt(maxValue) + 1;
        }
        return array;
    }

    /**
     * Returns a random value between 0 and .25
     *
     * @return
     */
    public static double getDouble(double rangeMax) {
        double rangeMin = 0;
        double randomValue = rangeMin + (rangeMax - rangeMin) * randomGenerator.nextDouble();
        return randomValue;
    }

    public static int getRandomMovieId() {
        return randomGenerator.nextInt(Constants.NO_OF_MOVIES);
    }

    public static int getRandomUserId() {
        return randomGenerator.nextInt(Constants.NO_OF_USERS);

    }
}
