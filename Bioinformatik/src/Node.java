import java.util.ArrayList;


public class Node {

	private ArrayList<Edge> edges;
	private Sequence seq;
	
	public Node(Sequence s) {
		this.edges = new ArrayList<Edge>();
		setSequence(s);
	}
	
	public Sequence getSequence() {
		return this.seq;
	}

	public void setSequence(Sequence s) {
		if (s == null) throw new RuntimeException("sequence is null");
		this.seq = s;
	}

	public ArrayList<Edge> getEdges() {
		return this.edges;
	}
	
	public void addEdge(Edge edge) {
		if (edge == null) throw new RuntimeException("edge is null");
		edges.add(edge);
	}
}
