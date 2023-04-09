package at.fhooe.swe4.queues;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapTest {

  private Heap<Integer> hq; // heap based queue

  @BeforeEach
  void setUp() {
    hq = new Heap<>();
    System.out.println("heap queue set up");
  }

  @AfterEach
  void tearDown() {
    hq = null;
    System.out.println("heap queue released");
  }

  @Test
  void isEmpty() {
  }

  @Test
  void peek() {
  }

  @Test
  void enqueue() {
  }

  @Test
  void dequeue() {
  }

  @Test
  void testToString() {
  }

  @Test
  void main() {
  }
}