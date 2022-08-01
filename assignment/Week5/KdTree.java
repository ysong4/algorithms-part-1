import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
  private static final boolean RED = true; // using x-coordinate
  private static final boolean BLUE = false; // using y-coordinate

  private Node root;
  private int count;

  private class Node {
    public Point2D point;
    public boolean color;
    public Node left;
    public Node right;
    public RectHV rect;

    public Node(Point2D p) {
      this.point = p;
    }
  }

  public KdTree() {
    this.root = null;
  } // construct an empty set of points

  public boolean isEmpty() {
    return this.count == 0;
  } // is the set empty?

  public int size() {
    return this.count;
  } // number of points in the set

  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException("p is null");
    }

    Node newNode = new Node(p);

    // if tree is empty
    if (this.isEmpty()) {

      newNode.color = RED;
      newNode.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
      this.root = newNode;
      this.count++;
      return;

    }

    // if tree is not empty
    Node current = this.root;

    while (current != null) {

      // If already contains the point, do nothing
      if (current.point.x() == p.x() && current.point.y() == p.y()) {
        return;
      }

      if (current.color == RED) {

        if (p.x() > current.point.x()) {

          // go right
          if (current.right == null) {

            // Insert new node
            newNode.color = !current.color;
            newNode.rect = new RectHV(current.point.x(), current.rect.ymin(), current.rect.xmax(), current.rect.ymax());
            current.right = newNode;
            this.count++;
            break;

          } else {
            current = current.right;
          }

        } else {

          // go left
          if (current.left == null) {

            // Insert new node
            newNode.color = !current.color;
            newNode.rect = new RectHV(current.rect.xmin(), current.rect.ymin(), current.point.x(), current.rect.ymax());
            current.left = newNode;
            this.count++;
            break;

          } else {
            current = current.left;
          }

        }

      } else {
        // current.color == BLUE

        if (p.y() > current.point.y()) {

          // go right
          if (current.right == null) {

            // Insert new node
            newNode.color = !current.color;
            newNode.rect = new RectHV(current.rect.xmin(), current.point.y(), current.rect.xmax(), current.rect.ymax());
            current.right = newNode;
            this.count++;
            break;

          } else {
            current = current.right;
          }

        } else {

          // go left
          if (current.left == null) {

            // Insert new node
            newNode.color = !current.color;
            newNode.rect = new RectHV(current.rect.xmin(), current.rect.ymin(), current.rect.xmax(), current.point.y());
            current.left = newNode;
            this.count++;
            break;

          } else {
            current = current.left;
          }

        }

      }

    }

  } // add the point to the set (if it is not already in the set)

  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException("p is null");
    }

    Node current = this.root;

    while (current != null) {

      if (current.point.x() == p.x() && current.point.y() == p.y()) {
        return true;
      }

      if (current.color == RED) {

        if (p.x() > current.point.x()) {
          current = current.right;
        } else {
          current = current.left;
        }

      } else {
        // current.color == BLUE

        if (p.y() > current.point.y()) {
          current = current.right;
        } else {
          current = current.left;
        }

      }

    }

    return false;
  } // does the set contain point p?

  public void draw() {
    this._draw(this.root, new RectHV(0.0, 0.0, 1.0, 1.0));
  } // draw all points to standard draw

  private void _draw(Node n, RectHV rect) {
    if (n == null || rect == null) {
      return;
    }

    StdDraw.setPenColor(StdDraw.BLACK);
    n.point.draw();

    if (n.color == RED) {

      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.line(n.point.x(), rect.ymin(), n.point.x(), rect.ymax());

      this._draw(n.left, new RectHV(rect.xmin(), rect.ymin(), n.point.x(), rect.ymax()));
      this._draw(n.right, new RectHV(n.point.x(), rect.ymin(), rect.xmax(), rect.ymax()));

    } else {

      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.line(rect.xmin(), n.point.y(), rect.xmax(), n.point.y());

      this._draw(n.left, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), n.point.y()));
      this._draw(n.right, new RectHV(rect.xmin(), n.point.y(), rect.xmax(), rect.ymax()));

    }
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException("rect is null");
    }
    
    if (this.root == null) {
      return null;
    }

    ArrayList<Point2D> result = new ArrayList<Point2D>();

    this._range(this.root, rect, result);

    return result;
  } // all points that are inside the rectangle (or on the boundary)

  private void _range(Node current, RectHV targetRect, ArrayList<Point2D> result) {

    if (targetRect.contains(current.point)) {
      result.add(current.point);
    }

    if (current.left != null && targetRect.intersects(current.left.rect)) {
      this._range(current.left, targetRect, result);
    }

    if (current.right != null && targetRect.intersects(current.right.rect)) {
      this._range(current.right, targetRect, result);
    }

    return;
  }

  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException("p is null");
    }

    if (this.root == null) {
      return null;
    }

    Point2D result = this.root.point;
    double minDistance = this.root.point.distanceTo(p);

    return this._nearest(p, result, minDistance, this.root);
  } // a nearest neighbor in the set to point p; null if the set is empty

  private Point2D _nearest(Point2D p, Point2D result, double minDistance, Node current) {

    if (current == null) {
      return result;
    }

    double currentDistance = current.point.distanceTo(p);
    if (currentDistance < minDistance) {
      result = current.point;
      minDistance = currentDistance;
    }

    // Always start with the side
    if (current.color == RED) {
      // Divide by x-axis
      if (p.x() > current.point.x()) {

        // Check right first, then check left
        result = this._nearest(p, result, minDistance, current.right);
        // minDistance could be changed 
        minDistance = result.distanceTo(p);

        if (current.left != null) {
          double leftMinDistance = current.left.rect.distanceTo(p);
          if (leftMinDistance < minDistance) {
            result = this._nearest(p, result, minDistance, current.left);
          }
        }

      } else {

        // Check left first, then check right
        result = this._nearest(p, result, minDistance, current.left);
        // minDistance could be changed 
        minDistance = result.distanceTo(p);

        if (current.right != null) {
          double rightMinDistance = current.right.rect.distanceTo(p);
          if (rightMinDistance < minDistance) {
            result = this._nearest(p, result, minDistance, current.right);
          }
        }

      }

    } else {
      // current.color == BLUE
      // Divide by y-axis
      if (p.y() > current.point.y()) {

        // Check right first, then check left
        result = this._nearest(p, result, minDistance, current.right);
        // minDistance could be changed 
        minDistance = result.distanceTo(p);

        if (current.left != null) {
          double leftMinDistance = current.left.rect.distanceTo(p);
          if (leftMinDistance < minDistance) {
            result = this._nearest(p, result, minDistance, current.left);
          }
        }

      } else {

        // Check left first, then check right
        result = this._nearest(p, result, minDistance, current.left);
        // minDistance could be changed 
        minDistance = result.distanceTo(p);

        if (current.right != null) {
          double rightMinDistance = current.right.rect.distanceTo(p);
          if (rightMinDistance < minDistance) {
            result = this._nearest(p, result, minDistance, current.right);
          }
        }

      }

    }

    return result;
  }

  public static void main(String[] args) {

  } // unit testing of the methods (optional)
}