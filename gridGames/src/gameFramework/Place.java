package gameFramework;

import java.awt.Color;

public class Place extends Entity {
  // Constants
  final static Color DEFAULT_COLOR = Color.green;
  
  // Variables
  private Map map;

  // Constructors
  public Place(Map map) {
    super(DEFAULT_COLOR);
    this.map = map;
  }
  
  public Place(int location[], Map map) {
    this(location, DEFAULT_COLOR, map);
  }
  
  public Place(int location[], Color color, Map map) {
    super(location, color);
    this.map = map;
  }

  // Accessors
  public Map getMap() { return map; }
}
