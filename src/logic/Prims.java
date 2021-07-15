package logic;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* 
 * A Grid consists of a 2 dimensional array of cells.
 * A Cell has 2 states: Blocked or Passage.
 * 1. Start with a Grid full of Cells in state Blocked.
 * 2. Pick a random Cell
 * 3. Set it to state Passage
 * 4. Compute its frontier cells. A frontier cell of a Cell is a cell with distance 2 in state Blocked and within the grid.
 * 5. While the list of frontier cells is not empty:
 *      I. Pick a random frontier cell from the list of frontier cells.
 *      II. Let neighbors(frontierCell) = All cells in distance 2 in state Passage. 
 *      III. Pick a random neighbor and connect the frontier cell with the neighbor by setting the cell in-between to state Passage. 
 *      IV. Compute the frontier cells of the chosen frontier cell and add them to the frontier list. 
 *      V. Remove the chosen frontier cell from the list of frontier cells.
 *      
 *      Pseudocode from SO by BitTickler Apr 20 '15
 */

public class Prims implements Runnable {
    
    private static final int[][] OFFSETS = { 
            { 0 ,-2}, 
            { 0 , 2}, 
            { 2 , 0}, 
            {-2 , 0}, 
    };
    
    private long delay = 0;
    private Cell[][] grid;
    private Random random;
    
    
    public Prims(Cell[][] grid) {
        this.grid = grid;
        random = new Random();
    }
    
    @Override
    public void run() {
        generateMaze();
    }
    
    public void execute() {
        new Thread(this).start();
    }
    
    public void generateMaze() {
        // 1. is achieved automatically
        
        // randomize x and y coordinate
        int randX = random.nextInt(grid.length);
        int randY = random.nextInt(grid[0].length);
        
        // 2, 3.
        // change from Wall to Passage
        grid[randX][randY].setWall(false);
        
        // 4.
        // find frontiers of randomized cell
        List<Cell> frontierNeighbors = findFrontiers(grid[randX][randY]);
        
        // 5.
        // loop while there are still frontier neighbors
        while(frontierNeighbors.size() >= 0) {
            
            // 5 I.
            // choose random frontier neighbor
            Cell frontierCell = frontierNeighbors.get(random.nextInt(frontierNeighbors.size()));
            
            // 5 II.
            List<Cell> passageNeighbors = findPassages(frontierCell);
            if(passageNeighbors.size() >= 0) {
                // 5 III.
                Cell randomNeighbor = passageNeighbors.get(random.nextInt(passageNeighbors.size()));
                connect(frontierCell, randomNeighbor);
            }
            
            // 5 IV.
            List<Cell> frontiersFrontier = findFrontiers(frontierCell);
            for(Cell nb : frontiersFrontier) 
                frontierNeighbors.add(nb);
            
            // 5 V.
            frontierNeighbors.remove(frontierCell);
            
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
    // find frontiers (neighbors 2 cells from 'cell' that is a Wall)
    private List<Cell> findFrontiers(Cell cell) {
        return neighbors(cell, true);
    }
    
    // find passages (neighbors 2 cells from 'cell' that is a Passage)
    private List<Cell> findPassages(Cell cell) {
        return neighbors(cell, false);
    }
    
    // find NSEW neighbors of the cell that matches the given wall property
    private List<Cell> neighbors(Cell cell, boolean isWall) {
        List<Cell> neighbors = new ArrayList<Cell>();
        for(int[] offset : OFFSETS) {
            int offsettedX = cell.getX() + offset[0];
            int offsettedY = cell.getY() + offset[1];
            if(validXY(offsettedX, offsettedY) && cell.getIsWall() == isWall) {
                neighbors.add(grid[offsettedY][offsettedY]);
            }
        }
        return neighbors;
    }
    
    // connect two cells by making "opening up" their gap and making them passages
    private void connect(Cell x, Cell y) {
        int gapRow = (x.getX() + y.getX())/2;
        int gapCol = (x.getY() + y.getY())/2;
        x.setWall(false);
        y.setWall(false);
        grid[gapRow][gapCol].setWall(false);
    }
    
    // check for valid coords
    private boolean validXY(int x, int y) {
        return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length;
    }
    
    // For algo visualization
    public Prims setDelay(long delay) {
        this.delay = delay;
        return this;
    }
}
