package network;

import java.awt.Color;
import java.io.Serializable;

public class RemoveColor implements Serializable{

	private static final long serialVersionUID = -4930952299057777071L;
	private Color c;
	public RemoveColor(Color c){
		this.c = c;
	}
	public Color getColor(){
		return c;
	}
}
