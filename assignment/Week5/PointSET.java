import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
  private SET<Point2D> points;

  public PointSET() {
    this.points = new SET<Point2D>();
  } // construct an empty set of points

  public boolean isEmpty() {
    return this.points.size() == 0;
  } // is the set empty?

  public int size() {
    return this.points.size();
  } // number of points in the set

  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException("p is null");
    }

    // If already contains the point, do nothing
    if (this.points.contains(p)) {
      return;
    }

    this.points.add(p);
  } // add the point to the set (if it is not already in the set)

  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException("p is null");
    }

    return this.points.contains(p);
  } // does the set contain point p?

  public void draw() {
    for (Point2D p : this.points) {
      p.draw();
    }
  } // draw all points to standard draw

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException("rect is null");
    }

    ArrayList<Point2D> result = new ArrayList<Point2D>();

    for (Point2D p: this.points) {
      if (rect.contains(p)) {
        result.add(p);
      }
    }
  
    return result;
  } // all points that are inside the rectangle (or on the boundary)

  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException("p is null");
    }

    Point2D result = null;
    double minDistance = 0; 

    for (Point2D point: this.points) {
      // First point
      if (result == null) {
        result = point;
        minDistance = p.distanceTo(point);

        continue;
      }

      // Other points
      double newDistance = p.distanceTo(point);
      if (newDistance < minDistance) {
        result = point;
        minDistance = newDistance;
      }
    }

    return result;
  } // a nearest neighbor in the set to point p; null if the set is empty

  public static void main(String[] args) {

  } // unit testing of the methods (optional)
}