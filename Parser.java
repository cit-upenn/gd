import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Parser {
	public static void main (String[] args) {
	    // The name of the file to open.
        String fileName = "/Users/Yujie/Downloads/pg29765.txt";

        // This will reference one line at a time
        String line = null;
        // count the words that matched the regex to verify with the txt file
        int ct = 0;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bR = new BufferedReader(fileReader);

            line = bR.readLine();
            
            while(line != null) {
            	if (line.matches("^[A-Z-]+")) {
            		System.out.println(line);
            		ct++;
                }
                line = bR.readLine();
            }   

            System.out.println("######"+ct);
            
            bR.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
        }
	}
}
