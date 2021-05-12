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
	//private boolean testGUI = true; set to true to just test guy set to false to test actual
									// connectivity/functionality
	static DatabaseConnection dbConnection = new DatabaseConnection();
	private UserLoginAndRegister logger = new UserLoginAndRegister(dbConnection);

	public GUI(String func) {
		this.func = func;
	}
	
	public void loginScreen() {
		JFrame frame = new JFrame();
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JLabel userlabel = new JLabel("Username:");
		userlabel.setBounds(20, 20, 80, 25);
		panel.add(userlabel);

		userText = new JTextField(20);
		userText.setBounds(100, 20, 165, 25);
		userText.setVisible(true);
		panel.add(userText);
		
		JLabel passwordlabel = new JLabel("Password:");
		passwordlabel.setBounds(20, 70, 80, 25);
		panel.add(passwordlabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 70, 165, 25);
		passwordText.setVisible(true);
		panel.add(passwordText);
		
		JButton loginbutton = new JButton("Login");
		loginbutton.setBounds(20, 160, 80, 25);
		loginbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String user = userText.getText();
				String password = new String(passwordText.getPassword());
				
				if (logger.login1(user, password)) {
					JOptionPane.showMessageDialog(null,
							" You logged in as user: " + user + ", with password: " + password + " \n");
					frame.dispose();
					new PoliticalTrackerTable();
				}				
			}					
		});
		panel.add(loginbutton);
		
		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.setVisible(true);
		
	}
	
	public void registerScreen() {
		JFrame frame = new JFrame();
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JLabel userlabel = new JLabel("Username:");
		userlabel.setBounds(20, 20, 80, 25);
		panel.add(userlabel);

		userText = new JTextField(20);
		userText.setBounds(100, 20, 165, 25);
		userText.setVisible(true);
		panel.add(userText);
		
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(20, 60, 80, 25);
		panel.add(emailLabel);

		emailText = new JTextField(20);
		emailText.setBounds(100, 60, 165, 25);
		emailText.setVisible(true);
		panel.add(emailText);
		
		JLabel passwordlabel = new JLabel("Password:");
		passwordlabel.setBounds(20, 100, 80, 25);
		panel.add(passwordlabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 100, 165, 25);
		passwordText.setVisible(true);
		panel.add(passwordText);
		
		JButton registerbutton = new JButton("Register");
		registerbutton.setBounds(20, 160, 100, 25);
		registerbutton.addActionListener(new ActionListener() {
			
			String user = userText.getText();
			String password = new String(passwordText.getPassword());
			String email = emailText.getText();

			@Override
			public void actionPerformed(ActionEvent e) {
				if (logger.register(user, email, password)) {
					JOptionPane.showMessageDialog(null,
							" You registered a new user: " + user + ", with password: " + password + " \n");
					frame.dispose();
					new PoliticalTrackerTable();
				}
			}
			
		});
		panel.add(registerbutton);
		
		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent arg0) {

		// open login screen for returning users
		if (this.func.equals("Login")) {
			this.dispose();
			this.loginScreen();
		}
		
		// open registration screen for new users
		if (this.func.equals("Register")) {
			this.dispose();
			this.registerScreen();
		}

	}

	public static void main(String[] args) {
	//	dbConnection.getConnected();	
		
		JFrame frame = new JFrame();
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
