package com.arki.algorithms.common;

import java.util.Random;

public class MathUtil {

    public static Random random = new Random();

    /**
     * Random integer in [min, max].
     * @param min
     * @param max
     * @return
     */
    public static int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static void main(String[] args) {
        Logger.info(randomInt(0, 100)+"");
    }
}
