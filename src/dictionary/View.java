package dictionary;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import merriam_webster_api.MerriamWebsterAPI;
import sqlite.SQLiteJDBC;
import sqlite.Word;

public class View extends Panel implements ListSelectionListener {

	private Model model;
	private DefaultListModel<String> listModel;
	private JList<String> wordsList;
	// private JTextArea defArea;
	private JTextPane textPane;
	private String selectedWord;
	private ArrayList<String> candidateWords;
	private JScrollPane textScroller;
	private String initialText;

	public View(Model model) {
		this.model = model;
		listModel = new DefaultListModel<String>();
		listModel.addElement("         ");
		selectedWord = " ";
		wordsList = new JList<String>(listModel);
		wordsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		wordsList.setLayoutOrientation(JList.VERTICAL);
		wordsList.setSelectedIndex(0);
		wordsList.addListSelectionListener(this);
		wordsList.setVisibleRowCount(-1);
		wordsList.setFixedCellWidth(100);
		// wordsList.setBorder(
		// BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
		// BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		// listScroller = new JScrollPane(wordsList);
		// listScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		// listScroller.setPreferredSize(new Dimension(500, 500));

		// defArea = new JTextArea();
		// defArea.setEditable(false);
		// defArea.setLineWrap(true);
		// defArea.setWrapStyleWord(true);
		// defArea.setBorder(
		// BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
		// BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		// defArea.setFont(new Font("Helvetica", Font.PLAIN, 14));
		textPane = new JTextPane();
		initialText = "<html><body> <p> Welcome to our dictionary! </p></body></html>";
		textPane.setContentType("text/html");
		textPane.setText(initialText);
		textScroller = new JScrollPane(textPane);
		
		candidateWords = new ArrayList<String>();

		layOutComponents();

	}

	private void layOutComponents() {
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.WEST, wordsList);
		this.add(BorderLayout.CENTER, textScroller);
	}

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

	public void showWordsNote() {
		ArrayList<String> wordsNote = model.getWordsNote();
		listModel.clear();
		if (wordsNote.isEmpty()) {
			// listModel.addElement(" ");
		} else {
			candidateWords = wordsNote;
			for (String word : candidateWords) {
				listModel.addElement(word);
			}
		}
	}

	public void updateDefinitions(String wordStr) {
		if (wordStr.trim().length() == 0) {
			return;
		}
		String text = "<html><body>";
		Word word = SQLiteJDBC.selectFromDictionary(wordStr.toUpperCase());
		if (word.getDefinitions().size() == 0) {
			text += "<p>No such word!</p></body></html>";
		} else {
			selectedWord = wordStr.toLowerCase();
			text += selectedWord;
			text += "<br><br>Definitions:<br><br>";
			for (int i = 0; i < word.getDefinitions().size(); i++) {
				text += (i + 1) + ". ";
				text += word.getDefinitions().get(i);
				text += "<br><br>";
			}
		}
		textPane.setText(text);
		this.validate();

	}

	public void backToDefinitions() {
		updateDefinitions(selectedWord);
	}

	private void cleanView() {
		textPane.setText(initialText);
		this.validate();
	}

	public void appendTranslation() {
		String chinese = model.getChinese(selectedWord);
		textPane.setText(chinese);
		this.validate();
	}

	public void getThesaurus() {
		String initialText = MerriamWebsterAPI.getThesaurusHtml(selectedWord);
		System.out.println(initialText);
		textPane.setText(initialText);
		this.validate();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		if (e.getValueIsAdjusting() == false) {
			if (wordsList.getSelectedIndex() != -1) {
				int index = wordsList.getSelectedIndex();
				selectedWord = candidateWords.get(index).toLowerCase();
				updateDefinitions(selectedWord.toUpperCase());
			} else {
				cleanView();
			}
		}
	}

	public String getSelectedWord() {
		return selectedWord;
	}

}
