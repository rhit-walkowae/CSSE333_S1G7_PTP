import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class HomeScreen implements ActionListener {
	
	private static JTextField publisherName;
	private static JTextField descriptionField;
	private static JLabel title;
	String function;
	static DatabaseConnection dbConnection = new DatabaseConnection();
	private static Publishers publisher = new Publishers(dbConnection);

	public HomeScreen(String function) {
		this.function = function;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String pubName = publisherName.getText();
		if(descriptionField.getText() != null) {
			String descrip = descriptionField.getText();
			publisher.addPublisherWithDescription(pubName, descrip);
		} else {
			publisher.addPublisher(pubName);
		}
		
	}
	
	public static void main(String[] args) {
		dbConnection.getConnected();
		JFrame frame = new JFrame();
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		
		title = new JLabel("HOME");
		panel.add(title);
		
		JButton addPub = new JButton("Add New Publisher");
		panel.add(addPub);
		addPub.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog addPubWindow = new JDialog(frame);
				addPubWindow.setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.HORIZONTAL;
				
				// Publisher Name Components
				JLabel pubLabel = new JLabel("Publisher Name");
				c.gridx = 0;
		        c.gridy = 0;
		        c.gridwidth = 1;
		        c.gridheight = 1;
		        addPubWindow.add(pubLabel, c);
		        pubLabel.setVisible(true);
				publisherName = new JTextField(15);
				c.weightx = 1;
				c.gridwidth = 5;
				c.gridx = 1;
				addPubWindow.add(publisherName, c);
				publisherName.setVisible(true);
				
				// Description Components
				JLabel descripLabel = new JLabel("Description");				
				c.gridy = 1;
				c.gridx = 0;
				c.gridwidth = 1;
				addPubWindow.add(descripLabel, c);
				descripLabel.setVisible(true);
				descriptionField = new JTextField(200);
				c.gridx = 1;
				c.gridwidth = 5;
				addPubWindow.add(descriptionField, c);
				descriptionField.setVisible(true);
				
				
				JButton createPublisher = new JButton("Add Publisher");
				AddPublisherListnener listen = new AddPublisherListnener();
				createPublisher.addActionListener(listen);
				c.gridx = 1;
				c.gridy = 2;
				c.gridwidth = 2; 
				addPubWindow.add(createPublisher, c);
				createPublisher.setVisible(true);
				
				addPubWindow.setLocationRelativeTo(null);
				addPubWindow.setSize(450, 250);
				addPubWindow.setVisible(true);
			}		
		});
		
		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.setVisible(true);
	}

	public static class AddPublisherListnener implements ActionListener 
	{ 
		public void actionPerformed(ActionEvent e) 
		{   
			String pubName = publisherName.getText();
			System.out.println(pubName);
			if(descriptionField.getText() != null) {
				String descrip = descriptionField.getText();
				publisher.addPublisherWithDescription(pubName, descrip);
			} else {
				publisher.addPublisher(pubName);
			}	 
		} 
	} 
	
}
