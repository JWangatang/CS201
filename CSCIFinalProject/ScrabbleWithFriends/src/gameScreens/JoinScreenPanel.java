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

public class JoinScreenPanel extends JPanel
{
   public static final long serialVersionUID = 17;
   
   private JLabel joinLabel, portLabel, IPLabel;
   private JTextField portTF, IPTF;
   private JButton connectButton;
   private JPanel buttonPanel, joinPanel, joinLabelPanel, ipPortPanel;
   
   JoinScreenPanel(ActionListener actionListener)
   {
      initializeComponents();
      createGUI();
      connectButton.addActionListener(actionListener);
      connectButton.setEnabled(false);
   }
   
   public void initializeComponents()
   {   
	   Font font;
	   try {
		font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("images/Riffic.ttf"));
		Font styledAndSized = font.deriveFont(42F);

		 joinLabel = new JLabel("Join");
	     joinLabel.setFont(styledAndSized);
	     portLabel = new JLabel("Port: ");
	     styledAndSized = font.deriveFont(28F);
	     portLabel.setFont(styledAndSized);
	     portTF = new JTextField(15);
	     portTF.setFont(styledAndSized);
	     IPLabel = new JLabel("IP: ");
	     IPTF = new JTextField(15);
	     IPLabel.setFont(styledAndSized);
	     IPTF.setFont(styledAndSized);
	     joinLabelPanel = new JPanel();
	     joinPanel = new JPanel();
	     buttonPanel = new JPanel();
	     connectButton = new JButton("Connect");
	     styledAndSized = font.deriveFont(20F);
	     connectButton.setFont(styledAndSized);
	     connectButton.setPreferredSize(new Dimension(200,50));
	     ipPortPanel = new JPanel();
	     
	     portTF.getDocument().addDocumentListener(new DocumentListener() {
				public void changedUpdate(DocumentEvent e) {changed();}
				public void removeUpdate(DocumentEvent e) {changed();}
				public void insertUpdate(DocumentEvent e) {changed();}
				public void changed(){
					if(portTF.getText().equals("") || IPTF.getText().equals("")){
						connectButton.setEnabled(false);
					}
					else{
						connectButton.setEnabled(true);
					}
				}
	      });
	     
	     IPTF.getDocument().addDocumentListener(new DocumentListener() {
				public void changedUpdate(DocumentEvent e) {changed();}
				public void removeUpdate(DocumentEvent e) {changed();}
				public void insertUpdate(DocumentEvent e) {changed();}
				public void changed(){
					if(portTF.getText().equals("") || IPTF.getText().equals("")){
						connectButton.setEnabled(false);
					}
					else{
						connectButton.setEnabled(true);
					}
				}
	      });
		
		
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
      joinPanel.setLayout(new BorderLayout());
      ipPortPanel.setLayout(new GridBagLayout());
      buttonPanel.add(connectButton);
      buttonPanel.setSize(400, 400);
      GridBagConstraints gbc = new GridBagConstraints();
      joinLabelPanel.setLayout(new GridBagLayout());
      GridBagConstraints gbc2 = new GridBagConstraints();
      ImageIcon imageIcon = new ImageIcon("images/logo.png"); // load the image to a imageIcon
      Image image = imageIcon.getImage(); // transform it
      Image newimg = image.getScaledInstance(600, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
      imageIcon = new ImageIcon(newimg);  // transform it back
      JLabel scrabbleLabel = new JLabel(imageIcon, SwingConstants.CENTER);
      
      add(scrabbleLabel, BorderLayout.NORTH);
      gbc2.gridx = 0;
      gbc2.gridy = 0;
      gbc2.insets = new Insets(20, 0, 20, 0);
      joinLabelPanel.add(joinLabel, gbc2);
      joinPanel.add(joinLabelPanel, BorderLayout.NORTH);
      gbc.gridx = 0;
      gbc.gridy = 0;
      ipPortPanel.add(portLabel, gbc);
      gbc.gridx = 1;
      gbc.gridy = 0;
      ipPortPanel.add(portTF, gbc);
      gbc.gridx = 0;
      gbc.gridy = 1;
      ipPortPanel.add(IPLabel, gbc);
      gbc.gridx = 1;
      gbc.gridy = 1;
      ipPortPanel.add(IPTF, gbc);
      joinPanel.add(ipPortPanel, BorderLayout.CENTER);
      add(joinPanel, BorderLayout.CENTER);
      add(buttonPanel, BorderLayout.SOUTH);
   }
   
	public int getPort() {
		return Integer.parseInt(portTF.getText());
	}
	public String getIP() {
		return IPTF.getText();
	}
}
