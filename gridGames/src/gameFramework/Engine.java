package gameFramework;

import java.util.Date;
import java.util.Iterator;

public class Engine implements Runnable {
  private boolean alive = false;
  private final Game game;
  private final Display display;
  private int turnFrequency = 500; // milliseconds
  private final int DESIRED_SLEEP = 25; // milliseconds

  public Engine(final Game game, final Display display) {
    this.display = display;
    this.game = game;
    turnFrequency = game.getTurnFrequency();
  }
  
  public void markForDeath() {
    display.markForDeath();
    alive = false;
  }
  
  @Override
  public void run() {
    alive = true;
    boolean paused = game.getPaused();
    int sleepTime = DESIRED_SLEEP;
    double tickFraction = (double)DESIRED_SLEEP / (double)turnFrequency;
    Date date = new Date();
    long now = date.getTime();
    long previous = now;
    
    /* tickFraction shouldn't change after this point unless manually set for
     * preference purposes.  Instead, sleepTime should be adjusted to match
     * intended tickFraction. 
     */
    while(alive) {
      
      // DELAY
      try {
        Thread.sleep(sleepTime);
      } catch(Exception ex) { }
      
      paused = game.getPaused();

      // IF NOT PAUSED
      if(!paused) {
        Iterator<Person> iterator = game.getQueue().iterator();
        while(iterator.hasNext()) {
          Person current = iterator.next();
          if(current.isReady(tickFraction)) {
            int[] moveTo = current.getQueue().remove();
            game.getPlace().getMap().setEntity(null, current.getLocation());
            game.getPlace().getMap().setEntity(current, moveTo);
            current.setLocation(moveTo);
            if(game.checkMap() != 0) break;
          }
//          iterator.nextNode();
        }
      } // END "IF NOT PAUSED"
      
      display.repaint();
      
      /* compare actual iteration time vs desired iteration time and adjust
       * next sleep timer accordingly.
       */
      date = new Date();
      now = date.getTime();
      sleepTime -= now - previous - DESIRED_SLEEP;
      previous = now;

    } // end engine loop
    
    if(game != null) game.setPaused(true);
  }
}
