package gameFramework;

public interface Map {
  Entity getEntity(); // Returns contents of pointer
  int getSize(final int i); // Returns the size of the given dimension
  void setEntity(final Entity entity);
  void setEntity(final Entity entity, final int[] location);
  void setLocation(final int i, final int v); // Change pointer index i to value v
}
