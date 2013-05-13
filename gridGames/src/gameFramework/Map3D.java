package gameFramework;

public class Map3D implements Map {
  private Entity[][][] entity;
  private int[] location = new int[3];

  public Map3D(final int x, final int y, final int z) {
    this.entity = new Entity[z][y][x];
  }

  public int getSize(final int i) {
    switch (i) {
      case 0: return entity.length;
      case 1: return entity[0].length;
      case 2: return entity[0][0].length;
      default: return 0;
    }
  }

  public Entity getEntity() { return entity[location[0]][location[1]][location[2]]; }

  public void setLocation(final int i, final int v) {
    this.location[i] = v;
  }
  
  public void setEntity(final Entity entity, final int[] location) {
    for(int i = 0; i < location.length; i++) {
      setLocation(i, location[i]);
    }
    setEntity(entity);
  }

  public void setEntity(final Entity entity) {
    this.entity[location[0]][location[1]][location[2]] = entity;
  }
}
