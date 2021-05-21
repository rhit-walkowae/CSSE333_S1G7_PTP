import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginScreen extends JFrame{
	
	static JTextField userText, emailText;
	static JPasswordField passwordText;
	
	public LoginScreen(DatabaseConnection dbConnection) {

		UserLoginAndRegister logger = new UserLoginAndRegister(dbConnection);
		
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
				int ID = logger.login1(user, password);
				if (ID >=0) {
					JOptionPane.showMessageDialog(null,
							" You logged in as user: " + user + ", with password: " + password + " \n");
					frame.dispose();
					new PoliticalTrackerTable(user,ID);
				}				
			}					
		});
		
		panel.add(loginbutton);
		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.setVisible(true);
	}

}
