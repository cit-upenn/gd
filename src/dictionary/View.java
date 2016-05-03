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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import merriam_webster_api.MerriamWebsterAPI;
import sqlite.SQLiteJDBC;
import sqlite.Word;

public class View extends Panel {

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
		// wordsList.addListSelectionListener(this);
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
		initialText = readFile("welcome.html", Charset.forName("UTF-8"));
		textPane.setContentType("text/html");
		textPane.setText(initialText);
		textPane.setEditable(false);
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
			selectedWord = " ";
		} else {
			selectedWord = wordStr.toLowerCase();
			text += "<h2>" + "&nbsp;" + "&nbsp;" + selectedWord + "</h2>";
			//text += "<br><br>Definitions:<br><br>";
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

	public void backToDefinitions() {
		updateDefinitions(selectedWord);
	}

	private void cleanView() {
		textPane.setText(initialText);
		this.validate();
	}

	public void appendTranslation() {
		String chinese = model.getChinese(selectedWord);
		
		if (chinese.contains("internet connection")) {                           // displays the error prompt when user's not connected to the internet
			String errorInfo = "<p>&nbsp;&nbsp;<b>" + chinese + "</p>";
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

	public void getThesaurus() {
		if (selectedWord.trim().length() == 0) {
			return;
		}
		String initialText = MerriamWebsterAPI.getThesaurusHtml(selectedWord);
		System.out.println(initialText);
		textPane.setText(initialText);
		this.validate();
	}

	public JList<String> getWordsList() {
		return wordsList;
	}

//	@Override
//	public void valueChanged(ListSelectionEvent e) {
//
//		if (e.getValueIsAdjusting() == false) {
//			if (wordsList.getSelectedIndex() != -1) {
//				int index = wordsList.getSelectedIndex();
//				selectedWord = candidateWords.get(index).toLowerCase();
//				updateDefinitions(selectedWord.toUpperCase());
//			} else {
//				cleanView();
//			}
//		}
//	}

	public String getSelectedWord() {
		return selectedWord;
	}
	
	static String readFile(String path, Charset encoding) {
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
