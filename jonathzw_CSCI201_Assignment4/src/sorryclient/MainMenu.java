package sorryclient;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import customUI.PaintedButton;
import customUI.PaintedPanel;
import library.ImageLibrary;

public class MainMenu extends PaintedPanel{
	private static final long serialVersionUID = 3609831945869059312L;
	
	private final JButton host;
	private final JButton join;
	private final JPanel hostJoinPanel;
	
	public MainMenu(ActionListener hostAction, ActionListener joinAction, Image inImage){
		super(inImage,true);
		
		hostJoinPanel = new JPanel();
		hostJoinPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		host = new PaintedButton(
				"Host",
				ImageLibrary.getImage("images/buttons/grey_button00.png"),
				ImageLibrary.getImage("images/buttons/grey_button01.png"),
				22
				);
		host.addActionListener(hostAction);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 100;
		gbc.ipady = 25;
		gbc.insets = new Insets(30, 30, 30, 30);
		hostJoinPanel.add(host,gbc);
		
		join = new PaintedButton(
				"Join",
				ImageLibrary.getImage("images/buttons/grey_button00.png"),
				ImageLibrary.getImage("images/buttons/grey_button01.png"),
				22
				);
		join.addActionListener(joinAction);		
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		hostJoinPanel.add(join,gbc);
		
		Image titleImage = ImageLibrary.getImage("images/sorry.png");
		PaintedPanel titlePanel = new PaintedPanel(titleImage);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();

		gbc1.ipadx = titleImage.getWidth(null);
		gbc1.ipady = titleImage.getHeight(null);
		gbc1.insets = new Insets(30,30,30,30);
		gbc1.gridy = 1;	
		add(titlePanel,gbc1);
		
		gbc1.ipadx = 100;
		gbc1.ipady = 25;
		gbc1.gridy = 2;
		add(hostJoinPanel,gbc1);
	}
	
}
