package org.frozenarc.ml;

import java.util.Scanner;

import static org.frozenarc.ml.Util.pos;
import static org.frozenarc.ml.Util.randomDirection;

/**
 * Date: 01-04-2025 16:50
 * Author: manan
 */
public class App2 {

    public static void main(String[] args) {
        int[] targetPos = pos(0, 8);
        Ground ground = new Ground(9, 100);
        ground.setValue(targetPos, 0);
        Scanner scanner = new Scanner(System.in);
        String line = null;
        do {
            ground.setCurPos(pos(0, 0));
            ground.resetPath();
            for (int i = 0; i < 2000; i++) {
                int[] minDirPos = ground.getMinNeighbour(ground.getCurPos());
                if (minDirPos[2] == -1) {
                    boolean moved;
                    int dirTrial = 0;
                    do {
                        int direction = randomDirection();
                        //System.out.println(direction);
                        moved = ground.move(direction);
                        dirTrial++;
                    } while (!moved && dirTrial < 10);
                } else {
                    ground.move(pos(minDirPos[0], minDirPos[1]));
                    ground.setPrevValuePercentage(0.8);
                    ground.setSurroundingValuesPercentageExceptPrevNext(0.9);
                }
                if (Util.posEquals(ground.getCurPos(), targetPos)) {
                    ground.showGrd();
                    ground.showPath();
                    break;
                }
            }
            line = scanner.nextLine();
        } while (!line.equals("q"));
    }
}
