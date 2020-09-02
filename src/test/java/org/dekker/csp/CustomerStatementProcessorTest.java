package org.dekker.csp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.dekker.csp.factory.ReaderFactory;
import org.dekker.csp.model.Transaction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CustomerStatementProcessorTest {

    static Stream<Arguments> processTestOptions() {
        return Stream.of(
                // In the XML entry #2 and #9 are invalid
                Arguments.of("records.xml", List.of(true, false, true, true, true, true, true, true, false, true)),
                // In the CSV entry #5 and #6 are invalid
                Arguments.of("records.csv", List.of(true, true, true, true, false, false, true, true, true, true)));
    }

    @ParameterizedTest
    @MethodSource("processTestOptions")
    void process(String file, List<Boolean> expectedValids) throws IOException {
        CustomerStatementProcessor processor = new CustomerStatementProcessor(ReaderFactory.getReader(file));

        InputStream source = this.getClass().getClassLoader().getResourceAsStream(file);

        Map<Transaction, Boolean> results = processor.process(source);

        assertEquals(expectedValids, List.copyOf(results.values()));
    }
}