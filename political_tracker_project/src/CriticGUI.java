import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CriticGUI extends JFrame{
	
	private JLabel author, title, link, date, publisher, rating;
	private JTextArea inputAuthor, inputTitle, inputLink, inputDate, inputPublisher;
	private JRadioButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
	private JPanel panel;
	public CriticGUI() {
		this.setTitle("Add Article");
		
		this.panel = new JPanel();
		
		this.author = new JLabel("Author");
		this.title = new JLabel("Title");
		this.link = new JLabel("Link");
		this.date = new JLabel("Date");
		this.publisher = new JLabel("Publisher");
		this.rating = new JLabel("Rating");
		
		this.inputAuthor = new JTextArea();
		this.inputTitle = new JTextArea();
		this.inputLink = new JTextArea();
		this.inputDate = new JTextArea();
		this.inputPublisher = new JTextArea();
		
		author.setBounds(20, 20, 100, 20);
		title.setBounds(20, 60, 100, 20);
		link.setBounds(20, 100, 100, 20);
		date.setBounds(20, 140, 100, 20);
		publisher.setBounds(20, 180, 100, 20);
		rating.setBounds(20, 225, 100, 20);
		
		inputAuthor.setBounds(100, 20, 500, 20);
		inputTitle.setBounds(100, 60, 500, 20);
		inputLink.setBounds(100, 100, 500, 20);
		inputDate.setBounds(100, 140, 500, 20);
		inputPublisher.setBounds(100, 180, 500, 20);
		
		ButtonGroup group = new ButtonGroup();
		
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
		
		panel.setBounds(50, 220, 500, 100);
		
		panel.add(b1);
		panel.add(b2);
		panel.add(b3);
		panel.add(b4);
		panel.add(b5);
		panel.add(b6);
		panel.add(b7);
		panel.add(b8);
		panel.add(b9);
		panel.add(b10);
		
		this.add(author);
		this.add(title);
		this.add(link);
		this.add(date);
		this.add(publisher);
		this.add(rating);
		
		this.add(inputAuthor);
		this.add(inputTitle);
		this.add(inputLink);
		this.add(inputDate);
		this.add(inputPublisher);

		this.add(panel);
		
		this.setSize(1000, 400);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		CriticGUI gui = new CriticGUI();
	}
}
