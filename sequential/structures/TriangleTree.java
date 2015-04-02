package sequential.structures;

import java.util.HashSet;
import java.util.Iterator;

import sequential.geometry.Edge;
import sequential.geometry.Point;
import sequential.geometry.Triangle;
import sequential.geometry.Vector2D;

public class TriangleTree {
	private Node root = null;

	/**
	 * Constructor
	 * 
	 * @param t
	 */
	public TriangleTree(Triangle t) {
		root = new Node(t);
	}

	public void insert(int prIndex) {
		// TODO - this is obviously just for test
		// Find triangles that contain point p
		Point p = Triangle.pointsVector.elementAt(prIndex);

		Node[] nodes = testPoint(p);

		// If it's inside a triangle
		if (nodes.length == 1) {
			// Split node
			nodes[0].splitNode(prIndex);

			// Check if it's still a delaunay triangulation
			legalizeEdge(prIndex, new Edge(nodes[0].triangle.points[0], nodes[0].triangle.points[1]));
			legalizeEdge(prIndex, new Edge(nodes[0].triangle.points[1], nodes[0].triangle.points[2]));
			legalizeEdge(prIndex, new Edge(nodes[0].triangle.points[2], nodes[0].triangle.points[0]));
		}
		// It lies on and edge, so we need to do some work here
		else {
			// Get common edge
			Edge commonEdge = nodes[0].triangle.commonEdge(nodes[1].triangle);
			int piIndex = commonEdge.points[0];
			int pjIndex = commonEdge.points[1];

			// // Split first node
			// Instantiate array
			nodes[0].children = new Node[2];
			// Get the vertex that is not in the edge
			int plIndex = nodes[0].triangle.getOtherPoint(commonEdge);
			// Create nodes
			nodes[0].children[0] = new Node(new Triangle(prIndex, piIndex, plIndex));
			nodes[0].children[1] = new Node(new Triangle(prIndex, pjIndex, plIndex));

			// // Split second node
			// Instantiate array
			nodes[1].children = new Node[2];
			// Get the vertex that is not in the edge
			int pkIndex = nodes[1].triangle.getOtherPoint(commonEdge);
			// Create nodes
			nodes[1].children[0] = new Node(new Triangle(prIndex, piIndex, pkIndex));
			nodes[1].children[1] = new Node(new Triangle(prIndex, pjIndex, pkIndex));

			// Check if it's still a delaunay triangulation
			legalizeEdge(prIndex, new Edge(piIndex, plIndex));
			legalizeEdge(prIndex, new Edge(plIndex, pjIndex));
			legalizeEdge(prIndex, new Edge(pjIndex, pkIndex));
			legalizeEdge(prIndex, new Edge(pkIndex, piIndex));

		}
	}

	/**
	 * Print all triangles
	 */
	public void printTriangles() {
		// Print points
		int i = 0;
		System.out.println(Triangle.pointsVector.size() + "\t" + 2 + "\t" + 0 + "\t" + 0);
		for (Point point : Triangle.pointsVector) {
			point.print(i++);
		}

		// Get all triangles that are currently in the triangulation
		HashSet<Triangle> triangles = new HashSet<Triangle>();
		printTriangles(root, triangles);
		
		i = 0;
		for (Triangle triangle : triangles) {
			triangle.print(i++);
		}
	}

	private void printTriangles(Node n, HashSet<Triangle> triangles) {
		// Check if n is not null
		if (n == null)
			return;

		// Check if it's leaf
		if (n.children == null) {
			// Print triangle coordinates
			if ((n.triangle.points[0] > 2) && (n.triangle.points[1] > 2) && (n.triangle.points[2] > 2))
				triangles.add(n.triangle);
		} else {
			// Just call recursively.
			for (Node child : n.children) {
				printTriangles(child, triangles);
			}
		}
	}

