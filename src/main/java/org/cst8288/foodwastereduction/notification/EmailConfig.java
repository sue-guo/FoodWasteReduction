/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

/**
 *
 * @author ryany
 */
public class EmailConfig {
    private String host;
    private String port;
    private String username;
    private String password;

    public EmailConfig(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getHost() { return host; }
    public String getPort() { return port; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    
    /**
     * This static configuration is only used for testing, after testing, it should be deleted!!! by Ryan Xu
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