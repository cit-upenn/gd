package dictionary;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import sqlite.Word;

/**
 * This is the View class thath displays what information the user needed from
 * the model in a html format.
 * 
 * @author liujue
 *
 */
public class View extends Panel {

	private Model model;
	private DefaultListModel<String> listModel;
	private JList<String> wordsList;
	private JTextPane textPane;
	private String selectedWord;
	private ArrayList<String> candidateWords;
	private JScrollPane textScroller;
	private String initialText;

	/**
	 * Constructor of view class initialize the model and all the components
	 * 
	 * @param model
	 */
	public View(Model model) {
		this.model = model;
		listModel = new DefaultListModel<String>();
		listModel.addElement("         ");
		selectedWord = " ";
		wordsList = new JList<String>(listModel);
		wordsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		wordsList.setLayoutOrientation(JList.VERTICAL);
		wordsList.setSelectedIndex(0);
		// wordsList.addListSelectionListener(this);
		wordsList.setVisibleRowCount(-1);
		wordsList.setFixedCellWidth(100);
		// wordsList.setBorder(
		// BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
		// BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		// listScroller = new JScrollPane(wordsList);
		// listScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		// listScroller.setPreferredSize(new Dimension(500, 500));
		textPane = new JTextPane();
		initialText = readFile("welcome.html", Charset.forName("UTF-8"));
		textPane.setContentType("text/html");
		textPane.setText(initialText);
		textPane.setEditable(false);
		textScroller = new JScrollPane(textPane);
		candidateWords = new ArrayList<String>();

		layOutComponents();

	}

	/**
	 * Getter of selectedWord
	 * 
	 * @return
	 */
	public String getSelectedWord() {
		return selectedWord;
	}

	/**
	 * Getter of wordsList
	 * 
	 * @return
	 */
	public JList<String> getWordsList() {
		return wordsList;
	}

	/**
	 * Arranges the two components in the View.
	 */
	private void layOutComponents() {
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.WEST, wordsList);
		this.add(BorderLayout.CENTER, textScroller);
	}

	/**
	 * Given the prefix of a word, update candidate words displayed in the JList
	 * 
	 * @param starter
	 *            prefic of a word
	 */
	public void updateWords(String starter) {
		ArrayList<String> recomWords = model.dict.getWordsStartsWith(starter, 50);
		if (!recomWords.isEmpty()) {
			candidateWords = recomWords;
			listModel.clear();
			for (String word : candidateWords) {
				listModel.addElement(word);
			}
		}

	}

	/**
	 * Retrieve all the words from the user's personal wordsNote and display
	 */
	public void showWordsNote() {
		ArrayList<String> wordsNote = model.getWordsNote();
		listModel.clear();
		if (wordsNote.isEmpty()) {
			// do nothing, remain the previous list of words
		} else {
			candidateWords = wordsNote;
			for (String word : candidateWords) {
				listModel.addElement(word);
			}
		}
	}

	/**
	 * When user select a word, display the definitions
	 * 
	 * @param wordStr
	 *            an English word
	 */
	public void updateDefinitions(String wordStr) {
		if (wordStr.trim().length() == 0) {
			return;
		}
		String text = "<html><body>";
		Word word = model.searchWord(wordStr.toUpperCase());
		if (word.getDefinitions().size() == 0) {
			text += "<p>No such word!</p></body></html>";
			selectedWord = " ";
		} else {
			selectedWord = wordStr.toLowerCase();
			text += "<h2>" + "&nbsp;" + "&nbsp;" + selectedWord + "</h2>";
			// text += "<br><br>Definitions:<br><br>";
			text += "<ol>";
			for (int i = 0; i < word.getDefinitions().size(); i++) {
				// text += (i + 1) + ". ";
				text += "<li>" + word.getDefinitions().get(i) + "</li>";
				text += "<br>";
			}
			text += "</ol>";
		}
		text += "</body></html>";
		textPane.setText(text);
		this.validate();

	}

	/**
	 * When user click the back button display the word definitions
	 */
	public void backToDefinitions() {
		updateDefinitions(selectedWord);
	}

	// private void cleanView() {
	// textPane.setText(initialText);
	// this.validate();
	// }

	/**
	 * Display the word's Chinese definition handle the case when the Internet
	 * is not connected
	 */
	public void updateTranslation() {
		String chinese = model.getChinese(selectedWord);

		if (chinese.contains("internet connection")) { // displays the error
														// prompt when user's
														// not connected to the
														// internet
			String errorInfo = "<html><body><h3>&nbsp;&nbsp;" + chinese + "</h3></body></html>";
			textPane.setText(errorInfo);
		} else {
			if (chinese.length() == 0) {
				chinese = "无";
			}
			String text = "<html><body>";
			text += "<p>&nbsp;&nbsp;<b>" + selectedWord + "</b> ----> " + chinese + "</p>";
			// citation required by Yandex API
			text += "<p>&nbsp;&nbsp;Powered by <a href='http://translate.yandex.com/' target='_blank'>Yandex.Translate</a>.</p>";
			text += "</body></html>";
			textPane.setText(text);
			this.validate();
		}
	}

	/**
	 * Display the thesaurus of a selected word
	 */
	public void updateThesaurus() {
		if (selectedWord.trim().length() == 0) {
			return;
		}
		String initialText = model.getThesaurus(selectedWord);
		System.out.println(initialText);
		textPane.setText(initialText);
		this.validate();
	}

	// @Override
	// public void valueChanged(ListSelectionEvent e) {
	//
	// if (e.getValueIsAdjusting() == false) {
	// if (wordsList.getSelectedIndex() != -1) {
	// int index = wordsList.getSelectedIndex();
	// selectedWord = candidateWords.get(index).toLowerCase();
	// updateDefinitions(selectedWord.toUpperCase());
	// } else {
	// cleanView();
	// }
	// }
	// }

	/**
	 * A helper function to get the initial welcome words
	 * 
	 * @param path
	 *            a html filename to open
	 * @param encoding
	 *            Unicode Transformation Format
	 * @return a String in html format
	 */
	private static String readFile(String path, Charset encoding) {
		String result = "";
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			result = new String(encoded, encoding);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
