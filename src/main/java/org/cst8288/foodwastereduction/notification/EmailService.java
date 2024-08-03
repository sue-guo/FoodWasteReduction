package org.cst8288.foodwastereduction.notification;

/**
 * File: EmailService.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-28
 * Modified: 2024-08-03
 * Description: The interface of Email Service
 */
public interface EmailService {
    
    /**
     * abstract method for send email
     * @param to
     * @param subject
     * @param content
     * @return string type
     */
    String sendEmail(String to, String subject, String content);    
}
