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
	private JLabel labelInfobox;
	private JTextField txtFilepath;
	private JButton btnChoose;
	private JButton btnLoad;
	private JFileChooser chooser;
	private JButton btnClearLog;
	private JButton btnShowGraph;
	private JButton btnSortEdges;
	private JLabel labelLog;
	
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
		frame.setBounds(100, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		icon = new ImageIcon(getClass().getResource("/graphics/dna.png"));
		frame.setIconImage(icon.getImage());
		frame.setTitle("DNA Assembler");
		frame.getContentPane().setLayout(new MigLayout("", "[50px:n,grow][50px:n,grow][50px:n][grow][50px:n]", "[20px:n][grow][50px:n][grow][][][10px:n][30px:n][30px:n]"));
		
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
		
		// clear log button
		btnClearLog = new JButton("clear log");
		btnClearLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearLog();
			}
		});
		
		btnSortEdges = new JButton("sort edges");
		btnSortEdges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (dnaGraph == null) {
					log("missing graph");
				} else {
					log(dnaGraph.getEdges().toString());
				}
			}
		});
		frame.getContentPane().add(btnSortEdges, "cell 4 4,alignx right");
		frame.getContentPane().add(btnClearLog, "cell 2 5");
		
		// button to show graph
		btnShowGraph = new JButton("show graph");
		btnShowGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					showGraph(dnaGraph);
				} catch (Exception e) {
					log(e.toString());
				}
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
	
	private void showGraph(Graph g) {
		if (g == null) throw new RuntimeException("graph is null");
		graph = new MultiGraph("graph");
		graph.addAttribute("ui.stylesheet", graphStyle);
		
		String leftseq;
		String rightseq;
		
		for (Node n : g.getNodes()) {
			org.graphstream.graph.Node currentNode = graph.addNode(n.getSequence().getSequence());
			currentNode.addAttribute("ui.label", currentNode.getId());
		}
		for (Node n : g.getNodes()) {
			for (Edge e : n.getEdges()) {
				leftseq = e.getFrom().getSequence().getSequence();
				rightseq = e.getTo().getSequence().getSequence();
				org.graphstream.graph.Edge currentEdge = graph.addEdge(leftseq+rightseq, leftseq, rightseq, true);
				currentEdge.addAttribute("ui.label", e.getWeight());
			}
		}
		
        viewer = graph.display();
        viewer.setCloseFramePolicy(CloseFramePolicy.CLOSE_VIEWER);
	}
}
