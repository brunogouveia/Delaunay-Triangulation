package sequential.geometry;

public class Vector2D {
	public double x, y;

	/**
	 * Constructor using two points. The final vector is b-a
	 * 
	 * @param a
	 * @param b
	 */
	public Vector2D(Point a, Point b) {
		x = b.x - a.x;
		y = b.y - a.y;
	}

	/**
	 * Constructor.
	 * 
	 * @param x
	 * @param y
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Compute the inner product of this vector with other vector
	 * 
	 * @param other
	 * @return
	 */
	public double dot(Vector2D other) {
		return (x * other.x) + (y * other.y);
	}

	/**
	 * Add this vector with other vector
	 * 
	 * @param other
	 * @return - the resultant vector
	 */
	public Vector2D add(Vector2D other) {
		return new Vector2D(x + other.x, y + other.y);
	}

	/**
	 * Subtract two vectors
	 * 
	 * @param other
	 * @return
	 */
	public Vector2D minus(Vector2D other) {
		return new Vector2D(x - other.x, y - other.y);
	}

	public double length() {
		return Math.sqrt(this.dot(this));
	}

	public double angles(Vector2D other) {
		return Math.acos(this.dot(other) / (this.length()*other.length()));
	}
}
