/* File: DatetimeUtil.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This is a utility class for handling date and time conversions.
 *
 */
package org.cst8288.foodwastereduction.utility;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A utility class for handling date and time conversions.
 * This class provides methods to format timestamps into strings
 * based on a given pattern.
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
