package engine.resources;

import java.util.Random;

public class RandomMapGenerator {
    private final int seed;
    private final int row;
    private final int column;
    private final int[][] mapLayout;
    private final int noOfIteration = 8;
    Random random;

    public RandomMapGenerator(int seed, int height, int width) {
        this.seed = seed;
        this.row = height;
        this.column = width;
        random = new Random();
        random.setSeed(seed);
        this.mapLayout = new int[row][column];
        setMapLayout();
    }

    public RandomMapGenerator(int seed, int dim) {
        this.seed = seed;
        this.row = dim;
        this.column = dim;
        random = new Random();
        random.setSeed(seed);
        this.mapLayout = new int[row][column];
        setMapLayout();
    }

    public void setMapLayout() {
        for (int i = 0; i < column; i++) {
            mapLayout[0][i] = 5;
            mapLayout[row - 1][i] = 5;
        }

        for (int i = 0; i < row; i++) {
            mapLayout[i][0] = 5;
            mapLayout[i][column - 1] = 5;
        }

        for (int currentIteration = 0; currentIteration < noOfIteration; currentIteration++) {
            if (currentIteration % 4 == 0) {
                int colNo = Math.abs(random.nextInt()) % column;
                System.out.println(colNo);
                for (int i = 1; i < row; i++) {
                    if (mapLayout[i][colNo] == 0) {
                        mapLayout[i][colNo] = 1;
                    } else {
                        if (i != 1) {
                            mapLayout[i - 1][colNo] = 0;
                        }
                        break;
                    }
                }
            } else if (currentIteration % 4 == 1) {
                int rowNo = Math.abs(random.nextInt()) % row;
                System.out.println(rowNo);
                for (int i = 1; i < column; i++) {
                    if (mapLayout[rowNo][i] == 0) {
                        mapLayout[rowNo][i] = 1;
                    } else {
                        if (i != 1) {
                            mapLayout[rowNo][i - 1] = 0;
                        }
                        break;
                    }
                }
            } else if (currentIteration % 4 == 2) {
                int colNo = Math.abs(random.nextInt()) % column;
                System.out.println(colNo);
                for (int i = row - 2; i >= 0; i--) {
                    if (mapLayout[i][colNo] == 0) {
                        mapLayout[i][colNo] = 1;
                    } else {
                        if (i != row - 2) {
                            mapLayout[i + 1][colNo] = 0;
                        }
                        break;
                    }
                }
            } else if (currentIteration % 4 == 3) {
                int rowNo = Math.abs(random.nextInt()) % row;
                System.out.println(rowNo);
                for (int i = column - 2; i >= 0; i--) {
                    if (mapLayout[rowNo][i] == 0) {
                        mapLayout[rowNo][i] = 1;
                    } else {
                        if (i != column - 2) {
                            mapLayout[rowNo][i + 1] = 0;
                        }
                        break;
                    }
                }
            }
        }

    }

    public int[][] getMapLayout() {
        return mapLayout;
    }
}
