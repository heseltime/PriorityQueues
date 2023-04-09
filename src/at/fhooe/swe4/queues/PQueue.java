package at.fhooe.swe4.queues;
public interface PQueue<T extends Comparable<T>> {

  boolean isEmpty();
  T peek();
  void enqueue(T value);
  T dequeue();
}
