package org.dekker.csp.validation;

/**
 * Exception thrown whenever a validation on a transaction fails
 */
public class TransactionValidationException extends RuntimeException {
    public TransactionValidationException(String msg) {
        super(msg);
    }
}
