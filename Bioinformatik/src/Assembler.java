import java.util.ArrayList;


public class Assembler {
	
	private Graph graph;
	
	
	public Assembler(Graph graph) {
		if (graph==null) throw new IllegalArgumentException("graph is null");
		this.graph = graph;
	}
	
	public ArrayList<Sequence> run() {
		ArrayList<Sequence> list = new ArrayList<Sequence>();
		// merge graph step by step until no edges left
		ArrayList<Edge> edges = graph.getEdges();
		while (edges.size() > 0) {
			graph.merge(edges.get(0));
			edges = graph.getEdges();
		}
		// save remaining sequences (nodes) in list
		for (Node n : graph.getNodes()) list.add(n.getSequence());
		return list;
	}
}
