package gameplay;

import java.io.Serializable;

import gameComponents.Letter;
import gameComponents.LetterBag;
import gameComponents.LetterRack;
import network.ScrabbleClient;

public class Player implements Serializable {
	private static final long serialVersionUID = 345345L;
	
	private String username;
	//total score is the score the user has in the game
	private int totalScore;
	//high score is the highest score the user has gotten
	private int highScore;
	//the rack of each player
	private LetterRack rack;
	//the letter bag the player is going to use
	private LetterBag letterBag;
	//check if the user is authenticated 
	private boolean authenticated;
	//player's associated client
	private ScrabbleClient client;

	public Player(String un, int hs, LetterBag letterBag, boolean auth){
		username = un;
		totalScore = 0;
		highScore = hs;
		rack = new LetterRack(letterBag);
		this.letterBag = letterBag;
		authenticated = auth;
	}
	
	public ScrabbleClient getClient() {
		return client;
	}
	
	public void setClient(ScrabbleClient sc) {
		client = sc;
	}
	
	public void updateScore(int amount){
		totalScore += amount;
	}
	
	public String getUsername(){
		return username;
	}
	
	public int getHighScore(){
		return highScore;
	}
	
	public int getTotalScore(){
		return totalScore;
	}

	public void initializeRack()
	{
		for(int i = 0; i < 7; i++)
		{
			if(rack.getLetterAt(i) == null)
			{
				Letter newLetter = letterBag.drawLetter();
				newLetter.changePlaceOnRack(i);
				rack.setLetterAt(i, newLetter);
				rack.add(rack.getLetterAt(i));
			}
		}
	}

	public LetterRack getLetterRack() {
		return rack;
	}
	
	public boolean isAuthenticated(){
		return authenticated;
	}
	
	public LetterBag getLetterBag(){
		return letterBag;
	}
}
