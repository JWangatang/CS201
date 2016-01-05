package gameComponents;

import javax.swing.JPanel;

public class LetterRack extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Letter [] rack;
	private LetterBag bag;
	
	public LetterRack(LetterBag bag) {
		this.bag = bag;
		rack = new Letter[7];
		for (int i = 0; i < 7; i++) {
			rack[i] = null;
		}
	}
	
	public int length() {
		return rack.length;
	}
	
	public Letter getLetterAt(int i) {
		return rack[i];
	}
	
	public void setLetterAt(int i, Letter l) {
		rack[i] = l;
	}
	
	public Letter [] getRack()
	{
		return rack;
	}
	
	public void replenishRack(){
		for(int i = 0; i < 7; i++){
			if(rack[i] == null){
				Letter l = bag.drawLetter();
				l.changePlaceOnRack(i);
				setLetterAt(i, l);
			}
		}
	}
}
