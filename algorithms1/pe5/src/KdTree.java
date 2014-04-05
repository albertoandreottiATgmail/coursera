import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class KdTree {

	private class KdNode {

		private KdNode right, left;
		private Point2D local;
		private int size = 1;
		private Comparator<Point2D> cmp;
		protected RectHV rect;

		public double distance(Point2D p) {
			return rect.distanceTo(p);
		}

		public KdNode(Point2D p, Comparator<Point2D> comparator, RectHV l,
				RectHV r) {
			local = p;
			left = new EmptyNode(l);
			right = new EmptyNode(r);
			rect = new RectHV(l.xmin(), l.ymin(), r.xmax(), r.ymax());
			cmp = comparator;
		}

		public KdNode() {

		}

		public int size() {
			return size;
		}

		public KdNode putX(Point2D k) {

			if (local.compareTo(k) == 0)
				return this;
			int cmp = Point2D.X_ORDER.compare(k, local);
			if (cmp > 0)
				right = right.putY(k);
			else
				left = left.putY(k);

			size = right.size() + left.size() + 1;
			return this;
		}

		public KdNode putY(Point2D k) {
			if (local.compareTo(k) == 0)
				return this;

			int cmp = Point2D.Y_ORDER.compare(k, local);
			if (cmp > 0)
				right = right.putX(k);
			else
				left = left.putX(k);

			size = right.size() + left.size() + 1;
			return this;

		}

		public Point2D nearest(Point2D p) {
			return nearest(p, local);
		}

		public Point2D nearest(Point2D p, Point2D best) {

			Point2D localbest = closer(local, best, p);

            if(cmp.compare(local, p) < 0){
            	localbest = explore(right, p, localbest);
            	localbest = explore(left, p, localbest);
            }
            else{
            	localbest = explore(left, p, localbest);
            	localbest = explore(right, p, localbest);
            }

			return localbest;

		}
		
		public Point2D explore(KdNode k, Point2D p, Point2D best){
			if (k.rect.distanceTo(p) < p.distanceTo(best))
				return closer(k.nearest(p, best), best, p);
			return best;
		}

		private Point2D closer(Point2D a, Point2D b, Point2D c) {

			return a.distanceTo(c) < b.distanceTo(c) ? a : b;

		}

		public KdNode getX(Point2D k) {

			if (local.compareTo(k) == 0)
				return this;

			int cmp = Point2D.X_ORDER.compare(k, local);

			if (cmp > 0)
				return right.getY(k);
			else
				return left.getY(k);

		}

		public KdNode getY(Point2D k) {

			if (local.compareTo(k) == 0)
				return this;

			int cmp = Point2D.Y_ORDER.compare(k, local);

			if (cmp > 0)
				return right.getX(k);
			else
				return left.getX(k);

		}

		public void draw() {
			left.draw();
			StdDraw.point(local.x(), local.y());
			right.draw();
		}

		public List<Point2D> range(RectHV r, List<Point2D> acc) {

			if (r.contains(local))
				acc.add(local);

			if (left.rect.intersects(r))
				left.range(r, acc);

			if (right.rect.intersects(r))
				right.range(r, acc);

			return acc;
		}


	}

	private class EmptyNode extends KdNode {

		public EmptyNode(RectHV r) {
			rect = r;
		}

		public Point2D nearest(Point2D p, Point2D best) {

			return best;
		}

		public List<Point2D> range(RectHV rect, List<Point2D> acc) {

			return acc;
		}

		public void draw() {

		}

		public KdNode putX(Point2D p) {
			return new KdNode(p, Point2D.X_ORDER, new RectHV(rect.xmin(),
					rect.ymin(), p.x(), rect.ymax()), new RectHV(p.x(),
					rect.ymin(), rect.xmax(), rect.ymax()));
		}

		public KdNode putY(Point2D p) {
			return new KdNode(p, Point2D.Y_ORDER, new RectHV(rect.xmin(),
					rect.ymin(), rect.xmax(), p.y()), new RectHV(rect.xmin(),
					p.y(), rect.xmax(), rect.ymax()));
		}

		public KdNode getX(Point2D p) {
			return null;
		}

		public KdNode getY(Point2D p) {
			return null;
		}

		public int size() {
			return 0;
		}
	}

	private KdNode root;

	// is the set empty?
	public boolean isEmpty() {
		return root == null;
	}

	// number of points in the set
	public int size() {

		return root == null ? 0 : root.size();
	}

	// add the point p to the set (if it is not already in the set)
	public void insert(Point2D p) {

		if (p.x() < 0 || p.y() < 0)
			return;

		if (root == null)
			root = new KdNode(p, Point2D.X_ORDER, new RectHV(0.0, 0.0, p.x(),
					1.0), new RectHV(p.x(), 0.0, 1.0, 1.0));
		else
			root.putX(p);
	}

	// does the set contain the point p?
	public boolean contains(Point2D p) {
		return root.getX(p) != null;
	}

	// draw all of the points to standard draw
	public void draw() {
		StdDraw.show(0);
		if (root != null)
			root.draw();
	}

	private class KdTreeIteratorFactory implements Iterable<Point2D> {

		private List<Point2D> values;

		public KdTreeIteratorFactory(List<Point2D> list) {
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
		root.range(rect, matches);
		return new KdTreeIteratorFactory(matches);
	}

	// a nearest neighbor in the set to p; null if set is empty
	public Point2D nearest(Point2D p) {
		return root.nearest(p);
	}

	public static void main(String[] args) {

		Point2D point;
		KdTree tree = new KdTree();
		
		for (int i = 0; i < 1200000; i++) {
			point = new Point2D(Math.random(), Math.random());
			tree.insert(point);
			assert (tree.contains(point)): "error on" + i;
    	}
		
		tree.insert(new Point2D(0.499, 0.499));
		
		final long startTime = System.currentTimeMillis();
		
		for (int i = 0; i < 10000; i++) {
						
			point = new Point2D(Math.random(), Math.random());
			
			assert (tree.nearest(point).distanceTo(point)<0.02): "error on" + i;
    	}
		
			
		
		final long endTime = System.currentTimeMillis();
		System.out.println("Average execution time: " + (endTime - startTime)/10000.0 );
		

	}
}
