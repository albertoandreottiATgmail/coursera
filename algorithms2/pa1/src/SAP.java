public class SAP {

	private Digraph dg;
	private DirectedCycle dc;

	private void checkArg(int v) {
		if (v >= dg.V() || v < 0)
			throw new IllegalArgumentException();
	}

	private class MinAncestor {

		private int min = -1;
		private int weight = Integer.MAX_VALUE;

		public void update(int V, int d) {
			if (d < 0)
				return;
			if (d < weight) {
				weight = d;
				min = V;
			}
		}

		public void update(MinAncestor m) {
			if (m.weight < 0)
				return;
			if (m.weight < weight) {
				weight = m.weight;
				min = m.min;
			}
		}

		public int getA() {
			return min;
		}

		public int getW() {
			if (weight == Integer.MAX_VALUE)
				return -1;
			else
				return weight;
		}

	}

	private class DigraphSP {

		private int[] distTo;
		private IndexMinPQ<Integer> pq;
		private Digraph dg;
		private int current = Integer.MAX_VALUE;

		public DigraphSP(Digraph dg) {
			this.dg = dg;

		}

		public void compute(int s) {
			if (current != s)
				current = s;
			else
				return;

			distTo = new int[dg.V()];
			pq = new IndexMinPQ<Integer>(dg.V());
			for (int i = 0; i < dg.V(); i++)
				distTo[i] = Integer.MAX_VALUE;

			distTo[s] = 0;
			pq.insert(s, 0);
			while (!pq.isEmpty()) {
				int v = pq.delMin();
				for (int w : dg.adj(v))
					relax(v, w);
			}
		}

		public void clean(int s) {
			current = Integer.MAX_VALUE;
			bfs(s);

		}

		private void bfs(int i) {
			distTo[i] = Integer.MAX_VALUE;
			for (int j : dg.adj(i))
				bfs(j);
		}

		private void relax(int v, int w) {
			if (distTo[w] > distTo[v] + 1) {
				distTo[w] = distTo[v] + 1;
				if (pq.contains(w))
					pq.decreaseKey(w, distTo[w]);
				else
					pq.insert(w, distTo[w]);
			}
		}

		public int distTo(int t) {
			return distTo[t];
		}
	}

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		dg = G.reverse().reverse();
		dc = new DirectedCycle(dg);

	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {

		return sadp(v, w).getW();
	}

	// a common ancestor of v and w that participates in a shortest ancestral
	// path; -1 if no such path
	public int ancestor(int v, int w) {

		return sadp(v, w).getA();
	}

	private DigraphSP dspA;
	private DigraphSP dspB;

	private MinAncestor sadp(int synsetA, int synsetB) {
		checkArg(synsetA);
		checkArg(synsetB);

		Queue<Integer> bfsq = new Queue<Integer>();
		MinAncestor ma = new MinAncestor();
		boolean[] visited = new boolean[dg.V()];

		// dspA.compute(synsetA);
		// dspB.compute(synsetB);
		dspA = new DigraphSP(dg);
		dspA.compute(synsetA);
		dspB = new DigraphSP(dg);
		dspB.compute(synsetB);

		bfsq.enqueue(synsetA);
		while (!bfsq.isEmpty()) {
			int idx = bfsq.dequeue();
			visited[idx] = true;
			if (dspB.distTo(idx) != Integer.MAX_VALUE)
				ma.update(idx, dspA.distTo(idx) + dspB.distTo(idx));
			for (int adj : dg.adj(idx))
				if (!visited[adj])
					bfsq.enqueue(adj);
		}

		// dspA.clean(synsetA);
		// dspA.clean(synsetB);

		return ma;

	}

	// length of shortest ancestral path between any vertex in v and any vertex
	// in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		MinAncestor ma = new MinAncestor();
		for (int t : v)
			for (int s : w)
				ma.update(0, length(t, s));

		return ma.getW();

	}

	// a common ancestor that participates in shortest ancestral path; -1 if no
	// such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		MinAncestor ma = new MinAncestor();
		ma.min = -1;
		for (int t : v)
			for (int s : w)
				ma.update(sadp(t, s));

		return ma.min;
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}
	}

}