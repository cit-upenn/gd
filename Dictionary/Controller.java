package Dictionary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


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
	private JComboBox<String> vocabs;
	
	private View view;
	private Model model;
	
	public Controller() {
		model = new Model();
		view = new View(model);
	}
	
	private void layOutComponents() {
		frame = new JFrame("Good dictionary");
		panel1 = new JPanel();
		SearchText = new JTextField("", 15);
		searchButton = new JButton("Search");
		vocabs = new JComboBox<String>();
		
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(BorderLayout.NORTH, panel1);
		panel1.setLayout(new FlowLayout());
		panel1.add(SearchText);
		panel1.add(searchButton);
		
		frame.add(BorderLayout.CENTER, view);
		
	}
	
	/**
	 * Displays the GUI.
	 */
	private void display() {
		layOutComponents();
		attachListenersToComponents();
		frame.setSize(400, 540);
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
				String word = SearchText.getText();
				//TODO
				System.out.println(word);
			}
		});
		
		SearchText.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("insert: " + SearchText.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("remove: " + SearchText.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
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
