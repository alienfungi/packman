package gameFramework;

import java.awt.*;

public class Person extends Entity {
  // Constants
  private final static Color DEFAULT_COLOR = Color.WHITE;
  
  // Variables
  private Queue<int[]> queue = new Queue<int[]>();
  private int direction = 0;
  private double speed = 1.0;
  private double shift = 0;

  // Constructors
  public Person(int[] location, Color color, int direction) {
    super(location, color);
    this.direction = direction;
  }

  public Person(int[] location, Color color) { this(location, color, 0); }
  
  public Person(int[] location, int direction) {
    this(location);
    this.direction = direction;
  }
  
  public Person(int[] location) { this(location, DEFAULT_COLOR); }

  // Mutators
  public void setActionQueue(Queue<int[]> queue) { this.queue = queue; }

  public void setDirection(int direction) { this.direction = direction; }
  
  public void setSpeed(double speed) { this.speed = speed; }
  
  public void setShift(double shift) { this.shift = shift; }
  
  public boolean isReady(double tickFraction) {
    boolean ready = false;
    shift += getSpeed() * tickFraction;
    if(queue.isEmpty()) {
      update();
    }
    if(shift >= 1.0) {
      ready = true;
      shift -= 1.0;
    }
    return ready;
  }
  
  public void update() { }

  // Accessors
  public Queue<int[]> getQueue() { return queue; }

  public int getDirection() { return direction; }
  
  public double getSpeed() { return speed; }
  
  public double getShift() { return shift; }
  
  @Override public void paint(Graphics g, int x, int y, int delta) {
    g.setColor(getColor());
    g.fillOval(x, y, delta, delta);
  }
}
