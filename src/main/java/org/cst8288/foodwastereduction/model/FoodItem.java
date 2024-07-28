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
    private int foodItemID;
    private int retailerID;
    private String name;
    private String description;
    private FoodCategory category;
    private String brand;
    private String unit;

    // Default constructor
    public FoodItem() {}

    // Parameterized constructor
    public FoodItem(int foodItemID, int retailerID, String name, String description, FoodCategory category, String brand, String unit) {
        this.foodItemID = foodItemID;
        this.retailerID = retailerID;
        this.name = name;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.unit = unit;
    }

    // Getters and Setters
    public int getFoodItemID() {
        return foodItemID;
    }

    public void setFoodItemID(int foodItemID) {
        this.foodItemID = foodItemID;
    }

    public int getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(int retailerID) {
        this.retailerID = retailerID;
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
                "foodItemID=" + foodItemID +
                ", retailerID=" + retailerID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}

