package gameScreens;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainMenuPanel extends JPanel{

	private static final long serialVersionUID = -1270245220698826850L;
	
	private JButton playButton;
	private JLabel logo;
	private JLabel uscLogo;
	private Font font, styledAndSized;

	public MainMenuPanel(ActionListener actionListener) {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		ImageIcon imageIcon2 = new ImageIcon("images/usc.png"); // load the image to a imageIcon
		Image image2 = imageIcon2.getImage(); // transform it 
		Image newimg2 = image2.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon2 = new ImageIcon(newimg2);  // transform it back
		uscLogo = new JLabel(imageIcon2, SwingConstants.CENTER);
				
		ImageIcon imageIcon = new ImageIcon("images/logo.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(600, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		logo = new JLabel(imageIcon, SwingConstants.CENTER);

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("images/Riffic.ttf"));
			styledAndSized = font.deriveFont(20F);

		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		playButton = new JButton("Play");
		playButton.setFont(styledAndSized);
		playButton.setPreferredSize(new Dimension(200,50));
		playButton.addActionListener(actionListener);

		gbc.insets = new Insets(0,0,0,0);
		gbc.gridy = 0;
		add(logo, gbc);
		gbc.gridy = 1;
		gbc.insets = new Insets(30,0,30,0);
		add(uscLogo, gbc);
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridy = 2;
		add(playButton, gbc);
	
		
	}

}
