package reader;

import debugging.Exceptions.MissingFileException;
import debugging.Exceptions.ResourceLoadingException;
import debugging.LogTypes;
import debugging.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Simple csv file reader
 * csv = comma separate values
 */
public class CSVReader {

    // Indicate what character is used to separate values
    private final static String SEPARATOR = ";";
    private int columnCount;
    // Container for all rows of values
    private List<String[]>  content;

    /**
     * Create a new CSVReader instance
     * @param url : csv file path
     * @param columnCount : specify how many columns the csv file should contain
     * @throws FileNotFoundException : If the file could not let the caller handle it
     */
    public CSVReader( String url, int columnCount) throws MissingFileException, ResourceLoadingException {
        this.content = new ArrayList<>();
        this.columnCount = columnCount;

        File f = new File(url);
        // maincomponents.AvMinArm if file exists
        if ( !f.exists() ){
            throw new MissingFileException(f, "csv");
        }
        Logger.log("Reading csv file: " + f.getAbsoluteFile());
        try {
            readFile(f);
        } catch (IOException e){
            throw new ResourceLoadingException(e);
        }
    }

    private void readFile(File f) throws IOException {
        // Start reading from file
        BufferedReader reader = new BufferedReader(new FileReader(f));
        // Create a container for all rows that does not have the specified amount of columns
        ArrayList<String> failedRows = new ArrayList<>();

        String line;
        // Iterate over all lines in the file
        while ((line = reader.readLine()) != null) {
            if (line.length() == 0){
                continue;
            }
            // Split line into its different columns, using the specified separator
            String[] lineContent = line.trim().split(SEPARATOR);
            for ( int i = 0; i < lineContent.length; i++ ){
                lineContent[i] = lineContent[i].trim();
            }
            // Check that the row contains specified amount of columns
            if (lineContent.length != columnCount){
                failedRows.add(line + "  " + lineContent.length);
            }else{
                // To avoid errors during creation of objects
                // Only lines that fulfil the criteria is used
                content.add(lineContent);
            }
        }

        if ( failedRows.size() > 0 ){
            StringBuilder errorMsg = new StringBuilder(" Found " + failedRows.size() + " rows with wrong number of columns");
            for ( String failedRow: failedRows ){
                errorMsg.append("\n    ").append(failedRow);
            }
            Logger.log(errorMsg.toString(), LogTypes.SOFTERROR);
        }
        Logger.log("Found " + content.size() + " valid rows in file");
        reader.close();
    }

    /**
     * Returns a iterator, that iterates over all rows in the csv file
     */
    public Iterator<String[]> getIterator(){
        return content.iterator();
    }

}
