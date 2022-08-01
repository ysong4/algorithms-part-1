
/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {

  private final int x; // x-coordinate of this point
  private final int y; // y-coordinate of this point

  /**
   * Initializes a new point.
   *
   * @param x the <em>x</em>-coordinate of the point
   * @param y the <em>y</em>-coordinate of the point
   */
  public Point(int x, int y) {
    /* DO NOT MODIFY */
    this.x = x;
    this.y = y;
  }

  /**
   * Draws this point to standard draw.
   */
  public void draw() {
    /* DO NOT MODIFY */
    StdDraw.point(x, y);
  }

  /**
   * Draws the line segment between this point and the specified point
   * to standard draw.
   *
   * @param that the other point
   */
  public void drawTo(Point that) {
    /* DO NOT MODIFY */
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  /**
   * Returns the slope between this point and the specified point.
   * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
   * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
   * +0.0 if the line segment connecting the two points is horizontal;
   * Double.POSITIVE_INFINITY if the line segment is vertical;
   * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
   *
   * @param that the other point
   * @return the slope between this point and the specified point
   */
  public double slopeTo(Point that) {
    /* YOUR CODE HERE */
    if (that == null) {
      throw new NullPointerException();
    }

    if (this.x == that.x && this.y == that.y) {

      // Two points equal
      return Double.NEGATIVE_INFINITY;

    } else if (this.x == that.x) {

      // Vertical
      return Double.POSITIVE_INFINITY;

    } else if (this.y == that.y) {

      // Horizontal
      return 0.0;

    } else {

      return ((double) (this.y - that.y)) / ((double) (this.x - that.x));

    }
  }

  /**
   * Compares two points by y-coordinate, breaking ties by x-coordinate.
   * Formally, the invoking point (x0, y0) is less than the argument point
   * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
   *
   * @param that the other point
   * @return the value <tt>0</tt> if this point is equal to the argument
   *         point (x0 = x1 and y0 = y1);
   *         a negative integer if this point is less than the argument
   *         point; and a positive integer if this point is greater than the
   *         argument point
   */
  public int compareTo(Point that) {
    /* YOUR CODE HERE */
    if (that == null) {
      throw new NullPointerException();
    }

    if (this.x == that.x && this.y == that.y) {
      return 0;
    }

    if (this.y < that.y) {
      return -1;
    }
    if (this.y == that.y && this.x < that.x) {
      return -1;
    }

    return 1;
  }

  /**
   * Compares two points by the slope they make with this point.
   * The slope is defined as in the slopeTo() method.
   *
   * @return the Comparator that defines this ordering on points
   */
  public Comparator<Point> slopeOrder() {
    /* YOUR CODE HERE */
    return new BySlope();
  }

  private class BySlope implements Comparator<Point> {
    public int compare(Point v, Point w) {
      if (v == null) {
        throw new NullPointerException();
      }
      if (w == null) {
        throw new NullPointerException();
      }

      double slope1 = slopeTo(v);
      double slope2 = slopeTo(w);

      if (slope1 == slope2) {
        return 0;
      }

      if (slope1 < slope2) {
        return -1;
      } else {
        // slope1 > slope2
        return 1;
      }
    }
  }

  /**
   * Returns a string representation of this point.
   * This method is provide for debugging;
   * your program should not rely on the format of the string representation.
   *
   * @return a string representation of this point
   */
  public String toString() {
    /* DO NOT MODIFY */
    return "(" + x + ", " + y + ")";
  }

  /**
   * Unit tests the Point data type.
   */
  public static void main(String[] args) {
    /* YOUR CODE HERE */

    Point x = new Point(0, 0);
    Point y = new Point(1, 1);
    Point z = new Point(2, 2);

    // Check slopeTo()
    double slopeXY = x.slopeTo(y);
    assert slopeXY == 1.0;
    StdOut.printf("test slopeXY: %b\n", slopeXY == 1.0);

    double slopeXZ = x.slopeTo(z);
    assert slopeXZ == 1.0;
    StdOut.printf("test slopeXZ: %b\n", slopeXZ == 1.0);

    // Check compareTo()
    int compareXY = x.compareTo(y);
    assert compareXY == -1;
    StdOut.printf("test compareXY: %b\n", compareXY == -1);

    int compareYX = y.compareTo(x);
    assert compareYX == 1;
    StdOut.printf("test compareYX: %b\n", compareYX == 1);

    Point p = new Point(309, 415);
    Point q = new Point(193, 415);
    int comparePQ = p.compareTo(q);
    assert comparePQ == 1;
    StdOut.printf("test comparePQ: %b\n", comparePQ == 1);

    int compareQP = q.compareTo(p);
    assert compareQP == -1;
    StdOut.printf("test compareQP: %b\n", compareQP == -1);

    // Check slopeOrder()
    Point[] points = new Point[] { y, z };
    Arrays.sort(points, x.slopeOrder());
    StdOut.printf("test slopeOrder() point[0]: %s\n", points[0].toString());
    StdOut.printf("test slopeOrder() point[1]: %s\n", points[1].toString());
    assert points[0].toString().equals("(1, 1)");
    assert points[1].toString().equals("(2, 2)");

    Point a = new Point(3, 3);
    Point b = new Point(3, 7);
    Point c = new Point(3, 9);

    int tmp = a.slopeOrder().compare(b, c);
    assert tmp == 0;
    StdOut.printf("test slopeOrder.compare(): %b\n", tmp == 0);

  }
}

// javac -cp ".:../algs4.jar" Point.java
// java -cp ".:../algs4.jar" -ea Point