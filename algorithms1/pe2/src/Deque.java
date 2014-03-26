import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
		   
	private Node first, last;
	private int count = 0;
	
	public Deque(){                     // construct an empty deque
		
		first = null;
		last = null;
		count = 0;
			   
	}
    public boolean isEmpty(){           // is the deque empty?
    	return count==0;
    }
    
    public int size(){                  // return the number of items on the deque
		return count;
	}
    
	public void addFirst(Item item){    // insert the item at the front
		
		if (item==null)
			throw new NullPointerException();
		
		Node newNode = new Node(item);
		newNode.prev = first;
		
		if (first!=null)
		    first.next = newNode;
		else
		    last = newNode;
			
		first = newNode;
		count++;
		
	}
    public void addLast(Item item){    // insert the item at the end
	    
    	if (item==null)
			throw new NullPointerException();
    	
    	Node newNode = new Node(item);
		newNode.next = last;
		
		if (last!=null)
		    last.prev = newNode;
		else
			first = newNode;
		
		last = newNode;
		count++;
	} 
		   
    public Item removeFirst(){  // delete and return the item at the front
	    if (first==null)
	    	throw new NoSuchElementException();
    	
    	Node oldFront = first;
		first = oldFront.prev;
		
		if (first!=null)
		    first.next = null;
		else 
			last = null;
		
		count--;
		return oldFront.item;
		
	
	}
    
	public Item removeLast()          {// delete and return the item at the end
	    
		if (last==null)
	    	throw new NoSuchElementException();
		
		Node oldLast = last;
		last = oldLast.next;
		
		if (last!=null)
		    last.prev = null;
		else
			first = null;
		
		count--;
		return oldLast.item;
	} 
	
	public Iterator<Item> iterator()   {// return an iterator over items in order from front to end
		   
    return new  Iterator<Item>() {
    	
    	Node nextElement = first;

		@Override
		public boolean hasNext() {
			
			return nextElement!=null;
		}

		@Override
		public Item next() {
            if (nextElement==null)
                throw new NoSuchElementException();
            
            Node oldNext = nextElement;
            nextElement = nextElement.prev;
			return oldNext.item;
		}

		@Override
		public void remove() {
            
			throw new UnsupportedOperationException();
			
		}
    	
    
                };
    }
	
	private class Node{
	    
		private Node next, prev;
		private Item item;
		Node(Item item){this.item = item; }
			   
  	}
}
	
	
	