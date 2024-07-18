
package org.cst8288.foodwastereduction.dataaccesslayer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    
    /**
     * 
     * @return 
     */
    public static Connection getConnection() {
    try {
            if (connection == null) {
                //get connection information
                String[] connectionInfo = openPropsFile();
                
                connection = DriverManager.getConnection(connectionInfo[0], connectionInfo[1], connectionInfo[2]);
            } else {
                //System.out.println("Cannot create new connection, using existing one");
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

        try (InputStream in = Files.newInputStream(Paths.get("./data/database.properties"));) {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //get information from database.properties file: jdbc:mysql://localhost:3306/lab2 
        String db = props.getProperty("db");//mysql
        String name = props.getProperty("name");//database name: lab2
        String host = props.getProperty("host"); // localhost
        String port = props.getProperty("port"); // 3306
        
        //connection string
        String connectionString = "jdbc:"+db+"://"+host+":"+port+"/"+name;
        String username = props.getProperty("user");
        String password = props.getProperty("pass");

        String[] info = new String[3];
        info[0] = connectionString;
        info[1] = username;
        info[2] = password;

        return info;
    }
}
