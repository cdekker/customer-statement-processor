package org.dekker.csp.factory;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.dekker.csp.model.Transaction;

/**
 * Factory responsible for creating the correct {@link ObjectReader} for the given input file type
 * Supports XML and CSV
 */
public class ReaderFactory {
    public static ObjectReader getReader(String fileName) {

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        switch (extension) {
            case "xml":
                return new XmlMapper().readerFor(Transaction.class);
            case "csv":
                CsvMapper mapper = new CsvMapper();
                // We define the schema in the Transaction POJO. Ignore the header
                CsvSchema schema = mapper.schemaFor(Transaction.class).withHeader();
                return mapper.readerFor(Transaction.class).with(schema);
            default:
                throw new UnsupportedOperationException(String.format("File type not supported: %s", extension));
        }
    }
}
