package packMan;

import java.awt.Color;
import java.awt.Graphics;
import gameFramework.*;

public class Floor extends Entity {
  // Constants
  private final static Color PELLET_COLOR = Color.WHITE;
  private final static Color FLOOR_COLOR = Color.BLACK;
  
  // Variables
  private boolean pellet = false;
  private boolean superPellet = false;
  
  // Constructors
  Floor(int location[]) {
    super(location);
    setColor(FLOOR_COLOR);
  }
  
  Floor(int location[], boolean pellet, boolean superPellet) {
    this(location);
    setPellet(pellet);
    setSuperPellet(superPellet);
  }
  
  // Accessors
  boolean isPellet() { return pellet; }
  
  boolean isSuperPellet() { return superPellet; }
  
  // Mutators
  void setPellet(boolean pellet) {
    this.pellet = pellet;
    if(pellet || superPellet) setColor(PELLET_COLOR);
    else setColor(FLOOR_COLOR);
  }
  
  void setSuperPellet(boolean superPellet) {
    this.superPellet = superPellet;
    if(pellet || superPellet) setColor(PELLET_COLOR);
    else setColor(FLOOR_COLOR);
  }
  
  // Paint Methods
  @Override
  public void paint(Graphics g, int x, int y, int delta) {
    g.setColor(FLOOR_COLOR);
    g.fillRect(x, y, delta, delta);
    if(superPellet) {
      int border = (int)((double)delta * 0.2);
      g.setColor(PELLET_COLOR);
      g.fillOval(x + border, y + border, delta - 2 * border,
          delta - 2 * border);
    } else if(pellet) {
      int border = (int)((double)delta * 0.37);
      g.setColor(PELLET_COLOR);
      g.fillOval(x + border, y + border, delta - 2 * border,
        delta - 2 * border);
    }
  }
}
