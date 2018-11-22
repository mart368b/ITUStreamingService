package reader;

import javax.annotation.processing.FilerException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Simple csv file reader
 * csv = comma seperated values
 */
public class CSVReader {

    // Indicate what character is used to seperate values
    public final static String SEPERATOR = ";";
    // Container for all rows of values
    protected List<String[]>  content;

    /**
     * Create a new CSVReader instance
     * @param url : csv file path
     * @param columnCount : specify how many columns the csv file should contain
     * @throws FileNotFoundException : If the file could not be found indicate a problem somewhere else
     */
    public CSVReader( String url, int columnCount) throws FileNotFoundException{
        this.content = new ArrayList<String[]>();

        File f = new File(url);
        // Test if file exists
        if ( !f.exists() ){
            throw new FileNotFoundException();
        }
        try {
            // Start reading from file
            BufferedReader reader = new BufferedReader(new FileReader(f));
            // Create a container for all rows that does not have the specified amount of columns
            ArrayList<String> failedRows = new ArrayList<>();

            String line;
            // Iterate over all lines in the file
            while ((line = reader.readLine()) != null) {
                // Split line into its different columns, using the specified seperator
                String[] lineContent = line.trim().split(SEPERATOR);
                for ( int i = 0; i < lineContent.length; i++ ){
                    lineContent[i] = lineContent[i].trim();
                }
                // Check that the row contains specified amount of columns
                if (lineContent.length != columnCount){
                    failedRows.add(line + "  " + lineContent.length);
                }else{
                    // To avoid errors during creation of objects
                    // Only lines that fullfil the criterier is used
                    content.add(lineContent);
                }
            }

            // For debugging purposes stop program if lines fail validation, and print them in a FileException
            if ( failedRows.size() > 0 ){
                String errorMsg = "File(url: " + url + ") contains wrong amount of columns in rows:";
                for ( String failedRow: failedRows ){
                    errorMsg += "\n" + failedRow;
                }
                throw new FilerException(errorMsg);
            }
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Returns a iterator, that iterates over all rows in the csv file
     */
    public Iterator<String[]> getIterator(){
        return content.iterator();
    }

}
