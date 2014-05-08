
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class WordNet {
	private SAP sap;
	private TreeMap<String, List<Integer>> nouns = new TreeMap<String, List<Integer>>();
	private TreeMap<Integer, String> sets = new TreeMap<Integer, String>();
	private int size = 0;

	// constructor takes the name of the two input files

	public WordNet(String synsets, String hypernyms) {

		String[] vw;
		Integer synset = 0;

		// step1: build noun TreeSet
		In br = new In(synsets);
		if (!br.hasNextLine())
			throw new IllegalArgumentException();

		do {
			String line = br.readLine();
			size++;
			vw = line.split(",");
			synset = new Integer(vw[0]);
			// save the set
			sets.put(Integer.parseInt(vw[0]), vw[1]);
			// save the nouns
			vw = vw[1].split(" ");
		
			for (String noun: vw) {
				if (nouns.containsKey(noun))
					nouns.get(noun).add(synset);
				else {
					ArrayList<Integer> al = new ArrayList<Integer>();
					al.add(synset);
					nouns.put(noun, al);
				}
			}
		} while (br.hasNextLine());
		br.close();

		// step2: build graph
		br = new In(hypernyms);
		Digraph dg = new Digraph(size);
		do {
			String line = br.readLine();
			vw = line.split(",");
			for (int i = 1; i < vw.length; i++)
				dg.addEdge(Integer.parseInt(vw[0].trim()),
						Integer.parseInt(vw[i].trim()));

		}while (br.hasNextLine());
			
		br.close();
		DirectedCycle dc = new DirectedCycle(dg);
		if (dc.hasCycle())
			throw new IllegalArgumentException();

		check(dg);
		sap = new SAP(dg);

	}

	private int[] roots;
	private boolean[] marked;

	private void check(Digraph dg) {

		roots = new int[dg.V()];
		marked = new boolean[dg.V()];

		for (int j = 0; j < dg.V(); j++)
			roots[j] = -1;

		int root = dfs(0, dg);
		roots[0] = root;

		for (int node = 0; node < dg.V(); node++) {
			if (dfs(node, dg) != root)
				throw new IllegalArgumentException();

		}
	}

	// returns the root
	private int dfs(int node, Digraph dg) {

		if (!dg.adj(node).iterator().hasNext())
			return node;

		if (roots[node] == -1)
			roots[node] = dfs(dg.adj(node).iterator().next(), dg);

		return roots[node];

	}

	// the set of nouns (no duplicates), returned as an Iterable
	public Iterable<String> nouns() {
		return nouns.keySet();
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {

		return nouns.containsKey(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		return sap.length(nouns.get(nounA), nouns.get(nounB));

	}

	public String sap(String nounA, String nounB) {
		// map nouns to vertex num
		return sets.get(sap.ancestor(nouns.get(nounA), nouns.get(nounB)));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of
	// nounA and nounB
	// in a shortest ancestral path (defined below)

	// for unit testing of this class
	public static void main(String[] args) {

		WordNet wn = new WordNet(args[0], args[1]);
		assert (wn.sap("'hood", "1780s").compareTo("entity") == 0);
		assert (wn.distance("1530s", "1530s") == 0);
		assert (wn.distance("collar_blight", "top") == 11);

		try {
			WordNet wn1 = new WordNet("syntest.txt", "2rootsnym.txt");
			assert (wn1.distance("1840s", "'hood") == -1);
			wn1.sap("1840s", "'hood");

			// wn1.distance("1830s", "'hood");
			// wn1.sap("1830s", "'hood");
			assert false : "A graph with 2 roots should throw exception";

		} catch (java.lang.IllegalArgumentException e) {
			System.out.println("2 Roots detected: " + e);
		}

		try {
			WordNet wn2 = new WordNet("syntest.txt", "cycnym.txt");
			assert false : "A cyclic graph should throw exception";

		} catch (java.lang.IllegalArgumentException e) {
			System.out.println("Cycle detected: " + e);
		}

	}
}