import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

 

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.microsoft.sqlserver.jdbc.SQLServerException;

 


@SuppressWarnings("serial")
public class ImportData extends JFrame {
    public static DatabaseConnection serviceConnection = new DatabaseConnection();

 

    public static void main(String[] args) {
        serviceConnection.getConnected();
        new ImportData();
    }

 

    JButton importButton;

 

    public ImportData() {

 

        setTitle("Data Import");
        setLayout(new BorderLayout());
        importButton = new JButton("Import Data");
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String path = System.getProperty("user.dir") + "\\Data.xlsx";
                Vector<List<Cell>> Articles = readExcel(path, 0);
                importArticles(Articles);
                System.out.println("Data import success. You can start demo now!");
            }

 

        });
        importButton.setSize(100, 100);
        add(importButton, BorderLayout.CENTER);
        setSize(350, 180);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

 

    public Vector<List<Cell>> readExcel(String path, int pageNum) {
        Vector<List<Cell>> result = new Vector<List<Cell>>();
        try {
            File file = new File(path);
            FileInputStream fileInput = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fileInput);
            XSSFSheet sheet = wb.getSheetAt(pageNum);
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                List<Cell> list = new ArrayList<Cell>();
                while (cellIterator.hasNext()) {

 

                    Cell cell = cellIterator.next();
                    list.add(cell);
                }
                result.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

 

        return result;
    }

 

    public void importArticles(Vector<List<Cell>> data) {
        String link = "";
        String title = "";
        String author = "";
        String publisher = "";
        java.sql.Date date = null;
        int score = 0;
        String criticUsername = "";
        String criticEmail = "";
        Iterator<List<Cell>> iter = data.iterator();
        while (iter.hasNext()) {
            List<Cell> list = iter.next();
            link = list.get(0).toString();
            title = list.get(1).toString();
            author = list.get(2).toString();
            publisher = list.get(3).toString();
            date = java.sql.Date.valueOf(java.time.LocalDate.now());
            score = new Double(list.get(5).getNumericCellValue()).intValue();
            criticUsername = list.get(6).toString();
            criticEmail = list.get(7).toString();
            
            String[] parts = author.split(" ");
            String authorFName = parts[0];
            String authorLName = parts[1];
            
            CallableStatement cs = null;
            
            try {
                
                Connection con = serviceConnection.getConnected();
                cs = con.prepareCall("{ ? = call [dbo].[AddScore] (?, ?, ?, ?, ?, ?, ?, ?, ?)}");
                cs.registerOutParameter(1, Types.INTEGER);
                cs.setInt(2, score);
                cs.setString(3, link);
                cs.setString(4, authorFName);
                cs.setString(5, authorLName);
                cs.setString(6, publisher);
                cs.setString(7, criticUsername);
                cs.setString(8, title);
                cs.setString(9, null);
                cs.setDate(10, date);
                cs.executeUpdate();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Articles import success.");
    }

 

}
 