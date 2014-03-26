import java.util.Comparator;


public class Solver {
    
	MinPQ<SearchNode> minpq;
	
	class SearchNode{
		public Board board;
		public int moves;
		public Board previous;
		public SearchNode(Board board, Board previous, int moves){
			this.board = board;
			this.previous = previous;
			this.moves = moves;
		}
	    
		// compare search nodes
	    public final Comparator<SearchNode> HAMMING_ORDER = new Comparator<SearchNode> (){
			@Override
			public int compare(SearchNode sn1, SearchNode sn2) {
				// TODO: check order of parameters
				int distance1 = sn1.board.hamming();
				int distance2 = sn2.board.hamming();
				
				if (distance1<distance2) return -1;
				if (distance1>distance2) return 1;
				return 0;
			}
	    };	
	}
	
	
	//private Board board;
	
	public Solver(Board initial){            // find a solution to the initial board (using the A* algorithm)
    	//board = initial;
    	minpq = new MinPQ<SearchNode>();   
    	minpq.insert(new SearchNode(initial, null, 0));
    	
    	while(true){
    		 SearchNode min = minpq.delMin();
    		 for(Board b : min.board.iterator())
    			 b.dimension();
    	}
    	
    }
    public boolean isSolvable(){             // is the initial board solvable?
    	return false;
    }
    
    public int moves(){                      // min number of moves to solve initial board; -1 if no solution
    	return -1;
    }
    public Iterable<Board> solution(){       // sequence of boards in a shortest solution; null if no solution
    	return null;
    }
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    
    
}