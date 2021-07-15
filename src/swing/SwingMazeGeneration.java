package swing;

import logic.Maze;
import logic.Prims;

public class SwingMazeGeneration{

    private static final int ROWS = 41, COLUMNS = 29;
    private static final long DELAY = 10;

    public SwingMazeGeneration() {
        Maze model = new Maze(ROWS, COLUMNS);
        View view = new View(model);
        view.addActionListener(e->{
             new Prims(model.getGrid()).setDelay(DELAY).execute();
        });
        view.showView();
    }

    public static void main(String[] args) {

        new SwingMazeGeneration();
    }
}


