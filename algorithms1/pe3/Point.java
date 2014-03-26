/*************************************************************************
 * Name: Alberto Andreotti
 * Email: albertoandreotti@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/


import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point> (){

		@Override
		public int compare(Point p1, Point p2) {
			// TODO: assumption, the "invoking point" is this.
			double slope1, slope2;
			slope1 = slopeTo(p1);
			slope2 = slopeTo(p2);
			
			if (slope1<slope2) return -1;
			if (slope1>slope2) return 1;
			return 0;
		}
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }
   
    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        
    	if (that==null) throw new NullPointerException();
    	
        /* YOUR CODE HERE */
    	if (that.y == this.y && that.x == this.x) return Double.NEGATIVE_INFINITY; 
    	if (that.y == this.y) return +0.0;
    	if (that.x == this.x) return Double.POSITIVE_INFINITY;
    	return (double)(that.y - this.y)/(double)(that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        
    	if (this.y == that.y){
    		if (this.x == that.x) return 0;
    	    return this.x > that.x ? 1: -1;
    	}	
    	return this.y < that.y ? -1 : 1;
    	
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
