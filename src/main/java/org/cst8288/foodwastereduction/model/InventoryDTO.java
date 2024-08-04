package org.cst8288.foodwastereduction.model;

import org.cst8288.foodwastereduction.constants.SurplusStatusEnum;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Data Transfer Object (DTO) for Inventory.
 * This class represents an inventory item with various attributes such as ID, retailer ID, food item ID, batch number, quantity, prices, dates, surplus status, and activity status.
 * It provides getter and setter methods to access and modify these attributes.
 * 
 * @author WANG JIAYUN
 */
public class InventoryDTO {
   
    // Attributes of the InventoryDTO
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

    /**
     * Default constructor.
     * Initializes a new instance of the InventoryDTO class with default values.
     */
    public InventoryDTO() {}

    /**
     * Parameterized constructor.
     * Initializes a new instance of the InventoryDTO class with specified values.
     * 
     * @param inventoryId the ID of the inventory item
     * @param retailerId the ID of the retailer
     * @param foodItemId the ID of the food item
     * @param batchNumber the batch number of the inventory item
     * @param quantity the quantity of the inventory item
     * @param regularPrice the regular price of the inventory item
     * @param discountRate the discount rate of the inventory item
     * @param expirationDate the expiration date of the inventory item
     * @param receiveDate the receive date of the inventory item
     * @param isSurplus whether the inventory item is surplus
     * @param surplusStatus the surplus status of the inventory item
     * @param isActive whether the inventory item is active
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

    /**
     * Gets the ID of the inventory item.
     * 
     * @return the inventory item ID
     */
    public Integer getInventoryId() {
        return inventoryId;
    }

    /**
     * Sets the ID of the inventory item.
     * 
     * @param inventoryId the inventory item ID to set
     */
    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    /**
     * Gets the ID of the retailer.
     * 
     * @return the retailer ID
     */
    public Integer getRetailerId() {
        return retailerId;
    }

    /**
     * Sets the ID of the retailer.
     * 
     * @param retailerId the retailer ID to set
     */
    public void setRetailerId(Integer retailerId) {
        this.retailerId = retailerId;
    }

    /**
     * Gets the ID of the food item.
     * 
     * @return the food item ID
     */
    public Integer getFoodItemId() {
        return foodItemId;
    }

    /**
     * Sets the ID of the food item.
     * 
     * @param foodItemId the food item ID to set
     */
    public void setFoodItemId(Integer foodItemId) {
        this.foodItemId = foodItemId;
    }

    /**
     * Gets the batch number of the inventory item.
     * 
     * @return the batch number
     */
    public String getBatchNumber() {
        return batchNumber;
    }

    /**
     * Sets the batch number of the inventory item.
     * 
     * @param batchNumber the batch number to set
     */
    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    /**
     * Gets the quantity of the inventory item.
     * 
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the inventory item.
     * 
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the regular price of the inventory item.
     * 
     * @return the regular price
     */
    public Double getRegularPrice() {
        return regularPrice;
    }

    /**
     * Sets the regular price of the inventory item.
     * 
     * @param regularPrice the regular price to set
     */
    public void setRegularPrice(Double regularPrice) {
        this.regularPrice = regularPrice;
    }

    /**
     * Gets the discount rate of the inventory item.
     * 
     * @return the discount rate
     */
    public Double getDiscountRate() {
        return discountRate;
    }

    /**
     * Sets the discount rate of the inventory item.
     * 
     * @param discountRate the discount rate to set
     */
    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    /**
     * Gets the expiration date of the inventory item.
     * 
     * @return the expiration date
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the expiration date of the inventory item.
     * 
     * @param expirationDate the expiration date to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Gets the receive date of the inventory item.
     * 
     * @return the receive date
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * Sets the receive date of the inventory item.
     * 
     * @param receiveDate the receive date to set
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * Gets whether the inventory item is surplus.
     * 
     * @return true if the inventory item is surplus, false otherwise
     */
    public Boolean getIsSurplus() {
        return isSurplus;
    }

    /**
     * Sets whether the inventory item is surplus.
     * 
     * @param isSurplus true if the inventory item is surplus, false otherwise
     */
    public void setIsSurplus(Boolean isSurplus) {
        this.isSurplus = isSurplus;
    }

    /**
     * Gets the surplus status of the inventory item.
     * 
     * @return the surplus status
     */
    public SurplusStatusEnum getSurplusStatus() {
        return surplusStatus;
    }

    /**
     * Sets the surplus status of the inventory item.
     * 
     * @param surplusStatus the surplus status to set
     */
    public void setSurplusStatus(SurplusStatusEnum surplusStatus) {
        this.surplusStatus = surplusStatus;
    }

    /**
     * Gets the last updated timestamp of the inventory item.
     * 
     * @return the last updated timestamp
     */
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Sets the last updated timestamp of the inventory item.
     * 
     * @param lastUpdated the last updated timestamp to set
     */
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Gets whether the inventory item is active.
     * 
     * @return true if the inventory item is active, false otherwise
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * Sets whether the inventory item is active.
     * 
     * @param isActive true if the inventory item is active, false otherwise
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}