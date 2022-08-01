import java.util.ArrayList;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
  private boolean solvable;
  private int moves;
  private SearchNode last;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException();
    }

    Board twin = initial.twin();

    MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
    pq.insert(new SearchNode(initial, 0, null));

    MinPQ<SearchNode> tpq = new MinPQ<SearchNode>();
    tpq.insert(new SearchNode(twin, 0, null));

    while (true) {

      // Handle twinGameTree
      SearchNode tnode = tpq.delMin();
      if (tnode.board.isGoal()) {

        this.solvable = false;
        this.moves = -1;
        this.last = null;

        return;

      } else {

        Iterable<Board> neighbors = tnode.board.neighbors();
        for (Board neighbor : neighbors) {

          SearchNode newTnode = new SearchNode(neighbor, tnode.moves + 1, tnode);

          if (tnode.previous != null && newTnode.board.equals(tnode.previous.board)) {
            continue;
          }

          tpq.insert(newTnode);

        }

      }

      // Handle initialGameTree
      SearchNode node = pq.delMin();

      if (node.board.isGoal()) {

        this.solvable = true;
        this.moves = node.moves;
        this.last = node;

        return;

      } else {

        Iterable<Board> neighbors = node.board.neighbors();
        for (Board neighbor : neighbors) {

          SearchNode newNode = new SearchNode(neighbor, node.moves + 1, node);

          if (node.previous != null && newNode.board.equals(node.previous.board)) {
            continue;
          }

          pq.insert(newNode);

        }

      }

    }

  }

  private class SearchNode implements Comparable<SearchNode> {
    public Board board;
    public int moves;
    public SearchNode previous;

    public int manhattanPriority;

    public SearchNode(Board board, int moves, SearchNode previous) {
      this.board = board;
      this.moves = moves;
      this.previous = previous;

      this.manhattanPriority = this.moves + this.board.manhattan();
    }

    public int compareTo(SearchNode that) {
      if (that == null) {
        throw new NullPointerException();
      }

      // Manhattan
      if (this.manhattanPriority > that.manhattanPriority) {
        return 1;
      } else if (this.manhattanPriority < that.manhattanPriority) {
        return -1;
      } else {
        // ==
        return 0;
      }

    }
  }

  private class Solution implements Iterable<Board> {
    private Board[] steps;

    public Solution(SearchNode last) {
      Stack<Board> stack = new Stack<Board>();

      stack.push(last.board);
      while(last.previous != null) {
        last = last.previous;

        stack.push(last.board);
      }

      ArrayList<Board> solution = new ArrayList<Board>();
      while(!stack.isEmpty()) {
        solution.add(stack.pop());
      }

      this.steps = solution.toArray(new Board[solution.size()]);
    }

    public Iterator<Board> iterator() {
      return new ListSteps();
    }

    private class ListSteps implements Iterator<Board> {
      private int current;

      public boolean hasNext() {
        return current < steps.length;
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }

      public Board next() {
        Board item = steps[current];
        current++;
        return item;
      }
    }

  }

  // is the initial board solvable? (see below)
  public boolean isSolvable() {
    return solvable;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    if (!this.solvable) {
      return -1;
    }
    return this.moves;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (!this.solvable) {
      return null;
    }

    return new Solution(this.last);
  }

  // test client (see below)
  public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        tiles[i][j] = in.readInt();
    Board initial = new Board(tiles);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }

  }

}