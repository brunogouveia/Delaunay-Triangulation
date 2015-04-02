package sequential.geometry;

import java.util.Vector;

public class Triangle {
	// This are all points used in any triangle
	public static Vector<Point> pointsVector = new Vector<Point>();

	// This are the indexes of the points in the pointsVector that compose
	// the triangle
	public int[] points = new int[3];

	/**
	 * Constructor
	 * 
	 * @param points
	 */
	public Triangle(int[] points) {
		this.points = points;
	}

	/**
	 * Constructor
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	public Triangle(int a, int b, int c) {
		points[0] = a;
		points[1] = b;
		points[2] = c;
	}

	/**
	 * Compare the mid point of two triangles
	 * 
	 * @param other
	 * @return
	 */
	public int compareTo(Triangle other) {
		return getMiddlePoint().compareTo(other.getMiddlePoint());
	}

	public boolean contains(Edge edge) {
		// Test each edge
		for (int i = 0; i < 3; i++) {
			Edge curEdge = new Edge(points[i], points[(i + 1) % 3]);

			// If the current edge is equal 'edge', return true
			if (curEdge.isEqual(edge))
				return true;
		}

		// Return false
		return false;
	}

	/**
	 * Return the mid point of this triangle
	 * 
	 * @return
	 */
	public Point getMiddlePoint() {
		// Compute middle point
		Point a = pointsVector.elementAt(points[0]);
		Point b = pointsVector.elementAt(points[1]);
		Point c = pointsVector.elementAt(points[2]);

		// Return middle point
		return a.add(b.add(c)).divideBy(3);
	}

	/**
	 * Test if point lies inside this triangle
	 * 
	 * @param p
	 * @return
	 */
	public boolean isPointInside(Point p) {
		// Initialize points
		Point a = pointsVector.elementAt(points[0]);
		Point b = pointsVector.elementAt(points[1]);
		Point c = pointsVector.elementAt(points[2]);

		// Initialize vectors
		Point v0 = c.minus(a);
		Point v1 = b.minus(a);
		Point v2 = p.minus(a);

		// Compute p in triangle coordinates
		double u = (v1.dot(v1) * v2.dot(v0) - v1.dot(v0) * v2.dot(v1)) / (v0.dot(v0) * v1.dot(v1) - v0.dot(v1) * v1.dot(v0));
		double v = (v0.dot(v0) * v2.dot(v1) - v0.dot(v1) * v2.dot(v0)) / (v0.dot(v0) * v1.dot(v1) - v0.dot(v1) * v1.dot(v0));

		// Check if point is in triangle
		return (u >= 0) && (v >= 0) && (u + v <= 1);
	}

	/**
	 * Returns the common edge between two triangles, if such edge doesn't
	 * exist, the method returns null.
	 * 
	 * @param other
	 * @return
	 */
	public Edge commonEdge(Triangle other) {
		// Check all the possibilities
		for (int i = 0; i < 3; i++) {
			// Compute the edge to be tested
			Edge myEdge = new Edge(points[i], points[(i + 1) % 3]);
			// Check if each edge of other triangle
			for (int j = 0; j < 3; j++) {
				Edge otherEdge = new Edge(other.points[i], other.points[(i + 1) % 3]);

				// Check if edges are equal
				if (myEdge.isEqual(otherEdge))
					// Return edge
					return myEdge;
			}
		}
		// Otherwise, return null
		return null;
	}

	/**
	 * Get the other point that is not in the edge. Note that this only works if
	 * the edge is in an edge of the triangle.
	 * 
	 * @param edge
	 * @return
	 */
	public int getOtherPoint(Edge edge) {
		// Check each vertex
		for (int i = 0; i < 3; i++) {
			if (!edge.contains(points[i]))
				return points[i];
		}

		// This is never gonna happen
		return -1;
	}

	/**
	 * Print the coordinates of the three points
	 */
	public void print(int index) {
		System.out.println(index + "\t" + points[0] + "\t" + points[1] + "\t" + points[2]);
	}
}
