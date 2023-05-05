package at.fhooe.swe4.queues;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DHeapQueueTest {

  public DHeapQueue<Integer> hq; // d-heap-queue but keeping variable names to not have to change the whole file
      // better to be generic in naming these tester variables, perhaps
  public DHeapQueue<String> hq2; // try some different data types for template test

  @BeforeEach
  void setUp() {
    hq = new DHeapQueue<>(3);
    System.out.println("d-heap queue set up");

    hq2 = new DHeapQueue<>(3);
    System.out.println("d-heap queue 2 set up");
  }

  @AfterEach
  void tearDown() {
    hq = null;
    System.out.println("d-heap queue released");

    hq2 = null;
    System.out.println("d-heap queue 2 released");
  }

  @Test
  void isEmpty() {
    // Integer case
    assertEquals(true, hq.isEmpty());
    Random r = new Random();
    for (int i = 0; i<3; i++) {
      hq.enqueue(r.nextInt(100));
    }
    assertEquals(false, hq.isEmpty());

    // alt data types
    assertEquals(true, hq2.isEmpty());
    hq2.enqueue("testString");
    hq2.enqueue("testString2");
    hq2.enqueue("testString3");
    assertEquals(false, hq2.isEmpty());

    // empty both and check is Empty
    for (int i = 0; i<3; i++) {
      hq.dequeue();
      hq2.dequeue();
    }
    assertEquals(true, hq.isEmpty());
    assertEquals(true, hq2.isEmpty());
  }

  @Test
  void peek() {
    // Integers
    Random r = new Random();
    hq.enqueue(101);
    hq.enqueue(101); // test two same values at top of the heap
    for (int i = 0; i<5; i++) {
      hq.enqueue(r.nextInt(100));
    }
    assertEquals(101, hq.peek());

    hq.dequeue();
    // second 101 left
    assertEquals(101, hq.peek());
    hq.dequeue();
    // random values left, hard to test

    // alt data types
    hq2.enqueue("testString");
    hq2.enqueue("testString22");
    hq2.enqueue("testString333");

    // so, how are strings prioritized? Is it by length?
    assertEquals("testString333", hq2.peek());
    // possibly, let's try this differently
    hq2.dequeue();
    hq2.dequeue();
    hq2.dequeue();

    hq2.enqueue("testString4444");
    hq2.enqueue("testString333");
    hq2.enqueue("testString22");
    hq2.enqueue("testString");

    assertEquals("testString4444", hq2.peek());
    // yes, appears to be prioritization by length
    hq2.dequeue();
    assertEquals("testString333", hq2.peek());
    hq2.dequeue();
    assertEquals("testString22", hq2.peek());
    hq2.dequeue();
    assertEquals("testString", hq2.peek());
  }

  @Test
  void enqueue() {
    // standard functionality is already tested by way of tests for isEmpty() and peek()
    // so this focusses on edge cases: Large queues
    Random r = new Random();
    for (int i = 0; i<5000; i++) {
      hq.enqueue(r.nextInt(100));
    }
    assertEquals(hq.getSize(), 5000);

    // strings
    for (int i = 0; i<5000; i++) {
      String rStr = Integer.toString(r.nextInt(100));
      hq2.enqueue("test" + rStr);
    }
    assertEquals(hq2.getSize(), 5000);
  }

  @Test
  void dequeue() {
    // standard functionality is already tested, but let's try lots of dequeue operations
    Random r = new Random();
    for (int i = 0; i<500; i++) {
      hq.enqueue(r.nextInt(100));
    }
    for (int i = 0; i<500; i++) {
      hq.dequeue();
    }
    assertEquals(true, hq.isEmpty());

    // empty queue
    assertThrows(NoSuchElementException.class, () -> hq.dequeue());

    // strings
    for (int i = 0; i<500; i++) {
      String rStr = Integer.toString(r.nextInt(100));
      hq2.enqueue("test" + rStr);
    }
    for (int i = 0; i<500; i++) {
      hq2.dequeue();
    }
    assertEquals(true, hq2.isEmpty());
  }

}