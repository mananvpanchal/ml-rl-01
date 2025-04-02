package org.frozenarc.ml;

/**
 * Date: 01-04-2025 17:44
 * Author: manan
 */
public class Util {

    public static int[] pos(int i, int j) {
        return new int[]{i, j};
    }

    public static int randomDirection() {
        return RandomDirection.get();
    }

    public static boolean posEquals(int[] p1, int[] p2) {
        return p1[0] == p2[0] && p1[1] == p2[1];
    }
}
