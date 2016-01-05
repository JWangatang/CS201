package gameplay;

import java.util.Vector;

import gameComponents.Letter;
import gameScreens.GameBoardPanel;

public class MoveChecker {
	
	private Vector<Letter> placedLettersVector;
	private Vector<Vector<Letter>> validWordsVector;
	private Dictionary dictionary;
	private GameBoardPanel gbp;
	
	public MoveChecker(Vector<Letter> pl, GameBoardPanel gbp){
		placedLettersVector = pl;
		this.gbp = gbp;
		validWordsVector = new Vector<Vector<Letter>>();
		dictionary = GameBoardPanel.dictionary;
	}
	
	@SuppressWarnings("unused")
	private String printWord(Vector<Letter> word){
		String s ="";
		for(int i=0; i<word.size(); i++){
			s+=word.get(i).getLetter();
		}
		return s;
	}
	
	public boolean isValidWord(){
		Letter lo, hi;
		
		if(rowCheck()){
			//System.out.println("@@in the same row!");

			lo = getAbsoluteLowestX(getLowestPlacedX());
			//System.out.println("ABSOLUTE Lowest X letter: " + lo.getLetter());

			hi = getAbsoluteHighestX(getHighestPlacedX());
			//System.out.println("ABSOLUTE Highest X letter: " + hi.getLetter());

			
			Vector<Letter> potentialRowWord = getNewWord(lo, hi);
			//System.out.println("POTENTIAL ROW WORD: " + printWord(potentialRowWord));

			if(potentialRowWord ==null){
				//System.out.println("potential row word has gaps so invalid");
				return false;
			}

			if(dictionary.isValidWord(convertLetterToString(potentialRowWord))){
				//System.out.println("$$$ WORD IS VALID IN DICTIONARY and added to v of v!");
				validWordsVector.add(potentialRowWord);
				
				for(int i=0; i<placedLettersVector.size(); i++){
					Letter low = getAbsoluteLowestY(placedLettersVector.get(i));
					Letter high = getAbsoluteHighestY(placedLettersVector.get(i));
					
					Vector<Letter> otherPotentialWord = getNewWord(low, high);
					if(otherPotentialWord.size()>1){
						if(dictionary.isValidWord(convertLetterToString(otherPotentialWord))){
							//System.out.println("mini word is valid: " + printWord(otherPotentialWord));
							validWordsVector.add(otherPotentialWord);
						}
						else{
							//System.out.println("Mini words were invalid: " + printWord(otherPotentialWord));
							return false;
						}
					}
				}
				return true;
			}
			else{
				//System.out.println("Word was not valid in dictionary");
				return false;
			}
		}
		
		else if(columnCheck()){
			//System.out.println("@@in the same column!");

			lo = getAbsoluteLowestY(getLowestPlacedY());
			//System.out.println("ABSOLUTE lowest Y letter: " + lo.getLetter());

			hi = getAbsoluteHighestY(getHighestPlacedY());
			//System.out.println("ABSOLUTE highest Y letter: " + hi.getLetter());

			
			Vector<Letter> potentialColWord = getNewWord(lo, hi);
			//System.out.println("POTENTIAL Col WORD: " + printWord(potentialColWord));

			if(potentialColWord==null){
				//System.out.println("potential col  word has gaps so invalid");
				return false;
			}
			
			if(dictionary.isValidWord(convertLetterToString(potentialColWord))){
				validWordsVector.add(potentialColWord);
				//System.out.println("$$$ WORD IS VALID IN DICTIONARY and added to v of v!");

				
				for(int i=0; i<placedLettersVector.size(); i++){
					Letter low = getAbsoluteLowestX(placedLettersVector.get(i));
					Letter high = getAbsoluteHighestX(placedLettersVector.get(i));
					
					Vector<Letter> otherPotentialWord = getNewWord(low, high);
					if(otherPotentialWord.size()>1){
						if(dictionary.isValidWord(convertLetterToString(otherPotentialWord))){
							//System.out.println("mini word is valid: " + printWord(otherPotentialWord));

							validWordsVector.add(otherPotentialWord);
						}
						else{
							//System.out.println("mini word is invalid: " + printWord(otherPotentialWord));

							return false;
						}
					}
				}
				return true;
			} 
			else return false;
		}
		//System.out.println("## not in the same row or col");

		return false;
	}
	
