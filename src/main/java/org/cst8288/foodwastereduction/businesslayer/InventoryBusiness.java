/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.businesslayer;

import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.dataaccesslayer.InventoryDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.InventoryDAOImpl;
import org.cst8288.foodwastereduction.model.InventoryDTO;

/**
 *
 * @author Carri
 */
public class InventoryBusiness {
    
    
    private InventoryDAO inventoryDAO = null;
    private List<InventoryDTO> inventories = new ArrayList<>();
    private InventoryDTO inventory = new InventoryDTO();
    
    public InventoryBusiness() {
        inventoryDAO = new InventoryDAOImpl();
    }

    public List<InventoryDTO> getFoodItemsByRetailerID(int RetailerID) {
      
        return inventories;
        
    }

    public InventoryDTO getInventoryByID(int InventoryID) {
        
       return inventory;
        
    }

    
    public void addInventory (InventoryDTO inventory) {
        // userDao.addUser(user);
    }
    
    
    
}
