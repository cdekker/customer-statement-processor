package org.dekker.csp.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.dekker.csp.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionValidatorTest {

    private TransactionValidator validator;

    @BeforeEach
    void setUp() {
        validator = new TransactionValidator();
    }

    @Test
    void validateCorrectAmount() {
        Transaction transaction = new Transaction();
        transaction.setReference("123");
        transaction.setDescription("Sample Description");
        transaction.setIban("NL91RABO0315273637");
        transaction.setStartBalance(BigDecimal.valueOf(10));
        transaction.setMutation(BigDecimal.valueOf(-2));
        transaction.setEndBalance(BigDecimal.valueOf(8));

        assertDoesNotThrow(() -> validator.validate(transaction));
    }

    @Test
    void validateInvalidAmount() {
        Transaction transaction = new Transaction();
        transaction.setReference("123");
        transaction.setDescription("Sample Description");
        transaction.setIban("NL91RABO0315273637");
        transaction.setStartBalance(BigDecimal.valueOf(10));
        transaction.setMutation(BigDecimal.valueOf(-2));
        transaction.setEndBalance(BigDecimal.valueOf(9));

        assertThrows(TransactionValidationException.class, () -> validator.validate(transaction));
    }

    @Test
    void validateDuplicateReference() {
        Transaction transaction = new Transaction();
        transaction.setReference("123");
        transaction.setDescription("Sample Description");
        transaction.setIban("NL91RABO0315273637");
        transaction.setStartBalance(BigDecimal.valueOf(10));
        transaction.setMutation(BigDecimal.valueOf(-2));
        transaction.setEndBalance(BigDecimal.valueOf(8));

        validator.validate(transaction);

        // Validate the same transaction twice. Should throw non-unique exception
        assertThrows(TransactionValidationException.class, () -> validator.validate(transaction));
    }
}