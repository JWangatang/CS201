package sorryclient;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import customUI.PaintedButton;
import customUI.PaintedPanel;
import library.FontLibrary;
import library.ImageLibrary;

public class JoinPanel extends PaintedPanel{

	private static final long serialVersionUID = 5060142790611023866L;
	
	private JLabel portLabel, ipLabel;
	private JFormattedTextField portTF;
	private JTextField ipTF;
	private JButton connect;
	
	private JPanel portPanel, ipPanel;
	
	private int portNumber = -1;
	private String ipAddress = null;

	public JoinPanel(ActionListener al, Image inImage) {
		super(inImage);
		
		portLabel = new JLabel("Port: ");
		portLabel.setFont(FontLibrary.getFont("fonts/kenvector_future_thin.ttf", Font.PLAIN, 28));
		
		ipLabel = new JLabel("IP: ");
		ipLabel.setFont(FontLibrary.getFont("fonts/kenvector_future_thin.ttf", Font.PLAIN, 28));
		
		ipTF = new JTextField(12);
		ipTF.setFont(FontLibrary.getFont("fonts/kenvector_future_thin.ttf", Font.PLAIN, 28));
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);
		portTF = new JFormattedTextField(decimalFormat);
		portTF.setColumns(6);
		portTF.setFont(FontLibrary.getFont("fonts/kenvector_future_thin.ttf", Font.PLAIN, 28));

		portTF.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {changed();}
			public void removeUpdate(DocumentEvent e) {changed();}
			public void insertUpdate(DocumentEvent e) {changed();}
			public void changed(){
				if(portTF.getText().equals("") || ipTF.getText().equals("")){
					connect.setEnabled(false);
				}
				else{
					portNumber = Integer.parseInt(portTF.getText());
					ipAddress = ipTF.getText();
					connect.setEnabled(true);
				}
			}
		});
		
		ipTF.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {changed();}
			public void removeUpdate(DocumentEvent e) {changed();}
			public void insertUpdate(DocumentEvent e) {changed();}
			public void changed(){
				if(portTF.getText().equals("") || ipTF.getText().equals("")){
					connect.setEnabled(false);
				}
				else{
					portNumber = Integer.parseInt(portTF.getText());
					ipAddress = ipTF.getText();
					connect.setEnabled(true);
				}
			}
		});
		
		connect = new PaintedButton("Connect",
				ImageLibrary.getImage("images/buttons/grey_button00.png"),
				ImageLibrary.getImage("images/buttons/grey_button01.png"),
				20
				);
		connect.setEnabled(false);
		connect.addActionListener(al);
		
		portPanel = new JPanel(new FlowLayout());
		portPanel.add(portLabel);
		portPanel.add(portTF);
		
		ipPanel = new JPanel(new FlowLayout());
		ipPanel.add(ipLabel);
		ipPanel.add(ipTF);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
		
		add(ipPanel, gbc);
		
		gbc.gridy=1;
		add(portPanel, gbc);
		
		gbc.insets = new Insets(30, 30, 30, 30);
		gbc.gridy=2;
		gbc.ipadx = 100;
		gbc.ipady = 25;
		add(connect, gbc);
		
	}
	
	public int getPortNumber(){
		return portNumber;
	}
	
	public String getIPAddress(){
		return ipAddress;
	}
}
