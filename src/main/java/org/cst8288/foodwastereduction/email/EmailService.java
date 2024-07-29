/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.email;

/**
 *
 * @author ryany
 */
public interface EmailService {
    String sendEmail(String to, String subject, String content);    
}
