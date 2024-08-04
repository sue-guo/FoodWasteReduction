package org.cst8288.foodwastereduction.constants;

/**
 * Enum representing the surplus status of food items.
 * This enum is used to indicate whether a food item is available for donation, discount, or has no surplus status.
 * 
 * @version 1.0
 * @since 2024-07-27
 * @author WANG JIAYUN
 * 
 */
public enum SurplusStatusEnum {
    /**
     * Indicates that the food item has no surplus status.
     */
    None,

    /**
     * Indicates that the food item is available for donation.
     */
    Donation,

    /**
     * Indicates that the food item is available at a discount.
     */
    Discount
}