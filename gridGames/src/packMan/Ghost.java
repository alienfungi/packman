package packMan;

import java.awt.*;
import java.util.*;

public class Ghost extends PackPerson {
  // Constants
  private final static Color SCARED_COLOR = Color.BLUE;
  private final static Color SCARED_BORDER_COLOR = Color.WHITE;
  private final static Color BORDER_COLOR = Color.BLACK;
  private final static double SCARED_SPEED = 0.4;
  private final static double DEFAULT_SPEED = 0.9;
  private final static String[] TYPES =
    {"A* Path", "A* Move", "Dumb Greedy", "True Greedy", "Random", "Immobile"};
  private final static Color[] COLORS =
    {Color.RED, Color.GREEN, Color.CYAN, new Color(128, 0, 255), Color.PINK,
    Color.GRAY};
  
  // Variables
  private boolean scared = false;
  private int type = 4;
  
  // Constructors
  Ghost(int[] location, int type, PackManGame game) {
    super(location, game);
    setType(type);
  }

  // Mutators
  void setScared(boolean scared) { this.scared = scared; }

  void setType(int type) {
    this.type = type;
    setColor(COLORS[type]);
  }

  // Accessors
  boolean isScared() { return scared; }
  
  static String[] getTypes() { return TYPES; }
  
  int getType() { return type; }
  
  @Override
  public double getSpeed() {
    if(scared) {
      return SCARED_SPEED;
    } else {
      return DEFAULT_SPEED;
    }
  }

  // Private Methods
  @Override
  public void update() {
    Random random = new Random();
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
        getQueue().push(move);
        done = true;
      } else {
        if(scared) { // Scared ghost
          setDirection(random.nextInt(4) + 1);
        } else if(TYPES[type].equals("A* Path")) { // A* ghost, delayed
          aStarPath();
          done = true;
        } else if(TYPES[type].equals("A* Move")) { // A* ghost
          aStarMove();
          done = true;
        } else if(TYPES[type].equals("Dumb Greedy")) { // Dumb Greedy ghost
          dumbGreedyMove();
          done = true;
        } else if(TYPES[type].equals("Random")) { // Random ghost
          setDirection(random.nextInt(4) + 1);
        } else if(TYPES[type].equals("Immobile")) { // Stationary ghost
          setDirection(0);
        } else if(TYPES[type].equals("True Greedy")) { // True Greedy ghost
          trueGreedyMove();
          done = true;
        }
      }
    }
  }

  /** Calculate distance between two points */
  private int dist(int i, int j) {
    int distance = 0;
    for(int k = 0; k < 2; ++k) {
      distance += Math.abs(locations()[i][k] - locations()[j][k]);
    }
    return distance;
  }
  
  private void aStarMove() {
    if(getQueue().isEmpty()) {
      int a = coordToIndex()[getLocation(1)][getLocation(2)];
      int b = coordToIndex()[packMan().getLocation(1)][packMan().getLocation(2)];
      aStar().setEndpoints(a, b);
      int[] soln = aStar().findSolution();
      getQueue().push(new int[] {
        getLocation(0),
        locations()[soln[1]][0],
        locations()[soln[1]][1]
      });
    }
  }

  private void aStarPath() {
    if(getQueue().isEmpty()) {
      int a = coordToIndex()[getLocation(1)][getLocation(2)];
      int b = coordToIndex()[packMan().getLocation(1)][packMan().getLocation(2)];
      aStar().setEndpoints(a, b);
      int[] soln = aStar().findSolution();
      for(int i = 1; i < soln.length; ++i) {
        getQueue().push(new int[] {
          getLocation(0),
          locations()[soln[i]][0],
          locations()[soln[i]][1]
        });
      }
    }
  }

  private void dumbGreedyMove() {
    int i = 0;
    int[] move = new int[getLocation().length];
    for(int j = 0; j < move.length; ++j)
      move[j] = getLocation(j);
    int a = coordToIndex()[getLocation(1)][getLocation(2)];
    int b = coordToIndex()[packMan().getLocation(1)][packMan().getLocation(2)];
    while(i < adjacency()[a].length && adjacency()[a][i] != -1) {
      if(dist(adjacency()[a][i], b) < dist(a, b)) {
        for(int j = 1; j < move.length; ++j)
          move[j] = locations()[adjacency()[a][i]][j - 1];
        break;
      }
      ++i;
    }
    getQueue().push(move);
    return;
  }

  private void trueGreedyMove() {
    int i = 0;
    int[] move = new int[getLocation().length];
    int temp = -1;
    int tempDist = -1;
    Random random = new Random();
    for(int j = 0; j < move.length; ++j)
      move[j] = getLocation(j);
    int a = coordToIndex()[getLocation(1)][getLocation(2)];
    int b = coordToIndex()[packMan().getLocation(1)][packMan().getLocation(2)];
    while(i < adjacency()[a].length && adjacency()[a][i] != -1) {
      if(dist(adjacency()[a][i], b) < tempDist || temp == -1) {
        temp = adjacency()[a][i];
        tempDist = dist(temp, b);
      } else if(dist(adjacency()[a][i], b) == tempDist) {
        if(random.nextBoolean()) {
          temp = adjacency()[a][i];
          tempDist = dist(adjacency()[a][i], b);
        }
      }
      ++i;
    }
    for(int j = 1; j < move.length; ++j)
      move[j] = locations()[temp][j - 1];
    getQueue().push(move);
    return;
  }
  
  // Paint Methods
  @Override
  public void paint(Graphics g, int x, int y, int delta) {
    int[] gx = new int[9];
    int[] gy = new int[9];
    gx[0] = 0;
    gy[0] = (int)((double)delta * .2);
    gx[1] = (int)((double)delta * .2);
    gy[1] = 0;
    gx[2] = (int)((double)delta * .8);
    gy[2] = 0;
    gx[3] = delta;
    gy[3] = (int)((double)delta * .2);
    gx[4] = delta;
    gy[4] = delta;
    gx[5] = (int)((double)delta * .7);
    gy[5] = (int)((double)delta * .8);
    gx[6] = (int)((double)delta * .5);
    gy[6] = delta;
    gx[7] = (int)((double)delta * .3);
    gy[7] = (int)((double)delta * .8);
    gx[8] = 0;
    gy[8] = delta;

    for(int i = 0; i < gx.length; i++) {
      gx[i] += x;
      gy[i] += y;
    }

    g.setColor(getColor());

    if(scared) {
      g.setColor(SCARED_COLOR);
      g.fillPolygon(gx, gy, gx.length);
      g.setColor(SCARED_BORDER_COLOR);
      g.drawPolygon(gx, gy, gx.length);
    } else {
      g.setColor(getColor());
      g.fillPolygon(gx, gy, gx.length);
      g.setColor(BORDER_COLOR);
      g.drawPolygon(gx, gy, gx.length);
    }
  }
}
