/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.model;

import org.cst8288.foodwastereduction.constants.CategoryEnum;

/**
 *
 * @author ryany
 */
public class FoodItemDTO {
    private Integer foodItemId;
    private Integer retailerId;
    private String name;
    private String description;
    private CategoryEnum category;
    private String brand;
    private String unit;

    // Default constructor
    public FoodItemDTO() {}

    // Parameterized constructor
    public FoodItemDTO(Integer foodItemId, Integer retailerId, String name, String description, CategoryEnum category, String brand, String unit) {
        this.foodItemId = foodItemId;
        this.retailerId = retailerId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.unit = unit;
    }

    // Getters and Setters
    public Integer getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(Integer foodItemId) {
        this.foodItemId = foodItemId;
    }

    public Integer getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Integer retailerId) {
        this.retailerId = retailerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
