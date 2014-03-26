import java.util.NoSuchElementException;
public class Subset {

	public static void main(String[] args){
		
		int N;
		RandomizedQueue<String> rqueue;
		
		if (args.length != 1)
			   return;
		   
		rqueue = new RandomizedQueue<String>();
		
		N = Integer.parseInt(args[0]);
		
		String a = StdIn.readString();
		
		while(a!=null) {
			rqueue.enqueue(a);
			try {
			      a = StdIn.readString();
			}
			catch (NoSuchElementException e){
				a = null;
				break;
			}
		}
		
		for(int j=0; j<N; j++)
			System.out.println(rqueue.dequeue());
     }
	
}
