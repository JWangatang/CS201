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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HostScreenPanel extends JPanel
{
   public static final long serialVersionUID = 17;
   
   private JLabel hostLabel;
   private JLabel portLabel;
   private JTextField portTF;
   private JButton submitButton;
   private JPanel buttonPanel, hostPanel;
   
   
   HostScreenPanel(ActionListener actionListener)
   {
      initializeComponents();
      createGUI();
      submitButton.addActionListener(actionListener);    
      submitButton.setEnabled(false);
   }
   
   public void initializeComponents()
   {
	   
	   Font font;  
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("images/Riffic.ttf"));
			Font styledAndSized = font.deriveFont(42F);			  
		      hostLabel = new JLabel("Host");
		      hostLabel.setFont(styledAndSized);
		      portLabel = new JLabel("Port:");
		      styledAndSized = font.deriveFont(28F);
		      portLabel.setFont(styledAndSized);
		      portTF = new JTextField(5);
		      portTF.setFont(styledAndSized);
		      
		      portTF.getDocument().addDocumentListener(new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {changed();}
					public void removeUpdate(DocumentEvent e) {changed();}
					public void insertUpdate(DocumentEvent e) {changed();}
					public void changed(){
						if(portTF.getText().equals("")){
							submitButton.setEnabled(false);
						}
						else{
							submitButton.setEnabled(true);
						}
					}
		      });
		      
		      submitButton = new JButton("Submit");
		      styledAndSized = font.deriveFont(20F);
		      submitButton.setFont(styledAndSized);
		      submitButton.setPreferredSize(new Dimension(200,50));
		      buttonPanel = new JPanel();
		      hostPanel = new JPanel();
			
			
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
   public void createGUI()
   {
      setLayout(new BorderLayout());
      
      buttonPanel.add(submitButton);
      buttonPanel.setSize(400, 400);
      hostPanel.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      ImageIcon imageIcon = new ImageIcon("images/logo.png"); // load the image to a imageIcon
      Image image = imageIcon.getImage(); // transform it
      Image newimg = image.getScaledInstance(600, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
      imageIcon = new ImageIcon(newimg);  // transform it back
      JLabel scrabbleLabel = new JLabel(imageIcon, SwingConstants.CENTER);
      add(scrabbleLabel, BorderLayout.NORTH);
      gbc.gridx = 0;
      gbc.gridy = 0;
      hostPanel.add(hostLabel, gbc);
      gbc.gridx = 0;
      gbc.insets = new Insets(46, 0, 46, 0);
      gbc.gridy = 1;
      hostPanel.add(portLabel, gbc);
      gbc.gridx = 1;
      gbc.gridy = 1;
      gbc.anchor = GridBagConstraints.EAST;
      hostPanel.add(portTF, gbc);
      gbc.anchor = GridBagConstraints.CENTER;
      gbc.gridx = 0;
      gbc.gridy = 2;
      gbc.gridwidth = 2;
      hostPanel.add(buttonPanel, gbc);
      add(hostPanel, BorderLayout.CENTER);
   }
   
	public int getPort() {
		return Integer.parseInt(portTF.getText());
	}
   
}
