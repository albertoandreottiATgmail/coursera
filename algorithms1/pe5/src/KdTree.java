import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
public class KdTree {
	
	
	class KdNode {
		
		private KdNode right, left;
		private Point2D local;
		
		public KdNode(KdNode right, KdNode left, Point2D p){
			local = p;
		}
		
		public KdNode(Point2D p){
			local = p;
			left = new EmptyNode();
			right = new EmptyNode();
		}
		
		public KdNode() {
			
		}
		
		public int size(){
			
			return left.size() + right.size();
		}
		
		public KdNode putX(Point2D k){
			
		if (local.compareTo(k) < 0)
			return right.putY(k);
		else if (local.x() < k.x())
			left
		else return this;
			
			return this;
			
		}
		
		public KdNode putY(Point2D k){
			
		}
		
	}
	
	class EmptyNode extends KdNode{
		
		public EmptyNode(){
			
		}
		
		public int size(){
			return 0;
		}
	}
	
	private KdNode root;
	private Point2D origin;
	
	// construct an empty set of points
	public KdTree() {
        origin = new Point2D(0.0, 0.0);
        root = new KdNode();
	}

	// is the set empty?
	public boolean isEmpty() {
		return root.size() == 0;
	}
	
	// number of points in the set
	public int size() {
		return root.size();
	}

	// add the point p to the set (if it is not already in the set)
	public void insert(Point2D p) {
        root.put(p);
	}

	// does the set contain the point p?
	public boolean contains(Point2D p) {
		
	}

	// draw all of the points to standard draw
	public void draw() {
		StdDraw.show(0);
        for(Point2D point: treeMap.values()) {
		    StdDraw.point(point.x(), point.y());
        }
	}


	class KdTreeIteratorFactory implements Iterable<Point2D>{
		
		private List<Point2D> values;
		public KdTreeIteratorFactory(List<Point2D> list){
			values = list;
		}
		@Override
        public Iterator<Point2D> iterator(){
        	return values.iterator();
        }
	}
	
	// all points in the set that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		List<Point2D> matches = new ArrayList<Point2D>();
		
		for (Point2D point: treeMap.values()){
			if(rect.contains(point))
				matches.add(point);
		}
		
		return new KdTreeIteratorFactory(matches);
	}

	// a nearest neighbor in the set to p; null if set is empty
	public Point2D nearest(Point2D p) {
		Point2D candidate = null;
		double min = Double.MAX_VALUE;
		
		for (Point2D point: treeMap.values())
			if(point.distanceTo(p)<min) {
			min = point.distanceTo(p);
			candidate = point;
			}
		
		//null returned when the set is empty.
		return candidate;
	}
}
 