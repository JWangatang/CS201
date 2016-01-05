package network;

import java.awt.Color;
import java.io.Serializable;

public class DisconnectMessage implements Serializable{

	private static final long serialVersionUID = 1589803421889304461L;
	
	private Color color;
	private String message;
	
	public DisconnectMessage(String m, Color c){
		color = c;
		message = m;
	}
	
	public Color getColor(){
		return color;
	}
	
	public String getMessage(){
		return message;
	}

}
