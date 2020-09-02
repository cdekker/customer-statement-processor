package org.dekker.csp;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.dekker.csp.model.Transaction;
import org.dekker.csp.validation.TransactionValidationException;
import org.dekker.csp.validation.TransactionValidator;

/**
 * Processor reading all {@link Transaction}s from the given {@link InputStream}, validating them and printing
 * any failures to the console.
 * <p>
 * Should be constructed with an {@link ObjectReader} matching the {@link InputStream}
 */
@RequiredArgsConstructor
public class CustomerStatementProcessor {

    private final ObjectReader reader;

    /**
     * Iterates over the {@link InputStream} of {@link Transaction}s, validates them and prints out any potential
     * validation errors.
     *
     * @param source input stream to be processed
     * @return map of {@link Transaction} and validation results
     * @throws IOException whenever the stream cannot be read
     */
    public Map<Transaction, Boolean> process(InputStream source) throws IOException {
        Objects.requireNonNull(source);

        TransactionValidator validator = new TransactionValidator();
        Map<Transaction, Boolean> transactions = new LinkedHashMap<>();

        MappingIterator<Transaction> iterator = reader.readValues(source);
        while (iterator.hasNextValue()) {
            JsonLocation location = iterator.getCurrentLocation();

            Transaction transaction = iterator.nextValue();

            try {
                validator.validate(transaction);
                transactions.put(transaction, true);
            } catch (TransactionValidationException e) {
                System.out.printf("Validation error at line %d: %s%n", location.getLineNr(), e.getMessage());
                transactions.put(transaction, false);
            }
        }

        return transactions;
    }
}
