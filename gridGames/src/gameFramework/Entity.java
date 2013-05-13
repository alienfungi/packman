package gameFramework;

import java.awt.*; // Color

/** The basic building block. */

public class Entity {
  // Constants
  private final static Color DEFAULT_COLOR = Color.BLACK;
  
  // Variables
  private int[] location = null;
  private Color color = DEFAULT_COLOR;

  // Constructors
  public Entity() { }

  public Entity(Color color) {
    this.color = color;
  }
  
  public Entity(int[] location) {
    this.location = new int[location.length];
    for(int i = 0; i < location.length; i++) {
      this.location[i] = location[i];
    }
  }

  public Entity(int[] location, Color color) {
    this(location);
    this.color = color;
  }

  // Accessors
  public Color getColor() { return color; }

  public int getLocation(int i) { return location[i]; } // Location in given dimension

  public int[] getLocation() { return location; } // location array
  
  public void paint(Graphics g, int x, int y, int delta) {
	  g.setColor(color);
	  g.fillRect(x, y, delta, delta);
  }

  // Mutators
  public void setLocation(int[] location) {
    for(int i = 0; i < location.length; ++i) {
      this.location[i] = location[i];
    }
  }

  public void setLocation(int i, int value) { this.location[i] = value; }

  public void setColor(Color color) { this.color = color; }
}
