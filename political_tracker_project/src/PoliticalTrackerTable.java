import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PoliticalTrackerTable extends JFrame  {
	
	static DatabaseConnection dbConnection = new DatabaseConnection();
	private FilterOrderService filter = new FilterOrderService(dbConnection);
	
	private static JTextField userText;
	private static JTextField titleText;
	private static JCheckBox titleFilter; 
	
	private static String[] filters = {"Title", "Publisher", "Author", "Genre", "Score"};

	public PoliticalTrackerTable() {
		//this.dbConnection.getConnected();	
		
		JFrame frame = new JFrame();
		frame.setSize(1000, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JLabel title = new JLabel("Political Tracker");
		title.setBounds(50, 10, 100, 20);
		panel.add(title);
		
		JLabel Filterlabel = new JLabel("Filter By:");
		Filterlabel.setBounds(630, 10, 70, 20);
		panel.add(Filterlabel);

		JComboBox possibleFilters = new JComboBox(filters);
		possibleFilters.setBounds(690, 10, 70, 20);
		panel.add(possibleFilters);
		
		titleText = new JTextField(20);
		titleText.setBounds(770, 10, 100, 20);
		titleText.setVisible(true);
		panel.add(titleText);
		
		JButton search = new JButton("Search");
		search.setBounds(890, 10, 80, 20);

/*create functionality to take name of column as table name and run filter against that
 * 
		search.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				int titleFlag = 0;
				if(possibleFilters.getSelectedItem().equals(filters[0])) {
					filter.FilterBY(titleFlag, titleText.getText());
			
				}
			}
		}); */
		panel.add(search);
		
		JLabel tLabel = new JLabel("Title");
		tLabel.setBounds(50, 40, 50, 20);
		panel.add(tLabel);
		
		JLabel dLabel = new JLabel("Date");
		dLabel.setBounds(120, 40, 50, 20);
		panel.add(dLabel);
		
		JLabel aLabel = new JLabel("Author");
		aLabel.setBounds(190, 40, 50, 20);
		panel.add(aLabel);
		
		JLabel pLabel = new JLabel("Publisher");
		pLabel.setBounds(260, 40, 70, 20);
		panel.add(pLabel);
		
		JLabel linkLabel = new JLabel("Link");
		linkLabel.setBounds(350, 40, 50, 20);
		panel.add(linkLabel);
		
		JLabel sLabel = new JLabel("Score");
		sLabel.setBounds(420, 40, 50, 20);
		panel.add(sLabel);
		
		JLabel gLabel = new JLabel("Genre");
		gLabel.setBounds(490, 40, 50, 20);
		panel.add(gLabel);
		
		JButton rateMe = new JButton("Rate");
		rateMe.setBounds(580, 70, 70, 20);
		panel.add(rateMe);
		
		JButton addNewArticle = new JButton("Rate a New Article");
		addNewArticle.setBounds(800, 300, 140, 20);
		addNewArticle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new ScoreGUI();
			}
			
		});
		panel.add(addNewArticle);
		
		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.setVisible(true);
	}

	
	public static void main(String args[]) {
		new PoliticalTrackerTable();
	}

}
