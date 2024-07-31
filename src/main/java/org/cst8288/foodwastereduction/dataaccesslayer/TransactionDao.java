package org.cst8288.foodwastereduction.dataaccesslayer;

import org.cst8288.foodwastereduction.model.Transaction;

import java.util.List;

/**
 *
 * @author yaoyi
 */
public interface TransactionDao {

    void addTransaction(Transaction transaction);
    List<Transaction> getTransactionByUser(int userID);
    Transaction getTransactionById(int transactionID);
}

