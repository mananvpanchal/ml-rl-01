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
        int[] sourcePos = pos(0, 0);
        int[] targetPos = pos(14, 14);
        Ground ground = new Ground(15, 100);
        ground.setValue(targetPos, 0);
        Scanner scanner = new Scanner(System.in);
        String line = null;
        do {
            ground.resetPath();
            ground.setCurPos(sourcePos);
            int i = 0;
            for (; i < 5000; i++) {
                //System.out.println("Iteration: "+i);
                int[] minDirPos = ground.getMinNeighbour(ground.getCurPos());
                if (minDirPos[2] == -1) {
                    boolean moved;
                    int dirTrial = 0;
                    do {
                        int direction = randomDirection();
                        moved = ground.move(direction);
                        dirTrial++;
                        System.out.println("Direction Trial: "+dirTrial);
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
            if (!Util.posEquals(ground.getCurPos(), targetPos)) {
                System.out.println("Iteration done without result");
            } else {
                System.out.println("Iteration done: "+i);
            }
            line = scanner.nextLine();
        } while (line.isEmpty());
        scanner.close();
    }
}
