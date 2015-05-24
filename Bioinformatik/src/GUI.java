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

import net.miginfocom.swing.MigLayout;

import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;

public class GUI {

	private JFrame frame;
	private ImageIcon icon_logo;
	private ImageIcon icon_background;
	private JScrollPane scrollPane;
	private JTextArea txtrLog;
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
	
	private org.graphstream.graph.Graph graph;
	private Viewer viewer;
	private static final String graphStyle = "node { size: 10px, 15px; shape: rounded-box; fill-mode: none; stroke-mode: none; size-mode: fit; text-style: bold; text-background-mode: rounded-box; text-background-color: #EEEE; text-padding: 5px, 5px; } edge { text-style: bold; text-background-mode: rounded-box; text-background-color: #FFFFFF; text-padding: 5px, 5px; arrow-shape: arrow; arrow-size: 12px, 6px; }";
	private Graph dnaGraph;
	private Assembler assembler;
	private JLabel lblImageArea;
	

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
		lblImageArea = new JLabel();
		icon_background = new ImageIcon(getClass().getResource("/graphics/dna_background.png"));
		lblImageArea.setIcon(icon_background);
		//lblImageArea.setIconTextGap(-300);
		frame.getContentPane().add(lblImageArea, "cell 2 3 3 1,alignx center,aligny center");
		
		// run assembler button
		btnRunAssembler = new JButton("run assembler");
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
		btnClearLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearLog();
			}
		});
		frame.getContentPane().add(btnClearLog, "cell 2 5,growx,aligny center");
		
		btnSortEdges = new JButton("sort edges");
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
		
		btnMergeStep = new JButton("merge step");
		btnMergeStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// merge nodes from first (heaviest) edge
				if (dnaGraph==null) {
					log("no graph available");
					return;
				}
				ArrayList<Edge> sortedEdges = dnaGraph.getEdges();
				if (sortedEdges.isEmpty()) {
					log("no edges in graph");
				} else {
					Edge e = sortedEdges.get(0);
					log("merge nodes " + dnaGraph.getIndex(e.getTo()) + " and " + dnaGraph.getIndex(e.getFrom()) + ": " + e.toString());
					dnaGraph.merge(e);
					refreshInfoBox();
				}
			}
		});
		frame.getContentPane().add(btnMergeStep, "cell 3 4,growx,aligny center");
		
		// button to show graph
		btnShowGraph = new JButton("show graph");
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
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooseFile();
		}});
		frame.getContentPane().add(btnChoose, "cell 2 7,growx,aligny center");
		
		// load button
		btnLoad = new JButton("load file");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFile();
				refreshInfoBox();
			}
		});
		frame.getContentPane().add(btnLoad, "cell 4 7,growx,aligny center");

		// file path text field
		txtFilepath = new JTextField();
		txtFilepath.setText("C:\\Users\\Sebastian\\frag.dat");
		txtFilepath.setColumns(1);
		frame.getContentPane().add(txtFilepath, "cell 2 8 3 1,growx");
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
	
	public void log(String text) {
		if (txtrLog==null || text.isEmpty()) return;
		// java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
		// timestamp.setNanos(0);
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
}
