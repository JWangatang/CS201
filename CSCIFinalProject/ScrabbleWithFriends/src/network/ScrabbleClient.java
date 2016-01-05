package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import gameScreens.GameBoardPanel;
import gameScreens.WaitingPanel;
import gameplay.Player;

public class ScrabbleClient extends Thread implements Serializable {
	private static final long serialVersionUID = 987567L;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;
	private Socket s;
	private boolean isHost = false;
	private int numPlayers;
	private int playerNumber;
	private Player player;
	public boolean isConnected = false;
	private int numPasses;
	
	public ScrabbleClient(String hostname, int port, Player p) {
		try {
			s = new Socket(hostname, port);
			isConnected = true;
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			player = p;
			numPasses = 0;
		} catch (IOException ioe) {
			System.out.println("ioe in ScrabbleClient constructor: " + ioe.getMessage());
		} 
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public int getNumPasses() {
		return numPasses;
	}
	
	public boolean isHost() {
		return isHost;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setHost(boolean host) {
		isHost = host;
	}
	
	public void sendTurnMessage(TurnMessage tm) {
		if (oos != null) {
			try {
				oos.writeObject(tm);
				oos.flush();
			} catch (IOException ioe) {
				System.out.println("ioe in sendTurnMessage of ScrabbleThread: " + ioe.getMessage());
			}
		}
	}
	
	public void sendPassMessage(PassMessage pm) {
		if (oos != null) {
			try {
				oos.writeObject(pm);
				oos.flush();
			} catch (IOException ioe) {
				System.out.println("ioe in sendPassMessage of ScrabbleThread: " + ioe.getMessage());
			}
		}
	}
	
	public void sendLettersMessage(LettersMessage lm) {
		if (oos != null) {
			try {
				oos.writeObject(lm);
				oos.flush();
			} catch (IOException ioe) {
				System.out.println("ioe in sendMessage of ScrabbleThread: " + ioe.getMessage());
			}
		}
	}
	
	public void sendScoreMessage(ScoreMessage sm) {
		if (oos != null) {
			try {
				oos.writeObject(sm);
				oos.flush();
			} catch (IOException ioe) {
				System.out.println("ioe in sendScoreMessage of ScrabbleThread: " + ioe.getMessage());
			}
		}
	}
	
	public void sendWinnerMessage(WinnerMessage wm) {
		if (oos != null) {
			try {
				oos.writeObject(wm);
				oos.flush();
			} catch (IOException ioe) {
				System.out.println("ioe in sendWinnerMessage of ScrabbleThread: " + ioe.getMessage());
			}
		}
	}
	
	public void run() {
		try {
			while (true) {
					if (ois != null) {
						Object o = ois.readObject();
						if (o != null) {
							if (o instanceof String) {
								String line = (String)o;
								if (line.startsWith("Number of players: ")) {
									int num = line.charAt(line.length()-1);
									num = num-48;
									numPlayers = num;
									//System.out.println("Number of players: " + numPlayers);
								}
								if (line.startsWith("Player number: ")) {
									int num = line.charAt(line.length()-1);
									num = num-48;
									playerNumber = num;
									//System.out.println("Player number: " + playerNumber);
								}
								if (line.startsWith("All connected")) {
									WaitingPanel.enableButton();
								}
								if (line.startsWith("Quit")) {
									return;
								}
							}
							if(o instanceof TurnMessage) {
								TurnMessage tm = (TurnMessage)o;
								if (playerNumber != tm.getTurn()) {
									GameBoardPanel.disableButtons();
								}
								else {
									GameBoardPanel.setCurrentPlaying(tm.getTurn());
									if (player.isAuthenticated()) {
										GameBoardPanel.enableAllButtons();
									}
									else {
										GameBoardPanel.enableMostButtons();
									}
								}
							}
							if (o instanceof LettersMessage) {
								GameBoardPanel.setLettersOnBoard(((LettersMessage) o).getLettersPlaced(), ((LettersMessage) o).getLettersLocation());
							}
							if (o instanceof PassMessage) {
								numPasses = ((PassMessage) o).getNumPasses();
								if (numPasses == (numPlayers*2)) {
									ScoreMessage sm = new ScoreMessage(player.getTotalScore());
									sendScoreMessage(sm);
								}
							}
							if (o instanceof ScoreMessage) {
								if (player.getTotalScore() == ((ScoreMessage) o).getScore()) {
									WinnerMessage wm = new WinnerMessage(player.getUsername(), player.getTotalScore());
									sendWinnerMessage(wm);
								}
							}
							if (o instanceof WinnerMessage) {
								GameBoardPanel.endGame(((WinnerMessage) o).getName(), ((WinnerMessage) o).getScore());
							}
						}
					}
				}
		} 	catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe in run of ScrabbleClient: " + cnfe.getMessage());
		} catch (IOException ioe) {
			return;
		} finally {
			try {
				if (oos != null) oos.close();
				if (ois != null) ois.close();
				if (s != null) s.close();
			} catch (IOException ioe){
				System.out.println("IOException in closing SorryClient: "  + ioe.getMessage());
			}
		}
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
}
