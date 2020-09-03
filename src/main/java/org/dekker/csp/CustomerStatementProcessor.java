package org.dekker.csp;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.dekker.csp.model.Transaction;
import org.dekker.csp.validation.TransactionValidationException;
import org.dekker.csp.validation.TransactionValidator;

/**
 * Processor reading all {@link Transaction}s from the given {@link InputStream}, validating them and printing
 * any failures to the provided {@link OutputStream}
 * <p>
 * Should be constructed with an {@link ObjectReader} matching the {@link InputStream}
 */
@RequiredArgsConstructor
public class CustomerStatementProcessor {

    private final ObjectReader reader;

    /**
     * Iterates over the {@link InputStream} of {@link Transaction}s, validates them and prints out any potential
     * validation errors in the provided {@link OutputStream}
     *
     * @param source input stream to be processed
     * @param sink   output stream to receive the validation errors
     * @throws IOException whenever the stream cannot be read
     */
    public void process(InputStream source, OutputStream sink) throws IOException {
        Objects.requireNonNull(source);

        TransactionValidator validator = new TransactionValidator();

        MappingIterator<Transaction> iterator = reader.readValues(source);
        while (iterator.hasNextValue()) {
            JsonLocation location = iterator.getCurrentLocation();

            Transaction transaction = iterator.nextValue();

            try {
                validator.validate(transaction);
            } catch (TransactionValidationException e) {
                sink.write(String.format("Validation error at line %d: %s%n",
                        location.getLineNr(), e.getMessage()).getBytes());
            }
        }
    }
}
