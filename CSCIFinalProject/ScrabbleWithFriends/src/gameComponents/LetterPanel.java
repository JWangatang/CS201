package gameComponents;

import javax.swing.JPanel;

public class LetterPanel extends JPanel {

	private static final long serialVersionUID = 889882839857590003L;
	
	private Letter letter = null;

	public LetterPanel(){
		super();
	}
	
	public Letter getLetter(){
		return letter;
	}
	
	public void setLetter(Letter l){
		letter = l;
	}
}
