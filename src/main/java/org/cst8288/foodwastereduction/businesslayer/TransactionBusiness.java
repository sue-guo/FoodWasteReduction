package org.cst8288.foodwastereduction.businesslayer;

import org.cst8288.foodwastereduction.dataaccesslayer.TransactionDao;
import org.cst8288.foodwastereduction.dataaccesslayer.TransactionDaoImpl;

import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.model.Transaction;
import org.cst8288.foodwastereduction.model.TransactionType;

import java.sql.SQLException;

/**
 *
 * @author yaoyi
 */
public class TransactionBusiness{
    private final TransactionDao transactionDao = new TransactionDaoImpl();
    private final InventoryBusiness inventoryBusiness = new InventoryBusiness();

    public void purchaseInventory(Integer inventoryID, Integer userID, Integer quantity) throws SQLException {
        InventoryDTO item = (InventoryDTO) inventoryBusiness.getInventoryById(inventoryID);
        if (item.getQuantity() < quantity) {
            throw new RuntimeException("Exceeds maximum quantity");
        }
        item.setQuantity(item.getQuantity() - quantity);
        inventoryBusiness.updateInventory(item);
        Transaction transaction = new Transaction();
        transaction.setInventoryID(inventoryID);
        transaction.setUserID(userID);
        transaction.setQuantity(quantity);
        transaction.setTransactionType(TransactionType.Purchase);
        transactionDao.addTransaction(transaction);
    }

    public void claimInventory(Integer inventoryID, Integer userID) throws SQLException {
        InventoryDTO item = (InventoryDTO) inventoryBusiness.getInventoryById(inventoryID);

        item.setQuantity(0);
        inventoryBusiness.updateInventory(item);
        Transaction transaction = new Transaction();
        transaction.setInventoryID(inventoryID);
        transaction.setUserID(userID);
        transaction.setQuantity(item.getQuantity());
        transaction.setTransactionType(TransactionType.Donation);
        transactionDao.addTransaction(transaction);
    }
}
