import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

public class CriticService {
	private DatabaseConnection dbService;

	public CriticService(DatabaseConnection dbService) {
		this.dbService = dbService;
		
	}

	public boolean addArticle(String link, String date, String title, String authorFName, String authorLName,
			String authorMInit, String publisherName, String genreName) {
		int returnValue = 0;
		
		try {
			CallableStatement cs = null;
			Connection con = this.dbService.getConnection();
			cs = con.prepareCall("{?=call AddArticle(?,?,?,?,?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, link);
			if(!date.isEmpty()) {
				cs.setDate(3, Date.valueOf(date));
			} else {
				cs.setDate(3, null);
			}
			cs.setString(4, title);
			cs.setString(5, authorFName);
			cs.setString(6, authorLName);
			cs.setString(7, authorMInit);
			cs.setString(8, publisherName);
			cs.setString(9, genreName);
			cs.execute();
			returnValue = cs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "AddArticle not implemented.");
			e.printStackTrace();
			return false;
		}
		
		if (returnValue == 1) {
			JOptionPane.showMessageDialog(null, "Title cannot be empty.");
			return false;
		}
		if (returnValue == 2) {
			JOptionPane.showMessageDialog(null, "Link cannot be empty.");
			return false;
		}
		if (returnValue == 3) {
			JOptionPane.showMessageDialog(null, "Article already exists.");
			return false;
		}
		if (returnValue == 4) {
			JOptionPane.showMessageDialog(null, "Not enough detail to search for author, please enter last name.");
			return false;
		}
		if (returnValue == 5) {
			JOptionPane.showMessageDialog(null, "Not enough detail to search for author, please enter first name.");
			return false;
		}
		if (returnValue > 0) {
			JOptionPane.showMessageDialog(null, "There are some issues with the values given!");
			return false;
		} else {
			JOptionPane.showMessageDialog(null, "You add article successfully!");
			return true;
		}
	}

}
