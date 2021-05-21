import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;



public class FilterOrderService {
	
	public class filterObject{
		String filter;
		String query;
		filterObject(String f,String q){
			this.filter = f;
			this.query = q;
		}
	}
	public filterObject makeFilterObject(String f, String q) {
		return new filterObject(f,q);
	}

	private DatabaseConnection dbService;
	
	ArrayList<filterObject> filterArray = new ArrayList<filterObject>();
	

	public FilterOrderService(DatabaseConnection dbService) {
		this.dbService = dbService;
		this.filterArray = new ArrayList<filterObject>();
		
		
	}

	public ArrayList<String[]> FilterBY(ArrayList<filterObject> filterQuery, int OrderVal) {
		int istitle = 0;
		String titleQuery = null;
		int isAuthor = 0;
		String AuthorQuery = null;
		int isGenre = 0;
		String GenreQuery = null;
		int isScore = 0;
		String ScoreQuery = null;
		int isPublisher = 0;
		String publisherQuery = null;
	
		for(int i = 0; i<filterQuery.size();i++) {
			String tFilter = filterQuery.get(i).filter;
			String tQuery = filterQuery.get(i).query;
			if(tFilter == "Title") {
				istitle = 1;
				titleQuery = tQuery;
			}
			if(tFilter == "Publisher") {
				isPublisher = 1;
				publisherQuery = tQuery;
			}
			if(tFilter == "Author") {
				isAuthor = 1;
				AuthorQuery = tQuery;
			}
			if(tFilter == "Genre") {
				isGenre = 1;
				GenreQuery = tQuery;
			}
			if(tFilter == "Score | =") {
				System.out.println("s = ");
				isScore = 1;
				ScoreQuery = tQuery;
			}
			if(tFilter == "Score | >") {
				isScore = 2;
				ScoreQuery = tQuery;
			}
			if(tFilter == "Score | <") {
				isScore = 3;
				ScoreQuery = tQuery;
			}
			
			System.out.println(tFilter + " ->"+tQuery);
		}
		
		//String[][] rows = {};
		if(filterArray == null) {
			istitle = 0;
			titleQuery = null;
			
		}
		
		ArrayList<String[]> rows = new ArrayList<String[]>();
		Connection con = this.dbService.getConnection();
		PreparedStatement filterStatement = null;
		String execStatement = "Exec dbo.FilterOrderBy @filterTitle = ?, @titleQuery = ?, @filterAuthor =?,"
				+ " @authorQuery = ?, @filterGenre =?, @GenreQuery = ?, @filterAvgIndex =?, @avgQuery =?, @filterPublisher =?, @PublisherQuery =?, @OrderBY = ? ";
		try {
			filterStatement = this.dbService.getConnection().prepareStatement(execStatement);
			filterStatement.setInt(1, istitle);
			filterStatement.setString(2, titleQuery);
			filterStatement.setInt(3, isAuthor);
			filterStatement.setString(4, AuthorQuery);
			filterStatement.setInt(5, isGenre);
			filterStatement.setString(6, GenreQuery);
			filterStatement.setInt(7, isScore);
			filterStatement.setString(8, ScoreQuery);
			filterStatement.setInt(9, isPublisher);
			filterStatement.setString(10, publisherQuery);
			filterStatement.setInt(11, OrderVal);
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
			int r = 0;
			//System.out.println("rs size" + rs.getFetchSize());
			while (rs.next()) {

				result = result + "\n -------\n " + "title:" + rs.getString(titleIndex) + "\n link: "
						+ rs.getString(linkIndex) + "\n date: " + rs.getString(datePublishedIndex) + "\n genre:"
						+ rs.getString(genreIndex) + "\n author:" + rs.getString(authorIndex) + "\n publisher"
						+ rs.getString(publisherIndex) + "\n AvgScore:" + rs.getString(scoreIndex);
				//System.out.println(result);

				// temp should go "Title","Link","Date","Author", "Publisher", "Genre", "Score"
				String[] temp = {rs.getString(titleIndex), rs.getString(linkIndex),rs.getString(datePublishedIndex), 
						rs.getString(authorIndex),rs.getString(publisherIndex),
						rs.getString(genreIndex),rs.getString(scoreIndex),"RateMe"};
				rows.add(temp );
				

				
				
			}
			
//			if (!result.isEmpty()) {
//				JOptionPane.showMessageDialog(null, result);
//			}
			System.out.println(rows.size());
			return rows;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return null;
	}

}

