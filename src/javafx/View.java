package javafx;

import java.beans.PropertyChangeSupport;

import logic.Cell;
import logic.Maze;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class View {
    private static final int GAP = 2;
    private final Pane panel;
    private final Button newMaze;

    View(Maze model) {

        Cell[][] cells = model.getGrid();
        GridPane grid = new GridPane();
        grid.setVgap(GAP);  grid.setHgap(GAP);
        grid.setStyle("-fx-border-color: black ; -fx-border-width:"+ GAP);

        CellView[][] cellViews = new CellView[cells.length][cells[0].length];
        for(int row=0; row <cellViews.length; row++) {
            for(int col=0; col<cellViews[row].length; col++) {
                CellView cellView = new CellView(cells[row][col]);
                cellViews[row][col] = cellView;
                grid.getChildren().add(cellView);
                GridPane.setConstraints(cellView, col, row);
            }
        }

        newMaze = new Button("Generate Maze");
        panel = new BorderPane(grid, null, null, newMaze, null);
    }

    void addActionListener(EventHandler<ActionEvent> handler){
        newMaze.setOnAction(handler);
    }

    void showView(Stage stage){

        stage.setTitle("Prime's Algorithm Demo");
        Scene scene = new Scene(panel);
        stage.setScene(scene);
        stage.show();
    }

    public Pane getPanel() {
        return panel;
    }
}

class CellView extends Label {

    private static int CELL_H =15, CELL_W = 15;
    private final Cell cell;
    private final PropertyChangeSupport pcs;

    CellView(Cell cell) {
        this.cell = cell;
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(evt -> {
            //change background color
            Platform.runLater(()-> setStyle("-fx-background-color:"+  (isWall() ?  "black" : "white")  +";"));
        });
        cell.setPCS(pcs);
        setPrefSize(CELL_W, CELL_H);
    }

    int getRow() {
        return cell.getX();
    }

    int getColumn() {
        return cell.getY();
    }

    void setWall(boolean isWall) {
        cell.setWall(isWall);
    }

    boolean isWall() {
        return cell.getIsWall();
    }

    @Override
    public String toString() {
        return cell.toString() ;
    }
}
