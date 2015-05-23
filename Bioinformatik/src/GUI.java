import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import net.miginfocom.swing.MigLayout;

import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;

public class GUI {

	private JFrame frame;
	private ImageIcon icon;
	private JScrollPane scrollPane;
	private JTextArea txtrLog;
	private JTextPane txtpnInfobox;
	private JTextField txtFilepath;
	private JButton btnChoose;
	private JButton btnLoad;
	private JFileChooser chooser;
	private JButton btnClearLog;
	private JButton btnShowGraph;
	
	private org.graphstream.graph.Graph graph;
	private Viewer viewer;
	private static final String graphStyle = "node { size: 10px, 15px; shape: rounded-box; fill-mode: none; stroke-mode: none; size-mode: fit; text-style: bold; text-background-mode: rounded-box; text-background-color: #EEEE; text-padding: 5px, 5px; } edge { text-style: bold; text-background-mode: rounded-box; text-background-color: #FFFFFF; text-padding: 5px, 5px; arrow-shape: arrow; arrow-size: 12px, 6px; }";
	private Graph dnaGraph;
	

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
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		icon = new ImageIcon(getClass().getResource("/graphics/dna.png"));
		frame.setIconImage(icon.getImage());
		frame.setTitle("DNA Assembler");
		frame.getContentPane().setLayout(new MigLayout("", "[50px:n][50px:n,grow][50px:n][grow][50px:n]", "[50px:n][grow][50px:n][grow][][][10px:n][30px:n][30px:n]"));
		
		// scroll layout
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 0 0 2 9,grow");
		
		// text area in scroll layout
		txtrLog = new JTextArea();
		txtrLog.setText("Welcome!");
		txtrLog.setEditable(false);
		scrollPane.setViewportView(txtrLog);

		// info text box
		txtpnInfobox = new JTextPane();
		txtpnInfobox.setText("infobox");
		txtpnInfobox.setEditable(false);
		frame.getContentPane().add(txtpnInfobox, "cell 2 0 3 3,grow");
		
		// clear log button
		btnClearLog = new JButton("clear log");
		btnClearLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearLog();
			}
		});
		frame.getContentPane().add(btnClearLog, "cell 2 5");
		
		// button to show graph
		btnShowGraph = new JButton("show graph");
		btnShowGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// showDummyGraph();
				showGraph(dnaGraph);
			}
		});
		frame.getContentPane().add(btnShowGraph, "cell 4 5,alignx right");

		// file path text field
		txtFilepath = new JTextField();
		txtFilepath.setText("input file");
		txtFilepath.setColumns(1);
		frame.getContentPane().add(txtFilepath, "cell 2 7 3 1,growx");
		
		// button to choose file
		btnChoose = new JButton("select file");
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooseFile();
		}});
		frame.getContentPane().add(btnChoose, "cell 2 8,alignx left,aligny center");
		
		// load button
		btnLoad = new JButton("load file");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFile();
			}
		});
		frame.getContentPane().add(btnLoad, "cell 4 8,alignx right,aligny center");
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
		}
	}
	
	public void log(String text) {
		if (txtrLog == null) return;
		String log = txtrLog.getText();
		if (log.isEmpty()) {
			txtrLog.setText(text);
		} else {
			txtrLog.setText(log + "\n" + text);
		}
	}
	
	private void clearLog() {
		if (txtrLog == null) return;
		txtrLog.setText(null);
	}
	
	private Graph createGraph(ArrayList<Sequence> list) {
		return new Graph(list);
	}
	
	private void showGraph(Graph g) {
		graph = new MultiGraph("graph");
		graph.addAttribute("ui.stylesheet", graphStyle);
		
		for (Node n : g.getNodes()) {
			graph.addNode(n.getSequence().getSequence());
			for (Edge e : n.getEdges()) {
				graph.addEdge(String.valueOf(e.getWeight()), e.getFrom().getSequence().getSequence(), e.getTo().getSequence().getSequence(), true);
			}
		}
		
        for (org.graphstream.graph.Node n : graph) n.addAttribute("ui.label", n.getId());
        for (org.graphstream.graph.Edge e : graph.getEdgeSet()) e.addAttribute("ui.label", e.getId());
        
        viewer = graph.display();
        viewer.setCloseFramePolicy(CloseFramePolicy.CLOSE_VIEWER);
	}
	
	private void showDummyGraph() {
		graph = new MultiGraph("graph");
		graph.addAttribute("ui.stylesheet", graphStyle);
		
		graph.addNode("Alpha");
        graph.addNode("Beta");
        graph.addNode("Gamma");
        graph.addNode("Delta");
        graph.addEdge("5", "Alpha", "Beta", true);
        graph.addEdge("1", "Alpha", "Delta", true);
        graph.addEdge("4", "Beta", "Alpha", true);
        graph.addEdge("3", "Beta", "Gamma", true);
        graph.addEdge("2", "Gamma", "Alpha", true);
        
        for (org.graphstream.graph.Node n : graph) n.addAttribute("ui.label", n.getId());
        for (org.graphstream.graph.Edge e : graph.getEdgeSet()) e.addAttribute("ui.label", e.getId());
        
        viewer = graph.display();
        viewer.setCloseFramePolicy(CloseFramePolicy.CLOSE_VIEWER);
	}
}
