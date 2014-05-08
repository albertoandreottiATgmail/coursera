import java.awt.Color;
import java.awt.List;

public class SeamCarver {

	private Picture picture;

	private abstract class ImageGraph {
		protected double[] imageb;

		public ImageGraph(double[] imageb) {
			this.imageb = imageb;
		}

		public int V() {
			return picture.height() * picture.width();
		}

		public abstract Iterable<Integer> adj(int v);

		public double energy(int w) {
			return imageb[w];
		}
	}

	private class VImageGraph extends ImageGraph {

		public VImageGraph(double[] br) {
			super(br);
		}

		public Iterable<Integer> adj(int v) {

			Bag<Integer> bag = new Bag<Integer>();
			if (v == V() + 1)
				return bag;

			if (v == 0) {
				for (int i = 1; i <= picture.width(); i++)
					bag.add(i);
				return bag;
			}

			// last row
			if (v > picture.width() * (picture.height() - 1)) {
				bag.add(V() + 1);
				return bag;

			}

			bag.add(v + picture.width());

			if (v % picture.width() != 0)
				bag.add(v + picture.width() + 1);
			if (v % picture.width() != 1)
				bag.add(v + picture.width() - 1);

			return bag;

		}
	}

	private class HImageGraph extends ImageGraph {

		public HImageGraph(double[] imageb) {
			super(imageb);

		}

		public Iterable<Integer> adj(int v) {
			Bag<Integer> bag = new Bag<Integer>();
			bag.add(v - picture.width() + 1);
			bag.add(v + picture.width() + 1);
			bag.add(v + 1);
			return bag;

		}

	}

	private class WeightedDigraphSP {

		private double[] distTo;
		private IndexMinPQ<Double> pq;
		private ImageGraph ig;
		private int current = Integer.MAX_VALUE;
		private int[] pathTo;

		public WeightedDigraphSP(ImageGraph ig) {
			this.ig = ig;

			distTo = new double[ig.V() + 2];
			pathTo = new int[ig.V() + 2];
			pq = new IndexMinPQ<Double>(ig.V() + 2);
			for (int i = 0; i < ig.V() + 2; i++)
				distTo[i] = Double.MAX_VALUE;

			distTo[0] = 0;
			pq.insert(0, 0.0);
			while (!pq.isEmpty()) {
				int v = pq.delMin();
				for (int w : ig.adj(v))
					relax(v, w);
			}
		}

		private void relax(int v, int w) {
			if (distTo[w] > distTo[v] + ig.energy(w)) {
				distTo[w] = distTo[v] + ig.energy(w);
				pathTo[w] = v;
				if (pq.contains(w))
					pq.decreaseKey(w, distTo[w]);
				else
					pq.insert(w, distTo[w]);
			}
		}

		public double distTo(int t) {
			return distTo[t];
		}

		public int pathTo(int prev) {
			return pathTo[prev];
		}
	}

	public SeamCarver(Picture picture) {
		this.picture = picture;
	}

	// current picture
	public Picture picture() {
		return picture;
	}

	// width of current picture
	public int width() {
		return picture.width();

	}

	// height of current picture
	public int height() {
		return picture.height();

	}

	private int squared(int number) {
		return number * number;

	}

	private int squaredSum(Color color1, Color color2) {
		int rdiff = color1.getRed() - color2.getRed();
		int gdiff = color1.getGreen() - color2.getGreen();
		int bdiff = color1.getBlue() - color2.getBlue();
		return squared(rdiff) + squared(gdiff) + squared(bdiff);
	}

	// handle borders
	private Color getColor(int x, int y) {
		if (x < 0 || x > picture.width() - 1 || y < 0
				|| y > picture.height() - 1)
			return new Color(255, 255, 255);

		return picture.get(x, y);
	}

	// energy of pixel at column x and row y in current picture
	public double energy(int x, int y) {

		int DeltaX = squaredSum(getColor(x - 1, y), getColor(x + 1, y));
		int DeltaY = squaredSum(getColor(x, y - 1), getColor(x, y + 1));
		return DeltaX + DeltaY;
	}

	// sequence of indices for horizontal seam in current picture
	public int[] findHorizontalSeam() {
		return null;
	}

	// sequence of indices for vertical seam in current picture
	public int[] findVerticalSeam() {

		WeightedDigraphSP wdsp;
		int h = picture.height();
		int w = picture.width();
		int[] seam = new int[h];
		int prev = h * w + 1;

		double[] br = new double[w * h + 2];
		br[w * h + 1] = 0.0;
		br[0] = 0.0;

		// Compute energies
		for (int x = 0; x < h; x++)
			for (int y = 0; x < w; x++)
				br[y + x + 1] = energy(x, y);

		wdsp = new WeightedDigraphSP(new VImageGraph(br));

		// Build the seam
		for (int i = h - 1; i >= 0; i--) {
			seam[i] = wdsp.pathTo(prev) - 1;
			prev = wdsp.pathTo(prev);
		}

		return seam;
	}

	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] a) {

	}

	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] a) {

		Picture pic = new Picture(picture.width() - 1, picture.height());
		for (int j = 0; j < picture.height(); j++)
			for (int i = 0, z = 0; i < pic.width(); i++, z++)
				if (a[j] != z)
					pic.set(i,j, picture.get(z,j));
				else
					i--;

		picture = pic;

	}

	public static void main(String[] args) {
		Picture sample1 = new Picture(40, 40);
		System.out.println(sample1.get(4, 4));
		Color color1 = new Color(10, 10, 10);
		Color color2 = new Color(12, 12, 12);

		// set and neighbors
		sample1.set(20, 20, color1);
		// at both sides
		sample1.set(19, 20, color1);
		sample1.set(21, 20, color2);
		// up and down
		sample1.set(20, 19, color1);
		sample1.set(20, 21, color2);

		// check the right energy
		SeamCarver sc = new SeamCarver(sample1);
		assert sc.energy(20, 20) == 12 * 2 : "Wrong energy";

		// check the right energy in a black surrounded vertex
		assert sc.energy(30, 30) == 0 : "Wrong energy";

		// check the right energy in vertices in the edges
		assert sc.energy(0, 30) == 255 * 255 * 3 : "Wrong energy";

		// check the right energy in vertices in the edges
		assert sc.energy(39, 30) == 255 * 255 * 3 : "Wrong energy";

		// check the right energy in vertices in the edges
		assert sc.energy(0, 0) == 255 * 255 * 6 : "Wrong energy";

		// check the right energy in vertices in the edges
		assert sc.energy(0, 39) == 255 * 255 * 6 : "Wrong energy";

		// check the right energy in vertices in the edges
		assert sc.energy(39, 39) == 255 * 255 * 6 : "Wrong energy";

		Picture sample2 = new Picture(4, 3);
		for (int x = 0; x < sample2.width(); x++)
			for (int y = 0; y < sample2.height(); y++)
				sample2.set(x, y, color2);

		sample2.set(0, 2, color1);
		sample2.set(1, 1, color1);
		sample2.set(1, 1, color1);
		sc = new SeamCarver(sample2);

		sc.removeVerticalSeam(sc.findVerticalSeam());
		
		
		Picture sample3 = new Picture("sample.png");
		sc = new SeamCarver(sample3);
		for (int i = 0; i < 180; i++)
			sc.removeVerticalSeam(sc.findVerticalSeam());


		sc.picture.show();
	}
}