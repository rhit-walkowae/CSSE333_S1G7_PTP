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
public class UserLoginAndRegister {
    
        private static final Random RANDOM = new SecureRandom();
        private static final Base64.Encoder enc = Base64.getEncoder();
        private static final Base64.Decoder dec = Base64.getDecoder();
        private DatabaseConnection dbService;
        public UserLoginAndRegister(DatabaseConnection dbService) {
            this.dbService = dbService;
        }

//      public boolean useApplicationLogins() {
//          return true;
//      }
        
        
        public int login1(String username, String password) {
            try {
                Connection con = this.dbService.getConnection();
                PreparedStatement userStatement = null;
                if(username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
                    String query = "SELECT Username, PasswordSalt, PasswordHash,ID FROM [dbo].[Critic] WHERE Username = ?";
                    con.setAutoCommit(false);   
                    userStatement = con.prepareStatement(query);
                    userStatement.setString(1, username);
                    ResultSet res = userStatement.executeQuery();
                    con.commit();
                    con.setAutoCommit(true);
                    res.next();
                    byte[] passTheSalt = res.getBytes(2);
                    String passTheHash = res.getString(3);
                    int ID = res.getInt(4);
                    System.out.println(passTheHash);
                    String checkHashPass = hashPassword(passTheSalt, password);
                    if(checkHashPass.equals(passTheHash)) {
                        return ID;
                    } else {
                        JOptionPane.showMessageDialog(null, "Login Failed.");
                        return -1;
                    }
                } else { 
                    JOptionPane.showMessageDialog(null, "Please provide values for both fields.");
                    return -1;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Login Failed.");
                ex.printStackTrace();
                return -1;
            }
        }
        
        public int register(String username, String email, String password) {
            byte[] userSalt = getNewSalt();
            String hashedPass = hashPassword(userSalt, password);
            try {   
                Connection con = this.dbService.getConnection();
                CallableStatement cs = con.prepareCall("{ ? = call [dbo].[Register] (?, ?, ?, ?)}");
                cs.registerOutParameter(1, Types.INTEGER);              
                cs.setString(2, username);
                cs.setString(3, email);
                cs.setBytes(4, userSalt);
                cs.setString(5, hashedPass);
                cs.executeUpdate();
                int ID = cs.getInt(1);
                System.out.println("ID = "+ ID);
                return ID;
            } catch(SQLException e) {
                JOptionPane.showMessageDialog(null, e);
                e.printStackTrace();
                return -1;
            }
        }
        
        public byte[] getNewSalt() {
            byte[] salt = new byte[16];
            RANDOM.nextBytes(salt);
            return salt;
        }
        
        public String getStringFromBytes(byte[] data) {
            return enc.encodeToString(data);
        }
        public String hashPassword(byte[] salt, String password) {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory f;
            byte[] hash = null;
            try {
                f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                hash = f.generateSecret(spec).getEncoded();
            } catch (NoSuchAlgorithmException e) {
                JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
                e.printStackTrace();
            }
            return getStringFromBytes(hash);
        }
}