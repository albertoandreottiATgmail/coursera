import java.lang.Math;


public class PercolationStats {
   
   private Percolation board;
   private double mean, stddev, lower, upper, temp;
   private double [] fractions;
   
   // perform T independent computational experiments on an N-by-N grid

   public PercolationStats(int N, int T){
	   
	   if (N<=0 || T<=0) {
	   	throw new IllegalArgumentException();
	   
	   }
	   
	   int a, b;
	   fractions = new double[T]; 
	   
	   for (int i=0; i<T; i++){
		   board = new Percolation(N);
		   
           while(!board.percolates()){
			   do{
    		       a = StdRandom.uniform(N);
                   b = StdRandom.uniform(N);
		       }
               while(board.isOpen(++a, ++b));
    
               board.open(a, b);
               fractions[i]++;
	       }
           fractions[i]/=N*N;
           mean += fractions[i];
       }
	   mean /= T;
	   
	   for (int i=0; i<T; i++){
	       stddev += Math.pow(fractions[i] - mean, 2.0);
	   }
	   stddev /=(T-1);
	   stddev = Math.sqrt(stddev);
	   
	   temp = (1.96 * stddev )/Math.sqrt(T);
	   lower = mean - temp;
	   upper = mean + temp;
	   
   }
   // sample mean of percolation threshold
   public double mean(){
	   return mean;
   }
   // sample standard deviation of percolation threshold
   public double stddev(){
	   return stddev;
   }
   
   // returns lower bound of confidence interval
   public double confidenceLo(){
	   return lower;
	   
   }
   
   public double confidenceHi(){
	   return upper;	   
	   
   }
   
   // test client, described below
   public static void main(String[] args){
	   if (args.length != 2)
		   return;
	   
	   int N = Integer.parseInt(args[0]); 
	   int T = Integer.parseInt(args[1]);
	   
	   PercolationStats stats = new PercolationStats(N, T);
	   
	   System.out.print("mean                    = ");
	   System.out.println(stats.mean);
	   
	   System.out.print("stddev                  = ");
	   System.out.println(stats.stddev);
	   
	   System.out.print("95% confidence interval = ");
	   System.out.print(stats.lower);
	   System.out.print(", ");
	   System.out.println(stats.upper);
   }
}