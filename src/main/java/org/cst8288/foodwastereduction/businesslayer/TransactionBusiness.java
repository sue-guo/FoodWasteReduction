package org.cst8288.foodwastereduction.businesslayer;

import org.cst8288.foodwastereduction.dataaccesslayer.TransactionDao;
import org.cst8288.foodwastereduction.dataaccesslayer.TransactionDaoImpl;

import org.cst8288.foodwastereduction.model.*;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author yaoyi
 */
public class TransactionBusiness {

    private TransactionDao transactionDao = null;

    public void addTransaction(Transaction transaction) {
        transactionDao.addTransaction(transaction);
    }

    public List<Transaction> getTransactionByUser(int userID) {
        return transactionDao.getTransactionByUser(userID);
    }

}