	/**
	 * Legalize an edge
	 * 
	 * @param prIndex
	 *            - it's the point inserted
	 * @param edge
	 *            - the edge to be tested
	 */
	public void legalizeEdge(int prIndex, Edge edge) {
		// Find the triangles that edge is incident
		HashSet<Node> hs = lookUp(edge);
		Node[] nodes = new Node[hs.size()];

		int i = 0;
		for (Iterator iterator = hs.iterator(); iterator.hasNext();) {
			Node node = (Node) iterator.next();
			nodes[i++] = node;
		}

		// If length is 1, then this edge is in the edge of the first triangle
		if (nodes == null) {
			// edge.getMiddlePoint().print(1);
			// Triangle.pointsVector.elementAt(edge.points[0]).print(2);
			// Triangle.pointsVector.elementAt(edge.points[1]).print(3);
			return;
		}
		if (nodes.length == 1)
			return;

		// Compute the opposite vertex
		int pkIndex = (nodes[0].triangle.getOtherPoint(edge) != prIndex) ? nodes[0].triangle.getOtherPoint(edge) : nodes[1].triangle.getOtherPoint(edge);
		int piIndex = edge.points[0];
		int pjIndex = edge.points[1];

		// References to all points of the two nodes
		Point pi = Triangle.pointsVector.elementAt(piIndex);
		Point pj = Triangle.pointsVector.elementAt(pjIndex);
		Point pr = Triangle.pointsVector.elementAt(prIndex);
		Point pk = Triangle.pointsVector.elementAt(pkIndex);

		// Compute angle of pr
		Vector2D prpi = new Vector2D(pr, pi);
		Vector2D prpj = new Vector2D(pr, pj);
		double anglePr = prpi.angles(prpj);

		// Compute angle of pl
		Vector2D plpi = new Vector2D(pk, pi);
		Vector2D plpj = new Vector2D(pk, pj);
		double anglePl = plpi.angles(plpj);

		// This means that pl lies out of the circumcircle
		if ((anglePr + anglePl) <= Math.PI) {
			return;
		}
		// Else, the edge is illegal and we need to flipp it.
		else {
			// Create the two new nodes
			Node[] children = new Node[2];
			children[0] = new Node(new Triangle(prIndex, piIndex, pkIndex));
			children[1] = new Node(new Triangle(pkIndex, pjIndex, prIndex));

			// Add then in the tree
			nodes[0].children = children;
			nodes[1].children = children;

			// Call recursively legalizeEdge
			legalizeEdge(prIndex, new Edge(piIndex, pkIndex));
			legalizeEdge(prIndex, new Edge(pjIndex, pkIndex));
		}
	}

	public HashSet<Node> lookUp(Edge edge) {
		HashSet<Node> nodes = new HashSet<Node>();
		lookUp(edge, root, nodes);
		return nodes;
	}

	public void lookUp(Edge edge, Node node, HashSet<Node> result) {
		if (node == null)
			return;

		// it's a leaf
		if (node.children == null && node.triangle.contains(edge)) {
			result.add(node);
		} else if (node.children != null) {
			for (Node child : node.children) {
				lookUp(edge, child, result);
			}
		}
	}

	/**
	 * Return the nodes which the point is inside it (must be one triangle or
	 * two)
	 * 
	 * @param p
	 * @return
	 */
	public Node[] testPoint(Point p) {
		// Search triangles
		Node[] result = testPoint(p, root, null);

		// Return result
		return result;
	}

	protected Node[] testPoint(Point p, Node node, Node[] result) {
		// If node is null, just return
		if (node == null)
			return result;

		// Test if point lies in the curent triangle
		if (node.triangle.isPointInside(p)) {
			// If node is a leaf
			if (node.children == null) {
				// Then this triangle is currently in the mesh, and the point
				// lies on it,
				// So we need to add this triangle to the result
				if (result == null) {
					result = new Node[1];
					result[0] = node;
				} else if (result.length == 1) {
					Node[] newResult = new Node[2];
					newResult[0] = result[0];
					newResult[1] = node;
					result = newResult;
				} else {
					System.out.println("Error in TriangleTree - testPoint");
				}
			} else {
				// The current node holds a triangle that were in the mesh, but
				// it's not anymore
				// So, we need to call recursively its children.
				for (Node child : node.children) {
					result = testPoint(p, child, result);
				}
			}
		}
		// Return
		return result;
	}
}

class Node {
	public Triangle triangle;
	public Node[] children = null;

	public Node(Triangle t) {
		triangle = t;
	}

	/**
	 * Given a point P that is in the triangle, this method split this node in
	 * three new nodes.
	 * 
	 * @param prIndex
	 *            = index of P
	 */
	public void splitNode(int prIndex) {
		// Allocate children
		children = new Node[3];

		// Set points
		int[] points = triangle.points;

		// Compute new triangles and add to children
		children[0] = new Node(new Triangle(points[0], points[1], prIndex));
		children[1] = new Node(new Triangle(points[1], points[2], prIndex));
		children[2] = new Node(new Triangle(points[2], points[0], prIndex));
	}
}