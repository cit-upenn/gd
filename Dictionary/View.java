package Dictionary;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class View extends Panel implements ListSelectionListener{
	
	private Model model;
	private DefaultListModel<String> listModel;
	private JList<String> wordsList;
//	JScrollPane listScroller;
//	private TextArea autoCompArea;
	private TextArea defiArea;
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
//		listScroller = new JScrollPane(wordsList);
//		listScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
//		listScroller.setPreferredSize(new Dimension(500, 500));
//		autoCompArea = new TextArea("auto", 20, 20);
		defiArea = new TextArea();
		
		selectWord = "";
		candidateWords = new ArrayList<String>();
		
		layOutComponents();
		
	}
	
	private void layOutComponents() {
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.WEST, wordsList);
		this.add(BorderLayout.CENTER, defiArea);
	}
	
	public void updateWords(String starter) {
		candidateWords = model.dict.getWordsStartsWith(starter);
		if (!candidateWords.isEmpty()) {
			listModel.clear();
			for (String word : candidateWords) {
				listModel.addElement(word);
			}
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		 if (e.getValueIsAdjusting() == false) {
	            if (wordsList.getSelectedIndex() == -1) {
	            //No selection, disable fire button.
	            } else {
	            	int index = wordsList.getSelectedIndex();
	            	System.out.println(candidateWords.get(index));
	            }
	        }
		
	}

}
