package Dictionary;

import java.awt.TextArea;
import java.util.Observable;
import java.util.Observer;

public class View extends TextArea implements Observer{
	
	private Model model;
	
	public View(Model model) {
		this.model = model;
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
