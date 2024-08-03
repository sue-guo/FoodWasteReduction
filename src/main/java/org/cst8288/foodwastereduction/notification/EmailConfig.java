package org.cst8288.foodwastereduction.notification;

/**
 * File: EmailConfig.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-28
 * Modified: 2024-08-03 
 * Description: This class is used to make EMAIL SMTP configuration
 */
public class EmailConfig {
    /**
     * Email SMTP host
     */
    private String host;
    
    /**
     * Email SMTP port
     */
    private String port;
    
    /**
     * Email SMTP username
     */
    private String username;
    
    /**
     * Email SMTP password
     */
    private String password;

    /**
     * Constructor for EmailConfig
     * @param host
     * @param port
     * @param username
     * @param password 
     */
    public EmailConfig(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /**
     * Getter of host
     * @return 
     */
    public String getHost() { return host; }
    
    /**
     * Getter of port
     * @return 
     */
    public String getPort() { return port; }
    
    /**
     * Getter of username
     * @return 
     */
    public String getUsername() { return username; }
    
    /**
     * Getter of password
     * @return 
     */
    public String getPassword() { return password; }
    
    /**
     * This static configuration is only used for testing, after testing, the password is valid until 2024-08-20.
     * @return 
     */
    public static EmailConfig getTestConfig() {
      return new EmailConfig(
        "smtp.gmail.com",
        "587",
        "ptcloudtech@gmail.com",
        "mnlzlohtbqvczuvp"
      );
    }  
    
}