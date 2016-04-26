package dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import trie.*;

public class Model{

	public Trie dict;
	private String searchWord;
	
	
	public Model() {
		dict = initData("words.txt");
		searchWord = "";
	}
	
	private Trie initData(String filename) {
		File file = new File(filename);
		Trie trieTree = new Trie();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                line = line.trim();
                if (line.equals("")) continue; // ignore possible blank lines
                trieTree.insert(line.toLowerCase());
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return trieTree;
	}
	
	
	// Test
	public static void main(String[] args) {
		Model m = new Model();
		
        ArrayList<String> words = m.dict.getWordsStartsWith("ele");
        for (String w : words) {
            System.out.println(w);
        }
	}
	
	
}
