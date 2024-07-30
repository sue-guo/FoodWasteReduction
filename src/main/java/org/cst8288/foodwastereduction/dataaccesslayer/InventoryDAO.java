/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.dataaccesslayer;

import java.util.List;
import org.cst8288.foodwastereduction.model.InventoryDTO;

/**
 *
 * @author WANG JIAYUN
 */
public interface InventoryDAO {
    
     
    List<InventoryDTO> getInventoriesByRetailerId(int retailerId);
    
    InventoryDTO getInventoryById(int inventoryID);
    
    void addInventory(InventoryDTO inventory);
    
    void updateInventory(InventoryDTO inventory);
    
    
}
