import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;

public class GUI {

	private JFrame frame;
	private JTextField txtFilepath;

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
		
		JButton btnLoad = new JButton("load");
		btnLoad.setBounds(463, 415, 107, 25);
		frame.getContentPane().add(btnLoad);
		
		txtFilepath = new JTextField();
		txtFilepath.setText("input file");
		txtFilepath.setBounds(354, 390, 216, 22);
		frame.getContentPane().add(txtFilepath);
		txtFilepath.setColumns(10);
		
		JTextArea txtrLog = new JTextArea();
		txtrLog.setText("Welcome!");
		txtrLog.setBounds(0, 0, 342, 453);
		txtrLog.setEditable(false);
		frame.getContentPane().add(txtrLog);
		
		JTextPane txtpnInfobox = new JTextPane();
		txtpnInfobox.setText("infobox");
		txtpnInfobox.setBounds(352, 13, 218, 116);
		txtpnInfobox.setEditable(false);
		frame.getContentPane().add(txtpnInfobox);
		
		JButton btnChoose = new JButton("select");
		btnChoose.setBounds(354, 415, 107, 25);
		frame.getContentPane().add(btnChoose);
	}
}
