package org.cst8288.foodwastereduction.dataaccesslayer;

import java.util.List;
import org.cst8288.foodwastereduction.model.InventoryDTO;

/**
 * Interface for Inventory Data Access Object.
 * This interface provides methods to perform CRUD operations on Inventory objects in the database.
 * 
 * @version 1.0
 * @since 2024-07-27
 * @author WNAG JIAYUN & YAO YI & Ryan Xu
 */
public interface InventoryDAO {

    /**
     * Retrieves a list of inventories for a specific retailer.
     * 
     * @param retailerId the ID of the retailer
     * @return a list of InventoryDTO objects
     */
    List<InventoryDTO> getInventoriesByRetailerId(int retailerId);

    /**
     * Retrieves an inventory by its ID.
     * 
     * @param inventoryID the ID of the inventory
     * @return the InventoryDTO object
     */
    InventoryDTO getInventoryById(int inventoryID);

    /**
     * Adds a new inventory to the database.
     * 
     * @param inventory the InventoryDTO object to be added
     */
    void addInventory(InventoryDTO inventory);

    /**
     * Updates an existing inventory in the database.
     * 
     * @param inventory the InventoryDTO object with updated information
     */
    void updateInventory(InventoryDTO inventory);

    /**
     * Retrieves a list of all inventories in the database.
     * 
     * @return a list of InventoryDTO objects
     */
    List<InventoryDTO> getAllInventories();
}