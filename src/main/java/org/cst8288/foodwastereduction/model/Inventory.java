/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.model;

/**
 *
 * @author ryany
 */
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.cst8288.foodwastereduction.constants.SurplusStatus;

public class Inventory {
    private int inventoryId;
    private int retailerId;
    private int foodItemId;
    private String batchNumber;
    private int quantity;
    private double regularPrice;
    private double discountRate;
    private LocalDate expirationDate;
    private LocalDate receiveDate;
    private boolean isSurplus;
    private SurplusStatus surplusStatus = SurplusStatus.NONE;
    private Timestamp lastUpdated;
    private boolean isActive;
    


    // Default constructor
    public Inventory() {}

    // Parameterized constructor
    public Inventory(int inventoryId, int retailerId, int foodItemId, String batchNumber,
            int quantity, double regularPrice, double discountRate, LocalDate expirationDate, 
            LocalDate receiveDate, boolean isSurplus, SurplusStatus surplusStatus, boolean isActive) {
        this.inventoryId = inventoryId;
        this.retailerId = retailerId;
        this.foodItemId = foodItemId;
        this.batchNumber = batchNumber;
        this.quantity = quantity;
        this.regularPrice = regularPrice;
        this.discountRate = discountRate;
        this.expirationDate = expirationDate;
        this.receiveDate = receiveDate;
        this.isSurplus = isSurplus;
        this.surplusStatus = surplusStatus;
        this.lastUpdated = lastUpdated;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(int retailerId) {
        this.retailerId = retailerId;
    }

    public int getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(int foodItemId) {
        this.foodItemId = foodItemId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
    }

    public boolean isSurplus() {
        return isSurplus;
    }

    public void setSurplus(boolean isSurplus) {
        this.isSurplus = isSurplus;
    }

    public SurplusStatus getSurplusStatus() {
        return surplusStatus;
    }

    public void setSurplusStatus(SurplusStatus surplusStatus) {
        this.surplusStatus = surplusStatus;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryID=" + inventoryId +
                ", retailerID=" + retailerId +
                ", foodItemID=" + foodItemId +
                ", batchNumber='" + batchNumber + '\'' +
                ", quantity=" + quantity +
                ", regularPrice=" + regularPrice +
                ", discountRate=" + discountRate +
                ", expirationDate=" + expirationDate +
                ", receiveDate=" + receiveDate +
                ", isSurplus=" + isSurplus +
                ", surplusStatus=" + surplusStatus +
                ", lastUpdated=" + lastUpdated +
                ", isActive=" + isActive +
                '}';
    }
    
    public void checkAndSetSurplus() {
        LocalDate now = LocalDate.now();
        long daysUntilExpiration = ChronoUnit.DAYS.between(now, expirationDate);
        if (daysUntilExpiration <= 7 && isActive) {
            this.isSurplus = true;
        }
    }    
}
