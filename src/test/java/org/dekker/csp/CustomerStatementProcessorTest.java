package org.dekker.csp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;
import org.dekker.csp.factory.ReaderFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CustomerStatementProcessorTest {

    static Stream<Arguments> processTestOptions() {
        return Stream.of(
                // In the XML entry #2 and #9 are invalid
                Arguments.of("records.xml", List.of(
                        "Validation error at line 9: Transaction '167875': End Balance '6368' is invalid\n",
                        "Validation error at line 58: Transaction '165102': End Balance '4981' is invalid\n")),
                // In the CSV entry #5 and #6 are invalid
                Arguments.of("records.csv", List.of(
                        "Validation error at line 6: Transaction '112806': Reference ID is not unique\n",
                        "Validation error at line 7: Transaction '112806': Reference ID is not unique\n")));
    }

    @ParameterizedTest
    @MethodSource("processTestOptions")
    void process(String file, List<String> expectedErrors) throws IOException {
        CustomerStatementProcessor processor = new CustomerStatementProcessor(ReaderFactory.getReader(file));

        InputStream source = this.getClass().getClassLoader().getResourceAsStream(file);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        processor.process(source, output);

        assertEquals(String.join("", expectedErrors), output.toString());
    }
}