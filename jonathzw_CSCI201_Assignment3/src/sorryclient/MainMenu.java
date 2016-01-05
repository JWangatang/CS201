package sorryclient;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * MainMenu
 * Menu to hold the start button
 * */
public class MainMenu extends JPanel{
	private static final long serialVersionUID = 3609831945869059312L;
	
	private final JButton start;
	
	@Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	        g.drawImage(new ImageIcon("grey_panel.png").getImage(), 0, 0, getWidth(), getHeight(), null);
	}
	
	public MainMenu(ActionListener startAction){
		start = new JButton("Start", new ImageIcon("grey_button00.png"));
		
		start.setHorizontalTextPosition(JButton.CENTER);
		start.setVerticalTextPosition(JButton.CENTER);
		
		start.setFont(new Font("KenVector Future",Font.BOLD,16));
		
		start.setBorderPainted(false);
		
		start.addActionListener(startAction);
		
		JLabel titleLabel = new JLabel(new ImageIcon("sorry.png"));
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridy = 1;
		gbc.ipady = 100;
		add(titleLabel,gbc);
		
		gbc.gridy = 2;
		gbc.ipady = 0;
		add(start,gbc);
	}
	
}
