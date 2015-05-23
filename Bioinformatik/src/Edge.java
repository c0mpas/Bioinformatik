
public class Edge {
	
	private Node from;
	private Node to;
	private int weight;
	
	public Edge() {
		this.from = null;
		this.to = null;
		this.weight = 0;
	}
	
	public Edge(Node from, Node to) {
		if (from == null || to == null) throw new RuntimeException("node is null");
		this.from = from;
		this.to = to;
		computeWeight();
	}
	
	private void computeWeight() {
		this.weight = this.from.getSequence().overlap(this.to.getSequence());
	}
	
	public Node getFrom() {
		return this.from;
	}

	public Node getTo() {
		return this.to;
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(this.from.toString());
		sb.append("---").append(this.weight);
		sb.append("--->").append(this.to.toString());
		sb.append("]");
		return sb.toString();
	}
}