	public boolean rowCheck(){

		if(placedLettersVector!=null && placedLettersVector.size()>0)
		{	
			if (placedLettersVector.size() == 1) {
				int column = placedLettersVector.get(0).getColumn();
				if (gbp.getSquaresArray()[placedLettersVector.get(0).getRow()][column-1].getLetter() != null || gbp.getSquaresArray()[placedLettersVector.get(0).getRow()][column+1].getLetter() != null) {
					return true;
				}
				else return false;

			}
			int temp = placedLettersVector.get(0).getRow();
			for(int i = 0; i < placedLettersVector.size(); i++)
			{
				if(placedLettersVector.get(i).getRow() != temp)
					return false;
			}
			return true;
			
		} 
		else{
			//System.out.println("placedLettersVector is null in row check");
			return false;
		}
	}
	
	public boolean columnCheck(){
		
		if(placedLettersVector!=null && placedLettersVector.size()>0)
		{
			if (placedLettersVector.size() == 1) {
				int row = placedLettersVector.get(0).getRow();
				if (gbp.getSquaresArray()[row-1][placedLettersVector.get(0).getColumn()].getLetter() != null || gbp.getSquaresArray()[row+1][placedLettersVector.get(0).getColumn()].getLetter() != null) {
					return true;
				}
				else return false;
			}
			int temp = placedLettersVector.get(0).getColumn();
			for(int i = 0; i < placedLettersVector.size(); i++)
			{
				if(placedLettersVector.get(i).getColumn() != temp)
					return false;
			}
			return true;
			
		} else{
			//System.out.println("placedLettersVector is null in col check");
			return false;
		}
	}
	
	//Get lowest and highest x
	public Letter getLowestPlacedX(){
		Letter letter = placedLettersVector.get(0);
		int x = placedLettersVector.get(0).getColumn();
		for(int i = 1; i < placedLettersVector.size(); i++){
			if(placedLettersVector.get(i).getColumn() < x){
				x = placedLettersVector.get(i).getColumn();
				letter = placedLettersVector.get(i);
			}
		}
		//System.out.println("Lowest X letter PLACED: " + letter.getLetter());
		return letter;
	}
	
	public Letter getHighestPlacedX(){
		Letter letter = placedLettersVector.get(0);
		int x = placedLettersVector.get(0).getColumn();
		for(int i = 1; i < placedLettersVector.size(); i++){
			if(placedLettersVector.get(i).getColumn() > x){
				x = placedLettersVector.get(i).getColumn();
				letter = placedLettersVector.get(i);
			}
		}
		//System.out.println("Highest X letter PLACED: " + letter.getLetter());
		return letter;
	}
	
	//Get lowest and highest y
	public Letter getLowestPlacedY(){
		Letter letter = placedLettersVector.get(0);
		int x = placedLettersVector.get(0).getRow();
		for(int i = 1; i < placedLettersVector.size(); i++){
			if(placedLettersVector.get(i).getRow() < x){
				x = placedLettersVector.get(i).getRow();
				letter = placedLettersVector.get(i);
			}
		}
		//System.out.println("Lowest Y letter PLACED: " + letter.getLetter());
		return letter;
	}
	
	public Letter getHighestPlacedY(){
		Letter letter = placedLettersVector.get(0);
		int x = placedLettersVector.get(0).getRow();
		for(int i = 1; i < placedLettersVector.size(); i++){
			if(placedLettersVector.get(i).getRow() > x){
				x = placedLettersVector.get(i).getRow();
				letter = placedLettersVector.get(i);
			}
		}
		//System.out.println("Highest Y letter PLACED: " + letter.getLetter());
		return letter;	
	}


