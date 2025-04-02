package org.frozenarc.ml;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import static org.frozenarc.ml.Util.pos;

/**
 * Date: 01-04-2025 16:06
 * Author: manan
 */
public class Ground {

    private final int size;
    private int curI = 0;
    private int curJ = 0;
    private int prevI = -1;
    private int prevJ = -1;
    private final double[][] grd;
    private final int[][] path;
    private final NumberFormat numberFormat;

    public Ground(int size, double initVal) {
        this.size = size;
        grd = new double[size][size];
        path = new int[size][size];
        for (double[] rows : grd) {
            Arrays.fill(rows, initVal);
        }
        numberFormat = new DecimalFormat("000.00");
    }

    public void resetPath() {
        for (int i = 0; i < grd.length; i++) {
            for (int j = 0; j < grd[i].length; j++) {
                path[i][j] = 0;
            }
        }
    }

    public void showGrd() {
        for (int i = 0; i < grd.length; i++) {
            for (int j = 0; j < grd[i].length; j++) {
                if (curI == i && curJ == j) {
                    System.out.print("[");
                }
                System.out.print(numberFormat.format(grd[i][j]));
                if (curI == i && curJ == j) {
                    System.out.print("]");
                }
                System.out.print("\t");
            }
            System.out.print("\n");
        }
        System.out.println("---------------------------------------------");
    }

    public void showPath() {
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[i].length; j++) {
                if (curI == i && curJ == j) {
                    System.out.print("[");
                }
                System.out.print(path[i][j]);
                if (curI == i && curJ == j) {
                    System.out.print("]");
                }
                System.out.print("\t");
            }
            System.out.print("\n");
        }
        System.out.println("---------------------------------------------");
    }

    public void setCurPos(int[] pos) {
        this.curI = pos[0];
        this.curJ = pos[1];
        path[curI][curJ] = 1;
    }

    public int[][] getSurroundings(int[] pos) {
        int[][] surroundings = new int[8][2];
        surroundings[0] = pos[0] > 0 ? new int[]{pos[0] - 1, pos[1]} : new int[]{-1, -1};
        surroundings[1] = pos[0] > 0 && pos[1] < size - 1 ? new int[]{pos[0] - 1, pos[1] + 1} : new int[]{-1, -1};
        surroundings[2] = pos[1] < size - 1 ? new int[]{pos[0], pos[1] + 1} : new int[]{-1, -1};
        surroundings[3] = pos[0] < size - 1 && pos[1] < size - 1 ? new int[]{pos[0] + 1, pos[1] + 1} : new int[]{-1, -1};
        surroundings[4] = pos[0] < size - 1 ? new int[]{pos[0] + 1, pos[1]} : new int[]{-1, -1};
        surroundings[5] = pos[0] < size - 1 && pos[1] > 0 ? new int[]{pos[0] + 1, pos[1] - 1} : new int[]{-1, -1};
        surroundings[6] = pos[1] > 0 ? new int[]{pos[0], pos[1] - 1} : new int[]{-1, -1};
        surroundings[7] = pos[0] > 0 && pos[1] > 0 ? new int[]{pos[0] - 1, pos[1] - 1} : new int[]{-1, -1};
        return surroundings;
    }

    public int[] getCurPos() {
        return new int[]{curI, curJ};
    }

    public int[] getPrevPos() {
        return new int[]{prevI, prevJ};
    }

    public void move(int[] pos) {
        prevI = curI;
        prevJ = curJ;
        curI = pos[0];
        curJ = pos[1];
        path[curI][curJ] = 1;
    }

    public boolean move(int direction) {
        int[][] surroundings = getSurroundings(getCurPos());
        int[] pos = surroundings[direction];
        if (pos[0] > -1 && pos[1] > -1) {
            move(pos);
            return true;
        } else {
            return false;
        }
    }

    public int[] getMinNeighbour(int[] pos) {
        int[][] surroundings = getSurroundings(pos);
        return getMinNeighbour(surroundings);
    }

    public int[] getMinNeighbour(int[][] surroundings) {
        int direction = -1;
        int i = -1;
        int j = -1;
        double min = 100D;
        for (int k = 0; k < 8; k++) {
            int[] _pos = surroundings[k];
            if (_pos[0] > -1 && _pos[1] > -1 && min > grd[_pos[0]][_pos[1]]) {
                min = grd[_pos[0]][_pos[1]];
                direction = k;
                i = _pos[0];
                j = _pos[1];
            }
        }
        return new int[]{i, j, direction};
    }

    public void setValue(int[] pos, double value) {
        grd[pos[0]][pos[1]] = value;
    }

    public double getValue(int[] pos) {
        return grd[pos[0]][pos[1]];
    }

    public void setPrevValuePercentage(double percentage) {
        double val = getValue(getPrevPos());
        if (val > 0.5) {
            setValue(getPrevPos(), val * percentage);
        }
    }

    public void setSurroundingValuesPercentageExceptPrevNext(double percentage) {
        int[][] surroundings = getSurroundings(getCurPos());
        int[] minNeighbour = getMinNeighbour(surroundings);
        for (int k = 0; k < 8; k++) {
            int[] pos = surroundings[k];
            if (pos[0] > -1 && pos[1] > -1) {
                if (!(pos[0] == prevI && pos[1] == prevJ)
                    && !(pos[0] == minNeighbour[0] && pos[1] == minNeighbour[1])) {
                    double val = getValue(pos(pos[0], pos[1]));
                    if (val > 0.5) {
                        setValue(pos(pos[0], pos[1]), val * percentage);
                    }
                }
            }
        }
    }

    public void showPosition(int[] pos) {
        System.out.println("[" + pos[0] + ", " + pos[1] + "]");
    }
}
