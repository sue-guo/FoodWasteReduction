/* File: UserType.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This Enum representing the different types of users in the Food Waste Reduction platform.
 *
 */
package org.cst8288.foodwastereduction.model;

/**
 * Enum representing the different types of users in the Food Waste Reduction platform.
 * This enum is used to categorize users and manage their roles within the application.
 * 
 * The user types are:
 * <ul>
 *   <li>{@link #RETAILER} - Represents a retailer user who can list items for sale.</li>
 *   <li>{@link #CONSUMER} - Represents a consumer user who can purchase items.</li>
 *   <li>{@link #CHARITABLE_ORGANIZATION} - Represents a charitable organization user who can receive donations.</li>
 * </ul>
 * 
 * Example usage:
 * <pre>
 *     UserType userType = UserType.RETAILER;
 *     switch (userType) {
 *         case RETAILER:
 *             // Handle retailer-specific logic
 *             break;
 *         case CONSUMER:
 *             // Handle consumer-specific logic
 *             break;
 *         case CHARITABLE_ORGANIZATION:
 *             // Handle charitable organization-specific logic
 *             break;
 *     }
 * </pre>
 * @author Hongxiu Guo
 */
public enum UserType {
    /**
     * Represents a retailer user who can list items for sale.
     */
    RETAILER,
    /**
     * Represents a consumer user who can purchase items.
     */
    CONSUMER, 
    /**
     * Represents a charitable organization user who can receive donations.
     */
    CHARITABLE_ORGANIZATION
}
