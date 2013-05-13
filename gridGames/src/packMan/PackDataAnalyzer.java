package packMan;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Iterator;

public class PackDataAnalyzer {
  private Queue<PackData> queue;

  // Constructors
  public PackDataAnalyzer() {
    queue = new LinkedList<PackData>();
  }

  // Accessors
  @Override public String toString() {
    String theString =
      "Turns for Win:     " + getMeanTurnsWin()         + "\n" +
      "Turns for Loss:    " + getMeanTurnsLoss()        + "\n" +
      "Pellets Remaining: " + getMeanPelletsRemaining() + "\n" +
      "Lives Remaining:   " + getMeanLivesRemaining()   + "\n" +
      "Games Played:      " + getGamesPlayed()          + "\n" +
      "Wins:              " + getWinTotal()             + "\n" +
      "Losses:            " + getLossTotal()            + "\n";
    return theString;
  }

  public double getMeanTurnsWin() {
    double mean = 0;
    int total = 0;
    int count = 0;
    Iterator<PackData> iterator = queue.iterator();
    while(iterator.hasNext()) {
      PackData current = iterator.next();
      if(current.isWin()) {
        total += current.getTurnsTaken();
        count++;
      }
    }
    if(count != 0)
      mean = (double)total / (double)count;
    return mean;
  }

  public double getMeanTurnsLoss() {
    double mean = 0;
    int total = 0;
    int count = 0;
    Iterator<PackData> iterator = queue.iterator();
    while(iterator.hasNext()) {
      PackData current = iterator.next();
      if(!current.isWin()) {
        total += current.getTurnsTaken();
        count++;
      }
    }
    if(count != 0)
      mean = (double)total / (double)count;
    return mean;
  }

  public double getMeanPelletsRemaining() {
    double mean = 0;
    int total = 0;
    int count = 0;
    Iterator<PackData> iterator = queue.iterator();
    while(iterator.hasNext()) {
      total += iterator.next().getPelletsRemaining();
      count++;
    }
    if(count != 0)
      mean = (double)total / (double)count;
    return mean;
  }

  public int getGamesPlayed() {
    int gamesPlayed = 0;
    Iterator<PackData> iterator = queue.iterator();
    while(iterator.hasNext()) {
      gamesPlayed++;
      iterator.next();
    }
    return gamesPlayed;
  }

  public int getWinTotal() {
    int winTotal = 0;
    Iterator<PackData> iterator = queue.iterator();
    while(iterator.hasNext()) {
      if(iterator.next().isWin())
        winTotal++;
    }
    return winTotal;
  }

  public int getLossTotal() {
    int lossTotal = 0;
    Iterator<PackData> iterator = queue.iterator();
    while(iterator.hasNext()) {
      if(!iterator.next().isWin())
        lossTotal++;
    }
    return lossTotal;
  }

  public double getMeanLivesRemaining() {
    double mean = 0;
    int total = 0;
    int count = 0;
    Iterator<PackData> iterator = queue.iterator();
    while(iterator.hasNext()) {
      total += iterator.next().getLivesRemaining();
      count++;
    }
    if(count != 0)
      mean = (double)total / (double)count;
    return mean;
  }

  // Mutators
  public void push(PackManGame game) {
    push(
      game.getTime(),
      game.getPelletCount(),
      game.getLifeCount(),
      game.getSpawnRate()
    );
  }

  public void push(int turnsTaken, int pelletsRemaining, int livesRemaining,
                   int spawnRate) {
    PackData temp = new PackData(turnsTaken, pelletsRemaining, livesRemaining,
                                 spawnRate);
    queue.add(temp);
  }
}
