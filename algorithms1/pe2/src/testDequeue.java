import java.util.Iterator;


public class testDequeue {
	

	public static void main(String[] args){
		
		Deque<String> deque;
		
		deque = new Deque<String>();
		
		deque.addFirst("Hola");
		deque.addFirst("Hola");
		deque.addLast("Chau");
		deque.addLast("Chau");
		
		for (String a: deque)
			System.out.print(a);
	
	}
	

}