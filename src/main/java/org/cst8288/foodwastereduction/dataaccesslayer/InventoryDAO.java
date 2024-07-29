/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.dataaccesslayer;
import java.sql.SQLException;
import java.util.List;
import org.cst8288.foodwastereduction.model.Inventory;


/**
 *
 * @author ryany
 */
public interface InventoryDAO {
    void insert(Inventory inventory) throws SQLException;
    Inventory getById(int inventoryId) throws SQLException;
    List<Inventory> getAll() throws SQLException;
    void update(Inventory inventory) throws SQLException;
    void delete(int inventoryId) throws SQLException;
}
