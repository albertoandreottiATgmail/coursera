
import java.lang.IndexOutOfBoundsException;


public class Percolation {
   
   private int[][] grid;
   private int N;
   //track the active components
   private int activeComponents;
   private int a[] = {-1,-1,0,0,0,1};
   private int b[] = {0,0,1,-1,-1,0};
   
   private int c[] = {0,1,-1,0,1,0};
   private int d[] = {-1,0,0,1,0,1};
   
   private WeightedQuickUnionUF ufa;
   private WeightedQuickUnionUF ufb;
   
   // create N-by-N grid, with all sites blocked
   public Percolation(int N) {
	   
	   this.N = N;
	   ufa = new WeightedQuickUnionUF(N*N/2 + 3);
	   ufb = new WeightedQuickUnionUF(N*N/2 + 3);
	   grid = new int[N+2][N+2];
   
   }
   
   // open site (row i, column j) if it is not already
   public void open(int i, int j){
	   
       if (i>N |j>N | i<1 | j<1) 
    	   throw new IndexOutOfBoundsException();
       
       if (grid[i][j]>0) return;
       
       for (int k=0; k<6; k++){
    	   if (grid[i+a[k]][j+b[k]]>0 && grid[i+c[k]][j+d[k]]>0)
    	       ufa.union(grid[i+a[k]] [j+b[k]], grid[i+c[k]][j+d[k]]);
    	   if (grid[i+a[k]][j+b[k]]>0 && grid[i+c[k]][j+d[k]]>0)
    	       ufb.union(grid[i+a[k]] [j+b[k]], grid[i+c[k]][j+d[k]]);
		   if(grid[i+c[k]][j+d[k]] > 0) 
		       grid[i][j] = grid[i+c[k]][j+d[k]];
	   }
       
       
       //New component!
       if (grid[i][j]==0){
    	   activeComponents++;
           grid[i][j] = activeComponents;
       }
       
       //If it is in the first row, connect it to the ceiling element N*N/2 + 1
       if (i==1){
    	   ufa.union(grid[i][j], N*N/2 +1);
    	   ufb.union(grid[i][j], N*N/2 +1);
       }
       //If it is in the last row, connect it to the floor element N*N/2 + 2
       if (i==N) ufa.union(grid[i][j], N*N/2 + 2);
   }
   
   // is site (row i, column j) open?
   public boolean isOpen(int i, int j){
	   if (i>N |j>N | i<1 | j<1) 
    	   throw new IndexOutOfBoundsException();
	   
	   return grid[i][j]>0;
   }

   // is site (row i, column j) full?
   public boolean isFull(int i, int j){
	   
	   if (i>N |j>N | i<1 | j<1) 
    	   throw new IndexOutOfBoundsException();
	   
       return ufb.connected(grid[i][j], N*N/2 + 1);
   }
   
   // does the system percolate?
   public boolean percolates(){

	   return ufa.connected(N*N/2 + 1, N*N/2 + 2);
   }
   
}