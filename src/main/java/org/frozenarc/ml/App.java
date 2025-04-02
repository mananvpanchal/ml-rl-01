package org.frozenarc.ml;

import java.util.*;
import java.util.stream.Stream;

/**
 * Hello world!
 */
public class App {

    static double[][] ground = new double[9][9];
    static int[][] ground2 = new int[9][9];
    static int prevI = -1;
    static int prevJ = -1;
    static int curI = 0;
    static int curJ = 0;

    private static void  resetGround2() {
        for (int i = 0; i < ground2.length; i++) {
            for (int j = 0; j < ground2[i].length; j++) {
                ground2[i][j] = 0;
            }
        }
    }

    public static void main(String[] args) {

        Random random = new Random();

        for (int i = 0; i < ground.length; i++) {
            for (int j = 0; j < ground[i].length; j++) {
                ground[i][j] = 100;
            }
        }

        resetGround2();

        ground[8][8] = 0;

        showGround();

        Scanner scanner = new Scanner(System.in);
        String line = null;
        do {
            prevI = -1;
            prevJ = -1;
            curI = 8;
            curJ = 0;
            resetGround2();
            for (int i = 0; i < 1000; i++) {
                System.out.println("Iteration: " + i);
                prevI = curI;
                prevJ = curJ;
                setDirection(random, 0);
                ground2[curI][curJ] = 1;
                ground[prevI][prevJ] = (ground[prevI][prevJ] + ground[curI][curJ]) / 2;
                showGround();
                if (curI == 8 && curJ == 8) {
                    break;
                }
            }
            line = scanner.nextLine();
        } while (!line.equals("q"));
    }

    private static int getMinDirection() {
        /*double _0 = curI > 0 ? ground[curI - 1][curJ] : 100;
        double _1 = curI > 0 && curJ < 8 ? ground[curI - 1][curJ + 1] : 100;
        double _2 = curJ < 8 ? ground[curI][curJ + 1] : 100;
        double _3 = curI < 8 && curJ < 8 ? ground[curI + 1][curJ + 1] : 100;
        double _4 = curI < 8 ? ground[curI + 1][curJ] : 100;
        double _5 = curI < 8 && curJ > 0 ? ground[curI + 1][curJ - 1] : 100;
        double _6 = curJ > 0 ? ground[curI][curJ - 1] : 100;
        double _7 = curI > 0 && curJ > 0 ? ground[curI - 1][curJ - 1] : 100;*/

        Map<Integer, Double> _0Map = curI > 0 ? Map.of(0, ground[curI - 1][curJ]) : Map.of(-1, 100D);
        Map<Integer, Double> _1Map = curI > 0 && curJ < 8 ? Map.of(1, ground[curI - 1][curJ + 1]) : Map.of(-1, 100D);
        Map<Integer, Double> _2Map = curJ < 8 ? Map.of(2, ground[curI][curJ + 1]) : Map.of(-1, 100D);
        Map<Integer, Double> _3Map = curI < 8 && curJ < 8 ? Map.of(3, ground[curI + 1][curJ + 1]) : Map.of(-1, 100D);
        Map<Integer, Double> _4Map = curI < 8 ? Map.of(4, ground[curI + 1][curJ]) : Map.of(-1, 100D);
        Map<Integer, Double> _5Map = curI < 8 && curJ > 0 ? Map.of(5, ground[curI + 1][curJ - 1]) : Map.of(-1, 100D);
        Map<Integer, Double> _6Map = curJ > 0 ? Map.of(6, ground[curI][curJ - 1]) : Map.of(-1, 100D);
        Map<Integer, Double> _7Map = curI > 0 && curJ > 0 ? Map.of(7, ground[curI - 1][curJ - 1]) : Map.of(-1, 100D);

        Optional<Map<Integer, Double>> min = Stream.of(_0Map, _1Map, _2Map, _3Map, _4Map, _5Map, _6Map, _7Map)
                                                   .min(Comparator.comparing(map -> map.values()
                                                                                       .stream()
                                                                                       .findAny()
                                                                                       .orElse(100D)));

        return min.flatMap(m -> m.keySet().stream().findAny())
                  .filter(key -> min.get().get(key) < 100D)
                  .orElse(-1);

    }

    private static void setDirection(Random random, int rec) {
        if (rec > 9) {
            return;
        }
        int minDirection = getMinDirection();
        System.out.println("Min direction: " + minDirection);
        int direction = minDirection == -1 ? random.nextInt(8) : minDirection;
        System.out.println("Direction: " + direction);
        if (direction == 0 && curI > 0) {
            curI = curI - 1;
        } else if (direction == 1 && curI > 0 && curJ < 8) {
            curI = curI - 1;
            curJ = curJ + 1;
        } else if (direction == 2 && curJ < 8) {
            curJ = curJ + 1;
        } else if (direction == 3 && curI < 8 && curJ < 8) {
            curI = curI + 1;
            curJ = curJ + 1;
        } else if (direction == 4 && curI < 8) {
            curI = curI + 1;
        } else if (direction == 5 && curI < 8 && curJ > 0) {
            curI = curI + 1;
            curJ = curJ - 1;
        } else if (direction == 6 && curJ > 0) {
            curJ = curJ - 1;
        } else if (direction == 7 && curI > 0 && curJ > 0) {
            curI = curI - 1;
            curJ = curJ - 1;
        } else {
            setDirection(random, rec);
        }
    }

    public static void showGround() {
        /*for (int i = 0; i < ground.length; i++) {
            for (int j = 0; j < ground[i].length; j++) {
                if (curI == i && curJ == j) {
                    System.out.print("[");
                }
                System.out.print(ground[i][j]);
                if (curI == i && curJ == j) {
                    System.out.print("]");
                }
                System.out.print("\t");
            }
            System.out.print("\n");
        }
        System.out.println("---------------------------------------------");*/

        for (int i = 0; i < ground2.length; i++) {
            for (int j = 0; j < ground2[i].length; j++) {
                if (curI == i && curJ == j) {
                    System.out.print("[");
                }
                System.out.print(ground2[i][j]);
                if (curI == i && curJ == j) {
                    System.out.print("]");
                }
                System.out.print("\t");
            }
            System.out.print("\n");
        }
        System.out.println("---------------------------------------------");

    }
}
