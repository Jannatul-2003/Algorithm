//https://www.hackerearth.com/practice/algorithms/graphs/breadth-first-search/practice-problems/algorithm/flip-grid-c6f88af8/
import java.util.*;

class Grid {
    int _2DArray[][];
    int numOfMoves;
    Grid parent = null;

    Grid(int _2DArray[][]) {
        this._2DArray = _2DArray;
        this.numOfMoves = 0;
    }
}

class FlipRow {
    int rowIndex;
    Grid grid;

    FlipRow(int rowIndex, Grid grid) {
        this.rowIndex = rowIndex;
        this.grid = grid;
    }

    public Grid flipRow() {
        int new2DArray[][] = HelperFunctions.copy2DArray(grid._2DArray);
        for (int j = 0; j < 4; j++) {
            new2DArray[rowIndex][j] = (new2DArray[rowIndex][j] == 0) ? 1 : 0;
        }
        Grid newGrid = new Grid(new2DArray);
        return newGrid;
    }
}

class FlipColumn {
    int colIndex;
    Grid grid;

    FlipColumn(int colIndex, Grid grid) {
        this.colIndex = colIndex;
        this.grid = grid;
    }

    public Grid flipColumn() {
        int new2DArray[][] = HelperFunctions.copy2DArray(grid._2DArray);
        for (int i = 0; i < 4; i++) {
            new2DArray[i][colIndex] = (new2DArray[i][colIndex] == 0) ? 1 : 0;
        }
        Grid newGrid = new Grid(new2DArray);
        return newGrid;
    }
}

class Rotate90 {
    Grid grid;

    Rotate90(Grid grid) {
        this.grid = grid;
    }

    public Grid rotate90() {
        int n = 4;
        int new2DArray[][] = new int[4][4];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 4; j++) {
                new2DArray[j][n - i - 1] = grid._2DArray[i][j];
            }
        }
        Grid newGrid = new Grid(new2DArray);
        return newGrid;
    }
}

class HelperFunctions {
    public static int[][] copy2DArray(int[][] arr) {
        int n = arr.length;
        int m = arr[0].length;
        int newArr[][] = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newArr[i][j] = arr[i][j];
            }
        }
        return newArr;
    }
}

class BFS {
    Grid srcGrid;
    Grid destGrid;

    BFS(Grid srcGrid, Grid destGrid) {
        this.srcGrid = srcGrid;
        this.destGrid = destGrid;
    }

    public int bfs() {
        int n = srcGrid._2DArray.length;// row length
        int m = srcGrid._2DArray[0].length;// column length
        Queue<Grid> queue = new LinkedList<Grid>();
        HashSet<String> visited = new HashSet<String>();
        queue.add(srcGrid);
        while (!queue.isEmpty()) {
            Grid currentGrid = queue.poll();
            // System.out.println(Arrays.deepToString(currentGrid._2DArray)+"
            // "+currentGrid.numOfMoves);
            visited.add(Arrays.deepToString(currentGrid._2DArray));
            if (Arrays.deepEquals(currentGrid._2DArray, destGrid._2DArray)) {
                return currentGrid.numOfMoves;
            }
            for (int i = 0; i < n; i++) {
                FlipRow flipRow = new FlipRow(i, currentGrid);
                Grid newGrid = flipRow.flipRow();
                if (!visited.contains(Arrays.deepToString(newGrid._2DArray))) {
                    newGrid.numOfMoves = currentGrid.numOfMoves + 1;
                    newGrid.parent = currentGrid;
                    queue.add(newGrid);
                }
            }
            // System.out.println("FlipRow done");
            for (int j = 0; j < m; j++) {
                FlipColumn flipColumn = new FlipColumn(j, currentGrid);
                Grid newGrid = flipColumn.flipColumn();
                if (!visited.contains(Arrays.deepToString(newGrid._2DArray))) {
                    newGrid.numOfMoves = currentGrid.numOfMoves + 1;
                    newGrid.parent = currentGrid;
                    queue.add(newGrid);
                }
            }
            // System.out.println("FlipColumn done");
            Rotate90 rotate90 = new Rotate90(currentGrid);
            Grid newGrid = rotate90.rotate90();
            if (!visited.contains(Arrays.deepToString(newGrid._2DArray))) {
                newGrid.numOfMoves = currentGrid.numOfMoves + 1;
                newGrid.parent = currentGrid;
                queue.add(newGrid);
            }
            // System.out.println("Rotate90 done");
        }
        return -1;
    }
}

public class FlipGrid {
    // Main function to handle input and run the BFS
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the source grid (startGrid)
        int[][] srcArray = new int[4][4];
        for (int i = 0; i < 4; i++) {
            String line = scanner.nextLine().trim();
            // Check if the line is valid
            if (line.length() != 4) {
                System.out.println("1.Invalid input. Each line must have exactly 4 characters.");
                return; // Exit the program if input is invalid
            }
            for (int j = 0; j < 4; j++) {
                srcArray[i][j] = line.charAt(j) - '0'; // Convert char to int
            }
        }
        //scanner.nextLine();
        // Read the destination grid (targetGrid)
        int[][] destArray = new int[4][4];
        for (int i = 0; i < 4; i++) {
            String line = scanner.nextLine().trim();
            // Check if the line is valid
            if (line.length() != 4) {
                System.out.println("Invalid input. Each line must have exactly 4 characters.");
                return; // Exit the program if input is invalid
            }
            for (int j = 0; j < 4; j++) {
                destArray[i][j] = line.charAt(j) - '0'; // Convert char to int
            }
        }

        Grid startGrid = new Grid(srcArray);
        Grid targetGrid = new Grid(destArray);
        BFS bfs = new BFS(startGrid, targetGrid);
        int result = bfs.bfs();
        System.out.println(result);

        scanner.close();
    }
}
