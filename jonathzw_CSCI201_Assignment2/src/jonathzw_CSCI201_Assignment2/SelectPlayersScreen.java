package jonathzw_CSCI201_Assignment2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SelectPlayersScreen extends JFrame {
	public static final long serialVersionUID =1;
	private JLabel selectLabel;
	private JButton confirmButton;
	private JRadioButton jrb2;
	private JRadioButton jrb3;
	private JRadioButton jrb4;
	private ButtonGroup bg;
	private int numPlayers = 2;
		
	public SelectPlayersScreen(){
		super("Sorry!");
		initializeComponents();
		createGUI();
		addEvents();
	}
	
	private void initializeComponents(){
		selectLabel = new JLabel("Select the number of players");
		selectLabel.setFont(selectLabel.getFont().deriveFont (40.0f));
		
		confirmButton = new JButton("Confirm");
		confirmButton.setEnabled(false);
		
		jrb2 = new JRadioButton("2");
		jrb3 = new JRadioButton("3");
		jrb4 = new JRadioButton("4");
		bg = new ButtonGroup();
	}
	
	private void createGUI(){
		setSize(640,480); 
		setLocation(450, 200);
		
		JPanel jp = new JPanel();
		jp.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipady = 300;
		jp.add(selectLabel, gbc);
		
		bg.add(jrb2);
		bg.add(jrb3);
		bg.add(jrb4);
		
		JPanel rbPanel = new JPanel();
		GridLayout gl = new GridLayout(1,3);
		gl.setHgap(180);
		rbPanel.setLayout(gl);
		
		rbPanel.add(jrb2);
		rbPanel.add(jrb3);
		rbPanel.add(jrb4);
		
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		jp.add(rbPanel, gbc);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(confirmButton);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		jp.add(buttonPanel, gbc);
		
		add(jp);
		
	}
	
	private void addEvents(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jrb2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){ confirmButton.setEnabled(true); }	
		});	
		
		jrb3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){ confirmButton.setEnabled(true); }
		});	
		
		jrb4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){ confirmButton.setEnabled(true); }	
		});	
		
		confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(jrb2.isSelected()){
					numPlayers = 2;
				}
				else if(jrb3.isSelected()){
					numPlayers = 3;
				}
				else if(jrb4.isSelected()){
					numPlayers = 4;
				}
				
				setVisible(false);
				SelectColorScreen scs = new SelectColorScreen(numPlayers);
				scs.setVisible(true);
				
			}
		});
		
	}

}
