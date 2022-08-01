import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

  private Node first = null;
  private Node last = null;
  private int count = 0;

  private class Node {
    Item value;
    Node prev;
    Node next;
  }

  // construct an empty deque
  public Deque() {
  }

  // is the deque empty?
  public boolean isEmpty() {
    return this.count == 0;
  }

  // return the number of items on the deque
  public int size() {
    return this.count;
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (this.isEmpty()) {

      this.first = new Node();
      this.first.value = item;
      this.last = this.first;

    } else {

      var prevFirst = this.first;

      this.first = new Node();
      this.first.value = item;
      this.first.next = prevFirst;

      prevFirst.prev = this.first;

    }

    this.count++;
  }

  // add the item to the back
  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (this.isEmpty()) {

      this.last = new Node();
      this.last.value = item;
      this.first = this.last;

    } else {

      var prevLast = this.last;

      this.last = new Node();
      this.last.value = item;
      this.last.prev = prevLast;

      prevLast.next = this.last;

    }

    this.count++;
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (this.isEmpty()) {
      throw new NoSuchElementException();
    }

    var removedFirst = this.first.value;

    if (this.size() > 1) {

      this.first = this.first.next;
      this.first.prev = null;

    } else {

      this.first = null;
      this.last = null;

    }

    this.count--;
    return removedFirst;
  }

  // remove and return the item from the back
  public Item removeLast() {
    if (this.isEmpty()) {
      throw new NoSuchElementException();
    }

    var removedLast = this.last.value;

    if (this.size() > 1) {

      this.last = this.last.prev;
      this.last.next = null;

    } else {

      this.first = null;
      this.last = null;

    }

    this.count--;
    return removedLast;
  }

  // return an iterator over items in order from front to back
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  private class DequeIterator implements Iterator<Item> {
    private Node current = first;

    public boolean hasNext() {
      return current != null;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      var item = current.value;
      current = current.next;
      return item;
    }
  }

  // unit testing (required)
  public static void main(String[] args) {
    var deque = new Deque<Integer>();

    // Insert 1,2,3,4
    StdOut.println("\nCase-1: Insert 1,2,3,4");

    deque.addFirst(2);
    deque.addFirst(1);
    StdOut.printf("addFirst(): ");
    for (var item : deque) {
      StdOut.printf("%d ", item);
    }
    StdOut.printf("\n");

    deque.addLast(3);
    deque.addLast(4);
    StdOut.printf("addLast(): ");
    for (var item : deque) {
      StdOut.printf("%d ", item);
    }
    StdOut.printf("\n");

    var removed = deque.removeFirst();
    StdOut.printf("The first element removed: %d\n", removed);
    removed = deque.removeFirst();
    StdOut.printf("The first element removed: %d\n", removed);
    StdOut.printf("removeFirst(): ");
    for (var item : deque) {
      StdOut.printf("%d ", item);
    }
    StdOut.printf("\n");

    removed = deque.removeLast();
    StdOut.printf("The last element removed: %d\n", removed);
    removed = deque.removeLast();
    StdOut.printf("The last element removed: %d\n", removed);
    StdOut.printf("removeLast(): ");
    for (var item : deque) {
      StdOut.printf("%d ", item);
    }
    StdOut.printf("\n");
    
    // Insert 1,2
    StdOut.println("\nCase-2: Insert 1,2");
    
    deque.addFirst(1);
    StdOut.printf("addFirst(): ");
    for (var item : deque) {
      StdOut.printf("%d ", item);
    }
    StdOut.printf("\n");

    deque.addLast(2);
    StdOut.printf("addLast(): ");
    for (var item : deque) {
      StdOut.printf("%d ", item);
    }
    StdOut.printf("\n");

    removed = deque.removeFirst();
    StdOut.printf("The first element removed: %d\n", removed);
    StdOut.printf("removeFirst(): ");
    for (var item : deque) {
      StdOut.printf("%d ", item);
    }
    StdOut.printf("\n");

    removed = deque.removeLast();
    StdOut.printf("The last element removed: %d\n", removed);
    StdOut.printf("removeLast(): ");
    for (var item : deque) {
      StdOut.printf("%d ", item);
    }
    StdOut.printf("\n");
  }

}