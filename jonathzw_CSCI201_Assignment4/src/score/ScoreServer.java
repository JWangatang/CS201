package score;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ScoreServer extends Thread{
	
	private ServerSocket ss = null;
	private Vector<ScoreThread> threads = new Vector<ScoreThread>();
	
	public ScoreServer(int port){
		try{
			ss = new ServerSocket(port);
			start();
			
		} catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
	public void run(){
		while(true){
			Socket s = null;
			try{
				s = ss.accept();
				ScoreThread st = new ScoreThread(s, this);
				threads.add(st);
				st.start();
				
			} catch(IOException ioe){
				System.out.println("IOE in ScoreServer constructor: " + ioe.getMessage());
			}
		}
	}
}
