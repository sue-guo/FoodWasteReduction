/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.businesslayer;

import org.cst8288.foodwastereduction.dataaccesslayer.InventoryDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.InventoryDAOImpl;
import org.cst8288.foodwastereduction.model.InventoryDTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WANG JIAYUN
 */
public class InventoryBusiness {

    private InventoryDAO inventoryDAO;
    private List<InventoryDTO> inventories;

    public InventoryBusiness() {
        this.inventoryDAO = new InventoryDAOImpl();
        this.inventories = new ArrayList<>();
    }

    public List<InventoryDTO> getInventoriesByRetailerId(int retailerId) {
        return inventoryDAO.getInventoriesByRetailerId(retailerId);
    }

    public Object getInventoryById(int inventoryID) {
        return inventoryDAO.getInventoryById(inventoryID);
    }

    public void addInventory(InventoryDTO inventory) {
        inventoryDAO.addInventory(inventory);
    }

    public void updateInventory(InventoryDTO inventory) {
        inventoryDAO.updateInventory(inventory);
    }

    public List<InventoryDTO> getAllInventories() {
        return inventoryDAO.getAllInventories();
    }
}