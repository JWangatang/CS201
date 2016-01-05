package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class ScrabbleServer extends Thread {
	private Vector <ScrabbleThread> stVector;
	private int port;
	private ServerSocket ss;
	private int numPlayers = 0;
	private volatile int numConnections = 0;
	private volatile boolean started = false;
	private int highestScore;
	private int numScoresReceived = 0;
	
	public ScrabbleServer(int port, int numPlayers) {
		stVector = new Vector<ScrabbleThread>();
		this.port = port;
		this.numPlayers = numPlayers;
		highestScore = 0;
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	
	public int getScoresReceived() {
		return numScoresReceived;
	}
	
	public void incrementScoresReceived() {
		numScoresReceived++;
	}
	
	public int getHighestScore() {
		return highestScore;
	}
	
	public void setHighestScore(int num) {
		highestScore = num;
	}
	
	public void sendMessageToAllClients(Object message, ScrabbleThread sendingThread) {
		for(ScrabbleThread st: stVector) {
			st.sendMessage(message);
		}
	}
	
	public void sendMessageToAllOtherClients(Object message, ScrabbleThread sendingThread) {
		for(ScrabbleThread st: stVector) {
			if (st != sendingThread) st.sendMessage(message);
		}
	}

	
	public void removeChatThread(ScrabbleThread st) {
		System.out.println("Client disconnected: " + st.getSocket().getInetAddress());
		stVector.remove(st);
	}
	
	public void run() {
		try {
			ss = new ServerSocket(port);
			while (!started) {
				if (numConnections < numPlayers) {
					Socket s = ss.accept();
					System.out.println("Connection from " + s.getInetAddress());
					ScrabbleThread st = new ScrabbleThread(s, this);
					stVector.add(st);
					st.start();
					st.sendMessage("Number of players: " + numPlayers);
					st.sendMessage("Player number: " + numConnections);
					numConnections++;
					if (numConnections == numPlayers) {
						started = true;
						for (int i = 0; i < stVector.size(); i++) {
							stVector.get(i).sendMessage("All connected");
							stVector.get(i).sendMessage("Need players");
						}
					}
				}
			}
		} catch(IOException ioe) {
			return;
			//System.out.println("ioe in SorryServer constructor: " + ioe.getMessage());
		} finally {
			try {
				ss.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
				System.out.println("ioe in closing ServerSocket: " + ioe.getMessage());
			}
		}
	}
}
