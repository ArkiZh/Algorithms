package com.arki.algorithms.common;

import java.util.Random;

public class MathUtil {

    public static Random random = new Random();

    public static int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

}
