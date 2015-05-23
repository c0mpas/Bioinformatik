import java.util.ArrayList;

public class Graph {

	private ArrayList<Node> nodes;
	
	
	public Graph() {
		this.nodes = new ArrayList<Node>();
	}
	
	public void addNode(Node n) {
		if (n == null) throw new RuntimeException("node is null");
		this.nodes.add(n);
	}
	
}
