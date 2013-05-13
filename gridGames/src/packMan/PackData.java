package packMan;

public class PackData {
  private int turnsTaken;
  private int pelletsRemaining;
  private int livesRemaining;
  private int spawnRate;

  public PackData(int turnsTaken, int pelletsRemaining, int livesRemaining,
                  int spawnRate) {
    this.turnsTaken = turnsTaken;
    this.pelletsRemaining = pelletsRemaining;
    this.livesRemaining = livesRemaining;
    this.spawnRate = spawnRate;
  }

  public int getTurnsTaken() { return turnsTaken; }

  public int getPelletsRemaining() { return pelletsRemaining; }

  public int getLivesRemaining() { return livesRemaining; }

  public int getSpawnRate() { return spawnRate; }

  public boolean isWin() { return pelletsRemaining == 0; }
}
