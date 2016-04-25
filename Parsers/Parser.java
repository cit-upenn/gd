import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class Parser {
	
	public static void main (String[] args) throws IOException {

        // error handling
        if (args.length != 2) {
            System.out.println("Please input two directories: 1. dir of the dictionary file; 2. dir to store the writeout.");
            return;
        }
		
        String fileName = args[0];                   // directory where the dictionary is stored
        
        FileWriter fw = new FileWriter(args[1]);     // directory to store the written file
        
        Set<String> words = new HashSet<String>();   // used to store the seen words. If seen before, do not write to the file

        String line = null;                          // read one line each time
        
        int ct = 0;                                  // count the words that matched the regex to verify with the dictionary
        int ctwritten = 0;                           // count the words that are written to the final file (repetition excluded)                     

        try {
        	
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            line = bufferedReader.readLine();
            
            while(line != null) {
            	if (line.matches("^[A-Z-]+")) {
            		System.out.println(line);
            		if (!words.contains(line)) {     // if the current word is unseen before, write it to the file and add it to the set.
            			words.add(line);
            			fw.write(line+"\n");         // words are separated by a newline char
            			ctwritten++;
            		}
            		
            		ct++;
                }
            	
                line = bufferedReader.readLine();
            }   

            System.out.println("#words written to the file (repetition excluded): "+ctwritten);
            System.out.println("#words in the original dictionary (repetition included): "+ct);
            
            bufferedReader.close();
            fw.close();
            
        } catch(FileNotFoundException ex) {
        	
            System.out.println( "Unable to open file '" + fileName + "'");
            
        } catch(IOException ex) {
        	
            System.out.println("Error reading file '" + fileName + "'");   
            
        }
	}
}
