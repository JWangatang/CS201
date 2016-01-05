package network;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SorryThread extends Thread {
	
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;

	private Socket socket;
	
	private SorryServer server;
	
	private Color color = null;
	
	public SorryThread(Socket s, SorryServer ss){
		socket = s;
		server = ss;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			
		} catch (IOException ioe){
			System.out.println("IOE in SorryThread constructor: " + ioe.getMessage());
		}
	}
	
	public void run(){
		try{
			while(true){
				Object o = ois.readObject();
				if(o!=null){
					if(o instanceof Integer){
						server.sendNumPlayers();
					}
					else{
						if(o instanceof Color){
							color = (Color)o;
						}
						server.sendChatMessage(this, o);
					}
				}	
			}
		} catch(IOException ioe){
			server.removeSorryThread(this);
			if(color!=null)
				server.sendDisconnectMessage(color);
			System.out.println(socket.getInetAddress() + " disconnected.");
		} catch (ClassNotFoundException e) {
			System.out.println("CNFE in SorryThread run: " + e.getMessage());
		}			
	}
	
	public void sendMessage(Object message){
		try{
			oos.writeObject(message);
			oos.flush();
		} catch (IOException e){
			System.out.println("IOE in Sorry Thread send message: " + e.getMessage());                                          
		}
	}
}
