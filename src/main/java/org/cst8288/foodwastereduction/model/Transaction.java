package org.cst8288.foodwastereduction.model;

import java.sql.Timestamp;

/**
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

    /**
     * Getters and Setters
     */
    public Integer getTransactionID() {
        return transactionID;
    }
    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public Integer getInventoryID() {
        return inventoryID;
    }
    public void setInventoryID(Integer inventoryID) {
        this.inventoryID = inventoryID;
    }

    public Integer getUserID() {
        return userID;
    }
    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }


}
