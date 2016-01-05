package sorryclient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/*
 * The main window for Sorry!
 * */
public class SorryClientWindow extends JFrame{
	private static final long serialVersionUID = 5147395078473323173L;
	
	//Dimensions for the game
	private final static Dimension minSize = new Dimension(640,480);
	private final static Dimension maxSize = new Dimension(960,640);
	
	private final JMenuBar menuBar;
	private final JMenuItem helpBar, topScoresBar;
	
	private final String helpText = "Starting the Game:\n"
			+ "      -Press 'START'\n"
			+ "      -Select number of players\n"
			+ "      -Select your color\n\n"
			+ "Playing the Game:\n"
			+ "      -Draw a card\n"
			+ "      -Select a square that is valid\n"
			+ "      -Get all the pieces\n"
			+ "      from start to home!";
	
	private static Hashtable<String, Integer> userRecords = new Hashtable<String,Integer>();
	
	public static Hashtable<String, Integer> getRecords(){ return userRecords;}
	
	{ //Construct
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("cursorHand_blue.png").getImage(),
				new Point(0,0),"cursorHand_blue.png"));
		
		setTitle("Sorry!");
		setSize(minSize);
		setMinimumSize(minSize);
		setMaximumSize(maxSize);
		menuBar = new JMenuBar();
		
		helpBar = new JMenuItem("Help");
		KeyStroke helpKey = KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK);
		helpBar.setMnemonic('H');
		helpBar.setAccelerator(helpKey);
		menuBar.add(helpBar);
		
		topScoresBar = new JMenuItem("Top Scores");
		KeyStroke scoresKey = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		topScoresBar.setAccelerator(scoresKey);
		topScoresBar.setMnemonic('S');
		menuBar.add(topScoresBar);
		
		HelpMenuItemListener hmil = new HelpMenuItemListener();
		ScoreMenuItemListener smil = new ScoreMenuItemListener();
		
		helpBar.addActionListener(hmil);
		topScoresBar.addActionListener(smil);
		
		setJMenuBar(menuBar);
		add(new ClientPanel());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	class HelpMenuItemListener implements ActionListener {
	      public void actionPerformed(ActionEvent e) {            
	         JDialog jd = new JDialog();
	         JPanel jp = new JPanel(){
	        	 private static final long serialVersionUID = 1L;
	 			
	 			@Override
	 			protected void paintComponent(Graphics g){
	 				super.paintComponent(g);
	 				g.drawImage(new ImageIcon("card_beigeLight.png").getImage(), 0, 0, getWidth(), getHeight(), null);
	 			}
	         };
	         
	         jp.setLayout(new GridBagLayout());
	         GridBagConstraints gbc = new GridBagConstraints();
	         
	         JLabel jl = new JLabel("Sorry! Instructions");
	         jl.setFont(new Font("KenVector Future", Font.BOLD, 18));
	         gbc.gridx = 0;
	         gbc.gridy = 0;
	         gbc.ipady = 80;
	         jp.add(jl, gbc);
	         
	         JTextArea helpTextArea = new JTextArea(helpText);
	         helpTextArea.setFont(new Font("KenVector Future", Font.BOLD, 12));
	         helpTextArea.setWrapStyleWord(true);
	         helpTextArea.setEditable(false);
	         helpTextArea.setOpaque(false);
	         
	         gbc.gridx=0;
	         gbc.gridy=1;
	         jp.add(helpTextArea, gbc);
	         
	         jd.add(jp);
	         
	         jd.setSize(300, 400);
	         jd.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	         jd.setVisible(true);
	      }    
	   }
	
	class ScoreMenuItemListener implements ActionListener {
	      public void actionPerformed(ActionEvent e) {            
	    	  JDialog jd = new JDialog();
		      jd.setSize(300, 400);
		      jd.setVisible(true);
		      
		      JPanel jp = new JPanel();
		      jp.setLayout(new BorderLayout());
		      
		      String[] columns = {"Name", "Score"};
		      String[][]data = new String[userRecords.size()][2];
		      
		      Set<String>keys = userRecords.keySet();
		      
		      String[] sortedKeys = new String[keys.size()];
		      int index=0;
		      for(String k: keys){
		    	  sortedKeys[index]=k;
		    	  index++;
		      }
		      
		      //Selection Sort
		      for(int i=0; i<sortedKeys.length-1; i++){
		    	  int count = i;
		    	  for(int j=i+1; j<sortedKeys.length;j++){
		    		  if(userRecords.get(sortedKeys[j])>userRecords.get(sortedKeys[count]))
		    			  count = j;
		    	  }
	    		  String bigger = sortedKeys[count];
	    		  sortedKeys[count] = sortedKeys[i];
	    		  sortedKeys[i]=bigger;
		
		      }
		      
		      for(int i=0; i<sortedKeys.length; i++){
		    	  data[i][0]=sortedKeys[i];
		    	  data[i][1]=""+userRecords.get(sortedKeys[i]);
		      }
		      
		      JTable table = new JTable(data, columns);
		      table.setEnabled(false);
		      JScrollPane jsp = new JScrollPane(table);
		      jp.add(jsp, BorderLayout.CENTER);
		      jd.add(jp);
	      }    
	}
	
	private static void parseUsers(String fn){
		try{
			
			FileReader fr = new FileReader(fn);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while(line!=null){
				String username = line;
				line = br.readLine();
				int score = Integer.parseInt(line);
				userRecords.put(username, score);
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException fnfe){
			System.out.println("FileNotFoundException: " + fnfe.getMessage());
			System.out.println("Creating new file for user records: user_records.txt");
			FileWriter fw = null;
			try{
				fw = new FileWriter("user_records.txt");
			}
			catch(IOException ioe){
				System.out.println("IOException: " + ioe.getMessage());
			}
			finally{
				try{
					if(fw!=null)
						fw.close();
				}
				catch(IOException ioe){
					System.out.println("IOException: " + ioe.getMessage());
				}
			}
		} catch (IOException ioe){
			System.out.println("IOException: " + ioe.getMessage());
		}
		
	}
	
	public static void main(String[] args) {
		parseUsers("user_records.txt");
		//Create a new SorryClient Window
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	new SorryClientWindow();
		    }
		});
	}
	
}
