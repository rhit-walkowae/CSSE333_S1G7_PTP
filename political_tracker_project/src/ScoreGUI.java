import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ScoreGUI extends JFrame{
	
	private JLabel author, title, link, publisher, rating, genre;
	private JTextArea inputAuthor, inputTitle, inputLink, inputPublisher, inputGenre;
	private JRadioButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
	private JButton submit;
	private JPanel panel;
	
	static DatabaseConnection dbConnection = new DatabaseConnection();
	private static Scores scoresCall = new Scores(dbConnection);
	
	public ScoreGUI(String titleBeingRated, String publisherBeingRated, String authorBeingRated, String genreOf, String linkOfArticle, String username, int uID) {
		
		this.setTitle("Add Score");
		
		this.panel = new JPanel();
		
		this.author = new JLabel("Author");
		this.title = new JLabel("Title");
		this.link = new JLabel("Link");
		this.genre = new JLabel("Genre");
		this.publisher = new JLabel("Publisher");
		this.rating = new JLabel("Rating");
		
		this.submit = new JButton("Submit");
		this.submit.setBounds(140, 280, 300, 20);;
		
		ButtonGroup group = new ButtonGroup();
		JPanel radioPanel = new JPanel();
		
		this.b1 = new JRadioButton("1");
		this.b2 = new JRadioButton("2");
		this.b3 = new JRadioButton("3");
		this.b4 = new JRadioButton("4");
		this.b5 = new JRadioButton("5");
		this.b6 = new JRadioButton("6");
		this.b7 = new JRadioButton("7");
		this.b8 = new JRadioButton("8");
		this.b9 = new JRadioButton("9");
		this.b10 = new JRadioButton("10");
		
		group.add(b1);
		group.add(b2);
		group.add(b3);
		group.add(b4);
		group.add(b5);
		group.add(b6);
		group.add(b7);
		group.add(b8);
		group.add(b9);
		group.add(b10);
		
		if(titleBeingRated == null) {
			
			this.inputAuthor = new JTextArea();
			this.inputTitle = new JTextArea();
			this.inputLink = new JTextArea();
			this.inputGenre = new JTextArea();
			this.inputPublisher = new JTextArea();
			
			
			this.submit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String authorNames = inputAuthor.getText();
					String linkInput = inputLink.getText();
					String pub = inputPublisher.getText();
					String gen = inputGenre.getText();
					String tit = inputTitle.getText();
					int score = 0;
					for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
						AbstractButton button = buttons.nextElement();
						if (button.isSelected()) {
							score = Integer.parseInt(button.getText());
						}
					}
					if(authorNames != null && linkInput != null && pub != null && score != 0) {
						String[] parts = authorNames.split(" ");
						String authorFName = parts[0];
						String authorLName = parts[1];
						scoresCall.AddScore(score, linkInput, authorFName, authorLName, pub, tit, gen,username);
					}
					dispose();
					new PoliticalTrackerTable(username,uID);
				} 
				
			});
			
			
		} else {
			
			this.inputAuthor = new JTextArea(authorBeingRated);
			this.inputTitle = new JTextArea(titleBeingRated);
			this.inputLink = new JTextArea(linkOfArticle);
			this.inputGenre = new JTextArea(genreOf);
			this.inputPublisher = new JTextArea(publisherBeingRated);
			
			this.submit.addActionListener(new ActionListener() {
				
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int score = 0;
					for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
						AbstractButton button = buttons.nextElement();
						if (button.isSelected()) {
							score = Integer.parseInt(button.getText());
						}
					}
					if(authorBeingRated != null && titleBeingRated != null && publisherBeingRated != null && score != 0) {
						String[] parts = authorBeingRated.split(" ");
						String authorFName = parts[0];
						String authorLName = parts[1];
						scoresCall.AddScore(score, linkOfArticle, authorFName, authorLName, publisherBeingRated, titleBeingRated, genreOf,username);
					}
					dispose();
					new PoliticalTrackerTable(username,uID);
				} 
				
			});
		}
		
		author.setBounds(20, 20, 100, 20);
		title.setBounds(20, 60, 100, 20);
		link.setBounds(20, 100, 100, 20);
		publisher.setBounds(20, 140, 100, 20);
		rating.setBounds(20, 180, 100, 20);
		genre.setBounds(20, 220, 100, 20);
		
		inputAuthor.setBounds(100, 20, 400, 20);
		inputTitle.setBounds(100, 60, 400, 20);
		inputLink.setBounds(100, 100, 400, 20);
		inputGenre.setBounds(100, 180, 400, 20);
		inputPublisher.setBounds(100, 140, 400, 20);
		
		panel.setBounds(50, 220, 500, 100);
		
		radioPanel.setBounds(100, 220, 400, 50);
		
		radioPanel.add(b1);
		radioPanel.add(b2);
		radioPanel.add(b3);
		radioPanel.add(b4);
		radioPanel.add(b5);
		radioPanel.add(b6);
		radioPanel.add(b7);
		radioPanel.add(b8);
		radioPanel.add(b9);
		radioPanel.add(b10);
		
		this.add(submit);
		
		this.add(author);
		this.add(title);
		this.add(link);
		this.add(publisher);
		this.add(rating);
		
		this.add(inputAuthor);
		this.add(inputTitle);
		this.add(inputLink);
		this.add(inputPublisher);
		
		this.add(radioPanel);
		this.add(panel);
		
		this.setSize(600, 400);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	} 
	
}
