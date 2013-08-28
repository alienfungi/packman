package packMan;

import java.awt.Color;
import java.awt.Graphics;

public class PackMan extends PackPerson {
  // Constants
  private final static Color DEFAULT_COLOR = Color.YELLOW;
  
  // Variables
  private int packDir = 0;
  private int mouthDir = 0;
  private int score = 0;
  
  // Constructors
  PackMan(int location[], PackManGame game) {
    super(location, DEFAULT_COLOR, game);
  }
  
  // Public methods
  public int getScore() { return score; }
  
  // Package private methods
  void augmentScore(int amount) {
    score += amount;
    if(score < 0) score = 0;
  }
  
  // Private Methods
  public void update() {
    int temp = 0;
    boolean done = false;
    while(!done) {
      int[] move = new int[3];
      for(int i = 0; i < move.length; ++i) {
        move[i] = getLocation(i);
      }
      int dir = getDirection();
      switch(dir) {
        case 1: move[1] += -1;
                break;
        case 2: move[2] += 1;
                break;
        case 3: move[1] += 1;
                break;
        case 4: move[2] += -1;
                break;
        case -1:move[1] = -1;
                break;
        default:
      }
      if(isLegal(move, dir)) {
        getQueue().add(move);
        done = true;
        if(dir != 0) mouthDir = dir;
        if(temp == 0)
          packDir = getDirection();
        else {
          packDir = getDirection();
          setDirection(temp);
        }
      } else if(temp == 0) {
        temp = getDirection();
        setDirection(packDir);
        packDir = temp;
      } else {
        setDirection(0);
        packDir = 0;
      }
    }
  }

  // Paint Methods
  @Override
  public void paint(Graphics g, int x, int y, int delta) {
    int angle;
    int dir = 90 * mouthDir;
    if(dir % 180 == 0)
      dir += 180;
    if(getShift() > 0.5) {
      angle = 270 + (int)(180.0 * (getShift() - 0.5));
      dir += 45 - (int)(90.0 * (getShift() - 0.5));
    } else {
      angle = 360 - (int)(180.0 * getShift());
      dir += (int)(90.0 * getShift());
    }
    g.setColor(getColor());
    g.fillArc(x, y, delta, delta, dir, angle);
  }
}
