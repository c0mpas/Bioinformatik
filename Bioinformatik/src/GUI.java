import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;

public class GUI {

	private static JTextArea txtrLog;
	
	private static final String homepath = "C:\\Users\\Sebastian\\frag.dat";
	private static final int[][] m = {	{0, 1, 0, 1, 1, 0, 0, 0},
										{1, 0, 1, 0, 0, 1, 0, 0},
										{0, 1, 0, 1, 0, 0, 1, 0},
										{1, 0, 1, 0, 0, 0, 0, 1},
										{1, 0, 0, 0, 0, 1, 0, 1},
										{0, 1, 0, 0, 1, 0, 1, 0},
										{0, 0, 1, 0, 0, 1, 0, 1},
										{0, 0, 0, 1, 1, 0, 1, 0} };
	
	private JFrame frame;
	private ImageIcon icon_logo;
	private JScrollPane scrollPane;
	private JLabel labelInfobox;
	private JTextField txtFilepath;
	private JButton btnChoose;
	private JButton btnLoad;
	private JFileChooser chooser;
	private JButton btnClearLog;
	private JButton btnShowGraph;
	private JButton btnSortEdges;
	private JLabel labelLog;
	private JButton btnMergeStep;
	private JButton btnRunAssembler;
	private JLabel lblImageArea;
	private JButton btnTest;
	
	private org.graphstream.graph.Graph graph;
	private Viewer viewer;
	private static final String graphStyle = "node { size: 10px, 15px; shape: rounded-box; fill-mode: none; stroke-mode: none; size-mode: fit; text-style: bold; text-background-mode: rounded-box; text-background-color: #EEEE; text-padding: 5px, 5px; } edge { text-style: bold; text-background-mode: rounded-box; text-background-color: #FFFFFF; text-padding: 5px, 5px; arrow-shape: arrow; arrow-size: 12px, 6px; }";
	private Graph dnaGraph;
	private Assembler assembler;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// main frame
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		icon_logo = new ImageIcon(getClass().getResource("/graphics/dna_icon.png"));
		frame.setIconImage(icon_logo.getImage());
		frame.setTitle("DNA Assembler");
		frame.getContentPane().setLayout(new MigLayout("", "[50px:n][50px:n,grow][50px:n][50px:n][50px:n]", "[20px:n][][50px:n][grow][30px:n][30px:n][10px:n][30px:n][30px:n]"));
		
		// text pane
		labelLog = new JLabel();
		labelLog.setHorizontalAlignment(JLabel.CENTER);
		labelLog.setVerticalAlignment(JLabel.CENTER);
		labelLog.setText("Log");
		frame.getContentPane().add(labelLog, "cell 0 0 2 1,grow");
		
		// scroll layout
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 0 1 2 8,grow");
		
		// text area in scroll layout
		txtrLog = new JTextArea();
		txtrLog.setText("\nWelcome!");
		txtrLog.setEditable(false);
		scrollPane.setViewportView(txtrLog);

		// info text box
		labelInfobox = new JLabel();
		labelInfobox.setHorizontalAlignment(JLabel.CENTER);
		labelInfobox.setVerticalAlignment(JLabel.TOP);
		labelInfobox.setText("infobox");
		frame.getContentPane().add(labelInfobox, "cell 2 0 3 3,grow");
		
		// image area
		lblImageArea = new JLabel(new ImageIcon(getClass().getResource("/graphics/dna_background.png")));
		//lblImageArea.setIconTextGap(-300);
		frame.getContentPane().add(lblImageArea, "cell 2 3 3 1,alignx center,aligny center");
		
