package gameFramework;

public class Stack<T> {
  private Node<T> head = null;

  public boolean isEmpty() { return head == null; }

  public void push(T data) {
    Node<T> temp = new Node<T>();
    temp.data = data;
    temp.next = head;
    head = temp;
  }

  public T pop() {
    Node<T> temp = head;
    head = head.next;
    return temp.data;
  }

  public T peek() { return head.data; }

  public Node<T> getHead() { return head; }

  public StackIterator<T> getIterator() { return new StackIterator<T>(this); }
}
