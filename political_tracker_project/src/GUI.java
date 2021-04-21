import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GUI implements ActionListener {
	private static JTextField userText;
	private static JPasswordField passwordText;
	private String func;
	private boolean testGUI = true; // set to true to just test guy set to false to test actual
									// connectivity/functionality

	public GUI(String func) {
		this.func = func;
	}

	public void loginClicked(String user, String password) {

		// !!!---you would call login function of UserService Here---

		if (testGUI)
			JOptionPane.showMessageDialog(null,
					" You logged in as user: " + user + ", with password: " + password + " \n");

	}

	public void registerClicked(String user, String password) {
		// !!!---you would call register function of UserService Here---

		if (testGUI)
			JOptionPane.showMessageDialog(null,
					" You registered a new user: " + user + ", with password: " + password + " \n");

	}

	public void actionPerformed(ActionEvent arg0) {

		String user = userText.getText();
		String password = passwordText.getText();
		userText.setText(null);
		passwordText.setText(null);
		if (this.func.equals("Login")) {
			loginClicked(user, password);
		}
		if (this.func.equals("Register")) {
			registerClicked(user, password);
		}

		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();

		panel.setLayout(null);
		JLabel userlabel = new JLabel("User");
		userlabel.setBounds(20, 20, 80, 25);
		panel.add(userlabel);

		userText = new JTextField(20);
		userText.setBounds(100, 20, 165, 25);
		userText.setVisible(true);
		panel.add(userText);

		JLabel passwordlabel = new JLabel("Password");
		passwordlabel.setBounds(20, 60, 80, 25);
		panel.add(passwordlabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 60, 165, 25);
		passwordText.setVisible(true);
		panel.add(passwordText);

		JButton loginbutton = new JButton("Login");
		loginbutton.setBounds(20, 100, 80, 25);
		loginbutton.addActionListener(new GUI("Login") {

		});
		panel.add(loginbutton);

		JButton registerbutton = new JButton("Register");
		registerbutton.setBounds(120, 100, 120, 25);
		registerbutton.addActionListener(new GUI("Register"));
		panel.add(registerbutton);

		frame.add(panel);
		frame.setVisible(true);

	}

}
