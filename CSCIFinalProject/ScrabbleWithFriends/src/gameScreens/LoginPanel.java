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
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class LoginPanel extends JPanel {
	private static final long serialVersionUID = 2944485969232183986L;
	
	private JLabel titleLabel;
	private JLabel logo;
	private JLabel usernameLabel;
	JTextField usernameTF;
	private JLabel passwordLabel;
	JTextField passwordTF;
	private JButton loginButton;
	private JButton createAccountButton;
	private JButton playAsGuestButton;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel bottomPanel;
	private DocumentListener documentListener;

	public LoginPanel(ActionListener loginAction, ActionListener createAccountAction, ActionListener playAsGuestAction) {
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		Font font;
		try {
		font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("images/Riffic.ttf"));
		Font styledAndSized = font.deriveFont(42F);
		titleLabel = new JLabel("    Registered User or Guest?");
		titleLabel.setFont(styledAndSized);
		topPanel.add(titleLabel, BorderLayout.CENTER);
		middlePanel = new JPanel();
		middlePanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10,0,10,0);
		
		documentListener = new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {changed();}
			public void removeUpdate(DocumentEvent e) {changed();}
			public void insertUpdate(DocumentEvent e) {changed();}
			public void changed(){
				if(usernameTF.getText().equals("") || passwordTF.getText().equals("")){
					loginButton.setEnabled(false);
				}
				else{
					loginButton.setEnabled(true);
				}
			}
		};
		
		styledAndSized = font.deriveFont(28F);
		usernameLabel = new JLabel("Username: ");
		usernameLabel.setFont(styledAndSized);
		usernameTF = new JTextField(15);
		usernameTF.setFont(styledAndSized);
		usernameTF.getDocument().addDocumentListener(documentListener);
		middlePanel.add(usernameLabel, gbc);
		gbc.gridx = 1;
		middlePanel.add(usernameTF, gbc);		
		passwordLabel = new JLabel("Password: ");
		passwordLabel.setFont(styledAndSized);
		gbc.gridy = 1;
		gbc.gridx = 0;
		middlePanel.add(passwordLabel, gbc);
		gbc.gridx = 1;
		passwordTF = new JTextField(15);
		passwordTF.setFont(styledAndSized);
		passwordTF.getDocument().addDocumentListener(documentListener);
		middlePanel.add(passwordTF, gbc);		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		styledAndSized = font.deriveFont(20F);
		loginButton = new JButton("Login");
		loginButton.setFont(styledAndSized);
		loginButton.setPreferredSize(new Dimension(200,50));
		loginButton.addActionListener(loginAction);
		loginButton.setEnabled(false);		
		gbc.gridx = 0;
		gbc.gridy = 0;
		bottomPanel.add(loginButton, gbc);	
		createAccountButton = new JButton("Create Account");
		createAccountButton.setFont(styledAndSized);
		createAccountButton.setPreferredSize(new Dimension(200,50));
		createAccountButton.addActionListener(createAccountAction);		
		gbc.gridy = 1;
		bottomPanel.add(createAccountButton, gbc);		
		playAsGuestButton = new JButton("Play As Guest");
		playAsGuestButton.setFont(styledAndSized);
		playAsGuestButton.setPreferredSize(new Dimension(200,50));
		playAsGuestButton.addActionListener(playAsGuestAction);
		gbc.gridy = 2;
		bottomPanel.add(playAsGuestButton, gbc);	
		setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;		
		centerPanel.add(middlePanel, gbc);
		gbc.gridy = 1;
		centerPanel.add(bottomPanel, gbc);
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		} catch (IOException ioe)
		{
			System.out.println("ioe: " + ioe.getMessage());
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		ImageIcon imageIcon = new ImageIcon("images/logo.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(600, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		logo = new JLabel(imageIcon, SwingConstants.CENTER);
		topPanel.add(logo, BorderLayout.NORTH);
		
		
		
		
	}
}
