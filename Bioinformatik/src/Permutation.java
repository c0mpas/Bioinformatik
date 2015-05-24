import java.util.ArrayList;


public class Permutation {
	
	private ArrayList<Integer> list;
	
	
	public Permutation() {
		this.list = new ArrayList<Integer>();
	}
	
	public void add(int n) {
		this.list.add(n);
	}
	
	public int size() {
		if (this.list==null || this.list.isEmpty()) return 0;
		return this.list.size();
	}
	
	public ArrayList<Integer> get() {
		if (this.list==null || this.list.isEmpty()) throw new RuntimeException("permutation empty");
		return this.list;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (Integer i : this.list) sb.append(i).append(" ");
		sb.append("]");
		return sb.toString();
	}
	
}
