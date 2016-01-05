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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CreateAccountPanel extends JPanel{

	private static final long serialVersionUID = 4670286542190223407L;
	private JLabel usernameLabel, passwordLabel;
	JTextField usernameTextField, passwordTextField;
	private JButton submitButton;
	private ImageIcon logo;
	private JLabel logoLabel;
	
	public CreateAccountPanel(ActionListener actionListener){
		initializeComponents();
		createGUI();
		submitButton.addActionListener(actionListener);
	}
	
	private void initializeComponents() {
		logo = new ImageIcon("images/logo.png"); // load the image to a imageIcon
		Image image = logo.getImage(); // transform it
		Image newimg = image.getScaledInstance(600, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		logo = new ImageIcon(newimg);  // transform it back
		logoLabel = new JLabel(logo, SwingConstants.CENTER);
		Font font;
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("images/Riffic.ttf"));
			
			Font styledAndSized = font.deriveFont(20F);
			usernameLabel = new JLabel("Username:");
			passwordLabel = new JLabel("Password:");
			submitButton = new JButton("Submit");
			
			submitButton.setFont(styledAndSized);
			submitButton.setEnabled(false);
			submitButton.setPreferredSize(new Dimension(200,50));
			styledAndSized = font.deriveFont(28F);
			DocumentListener documentListener = new DocumentListener() {
				public void changedUpdate(DocumentEvent e) {changed();}
				public void removeUpdate(DocumentEvent e) {changed();}
				public void insertUpdate(DocumentEvent e) {changed();}
				public void changed(){
					if(usernameTextField.getText().equals("") || passwordTextField.getText().equals("")){
						submitButton.setEnabled(false);
					}
					else{
						submitButton.setEnabled(true);
					}
				}
			};
			
			usernameTextField = new JTextField(15);
			usernameLabel.setFont(styledAndSized);
			usernameTextField.setFont(styledAndSized);
		
			passwordTextField = new JTextField(15);
			passwordLabel.setFont(styledAndSized);
			passwordTextField.setFont(styledAndSized);
			passwordTextField.getDocument().addDocumentListener(documentListener);

			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	private void createGUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JPanel top = new JPanel();
		top.add(logoLabel);
		JPanel center = new JPanel();
		center.setLayout(new GridBagLayout());
		gbc.gridx = 1;
		gbc.gridy = 1;
		center.add(usernameLabel, gbc);
		gbc.gridx = 2;
		gbc.gridy = 1;
		center.add(usernameTextField, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		center.add(passwordLabel, gbc);
		gbc.gridx = 2;
		gbc.gridy = 2;
		center.add(passwordTextField, gbc);
		JPanel bottom = new JPanel();
		
		bottom.add(submitButton);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(top, gbc);
		gbc.insets = new Insets(50,0,20,0);
		gbc.gridy = 2;
		add(center, gbc);
		gbc.gridy = 3;
		add(bottom, gbc);
	}
}
