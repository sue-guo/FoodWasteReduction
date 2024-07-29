/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.model;

import org.cst8288.foodwastereduction.constants.FoodCategory;

/**
 *
 * @author ryany
 */
public class FoodItem {
    private int foodItemId;
    private int retailerId;
    private String name;
    private String description;
    private FoodCategory category;
    private String brand;
    private String unit;

    // Default constructor
    public FoodItem() {}

    // Parameterized constructor
    public FoodItem(int foodItemId, int retailerId, String name, String description, FoodCategory category, String brand, String unit) {
        this.foodItemId = foodItemId;
        this.retailerId = retailerId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.unit = unit;
    }

    // Getters and Setters
    public int getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(int foodItemId) {
        this.foodItemId = foodItemId;
    }

    public int getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(int retailerId) {
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

    public FoodCategory getCategory() {
        return category;
    }

    public void setCategory(FoodCategory category) {
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

    @Override
    public String toString() {
        return "FoodItemDTO{" +
                "foodItemId=" + foodItemId +
                ", retailerId=" + retailerId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}

