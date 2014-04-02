import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
public class PointSET {
	
	private TreeMap<Double, Point2D> treeMap;
	private Point2D origin;
	
	// construct an empty set of points
	public PointSET() {
        treeMap = new TreeMap<Double, Point2D>();
        origin = new Point2D(0.0, 0.0);
	}

	// is the set empty?
	public boolean isEmpty() {
		return treeMap.isEmpty();
	}
	
	// number of points in the set
	public int size() {
		return treeMap.size();
	}

	// add the point p to the set (if it is not already in the set)
	public void insert(Point2D p) {
        treeMap.put(p.distanceTo(origin), p);
	}

	// does the set contain the point p?
	public boolean contains(Point2D p) {
		return treeMap.containsKey(p.distanceTo(origin));
	}

	// draw all of the points to standard draw
	public void draw() {
		StdDraw.show(0);
        for(Point2D point: treeMap.values()) {
		    StdDraw.point(point.x(), point.y());
        }
	}


	class PointSETFactory implements Iterable<Point2D>{
		
		private List<Point2D> values;
		public PointSETFactory(List<Point2D> list){
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
		
		return new PointSETFactory(matches);
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
 





