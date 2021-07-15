import java.util.Objects;
import java.beans.PropertyChangeSupport;

public class Cell {
    private final int x, y;
    private boolean isWall;
    private PropertyChangeSupport pcs;
    
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.isWall = false;
    }
    
    
    
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public boolean getIsWall() { return this.isWall; }
    
    public void setWall(boolean boolz) {
        this.isWall = boolz;
    }
    
    public void setPCS(PropertyChangeSupport pcs) {
        this.pcs = pcs;
    }
    
    public void firePCS(String name, Object oldVal, Object newVal) {
        if(pcs != null) {
            pcs.firePropertyChange(name, oldVal, newVal);
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null || getClass() != o.getClass()) return false;
        Cell cel = (Cell)o;
        return x == cel.x && y == cel.y;
    }
    
    @Override
    public String toString() {
        return "{" + (isWall ? "Wall" : "Path") + "x: " + x + ", y: " + y + "}";
    }
    
    
}
