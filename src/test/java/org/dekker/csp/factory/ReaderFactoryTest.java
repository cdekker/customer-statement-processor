package org.dekker.csp.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;
import java.math.BigDecimal;
import java.util.stream.Stream;
import org.dekker.csp.model.Transaction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ReaderFactoryTest {

    private static final String XML_SAMPLE = "<record reference=\"130498\">\n" +
            "  <accountNumber>NL69ABNA0433647324</accountNumber>\n" +
            "  <description>Tickets for Peter Theuß</description>\n" +
            "  <startBalance>26.9</startBalance>\n" +
            "  <mutation>-18.78</mutation>\n" +
            "  <endBalance>8.12</endBalance>\n" +
            "</record>";

    private static final String CSV_SAMPLE = "Reference,AccountNumber,Description,Start Balance,Mutation,End Balance\n" +
            "130498,NL69ABNA0433647324,Tickets for Peter Theuß,26.9,-18.78,8.12";

    static Stream<Arguments> readerOptionsProvider() {
        return Stream.of(
                Arguments.of("foo.xml", XML_SAMPLE),
                Arguments.of("foo.csv", CSV_SAMPLE));
    }

    @ParameterizedTest
    @MethodSource("readerOptionsProvider")
    void getReader(String file, String sample) throws JsonProcessingException {
        ObjectReader reader = ReaderFactory.getReader(file);

        Transaction transaction = reader.readValue(sample);

        assertNotNull(transaction);

        assertEquals("130498", transaction.getReference());
        assertEquals("NL69ABNA0433647324", transaction.getIban());
        assertEquals("Tickets for Peter Theuß", transaction.getDescription());
        assertEquals(BigDecimal.valueOf(269, 1), transaction.getStartBalance());
        assertEquals(BigDecimal.valueOf(-1878, 2), transaction.getMutation());
        assertEquals(BigDecimal.valueOf(812, 2), transaction.getEndBalance());
    }
}