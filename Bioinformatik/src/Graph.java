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
	
	public void merge(Edge e) {
		if (e==null) throw new RuntimeException("edge is null");
		Sequence s = Sequence.merge(e.getFrom().getSequence(), e.getTo().getSequence());
		// remove nodes
		this.removeNode(e.getTo());
		this.removeNode(e.getFrom());
		// add merged node
		this.addNode(new Node(s));
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
		return list;
	}
	
	// remove a node (and his edges) from the graph
	public void removeNode(Node node) {
		if (node==null) throw new RuntimeException("node is null");
		ArrayList<Edge> currentEdges = null;
		// remove all incoming edges
		for (Node n : this.nodes) {
			currentEdges = new ArrayList<Edge>();
			for (Edge e : n.getEdges()) {
				if (e.getTo().equals(node)) currentEdges.add(e);
			}
			for (Edge e : currentEdges) {
				n.removeEdge(e);
			}
		}
		// remove node and his edges
		nodes.remove(nodes.indexOf(node));
	}
	
	public int getIndex(Node n) {
		return nodes.indexOf(n);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("graph: [");
		for (Edge e : getEdges()) sb.append(e.toString());
		sb.append("]");
		return sb.toString();
	}
}
