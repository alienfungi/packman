package packMan;

import gameFramework.*;
import java.awt.Color;
import zaneAI.*;

public class PackPerson extends Person {
  // Constants
  private final static int DEFAULT_DIRECTION = 0;
  
  // Variables
  private static PackManGame game;
  private int[] spawn;
  
  // Constructors
  PackPerson(int[] location, PackManGame game) {
    this(location, Color.WHITE, game);
  }
  
  PackPerson(int[] location, Color color, PackManGame game) {
    super(location, color, DEFAULT_DIRECTION);
    PackPerson.game = game;
    spawn = new int[location.length];
    for(int i = 0; i < location.length; i++) {
      spawn[i] = location[i];
    }
  }
  
  void reset() {
    setDirection(0);
    setShift(0);
    setLocation(spawn);
    getQueue().clear();
  }
  
  /** Handle edge wrap and return true for a legal move */
  boolean isLegal(int[] move, int dir) {
    // Edge wrap
    if(move[2] >= mapLayout()[0].length)
      move[2] = 0;
    else if(move[2] < 0)
      move[2] = mapLayout()[0].length - 1;
    else if(move[1] >= mapLayout().length)
      move[1] = 0;
    else if(move[1] < 0)
      move[1] = mapLayout().length - 1;

    // Wall clip
    if(mapLayout()[move[1]][move[2]] % 2 == 0 || dir == 0)
      return true;
    else
      return false;
  }
  
  // Package Private Accessors
  int[][]   mapLayout()    { return game.getMapLayout()[selectedMap()]; }
  int       selectedMap()  { return game.getSelectedMap(); }
  int[][]   coordToIndex() { return game.getCoordToIndex(); }
  int[][]   adjacency()    { return game.getAdjacency(); }
  int[][]   locations()    { return game.getLocations(); }
  AStarTree aStar()        { return game.getAStar(); }
  Person    packMan()      { return game.getPackMan(); }
}
