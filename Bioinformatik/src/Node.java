
public class Node {

	private Edge edge;
	private int overlap;
	private Sequence sequence;
	
	public Node(){
		
	}
	
	public Node(Edge edge){
		this(new Sequence(), -1);
	}
	
	public Node(Sequence sequence, int overlap){
		this();
	}
	
	public Node(Sequence sequence, int overlap, Edge edge){
		setEdge(edge);
		setSequence(sequence);
		setOverlap(overlap);
	}
	
	public Node(Node startNode, Edge edge, Node endNode){

	}

	public Edge getEdge() {
		return edge;
	}

	public void setEdge(Edge edge) {
		this.edge = edge;
	}

	public int getOverlap() {
		return overlap;
	}

	public void setOverlap(int overlap) {
		this.overlap = overlap;
	}

	public Sequence getSequence() {
		return sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}
	
	
	
}
