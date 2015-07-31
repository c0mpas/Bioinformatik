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

public class GUI {

	private static JTextArea txtrLog;
	private static JLabel lblBusy;

	private JFrame frame;
	private ImageIcon icon_logo;
	private JScrollPane scrollPane;
	private JLabel labelInfobox;
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
	private JLabel lblSpinner;
	
	
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
		frame.getContentPane().setLayout(new MigLayout("", "[100px:n][50px:n,grow][50px:n][50px:n][50px:n]", "[20px:n][][50px:n][grow][10px:n,grow][50px:n][20px:n][10px:n,grow][30px:n][30px:n][10px:n,grow][30px:n]"));
		
		// text pane
		labelLog = new JLabel();
		labelLog.setHorizontalAlignment(JLabel.CENTER);
		labelLog.setVerticalAlignment(JLabel.CENTER);
		labelLog.setText("Log");
		frame.getContentPane().add(labelLog, "cell 0 0 2 1,grow");
		
		// scroll layout
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 0 1 2 11,grow");
		
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
		
		// busy label
		lblBusy = new JLabel("ready");
		lblBusy.setHorizontalAlignment(JLabel.CENTER);
		lblBusy.setForeground(Color.GREEN);
		frame.getContentPane().add(lblBusy, "cell 3 6,growx,aligny center");
		
		// forward button
		btnForward = new JButton("Forward");
		btnForward.setHorizontalAlignment(SwingConstants.LEFT);
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO
			}
		});
		frame.getContentPane().add(btnForward, "cell 2 8,growx,aligny center");
		
		// rforward button
		btnRForward = new JButton("R Forward");
		btnRForward.setHorizontalAlignment(SwingConstants.LEFT);
		btnRForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO
			}
		});
		frame.getContentPane().add(btnRForward, "cell 3 8,growx,aligny center");
		
		// viterbi button
		btnViterbi = new JButton("Viterbi");
		btnViterbi.setHorizontalAlignment(SwingConstants.LEFT);
		btnViterbi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO
			}
		});
		frame.getContentPane().add(btnViterbi, "cell 2 9,growx,aligny center");
		
		// rviterbi button
		btnRViterbi = new JButton("R Viterbi");
		btnRViterbi.setHorizontalAlignment(SwingConstants.LEFT);
		btnRViterbi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO
			}
		});
		frame.getContentPane().add(btnRViterbi, "cell 3 9,growx,aligny center");
		
		// rsviterbi button
		btnRSViterbi = new JButton("RS Viterbi");
		btnRSViterbi.setHorizontalAlignment(SwingConstants.LEFT);
		btnRSViterbi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO
			}
		});
		frame.getContentPane().add(btnRSViterbi, "cell 4 9,growx,aligny center");
		
		// button to choose file
		btnChooseInput = new JButton("load input");
		btnChooseInput.setIcon(new ImageIcon(getClass().getResource("/graphics/icon_open.png")));
		btnChooseInput.setHorizontalAlignment(SwingConstants.LEFT);
		btnChooseInput.setIconTextGap(20);
		btnChooseInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadFileInput(chooseFile());
		}});
		frame.getContentPane().add(btnChooseInput, "cell 2 11,growx,aligny center");
		
		// load button
		btnChooseParameters = new JButton("load params");
		btnChooseParameters.setIcon(new ImageIcon(getClass().getResource("/graphics/icon_load.png")));
		btnChooseParameters.setHorizontalAlignment(SwingConstants.LEFT);
		btnChooseParameters.setIconTextGap(20);
		btnChooseParameters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFileParameters(chooseFile());
			}
		});
		frame.getContentPane().add(btnChooseParameters, "cell 3 11,growx,aligny center");
		
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
		frame.getContentPane().add(btnClearLog, "cell 4 11,growx,aligny center");
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
	
	private void loadFileParameters(String path) {
		if (path == null) {
			log("invalid file path");
			return;
		}
		log("loading " + path);
		
		Parameters params = null;
		try {
			params = Parser.parseParameters(path);
		} catch (Exception e) {
			log(e.toString());
		}
		if (params == null) {
			log("error loading file");
		} else {
			log("load successful");
		}
	}
	
	private void loadFileInput(String path) {
		if (path == null) {
			log("invalid file path");
			return;
		}
		log("loading " + path);
		
		Parameters params = null;
		try {
			params = Parser.parseInput(path);
		} catch (Exception e) {
			log(e.toString());
		}
		if (params == null) {
			log("error loading file");
		} else {
			log("load successful");
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
	
	// refresh info box and show current info about graph
	private void refreshInfoBox() {
		StringBuilder sb = new StringBuilder();
		// TODO
		sb.append("<html><body>infobox<br><br>");
		sb.append("nodes: ");
		sb.append("<br>edges: ");
		sb.append("</body></html>");
		labelInfobox.setText(sb.toString());
	}
	
	// change busy label to working
	public void stateWorking() {
		if (lblBusy==null || lblSpinner==null) return;
		lblBusy.setText(" working");
		lblBusy.setForeground(Color.RED);
		lblSpinner.setVisible(true);
	}

	// change busy label to ready
	public void stateReady() {
		if (lblBusy==null || lblSpinner==null) return;
		lblBusy.setText("ready");
		lblBusy.setForeground(Color.GREEN);
		lblSpinner.setVisible(false);
	}
	
}
