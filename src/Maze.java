import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze {
    private final int WIDTH;
    private final int HEIGHT;
    private Cell[][] grid;
    
    public Maze(int size) {
        WIDTH = size;
        HEIGHT = WIDTH;
        grid = new Cell[WIDTH][HEIGHT];
        fillCells();
    }
    
    public void fillCells() {
        for(int x=0; x<WIDTH; x++) {
            for(int y=0; y<HEIGHT; y++) {
                grid[x][y] = new Cell(x, y);
            }
        }
    }
    
    public Cell[][] getGrid() {
        return grid;
    }
}
