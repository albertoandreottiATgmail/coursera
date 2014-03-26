import java.util.NoSuchElementException;
import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item> {
	
	
       private Item elements[];
       
       private int bCount, aSize;
	
	   public RandomizedQueue(){           // construct an empty randomized queue
		   
		   aSize = 100;
		   
		   elements = (Item[]) new Object[aSize];
  
	   }
	   
	   public boolean isEmpty(){           // is the queue empty?
		   return bCount==0;
	   }
	   
	   public int size(){                  // return the number of items on the queue
		   return bCount;
	   }
	   
	   public void enqueue(Item item){     // add the item
		   
		   if (item==null)
				throw new NullPointerException();
			
		   elements[++bCount] = item;		   
		   
           if (bCount> aSize/4){
        	   resize();
           }
	   }
	   
	   public Item dequeue(){              // delete and return a random item
		   
		   if (bCount==0)
			   throw new NoSuchElementException();
		   
		   int rndIndex = StdRandom.uniform(bCount);
		   Item item = elements[++rndIndex];
     	   elements[rndIndex] = elements[bCount];
     	   
     	   elements[bCount] = null;
		   
		   bCount--;
		   return item;
		   
	   }
	   
	   public Item sample(){			   // return (but do not delete) a random item

		   if (bCount==0)
			   throw new NoSuchElementException();
		   
		   int rndIndex = StdRandom.uniform(bCount);
		   
		   return elements[++rndIndex];
           		   
	   }
	   public Iterator<Item> iterator(){   // return an independent iterator over items in random order
		   
		    return new  Iterator<Item>() {
		    	
		    	private int nonVisited[];
		    	private boolean inited = false;
		    	private int index;

		        @Override
				public boolean hasNext() {
		 		    
		        	if(!inited)
		 		    	init();
					
		        	return index!=0;
				}

				@Override
				public Item next() {

		 		    if(!inited) init();
		 		    
					if (index==0)
		                throw new NoSuchElementException();
	            
		 		    int rndIndex = StdRandom.uniform(index);
		 		    int element  = nonVisited[++rndIndex];
		 		    
		 		    if (element==0)
		 		    	element = rndIndex;
                    
		 		    if (nonVisited[index]!=0)
		 		        nonVisited[rndIndex] = nonVisited[index];
		 		    else
		 		    	nonVisited[rndIndex] = index;
		 		    
		 		    index--;
		 		    
		 		    Item item = elements[element];
				    return item;
		           
				}

				@Override
				public void remove() {
		            
					throw new UnsupportedOperationException();
					
				}
				
				private void init(){
					index = bCount;
					nonVisited =  new int[bCount+1];
					inited = true;
				}
				};
		    	    
		     
	   }
	   
   
	   private void resize(){
		   
		   Item newElements[];
		   aSize = aSize*2;
		   newElements = (Item[]) new Object[aSize];
		   
		   for (int i=1; i<=bCount; i++)
			   newElements[i] = elements[i];
		   
		   elements = newElements;
			
	   }
	   
}