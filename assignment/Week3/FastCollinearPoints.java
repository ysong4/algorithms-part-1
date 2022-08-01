// Reference: 
// https://github.com/Ruoyu111/Collinear-Points/blob/master/src/FastCollinearPoints.java

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
  private List<LineSegment> results;

  public FastCollinearPoints(Point[] points) {
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

    // Copy input parameter to avoid direct modify
    Point[] localPoints = points.clone();

    // Sort local points to avoid mutate input
    Arrays.sort(localPoints);

    // Duplicate check
    if (localPoints.length > 1) {
      for (int m = 1; m < localPoints.length; m++) {
        if (localPoints[m].compareTo(localPoints[m - 1]) == 0)
          throw new IllegalArgumentException("Input contains duplicate.");
      }
    }

    this.results = new ArrayList<LineSegment>();

    // Take one point as pivot
    for (var i = 0; i < localPoints.length; i++) {

      Point v = localPoints[i];
      Point[] temp = localPoints.clone();

      // Sort by pivot slope
      Arrays.sort(temp, v.slopeOrder());

      // Loop through all sorted Points
      for (var j = 1; j < temp.length; j++) {
        Point w = temp[j];
        double currentSlope = v.slopeTo(w);

        List<Point> tempPoints = new ArrayList<Point>();
        tempPoints.add(v);
        tempPoints.add(w);

        for (var k = j + 1; k < temp.length; k++) {
          Point r = temp[k];
          double tempSlope = v.slopeTo(r);

          if (currentSlope == tempSlope) {
            // Equal slopes
            tempPoints.add(r);
            j = k;
          } else {
            // Change the j value;
            j = k - 1;
            break;
          }
        }

        // Check if any line segment found
        if (tempPoints.size() >= 4) {
          tempPoints.sort(null);

          // Only if the finded LineSegment started with Pivot v, then we add it to result
          if (tempPoints.get(0).compareTo(v) == 0) {
            LineSegment newLine = new LineSegment(tempPoints.get(0), tempPoints.get(tempPoints.size()-1));

            this.results.add(newLine);
          }
        }

      }

    }
  } // finds all line segments containing 4 or more points

  public int numberOfSegments() {
    return this.results.size();
  } // the number of line segments

  public LineSegment[] segments() {
    return this.results.toArray(LineSegment[]::new);
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
      FastCollinearPoints collinear = new FastCollinearPoints(points);
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

// javac -cp ".:../algs4.jar" FastCollinearPoints.java
// java -cp ".:../algs4.jar" FastCollinearPoints input6.txt