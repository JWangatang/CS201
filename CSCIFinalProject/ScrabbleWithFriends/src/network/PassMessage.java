package network;

import java.io.Serializable;

public class PassMessage implements Serializable
{

	private static final long serialVersionUID = 345987053249L;
	
	private int numConsecutivePasses;
	
	public PassMessage(int numPasses)
	{
		numConsecutivePasses = numPasses;
	}


	public int getNumPasses() 
	{
		return numConsecutivePasses;
	}
}


