/* File: PaymentType.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This enum representing the various types of payment methods.
 *
 */
package org.cst8288.foodwastereduction.model;

/**
 * Enum representing the various types of payment methods.
 * This enum is used to classify the type of payment being processed.
 * 
 * Possible values include:
 * - CHECK: Payment made using a check
 * - CREDIT_CARD: Payment made using a credit card
 * - APPLE_PAY: Payment made using Apple Pay
 * - PAYPAL: Payment made using PayPal
 * 
 * @author Hongxiu Guo
 */
public enum PaymentType {
    
    /** Payment made using a check */
    CHECK,
    
    /** Payment made using a credit card */
    CREDIT_CARD,
    
    /** Payment made using Apple Pay */
    APPLE_PAY,
    
    /** Payment made using PayPal */
    PAYPAL
}