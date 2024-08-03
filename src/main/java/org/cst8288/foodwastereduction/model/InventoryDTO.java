/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.model;

/**
 *
 * @author ryany
 */
import org.cst8288.foodwastereduction.constants.SurplusStatusEnum;
import java.sql.Date;
import java.sql.Timestamp;

public class InventoryDTO {
   
    private Integer inventoryId;
    private Integer retailerId;
    private Integer foodItemId;
    private String batchNumber;
    private Integer quantity;
    private Double regularPrice;
    private Double discountRate;
    private Date expirationDate;
    private Date receiveDate;
    private Boolean isSurplus;
    private SurplusStatusEnum surplusStatus;
    private Timestamp lastUpdated;
    private Boolean isActive;

    
    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Integer retailerId) {
        this.retailerId = retailerId;
    }

    public Integer getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(Integer foodItemId) {
        this.foodItemId = foodItemId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Boolean getIsSurplus() {
        return isSurplus;
    }

    public void setIsSurplus(Boolean isSurplus) {
        this.isSurplus = isSurplus;
    }

    public SurplusStatusEnum getSurplusStatus() {
        return surplusStatus;
    }

    public void setSurplusStatus(SurplusStatusEnum surplusStatus) {
        this.surplusStatus = surplusStatus;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public InventoryDTO(){}
    
    /**
     * 
     * @param inventoryId
     * @param retailerId
     * @param foodItemId
     * @param batchNumber
     * @param quantity
     * @param regularPrice
     * @param discountRate
     * @param expirationDate
     * @param receiveDate
     * @param isSurplus
     * @param surplusStatus
     * @param isActive 
     */
    public InventoryDTO(Integer inventoryId, Integer retailerId, Integer foodItemId, String batchNumber,
            Integer quantity, double regularPrice, double discountRate, Date expirationDate, 
            Date receiveDate, boolean isSurplus, SurplusStatusEnum surplusStatus, boolean isActive) {
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
        this.isActive = isActive;
    }
}
