import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class DefinitionParser {
		
	public static void main (String[] args) {
		
		// error handling
		if (args.length != 1) {
			System.out.println("Please input one directory: dir of the dictionary file.");
			return;
		}
		
        String fileName = args[0];                               // directory where the dictionary is stored
        
        Map<String, Word> words = new HashMap<String, Word>();   // hashmap to store the string-word pair

        String line = null;                                      // read one line each time
        
        String currentWord = null;                               // the current vocab entry we are looking at (work as a pointer)

        try {
        	
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            line = bufferedReader.readLine();
            
            Word current = null;                                 // the word obj of the current vocab (work as a pointer)
            
            while(line != null) {
            	if (line.matches("^[A-Z-]+")) {                  // first step: match the current vocab
            		
            		currentWord = line;
            		current = words.get(currentWord);            // try to get the corresponding word obj of the current entry
            		if (current == null) {                       // if it dne in our hashmap, 
            			Word newWord = new Word(currentWord);    // generate a word obj for it,
            			words.put(currentWord, newWord);         // and update our hashmap 
            			current = newWord;                       // update the current word obj pointer
            		}
            		
                } else if (line.matches("^[0-9]{1,2}[. (]{3}[^\n]+")) {
                	                                             // if matches pattern such as: 1. (law), skip it.  
                } else if (line.matches("^[0-9]{1,2}[.][^\n]+")) { 
                												 // second step: match the defn that starts with number.
                	String definition = "";
                	while (line.matches("[^\n]+")) {             // concatenate the sentences in that defn together,
                		definition += line + " ";                // since even inside one defn, parts are separated by \n.
                		line = bufferedReader.readLine();        // keep concatenating until we see a blank line.
                	}
                	
                	// System.out.println("[Number Def] " + definition); // for debugging
                	
                	int indexOfSpace = definition.indexOf(" ");  // find the first occurrence of " "
                	String trimmedDefn = definition.substring(indexOfSpace+1);
                	current.addDefinition(trimmedDefn);          // add this defn to the current word's defn list
                	
                }  else if (line.matches("^Defn:[^\n]+")) {      // third step: match the defn that starts with "Defn".
                	
                	String definition = "";                   
                	while (line.matches("[^\n]+")) {             // same as above
                		definition += line;
                		line = bufferedReader.readLine();
                	}
                	
                	// System.out.println("[Number Def] " + definition);
                	current.addDefinition(definition);
                	
                }
            	
                line = bufferedReader.readLine();    
            }   
            
            bufferedReader.close();
            
        } catch(FileNotFoundException ex) {
        	
            System.out.println( "Unable to open file '" + fileName + "'");
            
        } catch(IOException ex) {
        	
            System.out.println("Error reading file '" + fileName + "'");   
            
        }
        
        for (String word : words.keySet()) {
        	SQLiteJDBC.insertIntoDictionary(words.get(word));
        }

	}

}
