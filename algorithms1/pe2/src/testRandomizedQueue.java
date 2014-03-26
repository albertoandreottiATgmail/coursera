import java.util.Iterator;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.Collections;

public class testRandomizedQueue {
	
	
	
	public static void main(String[] args){
		
		RandomizedQueue<Integer> rqueue;
		ArrayList<Integer> alist = new ArrayList();
		
		rqueue = new RandomizedQueue<Integer>();
		
		assert(rqueue.isEmpty());
		
		/*
		rqueue.enqueue("a");
		rqueue.enqueue("b");
		assert(rqueue.size()==2);
		
		rqueue.enqueue("c");
		rqueue.enqueue("d");
		
		while(rqueue.sample() != "a");
		while(rqueue.sample() != "b");
		while(rqueue.sample() != "c");
		while(rqueue.sample() != "d"); 
		
		assert(rqueue.size()==4);
		
		rqueue.dequeue();
		rqueue.dequeue();
		
		assert(rqueue.size()==4); */
		
		for (int i=1; i<1000000; i++)
		    rqueue.enqueue(new Integer(i)); 
		
		Iterator<Integer> iter = rqueue.iterator();
		
		int count = 0;
		for (Integer i : rqueue)
			 alist.add(i);
		
		Collections.sort(alist);

		for (int i=0; i<999999; i++){
			int val = (alist.get(i)).intValue();
			if(val!=i+1)
				System.out.println("Broken"); 
			
		}
			
	
	}

}
