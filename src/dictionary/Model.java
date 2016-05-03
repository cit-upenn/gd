package dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Observable;

import sqlite.SQLiteJDBC;
import sqlite.Word;
import trie.*;
import yandex_api.YandexAPI;

public class Model {

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

	public ArrayList<String> getWordsNote() {
		return SQLiteJDBC.getWordsToLearn();
	}

	public void addToWordsNote(String word) {
		// wordsNote.add(word);
		SQLiteJDBC.addWordToLearn(word);
	}

	public void removeFromWordsNote(String word) {
		// wordsNote.remove(word);
		SQLiteJDBC.removeWordToLearn(word);
	}

	public boolean isInWordsNote(String word) {
		// return wordsNote.contains(word);
		return SQLiteJDBC.hasWordToLearn(word);
	}

	public String getChinese(String english) {
		return YandexAPI.chnTranslation(english);
		
	}
	// Test
	public static void main(String[] args) {
		Model m = new Model();

		ArrayList<String> words = m.dict.getWordsStartsWith("ele", 10);
		for (String w : words) {
			System.out.println(w);
		}
	}

}
