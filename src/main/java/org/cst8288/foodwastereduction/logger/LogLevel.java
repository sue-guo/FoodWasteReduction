/* File: LogLevel.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This Enum to represent various levels of logging.
 *
 */
package org.cst8288.foodwastereduction.logger;

/**
 * Enum to represent various levels of logging.
 * @author Hongxiu Guo
 */
public enum LogLevel {
    TRACE(1),// Very detailed information, typically of interest only when diagnosing problems
    DEBUG(2),// Detailed information on the flow through the system
    INFO(3), // Interesting runtime events (startup/shutdown)
    WARN(4), // Some warn messages or unexpected conditions
    ERROR(5);// Other runtime errors or unexpected conditions

    final int level;
    /**
     * Constructor for LogLevel enum.
     * 
     * @param level the integer value associated with the log level
     */
    LogLevel(int level) {
        this.level = level;
    }
}
