import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PoliticalTrackerTable extends JFrame  {
	
	static DatabaseConnection dbConnection = new DatabaseConnection();
	Connection connection;
	private FilterOrderService pols = new FilterOrderService(dbConnection);
	
	private static JTextField userText;
	private static JTextField titleText;
	private static JCheckBox titleFilter; 
	
	private static String[] filters = {"Title", "Publisher", "Author", "Genre", "Score"};
	private static String[] header = {"Title","Link","Date","Author", "Publisher", "Genre", "Score", ""};
	private static String[][] rows = {{"Title","Link","Date","Author", "Publisher", "Genre", "Score", "RateMe"}};
	private static JTable table =  new JTable(new DefaultTableModel(rows,header));
	

	public PoliticalTrackerTable() {
		this.connection = dbConnection.getConnected();	
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount((int) 0);
		
		ArrayList<String[]> newRows = pols.FilterBY(0,null);
		for(int i =0; i<newRows.size();i++) {
			model.addRow(newRows.get(i));
		}
		
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
		JButton andFilter = new JButton("And");
		andFilter.setBounds(890, 10, 80, 20);
		andFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount((int) 0);
				
				ArrayList<String[]> newRows = pols.FilterBY(0,null);
				for(int i =0; i<newRows.size();i++) {
					model.addRow(newRows.get(i));
				}
			}
		});
		panel.add(andFilter);
		
		JButton search = new JButton("Search");
		search.setBounds(890, 35, 80, 20);
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount((int) 0);
				
				ArrayList<String[]> newRows = pols.FilterBY(0,null);
				for(int i =0; i<newRows.size();i++) {
					model.addRow(newRows.get(i));
				}	
			}	
		});
		panel.add(search);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int rowT = table.rowAtPoint(evt.getPoint());
		        int colT = table.columnAtPoint(evt.getPoint());
		        if (colT == 3) {
		        	//Author clicked!!!
		        	frame.dispose();
		            new InfoScreen((String)table.getValueAt(rowT, colT), dbConnection, 3);
		        }
		        if (colT == 4) {
		        	//Publisher clicked!!!
		        	frame.dispose();
		        	new InfoScreen((String) table.getValueAt(rowT, colT), dbConnection, 4);
		        }
		        if(colT == 7) {
		        	//Rate article clicked
		        	frame.dispose();
		        	new ScoreGUI((String) table.getValueAt(rowT, 0), (String) table.getValueAt(rowT, 4), (String)table.getValueAt(rowT, 3),
		        			(String) table.getValueAt(rowT, 5), (String) table.getValueAt(rowT, 1));
		        }
		        
		    }
		});
		
		JScrollPane tablePane = new JScrollPane(table);
		tablePane.setBounds(50,80,900,200);
		tablePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(tablePane);
		
		//new article
		
		JButton addNewArticle = new JButton("Rate a New Article");
		addNewArticle.setBounds(800, 300, 140, 20);
		addNewArticle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new ScoreGUI(null, null, null, null, null);
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
