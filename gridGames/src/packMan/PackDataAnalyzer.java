package packMan;

import gameFramework.*;

public class PackDataAnalyzer {
  private Queue<PackData> queue;

  // Constructors
  public PackDataAnalyzer() {
    queue = new Queue<PackData>();
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
    QueueIterator<PackData> iterator = queue.getIterator();
    while(!iterator.atEnd()) {
      if(iterator.getCurrent().data.isWin()) {
        total += iterator.getCurrent().data.getTurnsTaken();
        count++;
      }
      iterator.nextNode();
    }
    if(count != 0)
      mean = (double)total / (double)count;
    return mean;
  }

  public double getMeanTurnsLoss() {
    double mean = 0;
    int total = 0;
    int count = 0;
    QueueIterator<PackData> iterator = queue.getIterator();
    while(!iterator.atEnd()) {
      if(!iterator.getCurrent().data.isWin()) {
        total += iterator.getCurrent().data.getTurnsTaken();
        count++;
      }
      iterator.nextNode();
    }
    if(count != 0)
      mean = (double)total / (double)count;
    return mean;
  }

  public double getMeanPelletsRemaining() {
    double mean = 0;
    int total = 0;
    int count = 0;
    QueueIterator<PackData> iterator = queue.getIterator();
    while(!iterator.atEnd()) {
      total += iterator.getCurrent().data.getPelletsRemaining();
      count++;
      iterator.nextNode();
    }
    if(count != 0)
      mean = (double)total / (double)count;
    return mean;
  }

  public int getGamesPlayed() {
    int gamesPlayed = 0;
    QueueIterator<PackData> iterator = queue.getIterator();
    while(!iterator.atEnd()) {
      gamesPlayed++;
      iterator.nextNode();
    }
    return gamesPlayed;
  }

  public int getWinTotal() {
    int winTotal = 0;
    QueueIterator<PackData> iterator = queue.getIterator();
    while(!iterator.atEnd()) {
      if(iterator.getCurrent().data.isWin())
        winTotal++;
      iterator.nextNode();
    }
    return winTotal;
  }

  public int getLossTotal() {
    int lossTotal = 0;
    QueueIterator<PackData> iterator = queue.getIterator();
    while(!iterator.atEnd()) {
      if(!iterator.getCurrent().data.isWin())
        lossTotal++;
      iterator.nextNode();
    }
    return lossTotal;
  }

  public double getMeanLivesRemaining() {
    double mean = 0;
    int total = 0;
    int count = 0;
    QueueIterator<PackData> iterator = queue.getIterator();
    while(!iterator.atEnd()) {
      total += iterator.getCurrent().data.getLivesRemaining();
      count++;
      iterator.nextNode();
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
    queue.push(temp);
  }
}
