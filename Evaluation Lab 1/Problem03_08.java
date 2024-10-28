import java.util.*;
import java.io.*;

public class Problem03_08 {
    static int rows, cols;
    static int[][] grid;
    static boolean[][] visited;
    static int[] dx = { -1, 0, 1, 0 };
    static int[] dy = { 0, 1, 0, -1 };
    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    private static Point findStart() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }
    private static int dfs(int x, int y) {
        visited[x][y] = true;
        int perimeter = 0;
        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];
            if ((newX < 0 || newX >= rows || newY < 0 || newY >= cols) || grid[newX][newY] == 0) {
                perimeter++;
            } else if (!visited[newX][newY] && grid[newX][newY] == 1) {
                perimeter += dfs(newX, newY);
            }
        }

        return perimeter;
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        try {
            File file = new File("input3.txt");
            Scanner scanner = new Scanner(file);

            rows = scanner.nextInt();
            cols = scanner.nextInt();
            scanner.nextLine();

            grid = new int[rows][cols];
            visited = new boolean[rows][cols];

            for (int i = 0; i < rows; i++) {
                String[] line = scanner.nextLine().split(",");
                for (int j = 0; j < cols; j++) {
                    grid[i][j] = Integer.parseInt(line[j]);
                }
            }

            Point start = findStart();
            if (start != null) {
                int perimeter = dfs(start.x, start.y);
                System.out.println(perimeter);
            } else {
                System.out.println(0);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    
    
}