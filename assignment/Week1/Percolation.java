import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private final WeightedQuickUnionUF weightedQuickUnionUF;
  private final int virtualTopIndex;
  private final int virtualBottomIndex;
  private final int n;

  private int openSitesCount;
  private boolean[][] grid;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("Your n is <= 0");
    }

    this.weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
    // Virtual top open site index: 0
    // Virtual bottom open site index: n * n + 1
    this.virtualTopIndex = 0;
    this.virtualBottomIndex = n * n + 1;

    this.n = n;
    this.openSitesCount = 0;
    this.grid = new boolean[n][n];
    // (1,1) (1,2)
    // (2,1) (2,2)
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    this.checkFormat(row, col);

    if (this.isOpen(row, col)) {
      return;
    }

    // Open the site
    this.grid[row - 1][col - 1] = true;

    this.openSitesCount++;

    // Check if need to union() on 4 directions
    var index = this.genIndex(row, col);

    var left = col - 1;
    if (left >= 1) {
      if (this.isOpen(row, left)) {
        var leftIndex = index - 1;
        this.weightedQuickUnionUF.union(index, leftIndex);
      }
    }

    var right = col + 1;
    if (right <= this.n) {
      if (this.isOpen(row, right)) {
        var rightIndex = index + 1;
        this.weightedQuickUnionUF.union(index, rightIndex);
      }
    }

    var top = row - 1;
    if (top >= 1) {
      if (this.isOpen(top, col)) {
        var topIndex = index - this.n;
        this.weightedQuickUnionUF.union(index, topIndex);
      }
    }

    var bottom = row + 1;
    if (bottom <= this.n) {
      if (this.isOpen(bottom, col)) {
        var bottomIndex = index + this.n;
        this.weightedQuickUnionUF.union(index, bottomIndex);
      }
    }

    // Check if it is a top site or bottom site
    if (row == 1) {
      this.weightedQuickUnionUF.union(index, this.virtualTopIndex);
    }
    if (row == this.n) {
      this.weightedQuickUnionUF.union(index, this.virtualBottomIndex);
    }

    return;
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    this.checkFormat(row, col);

    return this.grid[row - 1][col - 1];
  }

  // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    this.checkFormat(row, col);

    var index = this.genIndex(row, col);

    return this.weightedQuickUnionUF.find(index) == this.weightedQuickUnionUF.find(this.virtualTopIndex);
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    return this.openSitesCount;
  }

  // does the system percolate?
  public boolean percolates() {
    return this.weightedQuickUnionUF.find(this.virtualTopIndex) == this.weightedQuickUnionUF
        .find(this.virtualBottomIndex);
  }

  private int genIndex(int row, int col) {
    return (row - 1) * this.n + col;
  }

  private void checkFormat(int row, int col) {
    if (row < 1 || row > this.n || col < 1 || col > this.n) {
      throw new IllegalArgumentException("Wrong input row or col");
    }
  }

  // test client (optional)
  // public static void main(String[] args) {
  // }
}