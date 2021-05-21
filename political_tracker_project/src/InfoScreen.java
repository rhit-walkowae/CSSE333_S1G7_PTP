import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class InfoScreen extends JFrame {
	
	JPanel panel;
	Connection connection;
	DatabaseConnection dbConnection;

	JLabel pubOrAuth, description, avgScore;
	JTextArea pubOrAuthTextArea, descriptionTextArea, avgScoreText;
	JButton goBack;
	
	public InfoScreen(String name, DatabaseConnection dbService, int column) {
		
		this.dbConnection = dbService;
		this.connection = dbService.getConnected();
		
		this.setTitle(name);
	
		this.panel = new JPanel();
		panel.setBounds(50, 220, 500, 100);
	
		if(column == 3) {			
			this.pubOrAuth = new JLabel("Author");
		} else if (column == 4) {
			this.pubOrAuth = new JLabel("Publisher");
		}
		this.description = new JLabel("Description");
		this.avgScore = new JLabel("Average Score");
	
		this.pubOrAuth.setBounds(20, 20, 100, 20);
		this.description.setBounds(20, 60, 100, 20);
		this.avgScore.setBounds(20, 100, 100, 20);
		
		try {
			CallableStatement cs = null;
			if(column == 3) {
				cs = this.connection.prepareCall("{ ? = call [dbo].[GetAuthor] (?, ?, ?)}");
				cs.registerOutParameter(1, Types.INTEGER);
				String[] parts = name.split(" ");
				String authorFName = parts[0];
				String authorLName = parts[1];
				cs.setString(2, authorFName);
				cs.setString(3, authorLName);
				cs.setNull(4, Types.NULL);
				
				// execute query and get results
				ResultSet res = cs.executeQuery();
				res.next();
				String authText = res.getString(1) + " " + res.getString(2);
				String descripText = res.getString(4);
				int averageScore = res.getInt(6);
			
				// fill in text area with AUTHOR info
				this.pubOrAuthTextArea = new JTextArea(authText);
				this.descriptionTextArea = new JTextArea(descripText);
				this.avgScoreText = new JTextArea(Integer.toString(averageScore));
				
				// set bounds for JTextAreas
				this.pubOrAuthTextArea.setBounds(80, 20, 100, 20);
				this.descriptionTextArea.setBounds(120, 60, 300, 20);
				this.avgScoreText.setBounds(160, 100, 100, 20);
				
			} else if (column == 4) {
				cs = this.connection.prepareCall("{ ? = call [dbo].[GetPublisher] (?)}");
				cs.registerOutParameter(1, Types.INTEGER);
				cs.setString(2, name);
				
				// execute query and get results
				ResultSet res = cs.executeQuery();
				res.next();
				String pubText = res.getString(1);				
				String descripText = res.getString(2);
				int averageScore = res.getInt(3);
				
				// fill in text area with PUBLISHER info
				this.pubOrAuthTextArea = new JTextArea(pubText);
				this.descriptionTextArea = new JTextArea(descripText);
				this.avgScoreText = new JTextArea(Integer.toString(averageScore));
				
				// set bounds for JTextAreas
				this.pubOrAuthTextArea.setBounds(80, 20, 100, 20);
				this.descriptionTextArea.setBounds(120, 60, 300, 20);
				this.avgScoreText.setBounds(160, 100, 100, 20);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		this.goBack = new JButton("Go Back");
		this.goBack.setBounds(140, 240, 300, 20);;
		this.goBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new PoliticalTrackerTable("null", 0);
			} 
				
		});
		
		// adding JTextArea's to frame
		this.add(pubOrAuthTextArea);
		this.add(descriptionTextArea);
		this.add(avgScoreText);
		
		// adding JButton to frame
		this.add(goBack);

		// adding JLabel's to frame
		this.add(this.pubOrAuth);
		this.add(this.description);
		this.add(this.avgScore);
		this.add(panel);
		
		this.setSize(600, 400);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}


}
