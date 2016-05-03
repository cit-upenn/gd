package dictionary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import merriam_webster_api.MerriamWebsterAPI;
import sqlite.SQLiteJDBC;
import sqlite.Word;

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
	
		// vocabsBox = new JComboBox<String>();

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

		/* for thesaurus */
//		String initialText = MerriamWebsterAPI.getThesaurusHtml("happy");
//        htmlTextArea = new JTextArea(10, 20);
//        htmlTextArea.setText(initialText);
//        theLabel = new JLabel(initialText);
//        theLabel.setVerticalAlignment(SwingConstants.CENTER);
//        theLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        JPanel rightPanel = new JPanel();
//        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
//        rightPanel.add(theLabel);
//        rightPanel.setBackground(Color.WHITE);
//        frame.add(BorderLayout.SOUTH, rightPanel);
        /* end of thesaurus */


		addWordsButton.setEnabled(false);
		removeWordsButton.setEnabled(false);
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
					addWordsButton.setEnabled(true);
					removeWordsButton.setEnabled(true);
				}
			}
		});
		
		// TODO:
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				view.updateDefinitions(SearchText.getText());
				if (SQLiteJDBC.hasWordToLearn(SearchText.getText())) {
					addWordsButton.setEnabled(false);
					removeWordsButton.setEnabled(true);
				} else {
					addWordsButton.setEnabled(true);
					addWordsButton.setEnabled(false);
				}
				
			}
		});

		wordsNoteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.showWordsNote();
				addWordsButton.setEnabled(true);
				removeWordsButton.setEnabled(true);
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.backToDefinitions();
			}
		});
		
		translateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.appendTranslation();
			}
		});
		
		MWSynonymButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.getThesaurus();
			}
		});

		addWordsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.addToWordsNote(view.getSelectedWord());
				addWordsButton.setEnabled(false);
			}
		});

		removeWordsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.removeFromWordsNote(view.getSelectedWord());
				removeWordsButton.setEnabled(false);
			}
		});
		
		SearchText.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				view.updateWords(SearchText.getText());
//				System.out.println("insert: " + SearchText.getText());
				addWordsButton.setEnabled(true);
				removeWordsButton.setEnabled(true);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				view.updateWords(SearchText.getText());
//				System.out.println("remove: " + SearchText.getText());
				if (SearchText.getText().trim().length() == 0) {
					addWordsButton.setEnabled(false);
					removeWordsButton.setEnabled(false);
				} else {
					addWordsButton.setEnabled(true);
					removeWordsButton.setEnabled(true);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				view.updateWords(SearchText.getText());
			}
			// implement the methods
		});
	}

	public static void main(String[] args) {
		Controller c = new Controller();
		c.display();
	}
}
