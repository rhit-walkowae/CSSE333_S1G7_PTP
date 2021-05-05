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

public class Publishers {

	private DatabaseConnection dbService;
	private Connection connection;
	
	public Publishers(DatabaseConnection dbService) {
		this.dbService = dbService;
		this.connection = dbService.getConnection();
	}
	
	public boolean addPublisher(String name) {
		try {
			CallableStatement cs = null;
			cs = this.connection.prepareCall("{ ? = call [dbo].[AddPublishers] (?, ?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setNull(3, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			if(cs.getInt(1) != 1 && cs.getInt(1) != 2) {
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Could not successfully add Publisher to database.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean addPublisherWithDescription(String name, String description) {
		try {
			Connection con = this.dbService.getConnection();
			CallableStatement cs = null;
			cs = con.prepareCall("{ ? = call [dbo].[AddPublishers] (?, ?)}");
			cs.registerOutParameter(1, Types.INTEGER);				
			cs.setString(2, name);
			cs.setString(3, description);
			cs.executeUpdate();
			if(cs.getInt(1) != 1 && cs.getInt(1) != 2) {
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Could not successfully add Publisher to database.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	

}
