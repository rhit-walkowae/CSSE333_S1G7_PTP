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
	private static JTextField FilterText;
	private static JCheckBox titleFilter; 
	private static JLabel currentSearchlabel;
	private static JComboBox possibleOrders;
	
	private static String[] filters = {"Title", "Publisher", "Author", "Genre", "Score | =", "Score | >","Score | <"};
	private static String[] Orders = {"Date DESC", "Date ASC","Score DESC", "Score ASC"};
	private static String[] header = {"Title","Link","Date","Author", "Publisher", "Genre", "Score", ""};
	private static String[][] rows = {{"Title","Link","Date","Author", "Publisher", "Genre", "Score", "RateMe"}};
	private static JTable table =  new JTable(new DefaultTableModel(rows,header));
	

	public PoliticalTrackerTable(String username, int ID) {
		this.dbConnection.getConnected();	
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount((int) 0);
		
		ArrayList<String[]> newRows = pols.FilterBY(pols.filterArray,0);
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
		
		JLabel welcome = new JLabel("Welcome "+username);
		welcome.setBounds(50, 30, 300, 20);
		panel.add(welcome);
		
		JLabel Filterlabel = new JLabel("Filter By:");
		Filterlabel.setBounds(630, 10, 70, 20);
		panel.add(Filterlabel);
		
		currentSearchlabel = new JLabel("<html>Search:</html>",JLabel.LEFT);
		currentSearchlabel.setVerticalAlignment(JLabel.TOP);
		currentSearchlabel.setVerticalTextPosition(JLabel.TOP);
		currentSearchlabel.setBounds(630, 30, 250, 50);
		panel.add(currentSearchlabel);


		JComboBox possibleFilters = new JComboBox(filters);
		possibleFilters.setBounds(690, 10, 70, 20);
		panel.add(possibleFilters);
		
		FilterText = new JTextField();
		FilterText.setBounds(770, 10, 100, 20);
		FilterText.setVisible(true);
		panel.add(FilterText);
		
		JButton andFilter = new JButton("And");
		andFilter.setBounds(890, 10, 80, 20);
		andFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pols.filterArray.add(pols.makeFilterObject(possibleFilters.getSelectedItem().toString(),FilterText.getText()));
				
				String current = (String) currentSearchlabel.getText();
				String currentOpen = current.substring(0, current.length()-7);
				System.out.println(currentOpen);
				if(currentOpen.charAt(currentOpen.length()-1) != ':') {
					currentOpen = currentOpen + ",";
				}
				currentSearchlabel.setText(currentOpen +" "+ possibleFilters.getSelectedItem().toString() +"->"+FilterText.getText()+"</html>");
				FilterText.setText("");
				System.out.println("added filter");
			}
			
		});
		panel.add(andFilter);
		
		
		JButton search = new JButton("Search");
		search.setBounds(890, 75, 80, 20);
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount((int) 0);
				
				
				ArrayList<String[]> newRows = pols.FilterBY(pols.filterArray,possibleOrders.getSelectedIndex());
				pols.filterArray.clear();
				for(int i =0; i<newRows.size();i++) {
					model.addRow(newRows.get(i));
				}
				currentSearchlabel.setText("<html>Search:</html>");
				
			}
			
		});
		panel.add(search);
		JButton clear = new JButton("CLEAR");
		clear.setBounds(800, 75, 80, 20);
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentSearchlabel.setText("<html>Search:</html>");
				pols.filterArray.clear();
				
			}
			
		});
		panel.add(clear);
		
		JLabel OrderBylabel = new JLabel("Order By:");
		OrderBylabel.setBounds(50, 80, 70, 20);
		panel.add(OrderBylabel);
		
		possibleOrders = new JComboBox(Orders);
		possibleOrders.setBounds(120,80,120,20);
		panel.add(possibleOrders);
		
		
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
		        			(String) table.getValueAt(rowT, 5), (String) table.getValueAt(rowT, 1),(String) table.getValueAt(rowT, 2),username,ID);
		        }
		        
		    }
		});
		
		JScrollPane tablePane = new JScrollPane(table);
		tablePane.setBounds(50,120,900,200);
		tablePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(tablePane);
		
		//new article
		
		JButton addNewArticle = new JButton("Rate a New Article");
		addNewArticle.setBounds(800, 320, 140, 20);
		addNewArticle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new ScoreGUI(null, null, null, null, null,null,username, ID);
			}
			
		});
		panel.add(addNewArticle);
		
		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.setVisible(true);
	}

	
	public static void main(String args[]) {
		new PoliticalTrackerTable("null",0);
	}

}
