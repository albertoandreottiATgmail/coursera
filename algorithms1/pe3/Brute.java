
public class Brute {
	
	public static void main(String[] args) {
	
	int findex = 0;
	int found [];
		
	//Retrieve input data
	int N = 0;

	if (args.length != 1){
	       System.out.println("Please provide us with an argument specifying file name.");	
		   return;
	}
	
    String filename = args[0];
    Point points[];
    Point upoints[];
	
	In in = new In(filename);
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
		
	Quick.sort(points);
	
	
	int n = N-1;
	int u = 0;
	for (int i = 0; i < n; i++) {
        if(points[i].compareTo(points[i+1])!=0){
        	upoints[u] = points[i]; u++;
        	if(i==N-2) {
        		upoints[u] = points[i+1]; u++;
        	}
        }      
    }

	double slope = 0.0;
	//TODO: how many?
	found = new int[N*N*N];
	
	//Brute force loops
	for(int i=0; i<u-3; i++){	
		for(int j=i+1; j<u-2; j++){
			slope = upoints[i].slopeTo(upoints[j]);
			for(int k=j+1; k<u-1; k++){
				//If the third point is not aligned with the prior two, skip it.
				if (upoints[i].slopeTo(upoints[k]) != slope) continue;
				for(int l=k+1; l<u; l++){
					if (upoints[i].slopeTo(upoints[l]) != slope) continue;
					//Now we got the point
					found[findex] = i;
					found[findex+1] = j;
					found[findex+2] = k;
					found[findex+3] = l;
					findex = findex + 4;
				}
			}
		}
		
	}
	
	for(int j=0; j<findex; j = j + 4){
		StdOut.println(points[found[j]] + " -> " + points[found[j+1]] + " -> " + points[found[j+2]] + " -> " + points[found[j+3]]);
		points[found[j]].drawTo(points[found[j+3]]);
	}
    // display to screen all at once
    StdDraw.show(0);
	
	}
}
