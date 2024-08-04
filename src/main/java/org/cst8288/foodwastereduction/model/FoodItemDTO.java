package org.cst8288.foodwastereduction.model;

import org.cst8288.foodwastereduction.constants.CategoryEnum;

/**
 * Data Transfer Object (DTO) for FoodItem.
 * This class represents a food item with various attributes such as ID, retailer ID, name, description, category, brand, and unit.
 * It provides getter and setter methods to access and modify these attributes.
 * 
 * @author WANG JIAYUN
 */
public class FoodItemDTO {
    // Attributes of the FoodItemDTO
    private Integer foodItemId;
    private Integer retailerId;
    private String name;
    private String description;
    private CategoryEnum category;
    private String brand;
    private String unit;

    /**
     * Default constructor.
     * Initializes a new instance of the FoodItemDTO class with default values.
     */
    public FoodItemDTO() {}

    /**
     * Parameterized constructor.
     * Initializes a new instance of the FoodItemDTO class with specified values.
     * 
     * @param foodItemId the ID of the food item
     * @param retailerId the ID of the retailer
     * @param name the name of the food item
     * @param description the description of the food item
     * @param category the category of the food item
     * @param brand the brand of the food item
     * @param unit the unit of the food item
     */
    public FoodItemDTO(Integer foodItemId, Integer retailerId, String name, String description, CategoryEnum category, String brand, String unit) {
        this.foodItemId = foodItemId;
        this.retailerId = retailerId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.unit = unit;
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
     * Gets the name of the food item.
     * 
     * @return the name of the food item
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the food item.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the food item.
     * 
     * @return the description of the food item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the food item.
     * 
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the category of the food item.
     * 
     * @return the category of the food item
     */
    public CategoryEnum getCategory() {
        return category;
    }

    /**
     * Sets the category of the food item.
     * 
     * @param category the category to set
     */
    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    /**
     * Gets the brand of the food item.
     * 
     * @return the brand of the food item
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand of the food item.
     * 
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets the unit of the food item.
     * 
     * @return the unit of the food item
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the unit of the food item.
     * 
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
}