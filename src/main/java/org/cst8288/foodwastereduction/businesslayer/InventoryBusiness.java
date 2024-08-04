package org.cst8288.foodwastereduction.businesslayer;

import org.cst8288.foodwastereduction.dataaccesslayer.InventoryDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.InventoryDAOImpl;
import org.cst8288.foodwastereduction.model.InventoryDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Business layer class for managing inventories.
 * This class interacts with the data access layer to perform CRUD operations on inventories.
 * 
 * @see org.cst8288.foodwastereduction.dataaccesslayer.InventoryDAO
 * @see org.cst8288.foodwastereduction.model.InventoryDTO
 * @see org.cst8288.foodwastereduction.dataaccesslayer.InventoryDAOImpl
 * 
 * @version 1.0
 * @since 2024-07-27
 * @author WANG JIAYUN
 * 
 */
public class InventoryBusiness {

    private InventoryDAO inventoryDAO;
    private List<InventoryDTO> inventories;

    /**
     * Default constructor that initializes the InventoryDAO implementation and the inventories list.
     */
    public InventoryBusiness() {
        this.inventoryDAO = new InventoryDAOImpl();
        this.inventories = new ArrayList<>();
    }

    /**
     * Retrieves a list of inventories associated with a specific retailer ID.
     * 
     * @param retailerId the ID of the retailer whose inventories are to be retrieved
     * @return a list of InventoryDTO objects associated with the specified retailer ID
     */
    public List<InventoryDTO> getInventoriesByRetailerId(int retailerId) {
        return inventoryDAO.getInventoriesByRetailerId(retailerId);
    }

    /**
     * Retrieves an inventory by its ID.
     * 
     * @param inventoryID the ID of the inventory to be retrieved
     * @return the InventoryDTO object representing the inventory with the specified ID
     */
    public InventoryDTO getInventoryById(int inventoryID) {
        return inventoryDAO.getInventoryById(inventoryID);
    }

    /**
     * Adds a new inventory to the database.
     * 
     * @param inventory the InventoryDTO object representing the inventory to be added
     */
    public void addInventory(InventoryDTO inventory) {
        inventoryDAO.addInventory(inventory);
    }

    /**
     * Updates an existing inventory in the database.
     * 
     * @param inventory the InventoryDTO object representing the inventory to be updated
     */
    public void updateInventory(InventoryDTO inventory) {
        inventoryDAO.updateInventory(inventory);
    }

    /**
     * Retrieves a list of all inventories from the database.
     * 
     * @return a list of all InventoryDTO objects
     */
    public List<InventoryDTO> getAllInventories() {
        return inventoryDAO.getAllInventories();
    }
}