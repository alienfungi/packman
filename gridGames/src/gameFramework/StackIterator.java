package gameFramework;

public class StackIterator<T> {
  Stack<T> stack;
  Node<T> current;
  Node<T> previous;

  public StackIterator(Stack<T> stack) {
    this.stack = stack;
    reset();
  }

  public void reset() {
    current = stack.getHead();
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
}