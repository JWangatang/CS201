package network;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import sorryclient.ClientPanel;

public class SorryClient extends Thread {
	
	private String ipAddress;
	private int port;
	private Socket socket = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	
	private boolean isHost;
	
	private Color color = null;
	private ClientPanel clientPanel;
	
	private int numPlayers = -1;
	
	public SorryClient(String ip, int p, boolean h, ClientPanel cp){
		clientPanel = cp;
		ipAddress = ip;
		port = p;
		isHost = h;
		
		try{
			System.out.println("Client attempting to connect to host...");
			socket = new Socket(ipAddress, port);
			System.out.println("Client has connected to host!");
			
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			
		} catch (IOException ioe){
			System.out.println("Error in SorryClient constructor: " + ioe.getMessage());
		}
	}
	
	public void run(){
		
		try{
			ois = new ObjectInputStream(socket.getInputStream());
			oos.writeObject(10);
			Object n = ois.readObject();
			numPlayers = (Integer)n;
			while(true){
			//check the object that is being read
				Object o = ois.readObject();
				
				if(o!=null){					
					if(o instanceof ChatMessage){
						clientPanel.chatPanel.addMessage(((ChatMessage)o).getMessage(), ((ChatMessage)o).getColor());
					}
					
					else if(o instanceof Color){
						clientPanel.selectedColors.put((Color)o, true);
						clientPanel.colorSelect.updateButtons();
					}
					else if(o instanceof RemoveColor){
						clientPanel.selectedColors.put(((RemoveColor)o).getColor(), false);
						clientPanel.colorSelect.updateButtons();

					}
					else if(o instanceof DisconnectMessage){
						clientPanel.chatPanel.addDisconnectMessage(((DisconnectMessage)o).getMessage(), ((DisconnectMessage)o).getColor());
					}
					else if(o instanceof Boolean){
						clientPanel.removeAll();
						clientPanel.add(clientPanel.mainMenu);
						clientPanel.revalidate();
						clientPanel.repaint();
						clientPanel.refreshComponents();
						break;
					}
					else
						System.out.println("Message sent but don't know type!");	
				}	
			}			
		} catch(ClassNotFoundException cnfe){
			System.out.println("Class not found exception in SorryClient: " + cnfe.getMessage());
		} catch (IOException ioe){
			System.out.println("IOE in SorryClient: " + ioe.getMessage());
		} finally{
			try{
				if(oos!=null) oos.close();
				if(ois!=null) ois.close();
				if(socket!=null) socket.close();
			} catch (IOException ioe){
				System.out.println("IOException in closing SorryClient files: " + ioe.getMessage());
			}
		}
		
	}

	public boolean isHost(){
		return isHost;
	}
	
	public boolean isConnected(){
		if(socket==null){
			return false;
		}
		return socket.isConnected();
	}
	
	public Socket getSocket(){
		return socket;
	}
	
	public void send(Object o){	
		try {	
			if(o instanceof Color)
				color = (Color)o;
			
			oos.writeObject(o);
			oos.flush();
		} catch (IOException e) {
			System.out.println("SorryClient sending error: " + e.getMessage());
		}
	}
	
	public Color getColor(){
		return color;
	}
	
	public int getNumPlayers(){
		return numPlayers;
	}
}
