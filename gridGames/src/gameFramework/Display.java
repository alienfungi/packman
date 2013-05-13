package gameFramework;

import javax.swing.*;
import java.awt.*;

public class Display extends JPanel implements Runnable {
  /**
	 * 
	 */
  private static final long serialVersionUID = 2712201641354081216L;
  private boolean alive = false;
  private Game game;

  public Display(Game game) {
    this.game = game;
  }
  
  protected void markForDeath() { alive = false; }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    int width = getWidth();
    int height = getHeight();
    Map map = game.getPlace().getMap();
    final int z = map.getSize(0);
    final int y = map.getSize(1);
    final int x = map.getSize(2);
    final int dx = (width / x);
    final int dy = (height / y);
    final int delta = (dx > dy) ? dy : dx;
    Entity entity;
    for(int i = 0; i < z; i++) {
      map.setLocation(0, i);
      for(int j = 0; j < y; j++) {
        map.setLocation(1, j);
        for(int k = 0; k < x; k++) {
          map.setLocation(2, k);
          entity = map.getEntity();
          if(entity != null) {
            int[] coords = new int[] {j, k};
            if(entity instanceof Person && !((Person)entity).getQueue().isEmpty()) {
              Person person = (Person) entity;
              int[] future = person.getQueue().peek();
              
              if(coords[0] == 0 && future[1] == y - 1) {
                int temp = coords[1];
                future[1] = -1;
                shiftCoords(coords, future, person.getShift(), delta);
                entity.paint(g, coords[1], coords[0], delta);
                coords[0] = y;
                coords[1] = temp;
                future[1] = y - 1;
              } else if(coords[1] == 0 && future[2] == x - 1) {
                int temp = coords[0];
                future[2] = -1;
                shiftCoords(coords, future, person.getShift(), delta);
                entity.paint(g, coords[1], coords[0], delta);
                coords[1] = x;
                coords[0] = temp;
                future[2] = x - 1;
              } else if(coords[0] == y - 1 && future[1] == 0) {
                int temp = coords[1];
                future[1] = y;
                shiftCoords(coords, future, person.getShift(), delta);
                entity.paint(g, coords[1], coords[0], delta);
                coords[0] = -1;
                coords[1] = temp;
                future[1] = 0;
              } else if(coords[1] == x - 1 && future[2] == 0) {
                int temp = coords[0];
                future[2] = x;
                shiftCoords(coords, future, person.getShift(), delta);
                entity.paint(g, coords[1], coords[0], delta);
                coords[1] = -1;
                coords[0] = temp;
                future[2] = 0;
              }
              shiftCoords(coords, future, person.getShift(), delta);
            } else {
              coords[0] *= delta;
              coords[1] *= delta;
            }
            entity.paint(g, coords[1], coords[0], delta);
          }
        }
      }
    }
    g.setColor(game.getBackgroundColor());
    g.fillRect(-1 * delta, -1 * delta, (x + 2) * delta, delta);
    g.fillRect(x * delta, 0, delta, (y + 1) * delta);
    g.fillRect(-1 * delta, y * delta, (x + 1) * delta, delta);
    g.fillRect(-1 * delta, 0, delta, y * delta);
  }
  
  private void shiftCoords(int[] coords, int[] future, double shift, int delta) {
    int change[] = new int[] {future[1] - coords[0], future[2] - coords[1]};
    for(int i = 0; i < coords.length; i++) {
      int newShift = (int)(shift * (double)delta);
      coords[i] = (delta * coords[i]) + (newShift * change[i]);
    }
  }

  @Override
  public void run() {
    alive = true;
    while(alive) {
      try {
        Thread.sleep(50L);
      } catch(Exception ex) {}
      this.repaint();
    }
  }
}
