package dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import merriam_webster_api.MerriamWebsterAPI;
import sqlite.SQLiteJDBC;
import sqlite.Word;
import trie.*;
import yandex_api.YandexAPI;

/**
 * This is the Model class for our dictionary, it contains all the data we need
 * 1. word auto-completion using our local words list stored in a trie tree 
 * 2. get the definitions of a word through local database SQLiteJDBC 
 * 3. get thesaurus by using MerriamWebsterAPI 
 * 4. get chinese translation by using YandexAPI 
 * 5. store and retrieve the user's personal wordsNote in SQLiteJDBC
 * 
 * @author liujue
 *
 */
public class Model {

	public Trie dict;

	/**
	 * Constructor called to initial the our data
	 */
	public Model() {
		dict = initData("words.txt");
	}

	/**
	 * This method reads a list of words and stored into a trie tree
	 * 
	 * @param filename
	 *            the word list file
	 * @return
	 */
	private Trie initData(String filename) {
		File file = new File(filename);
		Trie trieTree = new Trie();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				line = line.trim();
				if (line.equals(""))
					continue; // ignore possible blank lines
				trieTree.insert(line.toLowerCase());
			}
			reader.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return trieTree;
	}

	/**
	 * Get the words user previously added to the wordsNote
	 * 
	 * @return a list of words
	 */
	public ArrayList<String> getWordsNote() {
		return SQLiteJDBC.getWordsToLearn();
	}

	/**
	 * Add a word to wordsNote
	 * 
	 * @param word
	 *            an English word
	 */
	public void addToWordsNote(String word) {
		// wordsNote.add(word);
		SQLiteJDBC.addWordToLearn(word);
	}

	/**
	 * Remove a word from wordsNote
	 * 
	 * @param word
	 *            an English word
	 */
	public void removeFromWordsNote(String word) {
		// wordsNote.remove(word);
		SQLiteJDBC.removeWordToLearn(word);
	}

	/**
	 * Check a word if it's already in the wordsNote
	 * 
	 * @param word
	 *            an English word to check
	 * @return true or false
	 */
	public boolean isInWordsNote(String word) {
		// return wordsNote.contains(word);
		return SQLiteJDBC.hasWordToLearn(word);
	}

	/**
	 * Given a word, get the Chinese translation of it
	 * 
	 * @param english
	 *            English
	 * @return Chinese
	 */
	public String getChinese(String english) {
		return YandexAPI.chnTranslation(english);

	}

	/**
	 * Search the definitions of a word
	 * 
	 * @param str
	 *            an English word
	 * @return an object Word
	 */
	public Word searchWord(String str) {
		return SQLiteJDBC.selectFromDictionary(str);
	}

	/**
	 * Get thesaurus of a word
	 * 
	 * @param word
	 *            an English word
	 * @return a string in html format
	 */
	public String getThesaurus(String word) {
		return MerriamWebsterAPI.getThesaurusHtml(word);
	}

	// Test
	// public static void main(String[] args) {
	// Model m = new Model();
	//
	// ArrayList<String> words = m.dict.getWordsStartsWith("ele", 10);
	// for (String w : words) {
	// System.out.println(w);
	// }
	// }

}
