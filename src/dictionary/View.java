package dictionary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.TextArea;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import merriam_webster_api.MerriamWebsterAPI;
import sqlite.SQLiteJDBC;
import sqlite.Word;

public class View extends Panel implements ListSelectionListener {

	private Model model;
	private DefaultListModel<String> listModel;
	private JList<String> wordsList;
	private JTextArea defArea;
	private String selectedWord;
	private ArrayList<String> candidateWords;

	public View(Model model) {
		this.model = model;
		listModel = new DefaultListModel<String>();
		listModel.addElement("         ");

		wordsList = new JList<String>(listModel);
		wordsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		wordsList.setLayoutOrientation(JList.VERTICAL);
		wordsList.setSelectedIndex(0);
		wordsList.addListSelectionListener(this);
		wordsList.setVisibleRowCount(-1);
		wordsList.setFixedCellWidth(100);
//		wordsList.setBorder(
//				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		// listScroller = new JScrollPane(wordsList);
		// listScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		// listScroller.setPreferredSize(new Dimension(500, 500));
		defArea = new JTextArea();
		defArea.setEditable(false);
		defArea.setLineWrap(true);
		defArea.setWrapStyleWord(true);
		defArea.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		defArea.setFont(new Font("Helvetica", Font.PLAIN, 14));

		candidateWords = new ArrayList<String>();

		layOutComponents();

	}

	private void layOutComponents() {
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.WEST, wordsList);
		this.add(BorderLayout.CENTER, defArea);

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
//			listModel.addElement("   ");
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
		defArea.setText("");
		selectedWord = wordStr.toLowerCase();
		defArea.append(selectedWord);
		defArea.append("\n\nDefinitions:\n\n");
		Word word = SQLiteJDBC.selectFromDictionary(selectedWord.toUpperCase());
		for (int i = 0; i < word.getDefinitions().size(); i++) {
			defArea.append("" + (i + 1) + ". ");
			defArea.append(word.getDefinitions().get(i));
			defArea.append("\n\n");
		}
	}
	
	public void backToDefinitions() {
		updateDefinitions(selectedWord);
	}
	
	private void cleanView() {
		defArea.setText("");
	}
	
	public void appendTranslation() {
		String chinese = model.getChinese(selectedWord);
		defArea.setText("");
		defArea.append(chinese);
	}
	
	public void getThesaurus() {
//		this.removeAll();
		/* for thesaurus */
		String initialText = MerriamWebsterAPI.getThesaurusHtml("happy");
		System.out.println(initialText);
		defArea.setText(initialText);
//        JTextArea htmlTextArea = new JTextArea(10, 20);
//        htmlTextArea.setText(initialText);
//        JLabel theLabel = new JLabel(initialText);
//        theLabel.setVerticalAlignment(SwingConstants.CENTER);
//        theLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        JPanel rightPanel = new JPanel();
//        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
//        this.add(htmlTextArea);
        this.setBackground(Color.GRAY);
//        frame.add(BorderLayout.NORTH, rightPanel);
        /* end of thesaurus */
//		String initialText = MerriamWebsterAPI.getThesaurusHtml(selectedWord);
//		defArea = new JTextArea(10, 20);
//		defArea.setText(initialText);
//		JTextArea htmlTextArea = new JTextArea(10, 20);
//		htmlTextArea.setText(initialText);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		if (e.getValueIsAdjusting() == false) {
			if (wordsList.getSelectedIndex() != -1) {
				int index = wordsList.getSelectedIndex();
//				Word word = SQLiteJDBC.selectFromDictionary(candidateWords.get(index).toUpperCase());
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
