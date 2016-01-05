package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ScrabbleThread extends Thread {
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;
	private ScrabbleServer ss;
	private Socket s;

	public ScrabbleThread(Socket s, ScrabbleServer ss) {
		this.s = s;
		this.ss = ss;
		try {
			 oos = new ObjectOutputStream(s.getOutputStream());
			 ois = new ObjectInputStream(s.getInputStream());
		} catch(IOException ioe) {
			System.out.println("ioe in ScrabbleThread constructor: " + ioe.getMessage());
		}
	}
	
	public void sendMessage(Object message) {
		if (oos != null) {
			try {
				oos.writeObject(message);
				oos.flush();
			} catch (IOException ioe) {
				System.out.println("ioe in sendMessage of ScrabbleThread: " + ioe.getMessage());
			}
		}
	}
	
	public Socket getSocket() {
		return this.s;
	}
	
	public void run() {
		try {
			while (true) {
				Object o = ois.readObject();
				if (o != null) {
					if (o instanceof ScoreMessage) {
						ss.incrementScoresReceived();
						if (((ScoreMessage) o).getScore() >= ss.getHighestScore()) {
							ss.setHighestScore(((ScoreMessage) o).getScore());
							if (ss.getScoresReceived() == ss.getNumPlayers()) {
								ScoreMessage sm = new ScoreMessage(ss.getHighestScore());
								ss.sendMessageToAllClients((Object)sm, this);
							}
						}
					}
					else ss.sendMessageToAllClients(o, this);
				}
				else {
					break;
				}
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("CNFException: " + cnfe.getMessage());
		} catch (IOException ioe) {
			return;
			//System.out.println("IOException: " + ioe.getMessage());
		} finally {
			sendMessage("Quit");
			ss.removeChatThread(this);
		}
	}
}

