import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class Scores {

	private DatabaseConnection dbService;
	private Connection connection;
	
	public Scores(DatabaseConnection dbService) {
		this.dbService = dbService;
		this.connection = dbService.getConnected();
	}
	
	public boolean AddScore(int number, String link, String authorFName, String authorLName, String publisher) {
		try {
			CallableStatement cs = null;
			cs = this.connection.prepareCall("{ ? = call [dbo].[AddScore] (?, ?, ?, ?, ?, ?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, number);
			cs.setString(3, link);
			cs.setString(4, authorFName);
			cs.setString(5, authorLName);
			cs.setString(6, publisher);
			cs.setString(7, "saayeh06");
			cs.executeUpdate();
			if(cs.getInt(1) != 1 && cs.getInt(1) != 2 && 
					cs.getInt(1) != 3 && cs.getInt(1) != 4 && cs.getInt(1) != 5 && cs.getInt(1) != 6 && cs.getInt(1) != 7) {
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Could not successfully add Score to database.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	

}
