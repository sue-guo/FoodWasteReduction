package org.cst8288.foodwastereduction.model;

/**
 * Enum representing the type of transactions.
 *
 * <p>This enumeration defines the different types of transactions:</p>
 *
 * <ul>
 *   <li>{@link #Purchase}: A transaction where items are for sale with a discounted price.</li>
 *   <li>{@link #Donation}: A transaction where items are for claim.</li>
 * </ul>
 *
 * @author yaoyi
 */
public enum TransactionType {
    Purchase, Donation
}