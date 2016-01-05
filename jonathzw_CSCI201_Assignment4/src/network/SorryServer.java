package network;

import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class SorryServer extends Thread {
	private Vector<SorryThread> stVector = new Vector<SorryThread>();
	private int numPlayers = -1;
	private ServerSocket ss = null;
	private int numThreads = 0;
	
	public SorryServer(int portNumber){
		try {
			System.out.println("Attempting to connect to server's port");
			ss = new ServerSocket(portNumber);
			System.out.println("Server connected to port! - " + portNumber);
		} catch (IOException e) {
			System.out.println("IOE in connecting to host port: " + e.getMessage());
		}
	}
	
	public void run(){
		Socket s;
		try {
			while(numThreads<numPlayers){	
				s = ss.accept();
				System.out.println("Client " + s.getInetAddress() + ":" + s.getPort() + " connected to server!");
									
				SorryThread st = new SorryThread(s, this);
				stVector.add(st);
				numThreads++;
				st.start();
			}
			
			
			/*
			while(started == false){
				
				if(numThreads<numPlayers){
					System.out.println("VECTOR SIZE:  " + stVector.size());
					System.out.println("Num Players:"  + numPlayers);
					System.out.println("num threads" + numThreads);
					
					s = ss.accept();
					System.out.println("Client " + s.getInetAddress() + ":" + s.getPort() + " connected to server!");
										
					SorryThread st = new SorryThread(s, this);
					stVector.add(st);
					numThreads++;
					st.start();
				}
			}
			*/			
		} catch (IOException e) {
			System.out.println("Error in SorryServer accepting sockets: " + e.getMessage());
		} finally{
			if (ss!=null) {
				try {
					ss.close();
				} catch(IOException ioe){
					System.out.println("IOE closing ServerSocket: " + ioe.getMessage());
				}
			}
		}			
	}
	
	public void setNumPlayers (int n){
		numPlayers = n;
	}
	
	public int getNumPlayers(){
		return numPlayers;
	}
	
	public boolean isBound(){
		if(ss==null)
			return false;
		else
			return ss.isBound();
	}
	
	public boolean isReady(){
		return numPlayers==stVector.size();
	}
	
	public int getCurrNumPlayers(){
		return stVector.size();
	}

	
	//Disconnecting 
	public void removeSorryThread(SorryThread st){
		stVector.remove(st);
		numThreads--;
	}
	
	public void sendChatMessage(SorryThread st, Object message){
		for(SorryThread s : stVector){
			if(!st.equals(s)){
				s.sendMessage(message);
			}
		}
	}
	
	public void sendNumPlayers(){
		for(SorryThread s : stVector){
			s.sendMessage(numPlayers);
		}
	}
	
	public void sendDisconnectMessage(Color c){
		String message = "";
		if(c.equals(Color.RED))
			message+="red ";
		else if(c.equals(Color.BLUE))
			message+="blue ";
		else if(c.equals(Color.GREEN))
			message+="green ";
		else
			message+="yellow ";
		
		message += "has disconnected!";
		for(SorryThread s : stVector){
			s.sendMessage(new DisconnectMessage(message, c));
		}
	}
	
	public void hostQuit(){
		for(SorryThread s : stVector){
			s.sendMessage(true);
		}
	}
}
