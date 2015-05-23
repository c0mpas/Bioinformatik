import java.util.ArrayList;


public class Node {

	private ArrayList<Edge> edges;
	private Sequence seq;
	private Boolean marked;
	
	// create new node with sequence
	public Node(Sequence s) {
		this.edges = new ArrayList<Edge>();
		this.marked = false;
		setSequence(s);
	}
	
	// get sequence
	public Sequence getSequence() {
		return this.seq;
	}

	// set sequence
	public void setSequence(Sequence s) {
		if (s == null) throw new RuntimeException("sequence is null");
		this.seq = s;
	}

	// get all outgoing edges
	public ArrayList<Edge> getEdges() {
		return this.edges;
	}
	
	// add outgoing edge
	public void addEdge(Edge edge) {
		if (edge == null) throw new RuntimeException("edge is null");
		edges.add(edge);
	}
	
	public void mark() {
		this.marked = true;
	}
	
	public void unmark() {
		this.marked = false;
	}
	
	public Boolean isMarked() {
		return this.marked;
	}
}
