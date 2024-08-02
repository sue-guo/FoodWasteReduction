package org.cst8288.foodwastereduction.businesslayer;

import org.cst8288.foodwastereduction.dataaccesslayer.TransactionDao;
import org.cst8288.foodwastereduction.dataaccesslayer.TransactionDaoImpl;

import org.cst8288.foodwastereduction.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the business logic for managing transactions.
 * This class handles transaction-related operations by interacting
 * with the data access layer through {@link TransactionDao}.
 * It provides methods to add a transaction and retrieve transactions
 * by user ID or transaction ID.
 *
 * @author yaoyi
 */
public class TransactionBusiness {

    /**
     * Data access object for performing operations on transactions.
     */
    private TransactionDao transactionDao = null;

    /**
     * List to store transactions in memory.
     */
    private List<Transaction> transactions;

    /**
     * Constructs a new instance of {@code TransactionBusiness}.
     * Initializes the {@code transactionDao} with an instance of
     * {@link TransactionDaoImpl} and creates an empty list for transactions.
     */
    public TransactionBusiness() {
        this.transactionDao = new TransactionDaoImpl();
        this.transactions = new ArrayList<>();
    }

    /**
     * Adds a transaction to the system.
     *
     * @param transaction the transaction to be added
     */
    public void addTransaction(Transaction transaction) {
        transactionDao.addTransaction(transaction);
    }

    /**
     * Retrieves a list of transactions associated with a specific user.
     *
     * @param userID the ID of the user whose transactions are to be retrieved
     * @return a list of transactions associated with the specified user
     */
    public List<Transaction> getTransactionByUser(int userID) {
        return transactionDao.getTransactionByUser(userID);
    }

    /**
     * Retrieves a transaction by its unique identifier.
     *
     * @param transactionID the ID of the transaction to be retrieved
     * @return the transaction with the specified ID, or {@code null} if not found
     */
    public Transaction getTransactionById(int transactionID) {
        return transactionDao.getTransactionById(transactionID);
    }

}
