/* File: DataSource.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This class responsible for managing database connections.
 *
 */
package org.cst8288.foodwastereduction.dataaccesslayer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;

/**
 * Singleton class responsible for managing database connections.
 * 
 * This class handles the initialization and retrieval of a database connection.
 * It uses a static block to load the JDBC driver and ensures that only one 
 * connection is created and reused throughout the application. Connection 
 * details are loaded from a properties file.
 * @author Hongxiu Guo
 */
public class DataSource {
    
    private static Connection connection = null;
    /**
     * Private constructor to prevent instantiation
     */
    private DataSource() { }
    
     /** static load jdbc driver*/
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LMSLogger.getInstance().saveLogInformation("SQLException occur at find jdbc driver: "+e.getMessage(), DataSource.class.getName() , LogLevel.TRACE);
        }
    }
    
    /**
     * Provides a connection to the database.
     * 
     * If no connection exists, a new connection is created using the details 
     * from the properties file. If a connection already exists, it is reused.
     * 
     * @return a Connection object to the database, or null if an error occurs
     */
    public static Connection getConnection() {
        try {
            if (connection == null) {
                // get connection information 
                String[] connectionInfo = openPropsFile();
                // connect to database
                connection = DriverManager.getConnection(connectionInfo[0], connectionInfo[1], connectionInfo[2]);
                
                LMSLogger.getInstance().saveLogInformation("Connect to database successfully", DataSource.class.getName() , LogLevel.INFO);
            }
        } catch (SQLException ex) {
             LMSLogger.getInstance().saveLogInformation("SQLException occur at getConnection: "+ex.getMessage(), DataSource.class.getName() , LogLevel.ERROR);
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
            LMSLogger.getInstance().saveLogInformation("Load the Properties file successfully", DataSource.class.getName() , LogLevel.INFO);
        } catch (IOException e) {
            
             LMSLogger.getInstance().saveLogInformation("SQLException occur at openPropsFile:" + e.getMessage(), DataSource.class.getName() , LogLevel.INFO);
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
