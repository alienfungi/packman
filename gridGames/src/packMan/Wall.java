package packMan;

import java.awt.Color;
import java.awt.Graphics;
import gameFramework.*;

public class Wall extends Entity {
  // Constants
  private final static Color DEFAULT_COLOR = Color.BLUE;
  private final static Color DEFAULT_COLOR_2 = Color.BLACK;
  
  // Constructors
  public Wall(int location[]) {
    super(location, DEFAULT_COLOR);
  }

  // Paint Methods
  @Override public void paint(Graphics g, int x, int y, int delta) {
    int border = (int)((double)delta * .1);
    g.setColor(getColor());
    g.fillRect(x, y, delta, delta);
    g.setColor(DEFAULT_COLOR_2);
    g.fillRect(x + border, y + border, delta - 2 * border, delta - 2 * border);
  }
}