	//Get absolute lowest and highest x
	//getting the lowest COLUMN
	public Letter getAbsoluteLowestX(Letter l) {
		
		int c = l.getColumn();
		int r = l.getRow();
		
		if(c-1 < 0 || GameBoardPanel.squaresArray[r][c-1].getLetter()==null) return l;
		
		Letter temp = GameBoardPanel.squaresArray[r][c-1].getLetter();
		
		for(int i=c-2; i>=0; i--){
			if(GameBoardPanel.squaresArray[r][i].getLetter()==null){
				//System.out.println("get absolute lowest x temp: " + temp.getLetter());
				return temp;
			}
			else{
				temp = GameBoardPanel.squaresArray[r][i].getLetter();
			}
		}
		return temp;
		
	}
	public Letter getAbsoluteHighestX(Letter l){
		
		int c = l.getColumn();
		int r = l.getRow();
		
		if(c+1 > 14 || GameBoardPanel.squaresArray[r][c+1].getLetter()==null) return l;
		
		Letter temp = GameBoardPanel.squaresArray[r][c+1].getLetter();
		
		for(int i=c+2; i<16; i++){
			if(GameBoardPanel.squaresArray[r][i].getLetter()==null){
				//System.out.println("get absolute highest x temp: " + temp.getLetter());
				return temp;
			}
			else{
				temp = GameBoardPanel.squaresArray[r][i].getLetter();
			}
		}
		return temp;
	}
	

	//Get absolute lowest and highest y
	//GET LOWEST ROW
	public Letter getAbsoluteLowestY(Letter l){
		int c = l.getColumn();
		int r = l.getRow();
		
		if(r-1 < 0 || GameBoardPanel.squaresArray[r-1][c].getLetter()==null) return l;
		
		Letter temp = GameBoardPanel.squaresArray[r-1][c].getLetter();
		
		for(int i=r-2; i>=0; i--){
			if(GameBoardPanel.squaresArray[i][c].getLetter()==null){
				//System.out.println("get absolute lowest Y temp: " + temp.getLetter());
				return temp;
			}
			else{
				temp = GameBoardPanel.squaresArray[i][c].getLetter();
			}
		}
		return temp;
	}
	
	//GET HIGEHST ROW
	public Letter getAbsoluteHighestY(Letter l){
		int c = l.getColumn();
		int r = l.getRow();
		
		if(r+1 > 14 || GameBoardPanel.squaresArray[r+1][c].getLetter()==null) return l;
		
		Letter temp = GameBoardPanel.squaresArray[r+1][c].getLetter();
		
		for(int i=r+2; i<16; i++){
			if(GameBoardPanel.squaresArray[i][c].getLetter()==null){
				//System.out.println("get absolute highest Y temp: " + temp.getLetter());
				return temp;
			}
			else{
				temp = GameBoardPanel.squaresArray[i][c].getLetter();
			}
		}
		return temp;
	}
	
	public Vector<Letter> getNewWord(Letter startLetter, Letter lastLetter){
		Vector<Letter> temp = new Vector<Letter>();
		if (columnCheck()) {
			for (int i = startLetter.getRow(); i <= lastLetter.getRow(); i++) {
				if (GameBoardPanel.squaresArray[i][startLetter.getColumn()].getLetter() != null) {
					temp.add(GameBoardPanel.squaresArray[i][startLetter.getColumn()].getLetter());
				} else {
					return null;
				}
			}
		} else if (rowCheck()) {
			for (int i = startLetter.getColumn(); i <= lastLetter.getColumn(); i++) {
				if (GameBoardPanel.squaresArray[startLetter.getRow()][i].getLetter() != null) {
					temp.add(GameBoardPanel.squaresArray[startLetter.getRow()][i].getLetter());
				} else {
					return null;
				}
			}
		}

		return temp;
	}
	
	public String convertLetterToString(Vector<Letter> sortedWord) {
		String temp = "";
		for (Letter l : sortedWord) {
			temp += l.getLetter();
		}
		return temp;
	}
	
	public Vector<Vector<Letter>> getValidWords(){
		return validWordsVector;
	}

}
