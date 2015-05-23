import java.util.ArrayList;

public class Graph {

	private ArrayList<Node> nodes;
	
	
	public Graph() {
		this.nodes = new ArrayList<Node>();
	}
	
	public ArrayList<Node> getNodes() {
		return this.nodes;
	}
	
	public Graph(ArrayList<Sequence> sequences) {
		this();
		for(Sequence sequence : sequences) {
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
		for (Node existingNode: nodes) {
			if (!existingNode.equals(n)) {
				if (n.getSequence().overlap(existingNode.getSequence()) > 0) n.addEdge(new Edge(n, existingNode));
				if (existingNode.getSequence().overlap(n.getSequence()) > 0) existingNode.addEdge(new Edge(existingNode, n));
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
