package sequential.geometry;

public class Point {
	public double x;
	public double y;

	public Point() {
		// do nothing
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Return the addition of two points.
	 * 
	 * @param other
	 * @return
	 */
	public Point add(Point other) {
		return new Point(x + other.x, y + other.y);
	}

	public Point minus(Point other) {
		return new Point(x - other.x, y - other.y);
	}

	/**
	 * Return a new node that is this point divide by d.
	 * 
	 * @param d
	 * @return
	 */
	public Point divideBy(float d) {
		return new Point(x / d, y / d);
	}

	public double dot(Point other) {
		return x * other.x + y * other.y;
	}

	int compareTo(Point other) {
		if (x == other.x) {
			if (y == other.y) {
				return 0;
			} else {
				if (y < other.y)
					return -1;
				else
					return 1;
			}
		} else {
			if (x < other.x)
				return -1;
			else
				return 1;
		}
	}

	/**
	 * Print the coordinates
	 */
	public void print(int index) {
		System.out.println(index + "\t" + x + "\t" + y);
	}
}
