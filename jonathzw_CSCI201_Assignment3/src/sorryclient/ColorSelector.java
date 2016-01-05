package sorryclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/*
 * ColorSelector
 * Menu to select Red,Blue,Green,Yellow
 * */
public class ColorSelector extends JPanel {
	
	private static final long serialVersionUID = 1900724217285760485L;
	
	//The options for color selection
	private Color selection;
	private final int numOptions = 4;
	private final JButton[] optionButtons;
	
	private final JButton confirmButton;
	
	private final static String selectColorString = "Select your color";
	
	private final static String[] colorNames = {"Red", "Blue", "Green", "Yellow"};
	private final static Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
	
	//For spacing to the borders
	private final static Insets spacing = new Insets(20,20,20,20);
	
	public Color getPlayerColor() {
		return selection;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	        g.drawImage(new ImageIcon("grey_panel.png").getImage(), 0, 0, getWidth(), getHeight(), null);
	}
	
	public ColorSelector(ActionListener confirmAction) {
		//set up the button so we can proceed
		confirmButton = new JButton("Confirm", new ImageIcon("grey_button00.png"));
		confirmButton.setFont(new Font("KenVector Future",Font.BOLD,16));
		confirmButton.setHorizontalTextPosition(JButton.CENTER);
		confirmButton.setVerticalTextPosition(JButton.CENTER);
		confirmButton.addActionListener(confirmAction);
		confirmButton.setBorderPainted(false);
		confirmButton.setEnabled(false);
		
		//The top of the panel, the label
		JLabel selectPlayerLabel = new JLabel(selectColorString);
		selectPlayerLabel.setFont(new Font("KenVector Future",Font.BOLD,32));
		
		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.add(selectPlayerLabel);
		
		//The middle of the panel, the color buttons
		JPanel centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		centerPanel.setOpaque(false);
		Font buttonFont = new Font("KenVector Future",Font.BOLD,16);
		optionButtons = new JButton[numOptions];
		for(int i = 0; i < numOptions; ++i) { 
			if(colorNames[i].equals("Red")){
				optionButtons[i] = new JButton(colorNames[i], new ImageIcon("red_button00.png"));	
				gbc.gridx=0;
				gbc.gridy=0;
			}
			else if(colorNames[i].equals("Blue")){
				optionButtons[i] = new JButton(colorNames[i], new ImageIcon("blue_button00.png"));
				gbc.gridx=1;
				gbc.gridy=0;
			}
			else if(colorNames[i].equals("Green")){
				optionButtons[i] = new JButton(colorNames[i], new ImageIcon("green_button00.png"));
				gbc.gridx=0;
				gbc.gridy=1;
			}
			else{
				optionButtons[i] = new JButton(colorNames[i], new ImageIcon("yellow_button00.png"));
				gbc.gridx=1;
				gbc.gridy=1;
			}
			optionButtons[i].setFont(buttonFont);
			optionButtons[i].setBackground(colors[i]);
			optionButtons[i].setHorizontalTextPosition(JButton.CENTER);
			optionButtons[i].setVerticalTextPosition(JButton.CENTER);
			optionButtons[i].setBorderPainted(false);
			final int buttonSelection = i;
			optionButtons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					selection = colors[buttonSelection];
					for(JButton button : optionButtons) button.setEnabled(true);
					optionButtons[buttonSelection].setEnabled(false);
					confirmButton.setEnabled(true);
				}
			});
			centerPanel.add(optionButtons[i], gbc);
		}
		centerPanel.setBorder(new EmptyBorder(spacing));
		
		//The bottom of the panel, the confirm button
		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.X_AXIS));
		bottomPanel.setBorder(new EmptyBorder(spacing));
		bottomPanel.add(Box.createGlue());
		bottomPanel.add(confirmButton);
				
		topPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 80));
		
		setLayout(new BorderLayout());
		
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

}
