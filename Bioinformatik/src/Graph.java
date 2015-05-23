import java.util.ArrayList;

public class Graph {

	private ArrayList<Node> nodes;
	
	
	public Graph() {
		this.nodes = new ArrayList<Node>();
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
		// created edges for new node
		// 1. edges from new node to other nodes
		// 2. edges from other nodes to new node
		// todo
	}
	
	public void merge(Node a, Node b) {
		if (a==null || b==null) throw new RuntimeException("node is null");
		// merge node a and b to new node n
		// connect(n);
		// todo
	}
}
