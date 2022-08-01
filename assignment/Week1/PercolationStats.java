import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private final double[] result;
  private final int trials;

  private static final double CONFIDENCE_95 = 1.96; 

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }

    this.result = new double[trials];
    this.trials = trials;

    for (var trial = 0; trial < trials; trial++) {

      var percolation = new Percolation(n);
      var count = 0;

      while (!percolation.percolates()) {

        var row = StdRandom.uniform(n) + 1;
        var col = StdRandom.uniform(n) + 1;

        if (!percolation.isOpen(row, col)) {
          percolation.open(row, col);
          count++;
        }

      }

      this.result[trial] = ((double) count) / (n * n);

    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(this.result);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(this.result);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return this.mean() - CONFIDENCE_95 * this.stddev() / Math.sqrt(this.trials);
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return this.mean() + CONFIDENCE_95 * this.stddev() / Math.sqrt(this.trials);
  }

  // test client (see below)
  public static void main(String[] args) {
    var n = Integer.parseInt(args[0]);
    var trials = Integer.parseInt(args[1]);

    var stats = new PercolationStats(n, trials);

    StdOut.printf("mean                    = %.16f\n", stats.mean());
    StdOut.printf("stddev                  = %.16f\n", stats.stddev());
    StdOut.printf("95%% confidence interval = [%.16f, %.16f]\n", stats.confidenceLo(), stats.confidenceHi());
  }

}