/* File: LMSLogger.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This is a utility class Logger class for logging messages to a file.
 *
 */
package org.cst8288.foodwastereduction.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Singleton Logger class for logging messages to a file.
 * This class uses a singleton pattern to ensure only one instance of the logger is created.
 * The logs are saved to a file named "logs.txt".
 * @see LogLevel
 * @see SimpleDateFormat
 * @see PrintWriter
 * 
 * @author Hongxiu Guo
 */
public class LMSLogger {
    /**
     * Single instance of LMSLogger
     */
    private static LMSLogger lmsLogger;
    /**
     * File to which logs are written
     */
    private final String LOGFILE = "logs/logs.txt";
    /**
     * writer for write file
     */
    private PrintWriter writer;
    /**
     * private Constructor.
     * Initializes the PrintWriter to append to the log file.
     */
    private LMSLogger (){
        try {
            writer = new PrintWriter(new FileWriter(LOGFILE,true));
           
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * get instance of LMSLogger.
     * If the instance is null, it initializes it.
     * 
     * @return  the single instance of LMSLogger
     */
    public static LMSLogger  getInstance(){
        if(lmsLogger == null ){
            lmsLogger  = new LMSLogger();
        }
    
    return lmsLogger;
    }
    /**
     * Write log information into file
     * 
     * @param msg the message to log
     * @param className  the name of the class where the log is generated
     * @param logLevel  the level of the log message
     */
    public void saveLogInformation(String msg, String className, LogLevel logLevel){
        
        writer.println(formatMessage (msg,className ,logLevel));               
        writer.flush();
    
    }
    /**
     * Formats the log message.
     * 
     * @param message the message to format
     * @param className the name of the class where the log is generated
     * @param logLevel the level of the log message
     * @return the formatted log message
     */
    private String formatMessage(String message,String className, LogLevel logLevel) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String timestamp = sdf.format(new Date());
        return String.format("%s [%s] [class:%s] [message:%s]", timestamp, logLevel.name(), className , message);
    }

}
