package sequential.geometry;

public class Edge {
	public int[] points = new int[2];

	/**
	 * Constructor
	 * 
	 * @param p0
	 * @param p1
	 */
	public Edge(int p0, int p1) {
		points[0] = p0;
		points[1] = p1;
	}

	public Point getMiddlePoint() {
		return Triangle.pointsVector.elementAt(points[0]).add(Triangle.pointsVector.elementAt(points[1])).divideBy(2);
	}

	public boolean contains(int point) {
		return (point == points[0]) || (point == points[1]);
	}

	public boolean isEqual(Edge other) {
		return (points[0] == other.points[0] && points[1] == other.points[1]) || (points[0] == other.points[1] && points[1] == other.points[0]);
	}

	public float length() {
		Point vector = Triangle.pointsVector.elementAt(points[1]).minus(Triangle.pointsVector.elementAt(points[0]));
		return (float) Math.sqrt(vector.dot(vector));
	}
}
