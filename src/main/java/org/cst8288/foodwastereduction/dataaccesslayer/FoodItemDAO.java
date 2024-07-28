/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.dataaccesslayer;
import java.sql.SQLException;
import java.util.List;
import org.cst8288.foodwastereduction.model.FoodItem;


/**
 *
 * @author ryany
 */
public interface FoodItemDAO {
    void insert(FoodItem foodItem) throws SQLException;
    FoodItem getById(int foodItemID) throws SQLException;
    List<FoodItem> getAll() throws SQLException;
    List<FoodItem> getByRetailer(int retailerID) throws SQLException;
    void update(FoodItem foodItem) throws SQLException;
    void delete(int foodItemID) throws SQLException;
}