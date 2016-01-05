package network;

import java.awt.Color;
import java.io.Serializable;

public class ChatMessage implements Serializable{

	private static final long serialVersionUID = -878730631747527970L;
	private Color color;
	private String message;
	
	public ChatMessage(String m, Color c){
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
