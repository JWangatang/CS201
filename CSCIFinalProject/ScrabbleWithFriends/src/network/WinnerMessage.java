package network;

import java.io.Serializable;

public class WinnerMessage implements Serializable
{

	private static final long serialVersionUID = 854983489L;
	
	private String name;
	private int score;
	
	public WinnerMessage(String user, int num)
	{
		name = user;
		score = num;
	}


	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
}


