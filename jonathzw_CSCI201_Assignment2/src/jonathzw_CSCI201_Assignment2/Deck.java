package jonathzw_CSCI201_Assignment2;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class Deck {
	
	private ArrayList<Card> deck;
	
	public Deck(){
		deck = new ArrayList<Card>();
		reset();
	}
	
	public void reset(){
		for(int i=0; i<4; i++){
			Card c1 = new Card("1", "Move a pawn from Start or move a pawn one space forward.");
			deck.add(c1);
			Card c2 = new Card("2", "Move a pawn from Start or move a pawn two spaces forward. "
					+ "Drawing a two entitles the player to draw again at the end of their turn.");
			deck.add(c2);
			Card c3 = new Card("3", "Move a pawn three spaces forward.");
			deck.add(c3);
			Card c4 = new Card("4", "Move a pawn four spaces backwards.");
			deck.add(c4);
			Card c5 = new Card("5", "Move a pawn five spaces forward.");
			deck.add(c5);	
			Card c7 = new Card("7", "Move one pawn seven spaces forward or split the seven spaces between two pawns.");
			deck.add(c7);	
			Card c8 = new Card("8", "Move a pawn eight spaces forward.");
			deck.add(c8);
			Card c10 = new Card("10", "Move a pawn ten spaces forward or one space backward.");
			deck.add(c10);
			Card c11 = new Card("11", "Move a pawn eleven spaces forward, or switch spaces with an opposing pawn.");
			deck.add(c11);
			Card c12 = new Card("12", "Move a pawn twelve spaces forward.");
			deck.add(c12);
			Card sorry = new Card("Sorry!", "Move any one pawn from Start to a square occupied by any opponent, sending that pawn back to its own Start.");
			deck.add(sorry);
		}
	}
	
	public Card pickCard(){
		if(deck.isEmpty()){
			JOptionPane.showMessageDialog(null, "Reshuffling Deck.");
			reset();
		}
		Random r = new Random();
		int cardNumber = r.nextInt(deck.size());
		Card temp = deck.get(cardNumber);
		deck.remove(cardNumber);
		return temp;	
	}
}
