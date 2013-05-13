package gameFramework;

public class QueueIterator<T> {
  private Queue<T> queue;
  private Node<T> current;
  private Node<T> previous;

  public QueueIterator(Queue<T> queue) {
    this.queue = queue;
    reset();
  }

  public void reset() {
    current = queue.getHead();
    previous = null;
  }

  public Node<T> nextNode() {
    previous = current;
    current = current.next;
    return current;
  }

  public Node<T> getCurrent() { return current; }

  public T deleteCurrent() {
    T value = current.data;
    if(previous == null) {
      reset();
    } else {
      previous.next = current.next;
      current = current.next;
    }
    return value;
  }

  public boolean atEnd() { return current == null; }
}