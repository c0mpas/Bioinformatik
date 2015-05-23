import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
	
	// return a sorted list of all edges
	public ArrayList<Edge> getEdges() {
		ArrayList<Edge> list = new ArrayList<Edge>();
		for (Node n : this.nodes) {
			for (Edge e : n.getEdges()) list.add(e);
		}
		// sort list
		Collections.sort(list, new Comparator<Edge>() {
		        @Override
		        public int compare(Edge a, Edge b) {
		            if (a.getWeight() > b.getWeight()) return -1;
		            else if (a.getWeight() < b.getWeight()) return 1;
		            else return 0;
		        }
		    });
		System.out.println(list);
		return list;
	}
}
