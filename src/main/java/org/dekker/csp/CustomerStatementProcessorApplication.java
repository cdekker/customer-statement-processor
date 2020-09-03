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

        // We create the InputStream and matching ObjectReader separately based on file extension
        // In the real world, the InputStream can come from any source, not just files
        ObjectReader reader = ReaderFactory.getReader(fileName);
        CustomerStatementProcessor processor = new CustomerStatementProcessor(reader);

        try (InputStream source = CustomerStatementProcessorApplication.class.getClassLoader().getResourceAsStream(fileName)) {
            processor.process(source, System.out);
        }
    }
}
