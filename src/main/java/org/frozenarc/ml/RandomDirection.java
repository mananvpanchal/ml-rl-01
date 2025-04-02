package org.frozenarc.ml;

import java.util.Random;

/**
 * Date: 01-04-2025 17:42
 * Author: manan
 */
public class RandomDirection {

    private static final Random random = new Random();

    public static int get() {
        return random.nextInt(8);
    }
}
