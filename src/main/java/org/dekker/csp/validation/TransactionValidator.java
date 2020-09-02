package org.dekker.csp.validation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.dekker.csp.model.Transaction;

/**
 * Stateful Transaction Validator responsible for validating individual {@link Transaction}
 * <p>
 * Currently implemented validations: Reference uniqueness, amount checksum
 * <p>
 * This validator keeps an internal state of all references previously seen to validate uniqueness.
 * Each {@link #validate(Transaction)} call validates the uniqueness of a reference in the context
 * of this validators lifetime.
 */
public class TransactionValidator {

    private final Set<String> references = new HashSet<>();

    /**
     * Validate a single transaction
     *
     * @param transaction to be validated
     * @throws TransactionValidationException whenever one of the validations fails
     */
    public void validate(Transaction transaction) {
        validateAmount(transaction);
        validateReferenceUniqueness(transaction);
    }

    private void validateReferenceUniqueness(Transaction transaction) {
        boolean unique = this.references.add(transaction.getReference());
        if (!unique) {
            throw new TransactionValidationException(
                    String.format("Transaction '%s': Reference ID is not unique",
                            transaction.getReference()));
        }
    }

    private void validateAmount(Transaction transaction) {
        BigDecimal expectedBalance = transaction.getStartBalance().add(transaction.getMutation());
        if (expectedBalance.compareTo(transaction.getEndBalance()) != 0) {
            throw new TransactionValidationException(
                    String.format("Transaction '%s': End Balance '%s' is invalid",
                            transaction.getReference(),
                            transaction.getEndBalance().toPlainString()));
        }
    }

}
