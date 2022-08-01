import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
  public static void main(String[] args) {
    var champion = "";
    double count = 1;
    while (!StdIn.isEmpty()) {
        var temp = StdIn.readString();
        var possibility = 1 / count;
        if (StdRandom.bernoulli(possibility)) {
          champion = temp;
        }
        count++;
    }
    StdOut.println(champion);
  }
}

// javac -cp ".:./algs4.jar" RandomWord.java
// java -cp ".:./algs4.jar" RandomWord
