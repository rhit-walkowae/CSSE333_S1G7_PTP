import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;



public class FilterOrderService {
	
	private DatabaseConnection dbService;
	

	public FilterOrderService(DatabaseConnection dbService) {
		this.dbService = dbService;
	}
	
	public boolean FilterBY(int istitle, String titleQuery) {
		Connection con = this.dbService.getConnection();
		PreparedStatement filterStatement = null;
		String execStatement = "Exec dbo.FilterOrderBy @filterTitle = ?, @titleQuery = ?";
		try {
			filterStatement = this.dbService.getConnection().prepareStatement(execStatement);
			filterStatement.setInt(1, istitle);
			filterStatement.setString(2, titleQuery);
			ResultSet rs = filterStatement.executeQuery();
			int titleIndex = rs.findColumn("Title");
			int linkIndex = rs.findColumn("link");
			int datePublishedIndex = rs.findColumn("datePublished");
			int genreIndex = rs.findColumn("Genre");
			int authorIndex = rs.findColumn("author");
			int publisherIndex = rs.findColumn("publisher");
			int scoreIndex = rs.findColumn("AvgScore");
			System.out.println("------NEW SEARCH------");
			String result = "";
			while (rs.next()) {
				result = result +"\n -------\n "+ "title:"+rs.getString(titleIndex) +"\n link: "+ rs.getString(linkIndex)+"\n date: "+
						rs.getString(datePublishedIndex)+"\n genre:"+ rs.getString(genreIndex)+
						"\n author:"+rs.getString(authorIndex)+"\n publisher"+rs.getString(publisherIndex)+"\n AvgScore:"+rs.getString(scoreIndex);
				System.out.println(result);
			}
			if(!result.isEmpty()) {
				JOptionPane.showMessageDialog(null, result );
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e );
			e.printStackTrace();
		}
		return false;
	}

}
