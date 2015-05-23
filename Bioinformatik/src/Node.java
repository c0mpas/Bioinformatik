import java.util.ArrayList;


public class Node {

	private ArrayList<Edge> edges;
	private Sequence sequence;
	
	public Node(){
		this(new Sequence(), new ArrayList<Edge>());
	}
	 
	
	public Node(ArrayList<Edge> edges){
		this(new Sequence(), edges);
	}
	
	public Node(Sequence sequence){
		this(sequence, new ArrayList<Edge>() );
	}
	
	public Node(Sequence sequence, ArrayList<Edge> edges){
		setEdges(edges);
		setSequence(sequence);
	}
	

	public Sequence getSequence() {
		return sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}


	public ArrayList<Edge> getEdges() {
		return edges;
	}

	
	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}
	
	public void addEdge(Edge edge){
		edges.add(edge);
	}
	
	
}
