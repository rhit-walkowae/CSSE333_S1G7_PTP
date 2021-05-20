import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterScreen extends JFrame {
	
	static JTextField userText, emailText;
	static JPasswordField passwordText;
	
	public RegisterScreen(DatabaseConnection dbConnection) {
		
		UserLoginAndRegister logger = new UserLoginAndRegister(dbConnection);
		
		JFrame frame = new JFrame();
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JLabel userlabel = new JLabel("Username:");
		userlabel.setBounds(20, 20, 80, 25);
		panel.add(userlabel);

		userText = new JTextField();
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
		panel.add(registerbutton);
		registerbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String user = userText.getText();
				String password = new String(passwordText.getPassword());
				String email = emailText.getText();
				if (logger.register(user, email, password)) {
					JOptionPane.showMessageDialog(null,
							" You registered a new user: " + user + ", with password: " + password + " \n");
					new PoliticalTrackerTable();
				}
			}
			
		});
		
		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.setVisible(true);
		
	}


}
