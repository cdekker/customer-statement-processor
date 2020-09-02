package org.dekker.csp;

import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.io.InputStream;
import org.dekker.csp.factory.ReaderFactory;

public final class CustomerStatementProcessorApplication {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Please provide the input file name or path");
            System.exit(1);
        }

        String fileName = args[0];

        ObjectReader reader = ReaderFactory.getReader(fileName);

        CustomerStatementProcessor processor = new CustomerStatementProcessor(reader);

        try (InputStream source = CustomerStatementProcessorApplication.class.getClassLoader().getResourceAsStream(fileName)) {
            processor.process(source);
        }
    }
}
