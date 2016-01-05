package jonathzw_CSCI201_Assignment2;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectColorScreen extends JFrame {
	public static final long serialVersionUID =1;
	private int numPlayers;
	private JLabel selectLabel;
	private JButton redButton, blueButton, greenButton, yellowButton, confirmButton;
	private Color playerColor;
	
	public SelectColorScreen(int np){
		super("Sorry! - " + np + " Players");
		numPlayers = np;
		initializeComponents();
		createGUI();
		addEvents();
	}
	
	private void initializeComponents(){
		selectLabel = new JLabel("Select your color");
		selectLabel.setFont(selectLabel.getFont().deriveFont (40.0f));
		
		redButton = new JButton("Red");
		redButton.setBackground(Color.RED);
		redButton.setOpaque(true);
		redButton.setBorderPainted(false);
		
		blueButton = new JButton("Blue");
		blueButton.setBackground(Color.BLUE);
		blueButton.setOpaque(true);
		blueButton.setBorderPainted(false);
		
		greenButton = new JButton("Green");
		greenButton.setBackground(Color.GREEN);
		greenButton.setOpaque(true);
		greenButton.setBorderPainted(false);
		
		yellowButton = new JButton("Yellow");
		yellowButton.setBackground(Color.YELLOW);
		yellowButton.setOpaque(true);
		yellowButton.setBorderPainted(false);
		
		confirmButton = new JButton ("Confirm");
		confirmButton.setEnabled(false);
	}
	
	private void createGUI(){
		setSize(640,480); 
		setLocation(450, 200);
		
		JPanel jp = new JPanel();
		jp.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipady = 100;
		jp.add(selectLabel, gbc);
		
		JPanel gridPanel = new JPanel();
		
		GridLayout gl = new GridLayout(2,2);
		gl.setHgap(10);
		gl.setVgap(10);
		gridPanel.setLayout(gl);
		
		gridPanel.add(redButton);
		gridPanel.add(blueButton);
		gridPanel.add(greenButton);
		gridPanel.add(yellowButton);	
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipadx = 380;
		gbc.ipady = 150;
		jp.add(gridPanel, gbc);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(confirmButton);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.ipadx=0;
		gbc.ipady=0;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		jp.add(buttonPanel, gbc);
		
		add(jp);
	}
	
	private void addEvents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		redButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				redButton.setForeground(Color.GRAY);
				
				greenButton.setForeground(Color.BLACK);
				blueButton.setForeground(Color.BLACK);
				yellowButton.setForeground(Color.BLACK);
				
				playerColor = Color.RED;
				confirmButton.setEnabled(true); 
			}
		});	
		
		blueButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				blueButton.setForeground(Color.GRAY);
				
				redButton.setForeground(Color.BLACK);
				greenButton.setForeground(Color.BLACK);
				yellowButton.setForeground(Color.BLACK);
				
				playerColor = Color.BLUE;
				confirmButton.setEnabled(true); 
			}
		});	
		
		greenButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				greenButton.setForeground(Color.GRAY);
				
				redButton.setForeground(Color.BLACK);
				blueButton.setForeground(Color.BLACK);
				yellowButton.setForeground(Color.BLACK);
				
				playerColor = Color.GREEN;
				confirmButton.setEnabled(true); 

			}
		});	
		
		yellowButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				yellowButton.setForeground(Color.GRAY);
				
				redButton.setForeground(Color.BLACK);
				greenButton.setForeground(Color.BLACK);
				blueButton.setForeground(Color.BLACK);
				
				playerColor = Color.YELLOW;
				confirmButton.setEnabled(true); 
			}
		});	
		
		confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setVisible(false);
				SorryBoard sb = new SorryBoard(numPlayers, playerColor);
				sb.setVisible(true);
				
			}
		});
		
	}

}
