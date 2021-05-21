import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnection {
	
		Connection connection;
		String connectionUrl;
		String dbServer = "titan.csse.rose-hulman.edu";
		String dbName = "PoliticalTracker_S1G7";
	
	    // Connect to your database.
	    // Replace server name, username, and password with your credentials
	
		public DatabaseConnection() {
			this.connectionUrl =
					"jdbc:sqlserver://titan.csse.rose-hulman.edu;databaseName=PoliticalTracker_S1G7;user=${userName};password={${password}}";
			this.connection = null;
			
		}
		


		public Connection getConnected(){
			
			//referenced https://mkyong.com/java/java-properties-file-examples/
			try (InputStream input = new FileInputStream("src/political1.properties")) {

	            Properties prop = new Properties();

	            
	            prop.load(input);

	            // get the property value and print it out
	            
	            this.connectionUrl = this.connectionUrl
						.replace("${userName}", prop.getProperty("db.username"))
						.replace("${password}", prop.getProperty("db.password"));

	           

	        } catch (IOException io) {
	            io.printStackTrace();
	        }
			
			

	        try {
	        	DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	        	this.connection = DriverManager.getConnection(connectionUrl);
	        	System.out.println("Connected successfully to Political Tracker with\n.");
	        	return this.connection;
	            // Code here.
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return this.connection;
	        }
	    }
		
		public Connection getConnection() {
			return this.connection;
		}
		
		public void closeConnection() {
			try {
				if(connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
}
