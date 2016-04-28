package dictionary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	private JPanel panel1;
	private JButton searchButton;
	private JTextField SearchText;
//	private JComboBox<String> vocabsBox;
	
	private JTextArea htmlTextArea;
	private JLabel theLabel;
	
	private View view;
	private Model model;
	
	public Controller() {
		model = new Model();
		view = new View(model);
	}
	
	private void layOutComponents() {
		frame = new JFrame("Good Dictionary");
		panel1 = new JPanel();
		SearchText = new JTextField("", 15);
		searchButton = new JButton("Search");
//		vocabsBox = new JComboBox<String>();
		
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(BorderLayout.NORTH, panel1);
		panel1.setLayout(new FlowLayout());
		panel1.add(SearchText);
//		panel1.add(vocabsBox);
		panel1.add(searchButton);
		
		frame.add(BorderLayout.CENTER, view);
		
		/* for thesaurus */
//		String initialText = MerriamWebsterAPI.getThesaurusHtml("sense");
//        htmlTextArea = new JTextArea(10, 20);
//        htmlTextArea.setText(initialText);
//        theLabel = new JLabel(initialText);
//        theLabel.setVerticalAlignment(SwingConstants.CENTER);
//        theLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        JPanel rightPanel = new JPanel();
//        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
//        rightPanel.add(theLabel);
//        rightPanel.setBackground(Color.WHITE);
//        frame.add(BorderLayout.NORTH, rightPanel);
        /* end of thesaurus */
		
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
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Word word = SQLiteJDBC.selectFromDictionary(SearchText.getText().toUpperCase());
				view.updateDefinitions(word);
				word.print();
			}
		});
		
		SearchText.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				view.updateWords(SearchText.getText());
				System.out.println("insert: " + SearchText.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				view.updateWords(SearchText.getText());
				System.out.println("remove: " + SearchText.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				view.updateWords(SearchText.getText());
				System.out.println("change");
			}
		    // implement the methods
		});
	}
	
	public static void main(String[] args) {
		Controller c = new Controller();
		c.display();
	}
}