		// run assembler button
		btnRunAssembler = new JButton("run assembler");
		btnRunAssembler.setIcon(new ImageIcon(getClass().getResource("/graphics/icon_assemble.png")));
		btnRunAssembler.setHorizontalAlignment(SwingConstants.LEFT);
		btnRunAssembler.setIconTextGap(10);
		btnRunAssembler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (dnaGraph==null) {
					log("no graph available");
				} else {
					assembler = new Assembler(dnaGraph);
					ArrayList<Sequence> list = assembler.run();
					refreshInfoBox();
					if (list.size() > 1) {
						log("DNA Sequence could not be assembled completely\nResults:");
						for (Sequence s : list) log(s.toString());
					} else if (list.size() == 1) {
						log("DNA Sequence assembled\nResult:");
						log(list.get(0).toString());
					} else {
						log("Assembler did not provide any result. Something went wrong.");
					}
				}
			}
		});
		frame.getContentPane().add(btnRunAssembler, "cell 2 4,growx,aligny center");
		
		// clear log button
		btnClearLog = new JButton("clear log");
		btnClearLog.setIcon(new ImageIcon(getClass().getResource("/graphics/icon_clear.png")));
		btnClearLog.setHorizontalAlignment(SwingConstants.LEFT);
		btnClearLog.setIconTextGap(20);
		btnClearLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearLog();
			}
		});
		frame.getContentPane().add(btnClearLog, "cell 2 5,growx,aligny center");
		
		// sort button
		btnSortEdges = new JButton("sort edges");
		btnSortEdges.setIcon(new ImageIcon(getClass().getResource("/graphics/icon_sort.png")));
		btnSortEdges.setHorizontalAlignment(SwingConstants.LEFT);
		btnSortEdges.setIconTextGap(10);
		btnSortEdges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (dnaGraph == null) {
					log("missing graph");
				} else {
					for (Edge e : dnaGraph.getEdges()) log(e.toString());
				}
			}
		});
		frame.getContentPane().add(btnSortEdges, "cell 4 4,growx,aligny center");
		
		// merge button
		btnMergeStep = new JButton("merge step");
		btnMergeStep.setIcon(new ImageIcon(getClass().getResource("/graphics/icon_merge.png")));
		btnMergeStep.setHorizontalAlignment(SwingConstants.LEFT);
		btnMergeStep.setIconTextGap(10);
		btnMergeStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// merge nodes from first (heaviest) edge
				if (dnaGraph==null) {
					log("no graph available");
					return;
				}
				ArrayList<Edge> sortedEdges = dnaGraph.hamiltonPath();
				if (sortedEdges!=null) {
					if (sortedEdges.isEmpty()) {
						log("no edges in graph");
					} else {
						Edge e = sortedEdges.get(0);
						log("merge nodes " + dnaGraph.getIndex(e.getTo()) + " and " + dnaGraph.getIndex(e.getFrom()) + ": " + e.toString());
						dnaGraph.merge(e);
						refreshInfoBox();
					}
				} else {
					ArrayList<Node> list = dnaGraph.getNodes();
					if (list.size() > 1) {
						log("\nDNA Sequence could not be assembled completely\nResults:");
						for (Node n : list) log(n.toString());
					} else if (list.size() == 1) {
						log("\nDNA Sequence assembled\nResult:");
						log(list.get(0).toString());
					}
				}
			}
		});
		frame.getContentPane().add(btnMergeStep, "cell 3 4,growx,aligny center");
		
		// button to show graph
		btnShowGraph = new JButton("show graph");
		btnShowGraph.setIcon(new ImageIcon(getClass().getResource("/graphics/icon_graph.png")));
		btnShowGraph.setHorizontalAlignment(SwingConstants.LEFT);
		btnShowGraph.setIconTextGap(10);
		btnShowGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					refreshGraph();
					viewer = graph.display();
			        viewer.setCloseFramePolicy(CloseFramePolicy.CLOSE_VIEWER);
				} catch (Exception e) {
					log(e.toString());
				}
			}
		});
		frame.getContentPane().add(btnShowGraph, "cell 4 5,growx,aligny center");
				
		// button to choose file
		btnChoose = new JButton("select file");
		btnChoose.setIcon(new ImageIcon(getClass().getResource("/graphics/icon_open.png")));
		btnChoose.setHorizontalAlignment(SwingConstants.LEFT);
		btnChoose.setIconTextGap(20);
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooseFile();
		}});
		frame.getContentPane().add(btnChoose, "cell 2 7,growx,aligny center");
		
		// load button
		btnLoad = new JButton("load file");
		btnLoad.setIcon(new ImageIcon(getClass().getResource("/graphics/icon_load.png")));
		btnLoad.setHorizontalAlignment(SwingConstants.LEFT);
		btnLoad.setIconTextGap(20);
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFile();
				refreshInfoBox();
			}
		});
		frame.getContentPane().add(btnLoad, "cell 4 7,growx,aligny center");

		// file path text field
		txtFilepath = new JTextField();
		txtFilepath.setText("");
		txtFilepath.setColumns(1);
		frame.getContentPane().add(txtFilepath, "cell 2 8 3 1,growx");
		
		// test button
		btnTest = new JButton("test");
		btnTest.setIcon(new ImageIcon(getClass().getResource("/graphics/icon_question.png")));
		btnTest.setHorizontalAlignment(SwingConstants.LEFT);
		btnTest.setIconTextGap(20);
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				run_test();
			}
		});
		frame.getContentPane().add(btnTest, "cell 3 5,growx,aligny center");
	}
	
	private void chooseFile() {
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(false);
		chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        	txtFilepath.setText(chooser.getSelectedFile().getAbsoluteFile().getPath());
        }
        log("file chosen: " + txtFilepath.getText());
	}
	
	private void loadFile() {
		String path = txtFilepath.getText();
		if (path == null) {
			log("invalid file path");
			return;
		}
		log("loading " + path);
		ArrayList<Sequence> list = null;
		try {
			list = Parser.parse(path);
		} catch (Exception e) {
			log(e.toString());
		}
		if (list == null) {
			log("error loading file");
		} else {
			log("load successful");
			this.dnaGraph = createGraph(list);
			refreshGraph();
		}
	}
	
	public static void log(String text) {
		if (txtrLog==null || text.isEmpty()) return;
		txtrLog.append("\n" + text);
	}
	
	private void clearLog() {
		if (txtrLog == null) return;
		txtrLog.setText(null);
	}
	
	private Graph createGraph(ArrayList<Sequence> list) {
		return new Graph(list);
	}
	
	// refresh gui graph 
	private void refreshGraph() {
		graph = new MultiGraph("graph");
		graph.addAttribute("ui.stylesheet", graphStyle);
		
		String leftseq;
		String rightseq;
		
		for (Node n : dnaGraph.getNodes()) {
			org.graphstream.graph.Node currentNode = graph.addNode(n.getSequence().getSequence());
			currentNode.addAttribute("ui.label", currentNode.getId());
		}
		for (Node n : dnaGraph.getNodes()) {
			for (Edge e : n.getEdges()) {
				leftseq = e.getFrom().getSequence().getSequence();
				rightseq = e.getTo().getSequence().getSequence();
				org.graphstream.graph.Edge currentEdge = graph.addEdge(leftseq+rightseq, leftseq, rightseq, true);
				currentEdge.addAttribute("ui.label", e.getWeight());
			}
		}
	}
	
	// refresh info box and show current info about graph
	private void refreshInfoBox() {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><body>infobox<br><br>");
		sb.append("nodes: ").append(dnaGraph.getNodes().size());
		sb.append("<br>edges: ").append(dnaGraph.getEdges().size());
		sb.append("</body></html>");
		labelInfobox.setText(sb.toString());
	}
	
	private String printMatrix(int[][] matrix) {
		int size = matrix.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			StringBuilder row = new StringBuilder();
			for (int j = 0; j < size; j++) {
				row.append(matrix[i][j] + " ");
			}
			sb.append(row.toString()).append("\n");
		}
		return sb.toString();
	}
	
	// run custom test
	private void run_test() {
		if (txtFilepath!=null) txtFilepath.setText(homepath);
		loadFile();
		refreshInfoBox();
		if (dnaGraph!=null) {
			int[][] matrix = dnaGraph.getAdjacencyMatrix();
			log(printMatrix(m));
			HamiltonianCycle hc = new HamiltonianCycle();
			try {
				int[] path = hc.get(m);
				String out = new String();
				for (int i = 0; i < path.length; i++) {
					out += String.valueOf(path[i]);
				}
				log(out);
			} catch (Exception e) {
				log(e.toString());
			}
			
			ArrayList<Edge> path = dnaGraph.hamiltonPath();
			if (path==null) {
				log("\n\nhamiltonPath() returned null");
			} else {
				log("\nhamilton path with biggest weight for current graph:\n" + dnaGraph.printPath(path) + "\nweight: " + Graph.getWeight(path));
			}
		}
	}
	
}
