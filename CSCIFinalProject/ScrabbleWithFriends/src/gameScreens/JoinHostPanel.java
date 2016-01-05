package gameScreens;
import java.awt.BorderLayout;
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

public class JoinHostPanel extends JPanel{

	private static final long serialVersionUID = -1291520282908333459L;
	private static JLabel joinHostLabel, logoLabel;
	private static JButton joinButton, hostButton;
	private static ImageIcon logo;
	
	public JoinHostPanel(ActionListener actionListener, ActionListener actionListener2){
		initializeComponents();
		createGUI();
		joinButton.addActionListener(actionListener);
		hostButton.addActionListener(actionListener2);
	}
	
	private void initializeComponents() {
		logo = new ImageIcon("images/logo.png"); // load the image to a imageIcon
		Image image = logo.getImage(); // transform it 
		image = image.getScaledInstance(600, 120,java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		logo = new ImageIcon(image);// transform it back
		logoLabel = new JLabel(logo, SwingConstants.CENTER);
		
		Font font;
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("images/Riffic.ttf"));
			Font styledAndSized = font.deriveFont(42F);
			
			
			joinHostLabel = new JLabel("Join or Host?");
			joinHostLabel.setFont(styledAndSized);
			styledAndSized = font.deriveFont(20F);
			joinButton = new JButton("Join");
			joinButton.setFont(styledAndSized);
			joinButton.setPreferredSize(new Dimension(200,50));
			hostButton = new JButton("Host");
			hostButton.setFont(styledAndSized);
			hostButton.setPreferredSize(new Dimension(200,50));
			
			
			
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
	
	private void createGUI() {
		setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JPanel top = new JPanel();
		top.setLayout(new GridBagLayout());
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(100,0,50,0);
		top.add(logoLabel,gbc);
		gbc.gridy = 2;
		gbc.insets = new Insets(0,0,0,0);
		top.add(joinHostLabel, gbc);
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridBagLayout());
		gbc.gridy = 1;
		bottom.add(joinButton, gbc);
		gbc.gridy = 2;
		bottom.add(hostButton, gbc);

		
		add(bottom, BorderLayout.CENTER);
		add(top, BorderLayout.NORTH);
		
		
				
	}
}
