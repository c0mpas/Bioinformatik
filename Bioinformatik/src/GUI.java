import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

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
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		icon = new ImageIcon(getClass().getResource("/graphics/dna.png"));
		frame.setIconImage(icon.getImage());
		frame.setTitle("DNA Assembler");
		frame.getContentPane().setLayout(new MigLayout("", "[50px:n][50px:n,grow][50px:n][50px:n]", "[50px:n][grow][30px:n][30px:n]"));
		
		// scroll layout
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 0 0 2 4,grow");
		
		// text area in scroll layout
		txtrLog = new JTextArea();
		txtrLog.setText("Welcome!");
		txtrLog.setEditable(false);
		scrollPane.setViewportView(txtrLog);

		// info text box
		txtpnInfobox = new JTextPane();
		txtpnInfobox.setText("infobox");
		txtpnInfobox.setEditable(false);
		frame.getContentPane().add(txtpnInfobox, "cell 2 0 2 1,grow");

		// filepath text field
		txtFilepath = new JTextField();
		txtFilepath.setText("input file");
		txtFilepath.setColumns(1);
		frame.getContentPane().add(txtFilepath, "cell 2 2 2 1,growx");
		
		// button to choose file
		btnChoose = new JButton("select");
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setMultiSelectionEnabled(false);
				chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		        	txtFilepath.setText(chooser.getSelectedFile().getAbsoluteFile().getPath());
		        }
		}});
		frame.getContentPane().add(btnChoose, "cell 2 3");
		
		// load button
		btnLoad = new JButton("load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log("loading " + txtFilepath.getText());
			}
		});
		frame.getContentPane().add(btnLoad, "cell 3 3");
	}
	
	public void log(String text) {
		if (txtrLog == null) return;
		txtrLog.setText(txtrLog.getText() + "\n" + text);
	}
}
