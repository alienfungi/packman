package gameFramework;

public class Queue<T> {
  private Node<T> head = null;
  private Node<T> tail = null;

  public boolean isEmpty() { return head == null; }

  public void push(T data) {
    if(tail == null) {
      head = new Node<T>(data);
      tail = head;
    } else {
      tail.next = new Node<T>(data);
      tail = tail.next;
    }
  }

  public T pop() {
    Node<T> temp = head;
    if(head == tail) {
      head = null;
      tail = null;
    } else {
    head = head.next;
    }
    return temp.data;
  }

  public T peek() { return head.data; }

  public Node<T> getHead() { return head; }

  public QueueIterator<T> getIterator() { return new QueueIterator<T>(this); }

  public void purge() { head = null; tail = null; }
}
