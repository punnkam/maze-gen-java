package swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import logic.Cell;
import logic.Maze;

public class View {
    private static final int GAP = 2;
    private final JPanel panel;

    private final JButton newMaze;

    View(Maze model) {

        Cell[][] cells = model.getGrid();
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(cells.length, cells[0].length, GAP, GAP));
        grid.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));

        CellView[][] cellViews = new CellView[cells.length][cells[0].length];
        for(int row=0; row <cellViews.length; row++) {
            for(int col=0; col<cellViews[row].length; col++) {
                CellView cellView = new CellView(cells[row][col]);
                cellViews[row][col] = cellView;
                grid.add(cellView);
            }
        }

        newMaze = new JButton("Generate Maze");

        panel = new JPanel(new BorderLayout(GAP, GAP));
        panel.add(grid, BorderLayout.CENTER);
        panel.add(newMaze,  BorderLayout.SOUTH);

    }

    void addActionListener(ActionListener l){
        newMaze.addActionListener(l);
    }

    void showView(){

        JFrame frame = new JFrame("Prime's Algorithm Demo");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.add(getPanel());
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getPanel() {
        return panel;
    }
}

class CellView extends JLabel {

    private static int CELL_H =15, CELL_W = 15;
    private final Cell cell;
    private final PropertyChangeSupport pcs;

    CellView(Cell cell) {
        this.cell = cell;
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(evt -> {
            //change background color
            SwingUtilities.invokeLater(()->setBackground(isWall() ?  Color.BLACK : Color.WHITE));
        });
        cell.setPCS(pcs);
        setPreferredSize(new Dimension(CELL_H , CELL_W));
        setOpaque(true);
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

