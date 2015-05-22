import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class GUI {

	private JFrame frame;
	private JTextField txtFilepath;
	private JTextArea txtrLog;
	private JTextPane txtpnInfobox;
	private JButton btnLoad;
	private JButton btnChoose;
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
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnLoad = new JButton("load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log("loading " + txtFilepath.getText());
			}
		});
		btnLoad.setBounds(463, 415, 107, 25);
		frame.getContentPane().add(btnLoad);
		
		txtFilepath = new JTextField();
		txtFilepath.setText("input file");
		txtFilepath.setBounds(354, 390, 216, 22);
		frame.getContentPane().add(txtFilepath);
		txtFilepath.setColumns(1);
		
		txtrLog = new JTextArea();
		txtrLog.setText("Welcome!");
		txtrLog.setBounds(0, 0, 342, 453);
		txtrLog.setEditable(false);
		frame.getContentPane().add(txtrLog);
		
		txtpnInfobox = new JTextPane();
		txtpnInfobox.setText("infobox");
		txtpnInfobox.setBounds(352, 13, 218, 116);
		txtpnInfobox.setEditable(false);
		frame.getContentPane().add(txtpnInfobox);
		
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
		btnChoose.setBounds(354, 415, 107, 25);
		frame.getContentPane().add(btnChoose);
	}
	
	public void log(String text) {
		if (txtrLog == null) return;
		txtrLog.setText(txtrLog.getText() + "\n" + text);
	}

}
