import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
  private final List<LineSegment> lines;

  public BruteCollinearPoints(Point[] points) {
    // Check points not null
    if (points == null) {
      throw new IllegalArgumentException("Points is null");
    }

    // Check points all not null
    for (Point point : points) {
      if (point == null) {
        throw new IllegalArgumentException("Exist null point in points");
      }
    }

    // Check duplicate points
    for (var i = 0; i < points.length; i++) {
      for (var j = i + 1; j < points.length; j++) {
        Point v = points[i];
        Point w = points[j];
        if (v.compareTo(w) == 0) {
          throw new IllegalArgumentException("Repeated point");
        }
      }
    }

    this.lines = new ArrayList<LineSegment>();

    for (var i = 0; i < points.length; i++) {

      Point point1 = points[i];
      for (var j = i + 1; j < points.length; j++) {

        Point point2 = points[j];
        double slope1 = point1.slopeTo(point2);

        for (var k = j + 1; k < points.length; k++) {

          Point point3 = points[k];
          double slope2 = point1.slopeTo(point3);

          for (var l = k + 1; l < points.length; l++) {

            Point point4 = points[l];
            double slope3 = point1.slopeTo(point4);

            if (slope1 == slope2 && slope1 == slope3) {

              Point[] tmpPoints = { point1, point2, point3, point4 };
              Arrays.sort(tmpPoints);

              LineSegment line = new LineSegment(tmpPoints[0], tmpPoints[3]);
              this.lines.add(line);

            }

          }

        }

      }

    }

  } // finds all line segments containing 4 points

  public int numberOfSegments() {
    return this.lines.size();
  } // the number of line segments

  public LineSegment[] segments() {
    return this.lines.toArray(LineSegment[]::new);
  } // the line segments

  public static void main(String[] args) {
    try {
      // read the n points from a file
      In in = new In(args[0]);
      int n = in.readInt();
      Point[] points = new Point[n];
      for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
      }

      // draw the points
      StdDraw.enableDoubleBuffering();
      StdDraw.setXscale(0, 32768);
      StdDraw.setYscale(0, 32768);
      for (Point p : points) {
        p.draw();
      }
      StdDraw.show();

      // print and draw the line segments
      BruteCollinearPoints collinear = new BruteCollinearPoints(points);
      for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
      }
      StdDraw.show();

    } catch (IllegalArgumentException e) {
      StdOut.println(e.toString());
    }
  }
}

// javac -cp ".:../algs4.jar" BruteCollinearPoints.java
// java -cp ".:../algs4.jar" BruteCollinearPoints input6.txt