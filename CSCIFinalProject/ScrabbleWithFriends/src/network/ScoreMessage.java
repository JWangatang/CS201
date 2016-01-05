package network;

import java.io.Serializable;

public class ScoreMessage implements Serializable
{

	private static final long serialVersionUID = 293898234L;
	
	private int score;
	
	public ScoreMessage(int num)
	{
		score = num;
	}


	public int getScore() 
	{
		return score;
	}
}


