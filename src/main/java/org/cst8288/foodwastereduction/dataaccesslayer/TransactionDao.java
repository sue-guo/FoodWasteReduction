package org.cst8288.foodwastereduction.dataaccesslayer;

import org.cst8288.foodwastereduction.model.Transaction;

import java.util.List;

/**
 * Data Access Object (DAO) interface for managing transactions.
 * This interface defines the methods for interacting with the
 * transaction data store, including adding, retrieving, and
 * querying transactions.
 *
 * <p>Implementations of this interface should provide the actual
 * data access logic for transactions.</p>
 *
 * @author yaoyi
 */
public interface TransactionDao {

    /**
     * Adds a new transaction to the data store.
     *
     * <p>This method should insert a new {@link Transaction} record
     * into the data store.</p>
     *
     * @param transaction the {@link Transaction} object to be added
     */
    void addTransaction(Transaction transaction);

    /**
     * Retrieves a list of transactions for a specific user.
     *
     * <p>This method should query the data store to retrieve all
     * transactions associated with the specified user ID.</p>
     *
     * @param userID the ID of the user whose transactions are to be retrieved
     * @return a {@link List} of {@link Transaction} objects for the specified user
     */
    List<Transaction> getTransactionByUser(int userID);

    /**
     * Retrieves a transaction by its ID.
     *
     * <p>This method should query the data store to retrieve the
     * {@link Transaction} object with the specified transaction ID.</p>
     *
     * @param transactionID the ID of the transaction to be retrieved
     * @return the {@link Transaction} object with the specified ID, or {@code null}
     *         if no transaction with that ID exists
     */
    Transaction getTransactionById(int transactionID);
}

