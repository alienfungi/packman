package zaneAI;

public class AStarTree {
  private int[][] adjacent;
  private int[][] points;
  private AStarNode root;
  private AStarNode tail;
  private int start;
  private int finish;
  private boolean solved = false;
  private boolean[] visited;
  private int f;

  public AStarTree(int[][] adjacent, int[][] points) {
    this.visited = new boolean[points.length];
    this.adjacent = adjacent;
    this.points = points;
  }

  public void setEndpoints(int start, int finish) {
    this.start = start;
    this.finish = finish;
    this.root = new AStarNode(start, getDist(points[start], points[finish]));
    this.solved = false;
    for(int i = 0; i < visited.length; ++i)
      this.visited[i] = false;
  }

  private void expandNode(AStarNode node) {
    int i = 0;
    this.visited[node.index] = true;
    while(i < adjacent[node.index].length && adjacent[node.index][i] != -1)
      i++;
    node.next = new AStarNode[i];
    for(i = 0; i < node.next.length; ++i) {
      node.next[i] = new AStarNode(adjacent[node.index][i], node,
        getDist(points[adjacent[node.index][i]], points[finish]));
    }
  }

  public int[] findSolution() {
    tail = root;
    while(!solved) {
      f = -1;
      expandNode(tail);
      test(root);
      if(tail.index == finish) {
        solved = true;
      } else if(f == -1) {
        tail = new AStarNode(start, root, 0);
        solved = true;
      }
    }
    AStarNode current = tail;
    int size = 0;
    while(current != null) {
      current = current.previous;
      size++;
    }
    int[] solution = new int[size];
    current = tail;
    for(int i = size - 1; i >= 0; i--) {
      solution[i] = current.index;
      current = current.previous;
    }
    return solution;
  }

  private void test(final AStarNode current) {
    if(current.next == null) {
      if((this.f < 0 || current.f < this.f) && !this.visited[current.index]) {
        this.tail = current;
        this.f = current.f;
      }
    } else {
      for(int i = 0; i < current.next.length; i++) { test(current.next[i]); }
    }
  }

  private int getDist(int[] p1, int[] p2) {
    int dist = 0;
    for(int i = 0; i < p1.length; ++i) {
      dist += Math.abs(p2[i] - p1[i]);
    }
    return dist;
  }

  class AStarNode {
    int index = 0; // index of the point to look up coords
    AStarNode[] next = null; // links to expanded adjacent points
    AStarNode previous = null; // link to where this point was expanced from
    int g = 0; // cost to get to this point
    int f; // estimate of value of this point f = g + h

    AStarNode(int index, int h) {
      this.index = index;
      this.f = h;
    }
    AStarNode(int index, AStarNode previous, int h) {
      this.previous = previous;
      this.g = previous.g + g;
      this.index = index;
      this.f = this.g + h;
    }
  } // end class AStarNode
} // end class AStarTree
