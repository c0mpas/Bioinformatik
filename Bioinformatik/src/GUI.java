/**
 * 
 * @author Sebastian Schultheiﬂ und Christoph Geidt
 *
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextPane;

public class GUI {

	private static JTextArea txtrLog;

	private JFrame frame;
	private JScrollPane scrollPaneLog;
	private JScrollPane scrollPaneInput;
	private JScrollPane scrollPaneParameters;
	private JTextArea textAreaInput;
	private JTextArea textAreaParameters;
	private JButton btnChooseInput;
	private JButton btnChooseParameters;
	private JFileChooser chooser;
	private JButton btnClearLog;
	private JButton btnRViterbi;
	private JButton btnViterbi;
	private JLabel labelLog;
	private JButton btnRForward;
	private JButton btnForward;
	private JButton btnRSViterbi;
	private JLabel lblInput;
	private JLabel lblParameters;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		initInput();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// main frame
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Hidden Markov Modell");
		frame.getContentPane().setLayout(new MigLayout("", "[100px:n][50px:n,grow][100px:n:100px][100px:n:100px][100px:n:100px][20px:n:20px][50px:n][20px:n:20px]", "[20px:n][20px:n][50px:n,grow][grow][50px:n][20px:n][20px:n:20px][20px:n][50px:n,grow][grow][50px:n][20px:n][20px:n:20px]"));
		
		// log label
		labelLog = new JLabel();
		labelLog.setHorizontalAlignment(JLabel.CENTER);
		labelLog.setVerticalAlignment(JLabel.CENTER);
		labelLog.setText("Log");
		frame.getContentPane().add(labelLog, "cell 0 0 2 1,grow");
		
		// scroll layout log
		scrollPaneLog = new JScrollPane();
		frame.getContentPane().add(scrollPaneLog, "cell 0 1 2 12,grow");
		// text area in scroll layout
		txtrLog = new JTextArea();
		txtrLog.setText("\nWelcome!");
		txtrLog.setEditable(false);
		scrollPaneLog.setViewportView(txtrLog);
		
		// input label
		lblInput = new JLabel("input");
		lblInput.setHorizontalAlignment(JLabel.CENTER);
		lblInput.setVerticalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblInput, "cell 2 1 3 1,grow");
		
		// scroll layout input
		scrollPaneInput = new JScrollPane();
		frame.getContentPane().add(scrollPaneInput, "cell 2 2 3 3,grow");
		// input text area
		textAreaInput = new JTextArea();
		scrollPaneInput.setViewportView(textAreaInput);
		
		// viterbi button
		btnViterbi = new JButton("Viterbi");
		btnViterbi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HMM hmm = new HMM(parseInput(), parseParameters());
				hmm.viterbi();
				log(hmm.logChances());
			}
		});
		frame.getContentPane().add(btnViterbi, "cell 6 2,growx,aligny center");
		
		// rviterbi button
		btnRViterbi = new JButton("R Viterbi");
		btnRViterbi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HMM hmm = new HMM(new StringBuilder(parseInput()).reverse().toString(), parseParameters());
				hmm.viterbi();
			}
		});
		frame.getContentPane().add(btnRViterbi, "cell 6 3,growx,aligny center");
		
		// rsviterbi button
		btnRSViterbi = new JButton("RS Viterbi");
		btnRSViterbi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HMM hmm = new HMM(parseInput(), parseParameters());
				hmm.rsviterbi();
			}
		});
		frame.getContentPane().add(btnRSViterbi, "cell 6 4,growx,aligny center");
		
		// parameters label
		lblParameters = new JLabel("parameters");
		lblParameters.setHorizontalAlignment(JLabel.CENTER);
		lblParameters.setVerticalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblParameters, "cell 2 7 3 1,grow");
		
		// scroll layout input
		scrollPaneParameters = new JScrollPane();
		frame.getContentPane().add(scrollPaneParameters, "cell 2 8 3 3,grow");
		// parameters text area
		textAreaParameters = new JTextArea();
		scrollPaneParameters.setViewportView(textAreaParameters);
		
		// button to choose file
		btnChooseInput = new JButton("load input");
		btnChooseInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textAreaInput.setText(loadFile(chooseFile()));
		}});
		frame.getContentPane().add(btnChooseInput, "cell 2 5,growx,aligny center");
		
		// forward button
		btnForward = new JButton("Forward");
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HMM hmm = new HMM(parseInput(), parseParameters());
				hmm.forward();
			}
		});
		frame.getContentPane().add(btnForward, "cell 6 8,growx,aligny center");
		
		// clear log button
		btnClearLog = new JButton("clear log");
		btnClearLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearLog();
			}
		});
		frame.getContentPane().add(btnClearLog, "cell 6 11,growx,aligny center");
		
		// load button
		btnChooseParameters = new JButton("load params");
		btnChooseParameters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaParameters.setText(loadFile(chooseFile()));
			}
		});
		frame.getContentPane().add(btnChooseParameters, "cell 2 11,growx,aligny center");
		
		// rforward button
		btnRForward = new JButton("R Forward");
		btnRForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HMM hmm = new HMM(new StringBuilder(parseInput()).reverse().toString(), parseParameters());
				hmm.forward();
			}
		});
		frame.getContentPane().add(btnRForward, "cell 6 9,growx,aligny center");
	}
	
	private Parameters parseParameters() {
		return Parser.parseParameters(textAreaParameters.getText());
	}

	private String parseInput() {
		return textAreaInput.getText();
	}

	/**
	 * Initialize the input fields with usable data
	 */
	private void initInput() {
		textAreaInput.setText("315116246446644245311321631164152133625144543631656626566666651166453132651245636664631636663162326455236266666625151631222555441666566563564324364131513465146353411126414626253356366163666466232534413661661163252562462255265252266435353336233121625364414432335163243633665562466662632666612355245242");
		textAreaParameters.setText("F 1/6 1/6 1/6 1/6 1/6 1/6\nU 1/10 1/10 1/10 1/10 1/10 1/2\n1/20");
	}
	
	private String chooseFile() {
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(false);
		chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        	log("file chosen: " + chooser.getSelectedFile().getAbsoluteFile().getPath());
        	return chooser.getSelectedFile().getAbsoluteFile().getPath();
        } else {
        	log("error with chosen file");
        	return null;
        }
	}
	
	private String loadFile(String path) {
		if (path == null) {
			log("invalid file path");
			return null;
		}
		log("loading " + path);
		
		String file = null;
		try {
			file = Parser.parse(path);
			if (file != null) {
				log("load successful");
			} else {
				log("error loading file");
			}
		} catch (Exception e) {
			log(e.toString());
		}
		return file;
	}
	
	public static void log(String text) {
		if (txtrLog==null || text.isEmpty()) return;
		txtrLog.append("\n" + text);
	}
	
	private void clearLog() {
		if (txtrLog == null) return;
		txtrLog.setText(null);
	}
	
}
