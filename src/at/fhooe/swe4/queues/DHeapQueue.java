package at.fhooe.swe4.queues;

import java.util.*;

import static java.lang.System.out;
import static java.lang.System.setOut;

public class DHeapQueue<T extends Comparable<T>> implements PQueue<T> {
  private List<T> values;

  private int d;

  public DHeapQueue(int k) {
    values = new ArrayList<>();
    d = k;
  }

  private boolean less(T a, T b) {
    return a.compareTo(b) < 0;
  }

  public boolean isEmpty() {
    return values.isEmpty();
  }

  @Override
  public T peek() { return values.isEmpty() ? null : values.get(0); }

  @Override
  public void enqueue(T value) {
    values.add(value);
    upheap();
  }

  @Override
  public T dequeue() {
    if (isEmpty())
      throw new NoSuchElementException("cannot dequeue from empty Heap");
    T max = values.get(0);
    // restore
    int last = values.size() - 1;
    values.set(0, values.get(last));
    values.remove(last);
    downheap(0);
    return max;
  }

  private int parent(int i) { return (i-1)/d; }

/*
* Gives the indices of all the child nodes if these have child nodes (are not leaves),
* otherwise marks them -1 in the array returned.
*
* @param i: index being queried
 */
  public int[] children(int i) {
    int[] children = new int[d];
    int h = 0;
    for (int j = i * d + 1; j <= i * d + d; j++) {
      int result = j < values.size() ? j : -1;
      children[h] = result;
      h++;
    }
    return children;
  }

  private void swap(int index, int index2) {
    T valueTemp = values.get(index);
    values.set(index,values.get(index2));
    values.set(index2,valueTemp);
  }

  private void upheap() {
    int newIndex = values.size()- 1;
    int parentIndex = parent(newIndex);

    int currentIndex = newIndex;
    while (parentIndex >= 0) {
      if (less(values.get(parentIndex), values.get(currentIndex))) { // i > i_p
        swap(currentIndex, parentIndex);
        currentIndex = parentIndex; // i = i_p
        parentIndex = parent(currentIndex); // i_p = the new parent
      }
      else {
        break; // value is in the correct place
      }
    }
  }

  private void downheap(int startIndex) {
    // propagate down
    while (true) {
      int[] children = children(startIndex); // array with indices of the children, marked -1 if leaf
      int maxChildIndex = 0;
      for (int i = 0; i < d && i < values.size() - startIndex; i++) {
        T maxChild = values.get(maxChildIndex);
        int currentChildIndex = children[i];
        if (currentChildIndex != -1 && less(maxChild, values.get(currentChildIndex))) {
          maxChildIndex = currentChildIndex;
        }
      }

      // nothing to do
      if (maxChildIndex == 0) {
        break;
      }

      if (less(values.get(startIndex), values.get(maxChildIndex))) {
        swap(startIndex, maxChildIndex);
      }

      startIndex = maxChildIndex; // set new startindex for new downward propagation
    }
  }

  public int getSize() {
    return values.size();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("DHeapQueue = [");
    for (int i = 0; i<values.size(); i++) {
      if (i>0) sb.append(", ");
      sb.append(values.get(i));
    }
    sb.append("]");
    return sb.toString();
  }

  public static void main(String[] args) {
    Random r = new Random();
    int[] tests = {1000,10000,100000,1000000,10000000};
    System.out.printf("d;size;sec%n"); // d;size;sec
    for (int d = 2; d < 10; d++) {
      DHeapQueue<Integer> dhq = new DHeapQueue<>(d);
      for (int j = 0; j < tests.length; j++) {
        // enqueue measurements
        long start = System.nanoTime();
        for (int i = 0; i < tests[j]; i++) {
          dhq.enqueue(r.nextInt(100));
        }
        long time = System.nanoTime() - start;
        System.out.printf("%d;%d;%f%n", d, tests[j], time/1000000000.0); // d;size;sec

        // dequeue measurements
        /*long start = System.nanoTime();
        for (int i = 0; i < tests[j]; i++) {
          dhq.dequeue();
        }
        long time = System.nanoTime() - start;
        System.out.printf("%d;%d;%f%n", d, tests[j], time/1000000000.0);*/ // d;size;sec
      }
      dhq = null;
    }

  }
}
