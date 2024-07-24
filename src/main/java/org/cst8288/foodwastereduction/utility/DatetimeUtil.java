/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.utility;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Hongxiu Guo
 */
public class DatetimeUtil {
    
    /**
     * This method is to convert timestamp to string by giving specific pattern
     * 
     * @param timestamp timestamp to convert
     * @param pattern pattern to convert
     * @return formatted date time in specific pattern
     */
    public static String formatTimestampAsString(Timestamp timestamp, String pattern){
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        
        return localDateTime.format(formatter);
    }
    
}
