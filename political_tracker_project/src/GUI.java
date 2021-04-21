import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(500,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		JPanel panel = new JPanel();
		frame.add(panel);
		
		panel.setLayout(null);
		JLabel userlabel = new JLabel("User");
		userlabel.setBounds(20,20,80,25);
		panel.add(userlabel);
		
		JTextField userText = new JTextField(20);
		userText.setBounds(100,20,165,25);
		userText.setVisible(true);
		panel.add(userText);
		
		panel.setLayout(null);
		JLabel passwordlabel = new JLabel("Password");
		passwordlabel.setBounds(20,60,80,25);
		panel.add(passwordlabel);
		
		JTextField passwordText = new JTextField(20);
		passwordText.setBounds(100,60,165,25);
		passwordText.setVisible(true);
		panel.add(passwordText);
		
		
	}

}
