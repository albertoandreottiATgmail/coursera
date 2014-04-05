import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class PointSET {

	private class PointKey implements Comparable<PointKey> {

		private Point2D origin = new Point2D(0, 0);
		private Point2D local;
		private double norm;

		public void draw() {

			StdDraw.point(local.x(), local.y());
		}

		public PointKey(Point2D p) {
			local = p;
			norm = p.distanceTo(origin);
		}


		@Override
		public int compareTo(PointKey other) {

			if (local.compareTo(other.local) == 0)
				return 0;
			if (this.norm < other.norm)
				return -1;
			if (this.norm > other.norm)
				return 1;
			return Point2D.X_ORDER.compare(local, other.local);
		}

	}

	private TreeSet<PointKey> treeSet;

	// construct an empty set of points
	public PointSET() {
		treeSet = new TreeSet<PointKey>();
	}

	// is the set empty?
	public boolean isEmpty() {
		return treeSet.isEmpty();
	}

	// number of points in the set
	public int size() {
		return treeSet.size();
	}

	// add the point p to the set (if it is not already in the set)
	public void insert(Point2D p) {
		treeSet.add(new PointKey(p));
	}

	// does the set contain the point p?
	public boolean contains(Point2D p) {
		return treeSet.contains(new PointKey(p));
	}

	// draw all of the points to standard draw
	public void draw() {
		StdDraw.show(0);
		for (PointKey point : treeSet)
			point.draw();
	}

	private class PointSETFactory implements Iterable<Point2D> {

		private List<Point2D> values;

		public PointSETFactory(List<Point2D> list) {
			values = list;
		}

		@Override
		public Iterator<Point2D> iterator() {
			return values.iterator();
		}
	}

	// all points in the set that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		List<Point2D> matches = new ArrayList<Point2D>();

		for (PointKey pkey : treeSet) {
			if (rect.contains(pkey.local))
				matches.add(pkey.local);
		}

		return new PointSETFactory(matches);
	}

	// a nearest neighbor in the set to p; null if set is empty
	public Point2D nearest(Point2D p) {
		Point2D candidate = null;
		double min = Double.MAX_VALUE;

		for (PointKey pkey: treeSet)
			if (pkey.local.distanceTo(p) < min) {
				min = pkey.local.distanceTo(p);
				candidate = pkey.local;
			}

		// null returned when the set is empty.
		return candidate;
	}
}
