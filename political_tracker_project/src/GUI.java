import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GUI extends JFrame implements ActionListener {
	private static JTextField userText;
	private static JTextField emailText;
	private static JPasswordField passwordText;
	private String func;
	private static JFrame frame;
	//private boolean testGUI = true; set to true to just test guy set to false to test actual
									// connectivity/functionality
	static DatabaseConnection dbConnection = new DatabaseConnection();

	public GUI(String func) {
		this.func = func;
	}	
	
	public void actionPerformed(ActionEvent arg0) {
		
		this.dispose();

		// open login screen for returning users
		if (this.func.equals("Login")) {
			frame.dispose();
			new LoginScreen(dbConnection);
		}
		
		// open registration screen for new users
		if (this.func.equals("Register")) {
			frame.dispose();
			new RegisterScreen(dbConnection);
		}

	}

	public static void main(String[] args) {
		dbConnection.getConnected();	
		
		frame = new JFrame();
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JLabel intro = new JLabel("Welcome to the Political Tracker!");
		intro.setBounds(150, 0, 200, 50);
		panel.add(intro);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.setBounds(180, 50, 100, 25);
		loginBtn.addActionListener(new GUI("Login"));
		panel.add(loginBtn);
		
		JButton registerBtn = new JButton("Register");
		registerBtn.setBounds(180, 100, 100, 25);
		registerBtn.addActionListener(new GUI("Register"));
		panel.add(registerBtn);

		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.setVisible(true);
		


	}

}
