package org.dekker.csp.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.math.BigDecimal;
import lombok.Data;

/**
 * POJO representing a single transaction, deserialized from either CSV or XML
 */
@Data
@JacksonXmlRootElement(localName = "record")
@JsonPropertyOrder({"reference", "iban", "description", "startBalance", "mutation", "endBalance"})
public class Transaction {
    @JacksonXmlProperty(localName = "reference", isAttribute = true)
    private String reference;
    @JacksonXmlProperty(localName = "accountNumber")
    private String iban;
    private String description;

    private BigDecimal startBalance;
    private BigDecimal mutation;
    private BigDecimal endBalance;
}
