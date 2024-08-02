/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

/**
 * This class is used to make EMAIL SMTP configuration
 * @author Ryan Xu
 * Created on 2024-07-28
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

    // Getters
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