package gameScreens;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class SelectPlayersPanel extends JPanel{

	private static final long serialVersionUID = -6238602075154221021L;

	private JLabel playerSelectLabel;
	private JButton submitButton;
	private JRadioButton p2, p3, p4;
	private ButtonGroup bg;
	private JPanel buttonPanel;
	private int selection = -1;

	public SelectPlayersPanel(ActionListener actionListener) {
		initializeComponents();
		createGUI();
		submitButton.addActionListener(actionListener);
	}
	
	public int getNumPlayers() {
		return selection;
	}
	
	private void initializeComponents(){
	
		 Font font;
		 try {
				font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("images/Riffic.ttf"));
				Font styledAndSized = font.deriveFont(36F);
				playerSelectLabel = new JLabel("Select Number of Players");
				playerSelectLabel.setFont(styledAndSized);
				
				submitButton = new JButton("Submit");
				styledAndSized = font.deriveFont(20F);
				submitButton.setFont(styledAndSized);
				submitButton.setEnabled(false);
				submitButton.setPreferredSize(new Dimension(200,50));
				
				p2 = new JRadioButton("2");
				styledAndSized = font.deriveFont(20F);
				p2.setFont(styledAndSized);
				p2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						selection = 2;
						submitButton.setEnabled(true);
					}
				});
				p3 = new JRadioButton("3");
				p3.setFont(styledAndSized);
				p3.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						selection = 3;
						submitButton.setEnabled(true);
					}
				});
				p4 = new JRadioButton("4");
				p4.setFont(styledAndSized);
				p4.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						selection = 4;
						submitButton.setEnabled(true);
					}
				});
				
				bg = new ButtonGroup();
				buttonPanel = new JPanel();
			     
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FontFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
		
		
		
		
		
		
	}
	
	private void createGUI(){
		//add radio buttons to button group and panel
		bg.add(p2); bg.add(p3); bg.add(p4);
		setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel();
		
		ImageIcon imageIcon = new ImageIcon("images/logo.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(600, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		JLabel logo = new JLabel(imageIcon, SwingConstants.CENTER);
		
		buttonPanel.setLayout(new FlowLayout());
		
		buttonPanel.add(p2); buttonPanel.add(p3); buttonPanel.add(p4);
		
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx=0; 
		add(logo, BorderLayout.NORTH);
		gbc.insets = new Insets(60,0,0,0);
		gbc.gridy=0;
		centerPanel.add(playerSelectLabel, gbc);

		gbc.gridy=1;
		centerPanel.add(buttonPanel, gbc);

		gbc.gridy=2;
		centerPanel.add(submitButton, gbc);
		add(centerPanel, BorderLayout.CENTER);
	}
	

}
