import java.util.ArrayList;

public class Graph {

	private ArrayList<Node> nodes;
	
	
	public Graph() {
		this.nodes = new ArrayList<Node>();
	}
	
	public Graph(ArrayList<Sequence> sequences){
		this();
		for(Sequence sequence : sequences){
			addNode(new Node(sequence));
		}
	}
	
	// add new node to graph
	public void addNode(Node n) {
		if (n==null) throw new RuntimeException("node is null");
		this.nodes.add(n);
		connect(n);
	}
	
	// integrate new node in graph by creating edges
	private void connect(Node n) {
		if (n==null) throw new RuntimeException("node is null");
		
		for(Node existingNode: nodes){
			Edge edge;
			int overlap;
			overlap= n.getSequence().overlap(existingNode.getSequence()); 
			if(overlap > 0){
				edge = new Edge(n, existingNode);
				n.addEdge(edge);
			}
			overlap = existingNode.getSequence().overlap(n.getSequence()); 
			if(overlap > 0){
				edge = new Edge(existingNode, n);
				existingNode.addEdge(edge);
			}
		}
		
	}
	
	public void merge(Node a, Node b) {
		if (a==null || b==null) throw new RuntimeException("node is null");
		// merge node a and b to new node n
		// connect(n);
		// todo
	}
}
