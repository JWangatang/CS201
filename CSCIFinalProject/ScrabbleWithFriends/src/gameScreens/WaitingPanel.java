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
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class WaitingPanel extends JPanel{

	private static final long serialVersionUID = 2031176103574597378L;
	
	private static JButton j;

	public WaitingPanel(ActionListener actionListener) {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		Font font;
			
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("images/Riffic.ttf"));
			Font styledAndSized = font.deriveFont(36F);
				
			JLabel waitingLabel = new JLabel(" Waiting for more friends...");
			waitingLabel.setFont(styledAndSized);
			j = new JButton ("Continue");
			j.setEnabled(false);
			j.setPreferredSize(new Dimension(200,50));
			styledAndSized = font.deriveFont(20F);
			j.setFont(styledAndSized);
			j.addActionListener(actionListener);

			ImageIcon imageIcon = new ImageIcon("images/logo.png"); // load the image to a imageIcon
			Image image = imageIcon.getImage(); // transform it 
			Image newimg = image.getScaledInstance(600, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			imageIcon = new ImageIcon(newimg);  // transform it back
			JLabel logo = new JLabel(imageIcon, SwingConstants.CENTER);
			
			gbc.insets = new Insets(50,0,0,0);
			gbc.gridy = 0;
			add(logo, gbc);
			gbc.insets = new Insets(50,0,50,0);
			gbc.gridy = 1;
			add(waitingLabel, gbc); 
			gbc.gridy = 2;
			add(j, gbc);
			
				
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (FontFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
	
	public static void enableButton() {
		j.setEnabled(true);
	}

}
