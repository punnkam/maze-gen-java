package javafx;

import logic.Maze;
import logic.Prims;
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFxMazeGeneration extends Application {
    private static final int ROWS = 41, COLUMNS = 29;
    private static final long DELAY = 10;

    @Override
    public void start(Stage primaryStage) {

        Maze grid = new Maze(ROWS, COLUMNS);
        View view = new View(grid);
        view.addActionListener(e->{
             new Prims(grid.getGrid()).setDelay(DELAY).execute();
        });
        view.showView(primaryStage);
    }

    public static void main(String[] args) {
        launch(null);
    }
}
