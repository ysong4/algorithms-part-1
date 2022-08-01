import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

public class Board {
  private int[][] tiles;
  private int N;

  // create a board from an n-by-n array of tiles,
  // where tiles[row][col] = tile at (row, col)
  public Board(int[][] tiles) {
    if (tiles == null) {
      throw new NullPointerException();
    }

    this.N = tiles.length;

    this.tiles = new int[this.N][this.N];
    for (int i = 0; i < this.N; i++) {
      for (int j = 0; j < this.N; j++) {
        this.tiles[i][j] = tiles[i][j];
      }
    }

  }

  // string representation of this board
  public String toString() {
    String result = Integer.toString(this.N);

    for (int i = 0; i < this.N; i++) {
      result += "\n";
      for (int j = 0; j < this.N; j++) {
        if (this.tiles[i][j] < 10) {
          result += String.format(" %d ", this.tiles[i][j]);
        } else {
          result += String.format("%d ", this.tiles[i][j]);
        }
      }
    }

    return result;
  }

  // board dimension n
  public int dimension() {
    return this.N;
  }

  // number of tiles out of place
  public int hamming() {
    int distance = 0;

    for (int i = 0; i < this.N; i++) {
      for (int j = 0; j < this.N; j++) {

        int expected = N * i + j + 1;
        if (i == this.N - 1 && i == j) {
          expected = 0;
        }

        if (this.tiles[i][j] != expected && expected != 0) {
          distance++;
        }
      }
    }

    return distance;
  }

  // sum of Manhattan distances between tiles and goal
  public int manhattan() {
    int distance = 0;

    for (int i = 0; i < this.N; i++) {
      for (int j = 0; j < this.N; j++) {

        int value = this.tiles[i][j];

        if (value != 0) {
          value--;

          int expectedRow = value / this.N;
          int expectedCol = value % this.N;

          int diffRow = Math.abs(expectedRow - i);
          int diffCol = Math.abs(expectedCol - j);
          distance += (diffRow + diffCol);

        }

      }
    }

    return distance;
  }

  // is this board the goal board?
  public boolean isGoal() {
    for (int i = 0; i < this.N; i++) {
      for (int j = 0; j < this.N; j++) {

        int expected = N * i + j + 1;
        if (i == this.N - 1 && i == j) {
          expected = 0;
        }

        if (this.tiles[i][j] != expected) {
          return false;
        }

      }
    }

    return true;
  }

  // does this board equal y?
  public boolean equals(Object y) {
    if (y == null) {
      return false;
    }

    if (!(y instanceof Board)) {
      return false;
    }

    Board that = (Board) y;

    if (this.dimension() != that.dimension()) {
      return false;
    }

    for (int i = 0; i < this.N; i++) {
      for (int j = 0; j < this.N; j++) {

        if (this.tiles[i][j] != that.tiles[i][j]) {
          return false;
        }

      }
    }

    return true;
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    return new BoardNeighbors(this.tiles);
  }

  private class BoardNeighbors implements Iterable<Board> {
    private Board[] neighbors;

    public BoardNeighbors(int[][] tiles) {
      List<Board> list = new ArrayList<Board>();

      // Find the blank tile index
      int blankRow = 0;
      int blankCol = 0;
      for (var i = 0; i < tiles.length; i++) {
        for (var j = 0; j < tiles.length; j++) {
          if (tiles[i][j] == 0) {
            blankRow = i;
            blankCol = j;
          }
        }
      }

      if (blankRow > 0) {
        Board neighbor = new Board(tiles);

        swap(neighbor, blankRow, blankCol, blankRow - 1, blankCol);
        list.add(neighbor);
      }

      if (blankRow < tiles.length - 1) {
        Board neighbor = new Board(tiles);

        swap(neighbor, blankRow, blankCol, blankRow + 1, blankCol);
        list.add(neighbor);
      }

      if (blankCol > 0) {
        Board neighbor = new Board(tiles);

        swap(neighbor, blankRow, blankCol, blankRow, blankCol - 1);
        list.add(neighbor);
      }

      if (blankCol < tiles.length - 1) {
        Board neighbor = new Board(tiles);

        swap(neighbor, blankRow, blankCol, blankRow, blankCol + 1);
        list.add(neighbor);
      }

      this.neighbors = list.toArray(new Board[list.size()]);
    }

    public Iterator<Board> iterator() {
      return new ListNeighbor();
    }

    private class ListNeighbor implements Iterator<Board> {
      private int current;

      public boolean hasNext() {
        return current < neighbors.length;
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }

      public Board next() {
        Board item = neighbors[current];
        current++;
        return item;
      }
    }

  }

  private void swap(Board board, int aRow, int aCol, int bRow, int bCol) {
    int tmp = board.tiles[aRow][aCol];
    board.tiles[aRow][aCol] = board.tiles[bRow][bCol];
    board.tiles[bRow][bCol] = tmp;
  }

  // a board that is obtained by exchanging any pair of tiles
  public Board twin() {
    Board newBoard = new Board(this.tiles);

    if (this.tiles[0][0] == 0) {
      swap(newBoard, 1, 0, 1,1);
    } else if (this.tiles[1][0] == 0) {
      swap(newBoard, 0,0,0,1);
    } else if (this.tiles[0][1] == 0) {
      swap(newBoard, 1,0, 1,1);
    } else {
      swap(newBoard, 0,0,0,1);
    }

    return newBoard;
  }

  // unit testing (not graded)
  public static void main(String[] args) {

    StdOut.println("\nTest all methods with a Board.isGoal() == true.\n");
    int[][] tiles = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
    Board board = new Board(tiles);

    StdOut.printf("toString(): \n%s\n", board.toString());
    StdOut.printf("dimension(): %d\n", board.dimension());
    StdOut.printf("hamming(): %d\n", board.hamming());
    StdOut.printf("manhattan(): %d\n", board.manhattan());
    StdOut.printf("isGoal(): %b\n", board.isGoal());

    Iterable<Board> neighbors = board.neighbors();
    StdOut.println("neighbors():");
    for (Board neighbor : neighbors) {
      StdOut.println(neighbor.toString());
    }

    StdOut.printf("twin(): \n%s\n", board.twin().toString());

    StdOut.println("\nTest all methods with a Board.isGoal() == false.\n");
    tiles[2][1] = 0;
    tiles[2][2] = 8;
    board = new Board(tiles);

    StdOut.printf("toString(): \n%s\n", board.toString());
    StdOut.printf("dimension(): %d\n", board.dimension());
    StdOut.printf("hamming(): %d\n", board.hamming());
    StdOut.printf("manhattan(): %d\n", board.manhattan());
    StdOut.printf("isGoal(): %b\n", board.isGoal());

    neighbors = board.neighbors();
    StdOut.println("neighbors():");
    for (Board neighbor : neighbors) {
      StdOut.println(neighbor.toString());
    }

    StdOut.printf("twin(): \n%s\n", board.twin().toString());
  }

}