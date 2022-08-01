import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
  public static void main(String[] args) {

    var k = Integer.parseInt(args[0]);
    var randomizedQueue = new RandomizedQueue<String>();

    while (!StdIn.isEmpty()) {
      var temp = StdIn.readString();
      randomizedQueue.enqueue(temp);
    }

    for (var i = 0; i < k; i++) {
      var temp = randomizedQueue.dequeue();
      StdOut.println(temp);
    }

  }
}

// javac -cp ".:../algs4.jar" Permutation.java
// java -cp ".:../algs4.jar" Permutation 3 < distinct.txt