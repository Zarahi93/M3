public class Islands {
  public static int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) {
      return 0;
    }
    int islandCount = 0;
    int rows = grid.length;
    int cols = grid[0].length;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (grid[i][j] == '1') {
          islandCount++;
          checkLand(grid, i, j, rows, cols);
        }
      }
    }
    printGrid(grid);
    return islandCount;
  }
  private static void checkLand(char[][] grid, int i, int j, int rows, int cols) {
    // Check boundaries and if current cell is land, is is ti not land gets out of the method
    if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] != '1') {
      return;
    }
    grid[i][j] = 'L'; // Mark the cell as visited
    // Check all four directions
    checkLand(grid, i + 1, j, rows, cols); // down
    checkLand(grid, i - 1, j, rows, cols); // up
    checkLand(grid, i, j + 1, rows, cols); // right
    checkLand(grid, i, j - 1, rows, cols); // left
  }
  
  public static void printGrid(char[][] grid) {
        System.out.println("---------------");
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println("---------------");
    }    
  
  public static void main(String[] args){
    char[][] grid = {
      {'1','1','1','1','0'},
      {'1','1','0','1','0'},
      {'1','1','0','0','0'},
      {'0','0','0','0','0'}
    };
    System.out.print(numIslands(grid));
 }
}