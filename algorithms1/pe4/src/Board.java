import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board implements Iterable<Board>{
    
	private int[][] blocks;
	private int N, x, y;
	public Board(int[][] blocks){           // construct a board from an N-by-N array of blocks
    	
		if(blocks==null)throw new NullPointerException();
		
		N = blocks.length;
		blocks = new int[N][N];
		for (int i=0; i<N;i++){
    		for(int j=0; j<N; j++){
    			this.blocks[i][j] = blocks[i][j];
    			if (blocks[i][j]>N || blocks[i][j]<1) {x=i;y=j;}
    		}
    	}
    }
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension(){                 // board dimension N
    	return N;
    }
    
    public int hamming(){                   // number of blocks out of place
    	int count = 0;
    	for (int i=0; i<N;i++){
    		for(int j=0; j<N; j++){
    			if(i==N-1 && j==N-1) continue;
    			count += (blocks[i][j] != i*N + j)? 1 : 0;
    		}
    	}
    	return count;
    	
    }
    
    public int manhattan(){                 // sum of Manhattan distances between blocks and goal
    	int count = 0;
    	int correctX, correctY;
    	
    	for (int i=0; i<N;i++){
    		for(int j=0; j<N; j++){
    			if(i==N-1 && j==N-1) continue;
    			correctY = blocks[i][j]%N - 1;
    		    correctX = (blocks[i][j]- 1)/N;
    			count += abs(correctX-i) + abs(correctY-j);
    		}
    	}
    	return count;
    }

    private int abs(int n){
    	return n<0? -n:n;
    }
    
    public boolean isGoal(){                // is this board the goal board?
    	boolean correct = true;
    	int correctX, correctY;
    	
    	for (int i=0; i<N;i++){
    		for(int j=0; j<N; j++){
    			if(i==N-1 && j==N-1) continue;
    			correctY = blocks[i][j]%N - 1;
    		    correctX = (blocks[i][j]- 1)/N;
    			correct = correct && correctX==i && correctY==j;
    		}
    	}
    	return correct;
    }
    
    public Board twin(){                    // a board obtained by exchanging two adjacent blocks in the same row

    	int clonned[][] = blocks.clone();
    	int rnd = StdRandom.uniform(N);
    	int temp = clonned[rnd][rnd];
    	int j;
    	
    	if (rnd%N==0) j = 1;
    	else j = -1;
    	
    	clonned[rnd][rnd] = clonned[rnd][rnd+j];
    	clonned[rnd][rnd+j] = temp;
    	return new Board(clonned);
    }
    public boolean equals(Object y){       // does this board equal y?
    	boolean correct = true;
   	
    	for (int i=0; i<N;i++){
    		for(int j=0; j<N; j++){
    			if(i==N-1 && j==N-1) continue;
    			correct = correct && ((Board)y).blocks[i][j]==blocks[i][j] ;
    		}
    	}
    	return correct;
    }
    public Iterable<Board> neighbors(){     // all neighboring boards
    	return this;	
    }
    public String toString(){               // string representation of the board (in the output format specified below)
    	return null;
    }
	
    @Override
	public Iterator<Board> iterator() {
		// return newly created iterator
		return new Iterator<Board>() {

			boolean inited = false;
			int count = 4;
			int items[] = {1,2,3,4};
			
			@Override
			public boolean hasNext() {
				if(!inited) init();
				return count>0;
			}

			@Override
			public Board next() {
				int movedX=x, movedY=y;
				if(!inited) init();
				if (count<1) throw new NoSuchElementException();
				
				count--;
				
				while(items[count]!=0 && count>0) count--;
				
				int newblocks[][] = blocks.clone();
				
				if(items[count]==4) movedY--;
				if(items[count]==1) movedX--;
				if(items[count]==2) movedY++;
				if(items[count]==3) movedX++;
				items[count]=0;
				
				newblocks[x][y] = newblocks[movedX][movedY];
				newblocks[movedX][movedY] = 0;
				
				return new Board(newblocks);
			}

			@Override
			public void remove() {
				if(!inited) init();
				
			}
			
			private void init(){
				inited = true;
				//how many do we have?
				if (x==0 || x==N-1) count--;
				if (y==0 || y==N-1) count--;
				
				if (x==0) items[0] = 0;
				if (x==N-1) items[2] = 0;
				if (y==0) items[3] = 0;
				if (y==N-1) items[1] = 0;
				
				
			}
		};
	}
}