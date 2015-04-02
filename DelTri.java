import java.util.Vector;

import sequential.geometry.Point;
import sequential.geometry.Triangle;
import sequential.structures.TriangleTree;

public class DelTri {

	public Vector<Point> loadPoints(String filename) {

		return null;
	}

	public static void main(String[] args) {
		Vector<Point> points = new Vector<Point>();

		// Set points
		points.add(new Point(-2,-2));
		points.add(new Point(-7,20));
		points.add(new Point(20,-7));
		points.add(new Point(0f, 0f));
		points.add(new Point(-0.416f, 0.909f));
		points.add(new Point(-1.35f, 0.436f));
		points.add(new Point(-1.64f, -0.549f));
		points.add(new Point(-1.31f, -1.51f));
		points.add(new Point(-0.532f, -2.17f));
		points.add(new Point(0.454f, -2.41f));
		points.add(new Point(1.45f, -2.21f));
		points.add(new Point(2.29f, -1.66f));
		points.add(new Point(2.88f, -0.838f));
		points.add(new Point(3.16f, 0.131f));
		points.add(new Point(3.12f, 1.14f));
		points.add(new Point(2.77f, 2.08f));
		points.add(new Point(2.16f, 2.89f));
		points.add(new Point(1.36f, 3.49f));

		Triangle.pointsVector = points;

		TriangleTree tt = new TriangleTree(new Triangle(0, 1, 2));
		
		

		for (int i = 3; i < points.size(); i++) {
			points.elementAt(i).print(i);
			tt.insert(i);
		}

		tt.printTriangles();
	}

}
