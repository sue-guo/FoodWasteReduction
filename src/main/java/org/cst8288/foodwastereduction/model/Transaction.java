package org.cst8288.foodwastereduction.model;

import java.sql.Date;


/**
 * Represents a transaction in the system.
 *
 * <p>This class encapsulates the details of a transaction, including
 * information about the transaction ID, inventory item, user, quantity,
 * type of transaction, and date. It is used to track and manage
 * transactions within the system.</p>
 *
 * @author yaoyi
 */
public class Transaction {
    private Integer transactionID;
    private Integer inventoryID;
    private Integer userID;
    private Integer quantity;
    private TransactionType transactionType;
    private String transactionDate;
    private String payStatus;
    private String foodItem;
    private String retailer;
    private Double regularPrice;
    private Double totalAmount;
    private String expirationDate;
    
    

    /**
     * Gets the unique identifier of the transaction.
     *
     * @return the transaction ID
     */
    public Integer getTransactionID() {
        return transactionID;
    }
    /**
     * Sets the unique identifier of the transaction.
     *
     * @param transactionID the transaction ID to set
     */
    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    /**
     * Gets the identifier of the inventory item associated with the transaction.
     *
     * @return the inventory ID
     */
    public Integer getInventoryID() {
        return inventoryID;
    }
    /**
     * Sets the identifier of the inventory item associated with the transaction.
     *
     * @param inventoryID the inventory ID to set
     */
    public void setInventoryID(Integer inventoryID) {
        this.inventoryID = inventoryID;
    }

    /**
     * Gets the identifier of the user associated with the transaction.
     *
     * @return the user ID
     */
    public Integer getUserID() {
        return userID;
    }
    /**
     * Sets the identifier of the user associated with the transaction.
     *
     * @param userID the user ID to set
     */
    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    /**
     * Gets the quantity of items involved in the transaction.
     *
     * @return the quantity of items
     */
    public Integer getQuantity() {
        return quantity;
    }
    /**
     * Sets the quantity of items involved in the transaction.
     *
     * @param quantity the quantity of items to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the type of transaction (e.g., Purchase or Donation).
     *
     * @return the transaction type
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }
    /**
     * Sets the type of transaction (e.g., Purchase or Donation).
     *
     * @param transactionType the transaction type to set
     */
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Gets the date and time when the transaction occurred.
     *
     * @return the transaction date as a {@code String}
     */
    public String getTransactionDate() {
        return transactionDate;
    }
    /**
     * Sets the date and time when the transaction occurred.
     *
     * @param transactionDate the transaction date to set, formatted as a {@code String}
     */
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
    /**
     * Retrieves the current payment status.
     *
     * @return The current payment status as a String.
     */
    public String getPayStatus() {
        return payStatus;
    }
    /**
     * Sets the payment status.
     *
     * @param payStatus The new payment status to be set.
     */
    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public Double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    

}
