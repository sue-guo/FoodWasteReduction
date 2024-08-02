/* File: Payment.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This class representing a payment record in the system.
 *
 */
package org.cst8288.foodwastereduction.model;

/**
 * Represents a payment record in the system.
 * This class encapsulates the details of a payment including the payment ID, transaction ID, payment type, amount,
 * credential (in JSON format), and creation timestamp.
 * 
 * @author Hongxiu Guo
 */
public class Payment {

    /** Unique identifier for the payment */
    private Integer paymentId;
    
    /** Identifier for the transaction associated with this payment */
    private Integer transactionId;
    
    /** Type of the payment (e.g., CHECK, CREDIT_CARD, APPLE_PAY, PAYPAL) */
    private PaymentType paymentType;
    
    /** Amount of money involved in the payment */
    private Double amount;
    
    /** Credentials related to the payment, stored in JSON format.
     * Different payment types will have different structures for this credential.
     */
    private String credential; 
    
    /** Timestamp when the payment was created */
    private String createAt;

    /**
     * Gets the unique identifier for the payment.
     * 
     * @return paymentId the unique identifier for the payment
     */
    public Integer getPaymentId() {
        return paymentId;
    }

    /**
     * Sets the unique identifier for the payment.
     * 
     * @param paymentId the unique identifier for the payment
     */
    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * Gets the identifier for the transaction associated with this payment.
     * 
     * @return transactionId the identifier for the transaction
     */
    public Integer getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the identifier for the transaction associated with this payment.
     * 
     * @param transactionId the identifier for the transaction
     */
    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Gets the type of the payment.
     * 
     * @return paymentType the type of the payment
     */
    public PaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the type of the payment.
     * 
     * @param paymentType the type of the payment
     */
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * Gets the amount of money involved in the payment.
     * 
     * @return amount the amount of money
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of money involved in the payment.
     * 
     * @param amount the amount of money
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * Gets the credentials related to the payment, stored in JSON format.
     * 
     * @return credential the payment credentials in JSON format
     */
    public String getCredential() {
        return credential;
    }

    /**
     * Sets the credentials related to the payment, stored in JSON format.
     * 
     * @param credential the payment credentials in JSON format
     */
    public void setCredential(String credential) {
        this.credential = credential;
    }

    /**
     * Gets the timestamp when the payment was created.
     * 
     * @return createAt the creation timestamp
     */
    public String getCreateAt() {
        return createAt;
    }

    /**
     * Sets the timestamp when the payment was created.
     * 
     * @param createAt the creation timestamp
     */
    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

}
