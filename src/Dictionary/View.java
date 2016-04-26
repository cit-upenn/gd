package Dictionary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.TextArea;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Dictionary.db.SQLiteJDBC;
import Dictionary.db.Word;

public class View extends Panel implements ListSelectionListener {

	private Model model;
	private DefaultListModel<String> listModel;
	private JList<String> wordsList;
	// JScrollPane listScroller;
	// private TextArea autoCompArea;
	private JTextArea defArea;
	private String selectWord;
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
		// listScroller = new JScrollPane(wordsList);
		// listScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		// listScroller.setPreferredSize(new Dimension(500, 500));
		// autoCompArea = new TextArea("auto", 20, 20);
		defArea = new JTextArea();
		defArea.setEditable(false);
		defArea.setLineWrap(true);
		defArea.setWrapStyleWord(true);
		defArea.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		defArea.setFont(new Font("Helvetica", Font.PLAIN, 14));

		selectWord = "";
		candidateWords = new ArrayList<String>();

		layOutComponents();

	}

	private void layOutComponents() {
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.WEST, wordsList);
		this.add(BorderLayout.CENTER, defArea);
	}

	public void updateWords(String starter) {
		ArrayList<String> recomWords = model.dict.getWordsStartsWith(starter);
		if (!recomWords.isEmpty()) {
			candidateWords = recomWords;
			listModel.clear();
			for (String word : candidateWords) {
				listModel.addElement(word);
			}
		}

	}

	public void updateDefinitions(Word word) {
		defArea.setText("");
		defArea.append(word.toString());
		defArea.append("\n\nDefinitions:\n\n");
		for (int i = 0; i < word.getDefinitions().size(); i++) {
			defArea.append("" + (i + 1) + ". ");
			defArea.append(word.getDefinitions().get(i));
			defArea.append("\n\n");
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			if (wordsList.getSelectedIndex() != -1) {
				int index = wordsList.getSelectedIndex();
				Word word = SQLiteJDBC.selectFromDictionary(candidateWords.get(index).toUpperCase());
				word.print();
				updateDefinitions(word);
			}
		}

	}

}
