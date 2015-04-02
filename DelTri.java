import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import sequential.geometry.Point;
import sequential.geometry.Triangle;
import sequential.structures.TriangleTree;

public class DelTri {

	public static Vector<Point> loadPoints(String filename) {
		int numPoints = 0;
		Vector<Point> points = new Vector<Point>();

		// Open file
		try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
			// Read number of points
			String firstLine = null;
			do {
				// Read next line
				firstLine = br.readLine();

				// Test if it's a commentary
				if (firstLine.startsWith("#")) {
					// Set to null in order to make the while test be false
					firstLine = null;
				} else {
					// Split line
//					String[] tokens = firstLine.split(" ",-1);
//					// The first number is the number of points
//					numPoints = Integer.parseInt(tokens[0]);
				}
			} while (firstLine == null);

			// initialize max and min
			double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
			double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;

			// Read Points
			for (String line; (line = br.readLine()) != null;) {
				// Check if it's a commentary
				if (line.startsWith("#")) {
					continue;
				}

				// Split line
				String[] tokens = line.split("\t",0);
				double x = Double.parseDouble(tokens[1]);
				double y = Double.parseDouble(tokens[2]);

				// Add point
				points.add(new Point(x, y));

				// Update max and min
				maxX = (x > maxX) ? x : maxX;
				maxY = (y > maxY) ? y : maxY;
				minX = (x < minX) ? x : minX;
				minY = (y < minY) ? y : minY;

			}

			// Add the initial big triangle
			double xSize = (maxX - minX);
			double ySize = (maxY - minY);
			points.add(0, new Point(minX - xSize * 10, minY - ySize * 10));
			points.add(1, new Point(maxX + xSize * 10, minY - ySize * 10));
			points.add(2, new Point((maxX + maxY) / 2, maxY + ySize * 10));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return points;
	}

	public static void main(String[] args) { 
//		Vector<Point> points = new Vector<Point>();
		
		// Check if has argument
		if (args.length == 0) {
			System.out.println("Missing .node file");
			return;
		}
		
		Vector<Point> ps = loadPoints(args[0]);

		// Set points
//		points.add(new Point(-2, -2));
//		points.add(new Point(-7, 20));
//		points.add(new Point(20, -7));
//		points.add(new Point(0f, 0f));
//		points.add(new Point(-0.416f, 0.909f));
//		points.add(new Point(-1.35f, 0.436f));
//		points.add(new Point(-1.64f, -0.549f));
//		points.add(new Point(-1.31f, -1.51f));
//		points.add(new Point(-0.532f, -2.17f));
//		points.add(new Point(0.454f, -2.41f));
//		points.add(new Point(1.45f, -2.21f));
//		points.add(new Point(2.29f, -1.66f));
//		points.add(new Point(2.88f, -0.838f));
//		points.add(new Point(3.16f, 0.131f));
//		points.add(new Point(3.12f, 1.14f));
//		points.add(new Point(2.77f, 2.08f));
//		points.add(new Point(2.16f, 2.89f));
//		points.add(new Point(1.36f, 3.49f));

		Triangle.pointsVector = ps;

		TriangleTree tt = new TriangleTree(new Triangle(0, 1, 2));

		for (int i = 3; i < ps.size(); i++) {
			// points.elementAt(i).print(i);
			try {
				tt.insert(i);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		tt.printTriangles();
	}

}
