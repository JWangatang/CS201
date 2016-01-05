package sorryclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import customUI.PaintedButton;
import library.FontLibrary;
import library.ImageLibrary;
import network.ChatMessage;

public class ChatPanel extends JPanel {

	private static final long serialVersionUID = -5415716102198006609L;

	private JScrollPane jsp;
	private PaintedButton send;
	private JTextField jtf;
	private JTextPane jtp;
	private StyledDocument doc;
	private Style style;
	private JPanel bottomPanel;
	private ClientPanel clientPanel;
	
	
	public ChatPanel(ClientPanel cp){
		clientPanel = cp;
		initialize();
		createGUI();
		addActionListeners();
	}
	
	private void initialize(){
		
		bottomPanel = new JPanel();
		
		send = new PaintedButton(
				"Send",
				ImageLibrary.getImage("images/buttons/grey_button00.png"),
				ImageLibrary.getImage("images/buttons/grey_button01.png"),
				12
				);
		
		jtf = new JTextField(50);
		jtf.setFont(FontLibrary.getFont("fonts/kenvector_future_thin.ttf", Font.PLAIN, 12));
		jtf.setBackground(Color.BLACK);
		jtf.setForeground(Color.WHITE);
		
		jtp = new JTextPane();
		jtp.setFont(FontLibrary.getFont("fonts/kenvector_future_thin.ttf", Font.PLAIN, 12));
		jtp.setBackground(Color.BLACK);
		jtp.setSize(960, 100);
		jtp.setEditable(false);
		
//		DefaultCaret caret = (DefaultCaret) jtp.getCaret();
//		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		
		jsp = new JScrollPane(jtp);
		jsp.setPreferredSize(new Dimension(640, 80));
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
	}
	
	private void createGUI(){
		setLayout(new GridBagLayout());
		
		GridBagConstraints g = new GridBagConstraints();

		g.fill = GridBagConstraints.HORIZONTAL;
		g.gridx=0;
		g.gridy=0;
		add(jsp, g);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx=0;
		gbc.gridy=0;
		bottomPanel.add(jtf, gbc);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.EAST;
		bottomPanel.add(send, gbc);
		
		g.gridy=1;
		g.fill = GridBagConstraints.HORIZONTAL;
		add(bottomPanel, g);
		setVisible(true);
	}
	
	private void addActionListeners(){
		send.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				addMessage(jtf.getText(), clientPanel.sorryClient.getColor());
				clientPanel.sorryClient.send(new ChatMessage(jtf.getText(), clientPanel.sorryClient.getColor()));
				jtf.setText("");
			}	
		});
	}
	
	public void addMessage(String s, Color c){	
		doc = jtp.getStyledDocument();
		style = jtp.addStyle("Color Style", null);
		try{
			if(c.equals(Color.RED)){
				StyleConstants.setForeground(style, Color.RED);
				doc.insertString(doc.getLength(), "red: ", style);
			}
			else if(c.equals(Color.BLUE)){

				StyleConstants.setForeground(style, Color.BLUE);
				doc.insertString(doc.getLength(), "blue: ",style);			
			}
			else if(c.equals(Color.GREEN)){

				StyleConstants.setForeground(style, Color.GREEN);
				doc.insertString(doc.getLength(), "green: ",style);			
			}
			else{

				StyleConstants.setForeground(style, Color.YELLOW);
				doc.insertString(doc.getLength(), "yellow: ",style);
			}
			StyleConstants.setForeground(style, Color.WHITE);
			doc.insertString(doc.getLength(), s + "\n", style);
		} catch (BadLocationException e){e.printStackTrace();}
	}
	
	public void addDisconnectMessage(String s, Color c){
		doc = jtp.getStyledDocument();
		style = jtp.addStyle("Color Style", null);
		try{
			StyleConstants.setForeground(style, c);
			doc.insertString(doc.getLength(), s + "\n", style);
		} catch (BadLocationException e){e.printStackTrace();}
	}
}
