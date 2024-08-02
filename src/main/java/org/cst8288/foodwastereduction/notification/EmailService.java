/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

/**
 * This is interface of Email Service
 * @author Ryan Xu
 * Created on 2024-07-28
 */
public interface EmailService {
    
    /**
     * abstract method for send email
     * @param to
     * @param subject
     * @param content
     * @return 
     */
    String sendEmail(String to, String subject, String content);    
}
