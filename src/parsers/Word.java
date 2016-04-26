package parsers;

import java.util.ArrayList;

/**
 * The Word class wraps up a word's information including the word itself and
 * its definitions.
 * 
 * @author Qingxiao Dong
 */
public class Word {

	private String word;
	private ArrayList<String> definitions;

	/**
	 * Word constructor.
	 * 
	 * @param word
	 *            the word string
	 */
	public Word(String word) {
		this.word = word;
		definitions = new ArrayList<String>();
	}

	/**
	 * Returns the word string.
	 * 
	 * @return the word string
	 */
	public String toString() {
		return word;
	}

	/**
	 * The method adds a definition to a word.
	 * 
	 * @param def
	 *            definition of the word
	 */
	public void addDefinition(String def) {
		definitions.add(def);
	}

	/**
	 * The method returns all the definitions of the word.
	 * 
	 * @return the definitions of the word
	 */
	public ArrayList<String> getDefinitions() {
		return new ArrayList<String>(definitions);
	}

	/**
	 * The method prints a word and its definitions.
	 */
	public void print() {
		System.out.println(word);
		for (int i = 0; i < definitions.size(); i++) {
			System.out.println((i + 1) + ". " + definitions.get(i));
		}

	}

}