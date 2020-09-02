# Customer Statement Processor

## Assignment

Our bank receives monthly deliveries of customer statement records. 
This information is delivered in two formats, CSV and XML. 
These records need to be validated.

### Input

The format of the file is a simplified format of the MT940 format. The format is as follows:

* Transaction reference
* Account number
* Start Balance
* Mutation
* Description
* End Balance

### Output

There are two validations:

* all transaction references should be unique
* the end balance needs to be validated

At the end of the processing, a report needs to be created which will display both the transaction reference and description of each of the failed records.

## Solution

The application takes a single argument, the file path of the to be processed file, as a command line input.

Based on the file extension (.csv or .xml) the correct object parser is created in the `ReaderFactory`. Any other unsupported file extension leads to an error.

The `CustomerStatementProcessor` is created with this object parser and provided an input stream of the given input file.

Each `Transaction` is validated in the `TransactionValidator` with the given constraints. Each failure is printed in the console as it is encountered.

See `CustomerStatementProcessorTest` for a sample execution of the provided data.
