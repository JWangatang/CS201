package gameScreens;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import about.About;
import help.Help;
import score.Score;

public class ScrabbleClientFrame extends JFrame{
	
	private static final long serialVersionUID = 5147395078473323173L;
	private final static Dimension Size = new Dimension(650,700);
	

	//Constructor
	public ScrabbleClientFrame(){
		setTitle("Scrabble With Friends");
		setSize(Size);
		setMinimumSize(Size);
		setMaximumSize(Size);
		add(new ClientPanel());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setJMenuBar(CreateMenuBar());
	}
	
	public JMenuBar CreateMenuBar() {
		try{
	        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("Images/cursor.png").getImage(),new Point(0,0),"custom cursor"));
        }catch(Exception e){
        	System.out.println(e.getMessage());
        }
		
		
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("images/Riffic.ttf")).deriveFont(14F);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		//Create main bar
		JMenuBar jmb = new JMenuBar();
		
		//Add about
		JMenuItem about = new JMenuItem("About");
    	about.setMnemonic('A');
    	about.setFont(font);
    	about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
		about.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				About about = new About();
				about.show();
			}
		});
		jmb.add(about);
    	
		//Add help
		JMenuItem help = new JMenuItem("Help");
		help.setFont(font);
    	help.setMnemonic('H');
    	help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		help.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				Help help = new Help();
				help.show();
			}
		});
		jmb.add(help);
		
		JMenuItem scores = new JMenuItem("Scores");
		scores.setFont(font);
		scores.setMnemonic('S');
		scores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		scores.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ae) {
				Score score = new Score();
				score.show();
			}
		});
		jmb.add(scores);
    	
		return jmb;
	}
	
	public static void main (String args[]){
		ScrabbleClientFrame scf = new ScrabbleClientFrame();
		scf.setVisible(true);
	}
}
