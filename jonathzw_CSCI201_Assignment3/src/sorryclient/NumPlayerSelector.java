package sorryclient;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

/*
 * NumPlayerSelector
 * Menu to select a number of players 2-3
 * */
public class NumPlayerSelector extends JPanel {
	private static final long serialVersionUID = -4510696620583889943L;
	
	//The options for number of player selection
	private int selection = -1;
	private final int numOptions = 3;
	private final JRadioButton[] optionButtons;
	private final ButtonGroup buttonGroup;
	
	private JButton confirmButton;
	
	private final String selectNumPlayerString = "Select the number of players";
	
	//The spacing of the border
	private static final Insets spacing = new Insets(20,20,20,20);
	
	public int getNumberOfPlayers() {
		return selection;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	        g.drawImage(new ImageIcon("grey_panel.png").getImage(), 0, 0, getWidth(), getHeight(), null);
	}
	
	public NumPlayerSelector(ActionListener confirmAction){
		//set up the button so we can proceed
		confirmButton = new JButton("Confirm", new ImageIcon("grey_button00.png"));
		confirmButton.setFont(new Font("KenVector Future",Font.BOLD,16));
		confirmButton.setHorizontalTextPosition(JButton.CENTER);
		confirmButton.setVerticalTextPosition(JButton.CENTER);
		confirmButton.addActionListener(confirmAction);
		confirmButton.setBorderPainted(false);
		confirmButton.setEnabled(false);
		
		//The top of the panel, the label
		JLabel selectPlayerLabel = new JLabel(selectNumPlayerString);
		Font sorryFont = new Font("KenVector Future", Font.BOLD, 24);
		selectPlayerLabel.setFont(sorryFont);
		
		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.add(selectPlayerLabel);
		
		//The center panel to hold the button panel
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		//The button panel to hold the buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		buttonGroup = new ButtonGroup();
		optionButtons = new JRadioButton[numOptions];
		for(int i = 0; i < numOptions; ++i) {
			JPanel numPanel = new JPanel();
			optionButtons[i] = new JRadioButton(""+(i+2));
			optionButtons[i].setFont(new Font("KenVector Future",Font.BOLD, 24));
			optionButtons[i].setOpaque(false);
			optionButtons[i].setIcon(new ImageIcon("grey_circle.png"));
			optionButtons[i].setSelectedIcon((new ImageIcon("grey_boxTick.png")));
			optionButtons[i].setBorderPainted(false);
			final int buttonSelection = i+2;
			optionButtons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					selection = buttonSelection;
					confirmButton.setEnabled(true);
				}
			});
			buttonGroup.add(optionButtons[i]);
			numPanel.add(optionButtons[i]);
			numPanel.setOpaque(false);
			buttonPanel.add(Box.createGlue());
			buttonPanel.add(numPanel);
			buttonPanel.add(Box.createGlue());
		}
		
		centerPanel.add(Box.createGlue());
		centerPanel.add(buttonPanel);
		centerPanel.add(Box.createGlue());
		
		//The bottom panel to hold the confirm button
		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.X_AXIS));
		bottomPanel.setBorder(new EmptyBorder(spacing));
		bottomPanel.add(Box.createGlue());
		bottomPanel.add(confirmButton);
				
		setLayout(new BorderLayout());
		topPanel.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 80));
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}
}
