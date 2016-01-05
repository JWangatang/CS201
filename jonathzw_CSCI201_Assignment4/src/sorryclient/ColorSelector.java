package sorryclient;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import customUI.ClearPanel;
import customUI.PaintedButton;
import customUI.PaintedPanel;
import library.FontLibrary;
import library.ImageLibrary;
import network.RemoveColor;

public class ColorSelector extends PaintedPanel {
	
	private static final long serialVersionUID = 1900724217285760485L;
	
	private Color selection = null;
	private final int numOptions = 4;
	private final PaintedButton[] optionButtons;
	
	final PaintedButton confirmButton;
	
	private final static String selectColorString = "Select your color";
	
	private final static String[] colorNames = {"Red", "Blue", "Green", "Yellow"};
	private final static Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
	
	private final static Insets spacing = new Insets(60,80,60,80);
	
	private ClientPanel clientPanel;
		
	public Color getPlayerColor() {
		return selection;
	}
	
	public ColorSelector(ActionListener confirmAction, Image inImage, ClientPanel cp) {
		super(inImage,true);
		
		clientPanel = cp;
				
		confirmButton = new PaintedButton(
				"Confirm",
				ImageLibrary.getImage("images/buttons/grey_button00.png"),
				ImageLibrary.getImage("images/buttons/grey_button01.png"),
				22
				);
		confirmButton.addActionListener(confirmAction);
		confirmButton.setEnabled(false);
		
		JLabel selectColorLabel = new JLabel(selectColorString);
		selectColorLabel.setFont(FontLibrary.getFont("fonts/kenvector_future_thin.ttf", Font.PLAIN, 28));
		
		ClearPanel topPanel = new ClearPanel();
		topPanel.setBorder(new EmptyBorder(spacing));
		topPanel.add(selectColorLabel);
		
		ClearPanel centerPanel = new ClearPanel();
		centerPanel.setLayout(new GridLayout(2,2,10,10));
		Font buttonFont = new Font("",Font.BOLD,22);
		optionButtons = new PaintedButton[numOptions];
		for(int i = 0; i < numOptions; ++i) {
			optionButtons[i] = new PaintedButton(
					colorNames[i],
					ImageLibrary.getImage("images/buttons/"+colorNames[i].toLowerCase()+"_button00.png"),
					ImageLibrary.getImage("images/buttons/"+colorNames[i].toLowerCase()+"_button01.png"),
					22
					);
			
			//Action Listeners for radio buttons
			final int buttonSelection = i;
			optionButtons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					
					if(selection!=null){
						System.out.println("Changed button selection from " + selection);
						clientPanel.sorryClient.send(new RemoveColor(selection));
						clientPanel.selectedColors.put(selection, false);
					}
					selection = colors[buttonSelection];
					clientPanel.sorryClient.send(selection);
					clientPanel.selectedColors.put(selection, true);
					
					//for(JButton button : optionButtons) button.setEnabled(true);
										
					updateButtons();
					
					confirmButton.setEnabled(true);
				}
			});
			optionButtons[i].setFont(buttonFont);
			centerPanel.add(optionButtons[i]);
		}
		
		
		//GUI STUFF
		centerPanel.setBorder(new EmptyBorder(new Insets(0, 80, 0, 80)));
		
		ClearPanel bottomPanel = new ClearPanel();
		bottomPanel.setLayout(new GridLayout(1,3));
		bottomPanel.setBorder(new EmptyBorder(spacing));
		bottomPanel.add(Box.createGlue());
		bottomPanel.add(Box.createGlue());
		bottomPanel.add(confirmButton);
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		add(topPanel);
		add(centerPanel);
		add(bottomPanel);
	}
	
	public void updateButtons(){
		System.out.println("------Calling Update Buttons");
		Set<Color> keys = clientPanel.selectedColors.keySet();
		System.out.println("Set:" + keys);
		for(Color c : keys){
			if(clientPanel.selectedColors.get(c)==true){
				System.out.println(c + " is TRUE");
				if(c.equals(Color.RED))
					optionButtons[0].setEnabled(false);
				else if(c.equals(Color.BLUE))
					optionButtons[1].setEnabled(false);
				else if(c.equals(Color.GREEN))
					optionButtons[2].setEnabled(false);
				else
					optionButtons[3].setEnabled(false);
			}
			else{
				System.out.println(c + " is FALSE");
				if(c.equals(Color.RED)) 
					optionButtons[0].setEnabled(true);
				else if(c.equals(Color.BLUE)) 
					optionButtons[1].setEnabled(true);
				else if(c.equals(Color.GREEN)) 
					optionButtons[2].setEnabled(true);
				else
					optionButtons[3].setEnabled(true);
			}
		}
		System.out.println("------END OF Update Buttons");

	}

}
