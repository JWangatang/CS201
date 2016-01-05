package network;

import java.io.Serializable;

public class TurnMessage implements Serializable
{

	private static final long serialVersionUID = -7156114097798070519L;
	
	private int turn;
	
	public TurnMessage(int id)
	{
		turn = id;
	}


	public int getTurn() 
	{
		return turn;
	}
	
	public void setTurn(int id)
	{
		turn = id;
	}
}


