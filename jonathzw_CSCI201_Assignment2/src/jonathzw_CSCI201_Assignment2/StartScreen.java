package jonathzw_CSCI201_Assignment2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartScreen extends JFrame{
	public static final long serialVersionUID =1;
	
	private JLabel sorryLabel;
	private JButton startButton;
	
	public StartScreen(){
		super("Sorry!");
		initializeComponents();
		createGUI();
		addEvents();
	}
	
	private void initializeComponents(){
		sorryLabel = new JLabel("Sorry!");
		sorryLabel.setFont(sorryLabel.getFont().deriveFont (72.0f));
		startButton = new JButton("Start");
	}
	
	private void createGUI(){
		
		setSize(640,480); 
		setLocation(450, 200);
		
		JPanel jp = new JPanel();
		jp.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		jp.add(sorryLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		jp.add(startButton, gbc);
		
		add(jp);
	}
	
	private void addEvents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setVisible(false);
				SelectPlayersScreen sps = new SelectPlayersScreen();
				sps.setVisible(true);
			}
		});
	}
}
