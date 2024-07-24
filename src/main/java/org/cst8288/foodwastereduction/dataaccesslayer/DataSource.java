
package org.cst8288.foodwastereduction.dataaccesslayer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Hongxiu Guo
 */
public class DataSource {
     private static Connection connection = null;
    private DataSource() { }
    
     // static load jdbc driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @return 
     */
    public static Connection getConnection() {
        try {
            if (connection == null) {
                // get connection information 
                String[] connectionInfo = openPropsFile();
                // connect to database
                connection = DriverManager.getConnection(connectionInfo[0], connectionInfo[1], connectionInfo[2]);
            } else {
        
                // System.out.println("Cannot create new connection, using existing one");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }
    
      /**
     * Read the Properties file set data into array
     * use the Properties class to load values from the database.properties file
     * 
     * @return a array that contains data connection information
     */
    private static String[] openPropsFile() {
        // added use of Properties and try-with-resources
        Properties props = new Properties();

        try (InputStream in = DataSource.class.getClassLoader().getResourceAsStream("data/database.properties")) {
            if (in == null) {
                throw new IOException("Properties file not found");
            }
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //get information from database.properties file: jdbc:mysql://localhost:3306/lab2 
        String connectionString = props.getProperty("jdbc.url");//jdbc:mysql://localhost:3306/FWRP
        String username = props.getProperty("jdbc.username");//CST8288Group
        String password = props.getProperty("jdbc.password"); // 12345678
       

        String[] info = new String[3];
        info[0] = connectionString;
        info[1] = username;
        info[2] = password;

        return info;
    }
}
