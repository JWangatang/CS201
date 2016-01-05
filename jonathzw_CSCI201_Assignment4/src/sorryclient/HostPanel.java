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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import customUI.PaintedButton;
import customUI.PaintedPanel;
import library.FontLibrary;
import library.ImageLibrary;

public class HostPanel extends PaintedPanel{

	private static final long serialVersionUID = 7432083141632844078L;
	
	private final JLabel port;
	private final JFormattedTextField portField;
	private final JPanel portPanel;
	private final JButton start;
	private int portNumber = -1;
	
	public HostPanel(ActionListener al, Image inImage) {
		super(inImage);
		
		port = new JLabel("Port: ");
		port.setFont(FontLibrary.getFont("fonts/kenvector_future_thin.ttf", Font.PLAIN, 28));
				
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);
		portField = new JFormattedTextField(decimalFormat);
		portField.setColumns(6);
		portField.setFont(FontLibrary.getFont("fonts/kenvector_future_thin.ttf", Font.PLAIN, 28));
		
		start = new PaintedButton(
				"Start",
				ImageLibrary.getImage("images/buttons/grey_button00.png"),
				ImageLibrary.getImage("images/buttons/grey_button01.png"),
				20
				);
		
		start.setEnabled(false);
		
		portField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {changed();}
			public void removeUpdate(DocumentEvent e) {changed();}
			public void insertUpdate(DocumentEvent e) {changed();}
			public void changed(){
				if(portField.getText().equals("")){
					start.setEnabled(false);
				}
				else{
					portNumber = Integer.parseInt(portField.getText());
					start.setEnabled(true);
				}
			}
		});
		
		start.addActionListener(al);
		
		portPanel = new JPanel (new FlowLayout());
		portPanel.add(port);
		portPanel.add(portField);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.ipadx = 200;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(30, 30, 30, 30);
		add(portPanel, gbc);
		
		gbc.gridy=2;
		gbc.fill = GridBagConstraints.NONE;
		gbc.ipadx = 100;
		gbc.ipady = 25;
		add(start, gbc);
			

	}
	
	public int getPortNumber(){
		return portNumber;
	}
}
