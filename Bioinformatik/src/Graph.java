import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

public class Graph {

	private ArrayList<Node> nodes;
	private ArrayList<Edge> hamiltonPath;
	private int hamcount;
	
	
	public Graph() {
		this.nodes = new ArrayList<Node>();
		this.hamiltonPath = null;
		this.hamcount = -1;
	}
	
	public Graph(ArrayList<Sequence> sequences) {
		this();
		for(Sequence sequence : sequences) addNode(new Node(sequence));
	}

	public ArrayList<Node> getNodes() {
		return this.nodes;
	}

	public int getHamcount() {
		return hamcount;
	}

	// return hamilton path
	// checks all possible permutations
	public ArrayList<Edge> hamiltonPathSlow() {
		if (this.nodes==null || this.nodes.isEmpty()) throw new RuntimeException("graph not initialized");
		ArrayList<Edge> currentHamilton = null;
		int hamiltonWeight = 0;
		
		// counter for statistics
		int pathcount = 0;
		int hamiltoncount = 0;
		
		// Create the initial vector of integers
		ICombinatoricsVector<Integer> originalVector = Factory.createVector(array(this.nodes.size()));
		// Create the permutation generator by calling the appropriate method in the Factory class
		Generator<Integer> gen = Factory.createPermutationGenerator(originalVector);
		
		// helpers
		Boolean exists = null;
		ArrayList<Node> nodepath = null;
		ArrayList<Edge> edgepath = null;
		
		// Iterate over permutations
		for (ICombinatoricsVector<Integer> perm : gen) {
			Permutation p = new Permutation();
			for (Integer i : perm.getVector()) p.add(i);
			pathcount++;
			
			// create path for permutation
			nodepath = new ArrayList<Node>();
			edgepath = new ArrayList<Edge>();
			exists = true;
			for (Integer i : p.get()) nodepath.add(this.nodes.get(i));
			// check if path exists, iterate over all nodes in path
			for (int i = 0; i < nodepath.size()-1; i++) {
				Edge c = getConnection(nodepath.get(i), nodepath.get(i+1));
				if (c!=null) {
					edgepath.add(c);
				} else {
					exists = false;
					break;
				}
			}
			// check if path has bigger weight
			if (exists) {
				hamiltoncount++;
				int weight = getWeight(edgepath);
				if (weight>hamiltonWeight) {
					// override current hamilton
					currentHamilton = edgepath;
					hamiltonWeight = weight;
				}
			}
		}
		return currentHamilton;
	}

	// return the weight of a path (edge list)
	public static int getWeight(ArrayList<Edge> list) {
		if (list==null) throw new IllegalArgumentException("no list");
		int count = 0;
		for (Edge e : list) count += e.getWeight();
		return count;
	}
	
	// add new node to graph
	public void addNode(Node n) {
		if (n==null) throw new IllegalArgumentException("node is null");
		this.nodes.add(n);
		connect(n);
	}
	
	// integrate new node in graph by creating edges
	private void connect(Node n) {
		if (n==null) throw new IllegalArgumentException("node is null");
		for (Node existingNode: nodes) {
			if (!existingNode.equals(n)) {
				if (n.getSequence().overlap(existingNode.getSequence()) > 0) n.addEdge(new Edge(n, existingNode));
				if (existingNode.getSequence().overlap(n.getSequence()) > 0) existingNode.addEdge(new Edge(existingNode, n));
			}
		}
	}
	
	// merge two nodes (connected with given edge)
	public void merge(Edge e) {
		if (e==null) throw new IllegalArgumentException("edge is null");
		GUI.log("merging: " + e);
		Sequence s = Sequence.merge(e.getFrom().getSequence(), e.getTo().getSequence());
		GUI.log("merged sequence: " + s);
		
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
		if (node==null) throw new IllegalArgumentException("node is null");
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
		sb.append("graph: [\n");
		for (Edge e : getEdges()) sb.append(e.toString()).append("\n");
		sb.append("]");
		return sb.toString();
	}
	
	// returns an array [1...n]
	private static Integer[] array(int n) {
		Integer[] p = new Integer[n];
		for (int i = 0; i < n; i++) p[i] = i;
		return p;
	}
	
	// returns edge for two nodes (if exists)
	public static Edge getConnection(Node from, Node to) {
		for (Edge e : from.getEdges()) {
			if (e.getTo().getSequence().equals(to.getSequence())) return e;
		}
		return null;
	}
	
	// returns an adjacency matrix representing the graph
	public int[][] getAdjacencyMatrix() {
		if (this.nodes==null || this.nodes.isEmpty()) throw new RuntimeException("no graph available");
		int size = this.nodes.size();
		int[][] matrix = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Edge e = getConnection(this.nodes.get(i), this.nodes.get(j));
				if (e!=null) {
					matrix[i][j] = 1;
				} else {
					matrix[i][j] = 0;
				}
			}
		}
		return matrix;
	}
	
	public String printPath(ArrayList<Edge> list) {
		StringBuilder sb = new StringBuilder();
		for (Edge e : list) {
			sb.append("[" + nodes.indexOf(e.getFrom()) + "-->" + nodes.indexOf(e.getTo()) + "]");
			sb.append(e).append("\n");
		}
		return sb.toString();
	}
	
	public String printNodes() {
		StringBuilder sb = new StringBuilder("\nnodes:\n");
		for (Node n : this.nodes) {
			sb.append(String.valueOf(this.nodes.indexOf(n)) + ": " + n.getSequence() + "\n");
		}
		return sb.toString();
	}
	
	public static int getBiggest(ArrayList<Edge> list) {
		int index = -1;
		int weight = -1;
		int currentWeight = -1;
		for (int i = 0; i < list.size(); i++) {
			currentWeight = list.get(i).getWeight();
			if (currentWeight > weight) {
				index = i;
				weight = currentWeight;
			}
		}
		return index;
	}
	
	public ArrayList<Edge> hamiltonPath() {
		if (this.nodes==null || this.nodes.isEmpty()) throw new RuntimeException("graph not initialized");
		this.hamiltonPath = new ArrayList<Edge>();
		this.hamcount = 0;
		permute(new ArrayList<Edge>(), new ArrayList<Node>(), this.nodes);
		return this.hamiltonPath;
	}
	
	private void permute(ArrayList<Edge> ham, ArrayList<Node> head, ArrayList<Node> tail) {
		if (ham==null || head==null || tail==null) throw new IllegalArgumentException("list is null");
		
		int headsize = head.size();
		if (headsize > 1) {
			// check if there is an edge between last two nodes
			Edge e = Graph.getConnection(head.get(headsize-2), head.get(headsize-1));
			if (e!=null) {
				// add new edge to current hamilton path
				ham.add(e);
			} else {
				// no connection for last node, end here
				return;
			}
		}
		
		// if tail is empty, path is complete
		if (tail.isEmpty()) {
			this.hamcount++;
			// check if this hamilton path has bigger weight than former path
			if (Graph.getWeight(ham) > Graph.getWeight(this.hamiltonPath)) {
				// overwrite current hamilton path
				this.hamiltonPath = ham;
			}
		}
		
		// proceed
		for (Node n : tail) {
			ArrayList<Node> newhead = (ArrayList<Node>)head.clone();
			ArrayList<Node> newtail = (ArrayList<Node>)tail.clone();
			ArrayList<Edge> newham = (ArrayList<Edge>)ham.clone();
			newhead.add(n);
			newtail.remove(newtail.indexOf(n));
			permute(newham, newhead, newtail);
		}
	}
}
