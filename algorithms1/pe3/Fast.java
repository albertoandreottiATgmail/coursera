import java.io.BufferedReader;

import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Fast {
	
	
	public static void main(String[] args) {
		//Retrieve input data
		int N = 0;

		if (args.length != 1){
		       System.out.println("Please provide us with an argument specifying file name.");	
			   return;
		}
		
	    Point points[], upoints[];
	    int found[];
	    
		In in = new In(args[0]);
	    N = in.readInt();
		points = new Point[N];
		upoints = new Point[N];
	        
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);

		
		for (int i = 0; i < N; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	        points[i].draw();
	    }	
		
		class Slope implements Comparable<Slope> {

			private int index;
			private double slope;
			
			public Slope(int i, double s){
				index = i; slope = s;
			}
			
			@Override
			public int compareTo(Slope other) {
				// TODO Auto-generated method stub
				if (this.slope < other.slope) return -1;
				if (this.slope > other.slope) return  1;
				return 0;
				
			}					
		}
		
		Slope slopes[];
		found = new int[N*N+1];
		int index = 0;
		Quick.sort(points);
		//Compute the slopes
        for (int i = 0 ; i< points.length - 1 ; i++){
        	slopes = new Slope[points.length-i];
        	slopes[points.length-i-1] = new Slope(0, Double.POSITIVE_INFINITY);
        	
        	for (int j = i+1 ; j< points.length ; j++){
        		slopes[j-i-1] = new Slope(j,points[i].slopeTo(points[j]));
        	}
        	Merge.sort(slopes);
        	slopes[points.length-i-1] = new Slope(0, Double.NEGATIVE_INFINITY);
        	
        	//Search collinear points
        	double slope = slopes[0].slope;
        	int count = 0;
    		double next;
        	for (int j = 0 ; j< slopes.length -1 ; j++){

        		    if (slope == slopes[j].slope) count++;        	
           		
        		    
        		    
        		    //If we enter that condition we found 4 or more points
            		if(slope != slopes[j+1].slope){
           			            			
            			if (count>2) {
	                		found[index] = i; 
	                		index++;
    	            		for(int k = 0; k<count; k++)
        	        			found[k+index] = slopes[k+j-count+1].index;                		
            	    		
    	            		index = index + count;
                			found[index] = -1;
                			++index;
                			count = 0;
                		}
            		}
            		if(slope != slopes[j+1].slope) {
            			count = 0;
            			slope = slopes[j+1].slope;
            		}
            		
            		
        	}
        }

    	int first = 0, last = 0;
        for(int j=0; j<index; j++){
    		
        	if (found[j]>=0){
    			System.out.print(points[found[j]]);
    			if (found[j+1]>=0)
    			   System.out.print(" -> ");
    		}
    		else{
    			System.out.print("\n");
    			last = j-1;
    			points[found[first]].drawTo(points[found[last]]);
    			first = j+1;
    		}
    		
    	}       
	}	
}
