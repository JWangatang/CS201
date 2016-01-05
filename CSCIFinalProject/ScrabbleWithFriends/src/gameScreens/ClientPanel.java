package gameScreens;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gameComponents.LetterBag;
import gameplay.Player;
import gameplay.UserDatabase;
import network.ScrabbleClient;
import network.ScrabbleServer;

public class ClientPanel extends JPanel {

	private static final long serialVersionUID = -8424625194683475397L;
	
	//Member Variables
	private MainMenuPanel mainMenu;
	private LoginPanel loginPanel;
	private CreateAccountPanel createAccountPanel;
	private JoinHostPanel joinHostPanel;
	private JoinScreenPanel joinScreenPanel;
	private HostScreenPanel hostScreenPanel;
	private SelectPlayersPanel selectPlayersPanel;
	public static WaitingPanel waitingPanel;
	private GameBoardPanel gameBoardPanel;
	
	private ScrabbleServer server;
	private ScrabbleClient client;
	
	private Player player = null;
	private UserDatabase accountDatabase;
	
	private LetterBag bag = new LetterBag();

	public ClientPanel() {
		SwitchScreens();
		setLayout(new BorderLayout());
		add(mainMenu);
		accountDatabase = new UserDatabase();
	}
	
	public void resetGame(){
		ClientPanel.this.removeAll();
		ClientPanel.this.add(mainMenu);
		ClientPanel.this.revalidate();
		ClientPanel.this.repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(new ImageIcon("Images/woodBackground.png").getImage(), 0, 0, this.getWidth(),this.getHeight(), this);
	}
	
	public WaitingPanel getWaitingPanel() {
		return waitingPanel;
	}
	
	private void SwitchScreens() {
		mainMenu = new MainMenuPanel(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientPanel.this.removeAll();
				ClientPanel.this.add(loginPanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}
			
		});
		
		loginPanel = new LoginPanel(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String un = loginPanel.usernameTF.getText().toLowerCase();
				String pw = loginPanel.passwordTF.getText().toLowerCase();
				if(accountDatabase.login(un, pw)){
					player = new Player(un, accountDatabase.getHighScoreFor(un), bag, true);
					ClientPanel.this.removeAll();
					ClientPanel.this.add(joinHostPanel);
					ClientPanel.this.revalidate();
					ClientPanel.this.repaint();
				}
				else
					JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again"
							+ "\nNote: Capitalization is ignored");
			}
		}, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientPanel.this.removeAll();
				ClientPanel.this.add(createAccountPanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}
		}, 
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					player = new Player("Guest", 0, bag, false);
					ClientPanel.this.removeAll();
					ClientPanel.this.add(joinHostPanel);
					ClientPanel.this.revalidate();
					ClientPanel.this.repaint();
				}
		});
		createAccountPanel = new CreateAccountPanel(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String un = createAccountPanel.usernameTextField.getText().toLowerCase();
				String pw = createAccountPanel.passwordTextField.getText().toLowerCase();
				if(accountDatabase.addAccount(un, pw)){
					JOptionPane.showMessageDialog(null, "Account successfully created!\nUsername: "+ un + 
							"\nPassword: " + pw);
					ClientPanel.this.removeAll();
					ClientPanel.this.add(loginPanel);
					ClientPanel.this.revalidate();
					ClientPanel.this.repaint();
				}
				else
					JOptionPane.showMessageDialog(null, "Username already exists. Please try again!");
			}
			
		});
		joinHostPanel = new JoinHostPanel(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientPanel.this.removeAll();
				ClientPanel.this.add(joinScreenPanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}
		}, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientPanel.this.removeAll();
				ClientPanel.this.add(hostScreenPanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}
		});
		joinScreenPanel = new JoinScreenPanel(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client = new ScrabbleClient(joinScreenPanel.getIP(), joinScreenPanel.getPort(), player);
				if(client.isConnected){
					client.start();
					player.setClient(client);
					gameBoardPanel = new GameBoardPanel(client.getNumPlayers(), player, ClientPanel.this);
					ClientPanel.this.removeAll();
					ClientPanel.this.add(waitingPanel);
					ClientPanel.this.revalidate();
					ClientPanel.this.repaint();
				}
				else
					JOptionPane.showMessageDialog(null, "Unabled to connect to host. Please try again!");
					
			}
			
		});
		hostScreenPanel = new HostScreenPanel(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientPanel.this.removeAll();
				ClientPanel.this.add(selectPlayersPanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}
			
		});
		selectPlayersPanel = new SelectPlayersPanel(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				server = new ScrabbleServer(hostScreenPanel.getPort(), selectPlayersPanel.getNumPlayers());
				server.start();
				client = new ScrabbleClient("localhost", hostScreenPanel.getPort(), player);
				client.setHost(true);
				client.start();
				player.setClient(client);
				gameBoardPanel = new GameBoardPanel(client.getNumPlayers(), player, ClientPanel.this);
				ClientPanel.this.removeAll();
				ClientPanel.this.add(waitingPanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}
			
		});
		waitingPanel = new WaitingPanel(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientPanel.this.removeAll();
				ClientPanel.this.add(gameBoardPanel);
				ClientPanel.this.revalidate();
				ClientPanel.this.repaint();
			}
		});
	}
}

