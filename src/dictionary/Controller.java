package dictionary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sqlite.SQLiteJDBC;

/**
 * The Controller sets up the GUI and handles all the controls (buttons, menu
 * items, etc.)
 *
 */
public class Controller {

	private JFrame frame;
	private JPanel buttonPanel;
	private JPanel panel1;
	private JPanel panel2;
	private JButton searchButton;
	private JTextField SearchText;
	private JButton wordsNoteButton;
	private JButton MWSynonymButton;
	private JButton translateButton;
	private JButton backButton;

	private JButton addWordsButton;
	private JButton removeWordsButton;

	private View view;
	private Model model;

	public Controller() {
		model = new Model();
		view = new View(model);
	}

	private void layOutComponents() {
		frame = new JFrame("Good Dictionary");
		buttonPanel = new JPanel();
		panel1 = new JPanel();
		panel2 = new JPanel();
		SearchText = new JTextField("", 15);
		searchButton = new JButton("Search");
		wordsNoteButton = new JButton("wordsNote");
		addWordsButton = new JButton("Add");
		removeWordsButton = new JButton("Remove");
		MWSynonymButton = new JButton("Thesaurus");
		translateButton = new JButton("Translate");
		backButton = new JButton("back");
	
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buttonPanel.setLayout(new GridLayout(2, 1));
		frame.add(BorderLayout.NORTH, buttonPanel);
		panel1.setLayout(new FlowLayout());
		panel1.add(SearchText);
		panel1.add(searchButton);
		panel1.add(wordsNoteButton);
		panel2.setLayout(new FlowLayout());
		panel2.add(backButton);
		panel2.add(translateButton);
		panel2.add(MWSynonymButton);
		panel2.add(addWordsButton);
		panel2.add(removeWordsButton);
		buttonPanel.add(panel1);
		buttonPanel.add(panel2);
		frame.add(BorderLayout.CENTER, view);

		searchButton.setEnabled(false);
		backButton.setEnabled(false);
		translateButton.setEnabled(false);
		MWSynonymButton.setEnabled(false);
		addWordsButton.setEnabled(false);
		removeWordsButton.setEnabled(false);
		backButton.setEnabled(false);
	}

	/**
	 * Displays the GUI.
	 */
	private void display() {
		layOutComponents();
		attachListenersToComponents();
		frame.setSize(600, 540);
		frame.setVisible(true);
	}

	/**
	 * Attaches listeners to the components, and schedules a Timer.
	 */
	private void attachListenersToComponents() {
		// The Run button tells the Model to start
		SearchText.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					view.updateDefinitions(SearchText.getText());
					if (SQLiteJDBC.hasWordToLearn(SearchText.getText())) {
						addWordsButton.setEnabled(false);
						removeWordsButton.setEnabled(true);
					} else {
						addWordsButton.setEnabled(true);
						removeWordsButton.setEnabled(false);
					}
					if (SQLiteJDBC.selectFromDictionary(SearchText.getText()) == null) {
						addWordsButton.setEnabled(false);
						MWSynonymButton.setEnabled(false);
						translateButton.setEnabled(false);
					} else {
						MWSynonymButton.setEnabled(true);
						translateButton.setEnabled(true);
					}
					backButton.setEnabled(false);
				}
			}
		});
		
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				view.updateDefinitions(SearchText.getText());
				if (SQLiteJDBC.hasWordToLearn(SearchText.getText())) {
					addWordsButton.setEnabled(false);
					removeWordsButton.setEnabled(true);
				} else {
					addWordsButton.setEnabled(true);
					removeWordsButton.setEnabled(false);
				}
				if (SQLiteJDBC.selectFromDictionary(SearchText.getText()) == null) {
					addWordsButton.setEnabled(false);
					MWSynonymButton.setEnabled(false);
					translateButton.setEnabled(false);
				} else {
					MWSynonymButton.setEnabled(true);
					translateButton.setEnabled(true);
				}
				backButton.setEnabled(false);
			}
		});

		wordsNoteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.showWordsNote();
				addWordsButton.setEnabled(false);
				removeWordsButton.setEnabled(false);
				translateButton.setEnabled(false);
				MWSynonymButton.setEnabled(false);
				backButton.setEnabled(false);
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.backToDefinitions();
				backButton.setEnabled(false);
				translateButton.setEnabled(true);
				MWSynonymButton.setEnabled(true);
			}
		});
		
		translateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.appendTranslation();
				MWSynonymButton.setEnabled(true);
				translateButton.setEnabled(false);
				backButton.setEnabled(true);
			}
		});
		
		MWSynonymButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.getThesaurus();
				translateButton.setEnabled(true);
				MWSynonymButton.setEnabled(false);
				backButton.setEnabled(true);
			}
		});

		addWordsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.addToWordsNote(view.getSelectedWord());
				addWordsButton.setEnabled(false);
				removeWordsButton.setEnabled(true);
			}
		});

		removeWordsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.removeFromWordsNote(view.getSelectedWord());
				removeWordsButton.setEnabled(false);
				addWordsButton.setEnabled(true);
			}
		});
		
		SearchText.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				view.updateWords(SearchText.getText());
//				System.out.println("insert: " + SearchText.getText());
				searchButton.setEnabled(true);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				view.updateWords(SearchText.getText());
//				System.out.println("remove: " + SearchText.getText());
				if (SearchText.getText().length() == 0) {
					searchButton.setEnabled(false);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				view.updateWords(SearchText.getText());
			}
			// implement the methods
		});
		
		JList<String> wordsList = view.getWordsList();
		
		wordsList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (e.getValueIsAdjusting() == false) {
					if (wordsList.getSelectedIndex() != -1) {
						String wordStr = wordsList.getSelectedValue();
						view.updateDefinitions(wordStr);
						backButton.setEnabled(false);
						if (SQLiteJDBC.hasWordToLearn(wordStr)) {
							addWordsButton.setEnabled(false);
							removeWordsButton.setEnabled(true);
						} else {
							addWordsButton.setEnabled(true);
							removeWordsButton.setEnabled(false);
						}
						MWSynonymButton.setEnabled(true);
						translateButton.setEnabled(true);
						backButton.setEnabled(false);
					}
				}
			}
		});
	}

	public static void main(String[] args) {
		Controller c = new Controller();
		c.display();
	}
}
