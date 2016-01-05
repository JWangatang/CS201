package sorryclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game.GameManager;
import library.ImageLibrary;
import network.SorryClient;
import network.SorryServer;

public class ClientPanel extends JPanel {
	private static final long serialVersionUID = 6415716059554739910L;
	
	public MainMenu mainMenu;
	private HostPanel hostPanel;
	private JoinPanel joinPanel;
	public NumPlayerSelector numPlayerSelect;
	public ColorSelector colorSelect;
	private GamePanel gamePanel;
	public ChatPanel chatPanel;
	
	private GameManager gameManager;
	
	public SorryServer sorryServer = null;
	public SorryClient sorryClient = null;
			
	public Hashtable<Color, Boolean> selectedColors = new Hashtable<Color, Boolean>();
	
	{		
		mainMenu = new MainMenu(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				ClientPanel.this.removeAll();
				ClientPanel.this.add(hostPanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}
		},
		new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				ClientPanel.this.removeAll();
				ClientPanel.this.add(joinPanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}
		},ImageLibrary.getImage("images/panels/grey_panel.png"));

		refreshComponents();
		setLayout(new BorderLayout());
		add(mainMenu);
	}
	
	public void refreshComponents() {
		
		hostPanel = new HostPanel(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(hostPanel.getPortNumber()!=-1){
					sorryServer = new SorryServer(hostPanel.getPortNumber());
					if(sorryServer!=null && sorryServer.isBound()){
						//create client for host too
						sorryClient = new SorryClient("localhost", hostPanel.getPortNumber(), true, ClientPanel.this);
						if(sorryClient!=null && sorryClient.isConnected()){
							ClientPanel.this.removeAll();
							ClientPanel.this.add(numPlayerSelect);
							ClientPanel.this.revalidate();
							ClientPanel.this.repaint();
							sorryClient.start();
						}
						else{
							JOptionPane.showMessageDialog(null, "Client was not created. Please try again.");
						}

					}
					else{
						JOptionPane.showMessageDialog(null, "Unable to bind to port number. Please try again.");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Invalid port number. Please try again.");
				}
			}
		}, ImageLibrary.getImage("images/panels/grey_panel.png"));
		
		
		
		joinPanel = new JoinPanel(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(joinPanel.getIPAddress()!=null && joinPanel.getPortNumber()!=-1){
					sorryClient = new SorryClient(joinPanel.getIPAddress(), joinPanel.getPortNumber(), false, ClientPanel.this);
					if(sorryClient!=null && sorryClient.isConnected()){
						ClientPanel.this.removeAll();
						ClientPanel.this.add(colorSelect);
						ClientPanel.this.revalidate();
						ClientPanel.this.repaint();
						sorryClient.start();
					}
					else{
						JOptionPane.showMessageDialog(null, "Unable to connect. Please try a different port or IP address.");
					}
				}
			}
		}, ImageLibrary.getImage("images/panels/grey_panel.png"));
		
		numPlayerSelect = new NumPlayerSelector(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				sorryServer.setNumPlayers(numPlayerSelect.getNumberOfPlayers());
				sorryServer.start();
				ClientPanel.this.removeAll();
				ClientPanel.this.add(colorSelect);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
				
			}
		},ImageLibrary.getImage("images/panels/grey_panel.png"));
		
		selectedColors.put(Color.RED, false);
		selectedColors.put(Color.BLUE, false);
		selectedColors.put(Color.GREEN, false);
		selectedColors.put(Color.YELLOW, false);
		
		chatPanel = new ChatPanel(this);
		
		colorSelect = new ColorSelector(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				colorSelect.confirmButton.setEnabled(false);
				
				while(true){
					if(allReady()){
						ClientPanel.this.removeAll();
						gameManager.setUp(
							colorSelect.getPlayerColor(), 
							sorryClient.getNumPlayers()
						);
						ClientPanel.this.add(gamePanel);
						ClientPanel.this.add(chatPanel, BorderLayout.SOUTH);
						ClientPanel.this.revalidate();
						ClientPanel.this.repaint();
						break;
					}
				}

			}
		},ImageLibrary.getImage("images/panels/grey_panel.png"), ClientPanel.this);
		
		gameManager = new GameManager();
		
		
		gamePanel = new GamePanel(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientPanel.this.removeAll();
				ClientPanel.this.add(mainMenu);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
				refreshComponents();
			}
		}, gameManager, ImageLibrary.getImage("images/sorry.png"));
		
	}
	
	private boolean allReady(){
		int count = 0;
	
		Set<Color> keys = selectedColors.keySet();
		for(Color c : keys){
			if(selectedColors.get(c)==true) count++;
		}
		return count==sorryClient.getNumPlayers();
	}

}
