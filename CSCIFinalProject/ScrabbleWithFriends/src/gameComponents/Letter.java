  package gameComponents;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Letter extends JButton{
	private static final long serialVersionUID = 1916522580086219810L;
	private final String letter;
	private final int value;
	private int placeOnRack;
	public boolean tempOnBoard, letterSelected;
	private int row, col;

	
	public Letter(String l, ImageIcon image){
		letter = l;
		value = LetterBag.getLettersInBag().get(l);
		placeOnRack = 7;
		row = -1;
		col = -1;
		tempOnBoard = false;
		letterSelected = false;
		this.setIcon(image);
		this.setOpaque(true);
		this.setBorder(null);
	}
	
	public void setTempOnBoard() {
		tempOnBoard = !tempOnBoard;
	}
	public void setLetterSelected() {
		letterSelected = true;
	}

	public String getLetter(){
		return letter;
	}
	
	public int getLetterValue(){
		return value;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getColumn(){
		return col;
	}
	
	public void updateRow(int row){
		this.row = row;
	}
	
	public void updateColumn(int col){
		this.col = col;
	}
	
	public int getPlaceOnRack() {
		return placeOnRack;
	}
	
	public void changePlaceOnRack(int val){
		placeOnRack = val;
	}
	
	public boolean isSelected(){
		return letterSelected;
	}
	
	public boolean isOnBoard(){
		return tempOnBoard;
	}
	

}
