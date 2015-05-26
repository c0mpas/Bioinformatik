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
		ArrayList<Edge> edges = graph.hamiltonPath();
		int hamcount = 0;
		while (edges!=null && edges.size()>0) {
			// merge edge with biggest weight
			hamcount += graph.getHamcount();
			
			// output
			GUI.log("\nmerge step");
			GUI.log(graph.getHamcount() + " hamilton paths");
			GUI.log("\nhamilton path with biggest weight (" + Graph.getWeight(edges) + ") for current graph:\n" + graph.printPath(edges));
			
			graph.merge(edges.get(Graph.getBiggest(edges)));
			edges = graph.hamiltonPath();
		}
		GUI.log("\n" + hamcount + " hamilton paths in total");
		// save remaining sequences (nodes) in list
		for (Node n : graph.getNodes()) list.add(n.getSequence());
		return list;
	}
}
