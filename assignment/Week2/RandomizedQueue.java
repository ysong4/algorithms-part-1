import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private Item[] collection = null;
  private int count = 0;
  private int capacity = 0;
  // The index for the next new value
  private int next = 0;

  // construct an empty randomized queue
  public RandomizedQueue() {
  }

  // is the randomized queue empty?
  public boolean isEmpty() {
    return this.size() == 0;
  }

  // return the number of items on the randomized queue
  public int size() {
    return this.count;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (this.isEmpty()) {

      this.capacity = 2;

      this.count = 1;
      this.collection = (Item[]) new Object[this.capacity];
      this.collection[0] = item;
      this.next = 1;

    } else {

      this.count++;
      this.collection[this.next] = item;
      this.next++;

    }

    if (this.next >= this.capacity) {
      this.doubleCapacity();
    }

    return;
  }

  private void doubleCapacity() {
    this.capacity = 2 * this.capacity;
    var newCollection = (Item[]) new Object[this.capacity];

    for (var i = 0; i < this.collection.length; i++) {
      newCollection[i] = this.collection[i];
    }

    this.collection = newCollection;
    return;
  }

  // remove and return a random item
  public Item dequeue() {
    if (this.isEmpty()) {
      throw new NoSuchElementException();
    }

    Item removeItem = null;
    while (true) {
      var removeIndex = StdRandom.uniform(this.next);
      if (this.collection[removeIndex] != null) {
        removeItem = this.collection[removeIndex];
        this.collection[removeIndex] = null;
        break;
      }
    }

    this.count--;

    if (this.count * 4 <= this.capacity) {
      this.halfCapacity();
    }

    return removeItem;
  }

  private void halfCapacity() {
    this.capacity = this.capacity / 2;

    Item[] newCollection = (Item[]) new Object[this.capacity];
    var newCollectionNext = 0;
    for (var i = 0; i < this.collection.length; i++) {
      if (this.collection[i] != null) {
        newCollection[newCollectionNext] = this.collection[i];
        newCollectionNext++;
      }
    }

    this.collection = newCollection;
    this.next = newCollectionNext;
    return;
  }

  // return a random item (but do not remove it)
  public Item sample() {
    if (this.isEmpty()) {
      throw new NoSuchElementException();
    }

    Item sampleItem = null;
    while (true) {
      var removeIndex = StdRandom.uniform(this.next);
      if (this.collection[removeIndex] != null) {
        sampleItem = this.collection[removeIndex];
        break;
      }
    }

    return sampleItem;
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  private class RandomizedQueueIterator implements Iterator<Item> {
    private Item[] compactCollection = null;
    // The index of the last value in compactCollection
    private int last = -1;

    public RandomizedQueueIterator() {
      if (count <= 0) {
        return;
      }

      this.compactCollection = (Item[]) new Object[count + 1];
      var next = 0;
      for (var i = 0; i < collection.length; i++) {
        if (collection[i] != null) {
          this.compactCollection[next] = collection[i];
          next++;
        }
      }

      this.last = next - 1;
    }

    public boolean hasNext() {
      return this.last >= 0;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      if (!this.hasNext()) {
        throw new NoSuchElementException();
      }

      int random = StdRandom.uniform(this.last + 1);

      Item item = compactCollection[random];
      compactCollection[random] = compactCollection[this.last];
      this.last--;

      return item;
    }
  }

  // unit testing (required)
  public static void main(String[] args) {
    var randomizedQueue = new RandomizedQueue<Integer>();

    StdOut.println("Usecase 1: Insert 1,2,3,4\n");
    // Insert 1,2,3,4
    randomizedQueue.enqueue(1);
    randomizedQueue.enqueue(2);
    randomizedQueue.enqueue(3);
    randomizedQueue.enqueue(4);
    StdOut.printf("enqueue(): ");
    for (var item : randomizedQueue) {
      StdOut.printf("%d ", item);
    }
    StdOut.printf("\n");

    // Sample
    StdOut.println("sample(): ");
    for (var i = 0; i < 4; i++) {
      int sample = randomizedQueue.sample();
      StdOut.printf("%d ", sample);
    }
    StdOut.printf("\n");

    // Dequeue
    StdOut.println("dequeue(): ");
    for (var i = 0; i < 4; i++) {
      int removed = randomizedQueue.dequeue();
      StdOut.printf("%d ", removed);
    }
    StdOut.printf("\n");

    StdOut.println("\nUsecase 2: Insert 1,2,3,4,5,6,7,8\n");
    // Insert 1,2,3,4,5,6,7,8
    randomizedQueue.enqueue(1);
    randomizedQueue.enqueue(2);
    randomizedQueue.enqueue(3);
    randomizedQueue.enqueue(4);
    randomizedQueue.enqueue(5);
    randomizedQueue.enqueue(6);
    randomizedQueue.enqueue(7);
    randomizedQueue.enqueue(8);
    StdOut.printf("enqueue(): ");
    for (var item : randomizedQueue) {
      StdOut.printf("%d ", item);
    }
    StdOut.printf("\n");

    // Sample
    StdOut.println("sample(): ");
    for (var i = 0; i < 4; i++) {
      int sample = randomizedQueue.sample();
      StdOut.printf("%d ", sample);
    }
    StdOut.printf("\n");

    // Dequeue
    StdOut.println("dequeue(): ");
    for (var i = 0; i < 8; i++) {
      int removed = randomizedQueue.dequeue();
      StdOut.printf("%d ", removed);
    }
    StdOut.printf("\n");

  }

}