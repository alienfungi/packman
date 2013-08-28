package gameFramework;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;
import netscape.javascript.*;

public abstract class Game {
  // Variables
  private int turnFrequency = 500; // in milliseconds
  private Place place;
  private Queue<Person> queue;
  private boolean paused = true;
  private Color backgroundColor;
  private int width;
  private int height;
  private int scale;
  private JSObject window;
  
  // Constructors
  public Game() {
    queue = new LinkedList<Person>();
  }

  // Accessors
  public final Place getPlace() { return place; }
  public final Queue<Person> getQueue() { return queue; }
  public final boolean getPaused() { return paused; }
  public final Color getBackgroundColor() { return backgroundColor; }
  public final int getWidth() { return width; }
  public final int getHeight() { return height; }
  public final int getScale() { return scale; }
  public final int getTurnFrequency() { return turnFrequency; }
  public abstract int checkMap();
  protected final JSObject getWindow() { return window; }

  
  // Mutators
  protected final void setPlace(Place place) { this.place = place; }
  protected final void setQueue(Queue<Person> queue) { this.queue = queue; }
  public final void setPaused(Boolean paused) { this.paused = paused; }
  protected final void setBackgroundColor(Color color) { this.backgroundColor = color; }
  protected final void setWidth(int width) { this.width = width; }
  protected final void setHeight(int height) { this.height = height; }
  protected final void setScale(int scale) { this.scale = scale; }
  protected final void setTurnFrequency(int turnFrequency) {
      this.turnFrequency = turnFrequency;
  }
  public final void setWindow(JSObject window) { this.window = window; }
}
