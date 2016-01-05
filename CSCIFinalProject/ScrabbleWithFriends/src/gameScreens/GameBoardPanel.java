package gameScreens;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import gameComponents.Letter;
import gameComponents.LetterBag;
import gameComponents.LetterPanel;
import gameComponents.LetterRack;
import gameComponents.Tile;
import gameComponents.TileType;
import gameplay.Dictionary;
import gameplay.MoveChecker;
import gameplay.Player;
import network.LettersMessage;
import network.PassMessage;
import network.TurnMessage;

public class GameBoardPanel extends JPanel {
	public static final long serialVersionUID = 17;
	private static JButton swapButton;
	private static JButton passButton;
	private static JButton playButton;
	private static JButton wordSearchButton;
	public Image mImage = null;
	private JPanel boardPanel, rackPanel;
	public static LetterPanel[][] squaresArray = new LetterPanel[15][15];
	public Tile[][] tileArray = new Tile[15][15];
	public Vector<Letter> placedLetters;
	public JDialog swap;
	public JDialog wordSearch;
	public static Dictionary dictionary;
	boolean swapIsActive, firstWord;
	LetterRack userRack, swapRack;
	public LetterBag letterBag;
	public Letter currentLetterSelected;
	private Player user;
	private volatile static int currentPlaying;	
	private int totalNumPlayers;
	
	private static ClientPanel clientPanel;
	
	GameBoardPanel(int noOfPlayers, Player user, ClientPanel cp) {
		this.user = user;
		currentPlaying = 0;

		totalNumPlayers = noOfPlayers;
		clientPanel = cp;
		
		initializeComponents();
		createGUI();
		addEvents();
	}
	
	public void initializeComponents() {	
		boardPanel = new JPanel();
		letterBag = user.getLetterBag();
		dictionary = new Dictionary();		
		
		currentLetterSelected = null;
		
		swapIsActive = false;
		
		swapRack = null;
		
		userRack = user.getLetterRack();
		
		placedLetters = new Vector<Letter>();
		
		firstWord = user.getClient().isHost();
	}
	public void createGUI() {
		setLayout(new BorderLayout());
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridBagLayout());
		
		boardPanel.setLayout(new GridLayout(15,15));
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("images/Riffic.ttf")).deriveFont(14F);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		playButton = new JButton("Play");
		playButton.setFont(font);
		swapButton = new JButton("Swap");
		swapButton.setFont(font);
		passButton = new JButton("Pass");
		passButton.setFont(font);
		wordSearchButton = new JButton("Word Search");	
		wordSearchButton.setFont(font);
		
		rackPanel = new JPanel();
		rackPanel.setLayout(new BorderLayout());
		rackPanel.add(userRack);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		buttonPanel.add(passButton, gbc);
		gbc.gridx = 1;
		buttonPanel.add(swapButton, gbc);
		gbc.gridx = 2;
		buttonPanel.add(playButton, gbc);
		gbc.gridx = 3;
		buttonPanel.add(wordSearchButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		southPanel.add(rackPanel, gbc);
		gbc.gridy = 1;
		southPanel.add(buttonPanel, gbc);
		add(southPanel, BorderLayout.SOUTH);
		
		for(int row = 0; row < 15; row++) {
			for (int col = 0; col < 15; col++) {
				squaresArray[row][col] = new LetterPanel();
				squaresArray[row][col].setLayout(new BorderLayout());
				squaresArray[row][col].setBorder(BorderFactory.createLineBorder(Color.black));
				squaresArray[row][col].setPreferredSize(new Dimension(40,40));
				boardPanel.add(squaresArray[row][col]);
				if(row==0&&col==0 || row==0&&col==7 || row==0&&col==14
					||row==7&&col==0 || row==7&&col==14
					||row==14&&col==0 || row==14&&col==7 || row==14&&col==14){
					squaresArray[row][col].setBackground(new Color(255,102,102));
					JLabel threeWord = new JLabel("3W", SwingConstants.CENTER);
					threeWord.setFont(font);
					squaresArray[row][col].addMouseListener(new MouseListener(row, col));
					squaresArray[row][col].add(threeWord);
					tileArray[row][col] = new Tile(TileType.TRIPLEWORD, row, col);
				} else if(row==7&&col==7){
					ImageIcon imageIcon = new ImageIcon("images/uscIcon.png");
					JLabel blank = new JLabel(imageIcon, SwingConstants.CENTER);
					squaresArray[row][col].addMouseListener(new MouseListener(row, col));
					squaresArray[row][col].add(blank);
					tileArray[row][col] = new Tile(TileType.START, row, col);
				} else if(row==0&&col==3 || row==0&&col==11
					|| row==2&&col==6 || row==2&&col==8
					|| row==3&&col==0 || row==3&&col==7 || row==3&&col==14
					|| row==6&&col==2 || row==6&&col==6 || row==6&&col==8 || row==6&&col==12
					|| row==7&&col==3 || row==7&&col==11 
					|| row==8&&col==2 || row==8&&col==6 || row==8&&col==8 || row==8&&col==12
					|| row==11&&col==0 || row==11&&col==7 || row==11&&col==14
					|| row==12&&col==6 || row==12&&col==8
					|| row==14&&col==3 || row==14&&col==11){
					squaresArray[row][col].setBackground(new Color(204,229,255));
					JLabel twoLetter = new JLabel("2L", SwingConstants.CENTER);
					twoLetter.setFont(font);
					squaresArray[row][col].addMouseListener(new MouseListener(row, col));
					squaresArray[row][col].add(twoLetter);
					tileArray[row][col] = new Tile(TileType.DOUBLELETTER, row, col);
				} else if(row==1&&col==1 || row==2&&col==2 || row==3&&col==3 || row==4&&col==4
					|| row==1&&col==13 || row==2&&col==12 || row==3&&col==11 || row==4&&col==10
					|| row==13&&col==1 || row==12&&col==2 || row==11&&col==3 || row==10&&col==4
					|| row==13&&col==13 || row==12&&col==12 || row==11&&col==11 || row==10&&col==10){
					squaresArray[row][col].setBackground(Color.PINK);
					JLabel twoWord = new JLabel("2W", SwingConstants.CENTER);
					twoWord.setFont(font);
					squaresArray[row][col].addMouseListener(new MouseListener(row, col));
					squaresArray[row][col].add(twoWord);
					tileArray[row][col] = new Tile(TileType.DOUBLEWORD, row, col);
				} else if(row==1&&col==5 || row==1&&col==9
					|| row==5&&col==1 || row==5&&col==5 || row==5&&col==9 || row==5&&col==13
					|| row==9&&col==1 || row==9&&col==5 || row==9&&col==9 || row==9&&col==13
					|| row==13&&col==5 || row==13&&col==9){
					squaresArray[row][col].setBackground(new Color(75,165,245));
					JLabel threeLetter = new JLabel("3L", SwingConstants.CENTER);
					threeLetter.setFont(font);
					squaresArray[row][col].addMouseListener(new MouseListener(row, col));
					squaresArray[row][col].add(threeLetter);
					tileArray[row][col] = new Tile(TileType.TRIPLELETTER, row, col);
				} else {
					JLabel blank2 = new JLabel("");
					squaresArray[row][col].addMouseListener(new MouseListener(row, col));
					squaresArray[row][col].add(blank2);
					tileArray[row][col] = new Tile(TileType.BLANK, row, col);
				}
			}		
		}
		add(boardPanel, BorderLayout.CENTER);
			
	}
	public void addEvents() {
		initializeLetterActionListeners();
		initializePlayerRacks();
		setPlayFunction();
		setPassFunction();
		setSwapFunction();
		setWordSearchFunction();
		if (!user.isAuthenticated()) {
			wordSearchButton.setEnabled(false);
		}
		TurnMessage tm = new TurnMessage(0);
		user.getClient().sendTurnMessage(tm);
	}
	
	public static void endGame(String winner, int score) {
		JOptionPane.showMessageDialog(null, "Game over! Winner's username and score: " + winner + " - " + score);
		
		clientPanel.resetGame();
	}
	
	public static void setCurrentPlaying(int i) {
		currentPlaying = i;
	}
	
	public LetterPanel[][] getSquaresArray() {
		return squaresArray;
	}
	
	public void iteratePlayers(boolean didPass) {
		currentPlaying = currentPlaying+1;
		if (currentPlaying == totalNumPlayers) currentPlaying = 0;
		TurnMessage tm = new TurnMessage(currentPlaying);
		user.getClient().sendTurnMessage(tm);
		if (didPass) {
			int numPasses = user.getClient().getNumPasses();
			numPasses = numPasses+1;
			PassMessage pm = new PassMessage(numPasses);
			user.getClient().sendPassMessage(pm);
		}
		else {
			PassMessage pm = new PassMessage(0);
			user.getClient().sendPassMessage(pm);
		}
	}
	
	public void initializePlayerRacks() {
		user.initializeRack();
	}
	
	public void simulateLetters(int playerNum) {
		currentPlaying = playerNum;
		Vector<String> stringPlacedLetters = new Vector<String>();
		Vector<Integer> locationLetters = new Vector<Integer>();
		if(placedLetters != null) {
			for(Letter l: placedLetters) {
				//first row then column
				locationLetters.add(l.getRow());
                locationLetters.add(l.getColumn());
                String temp = l.getLetter();
                stringPlacedLetters.add(temp);
                temp = null;
			}
		}
		LettersMessage lm = new LettersMessage(stringPlacedLetters, locationLetters);
		user.getClient().sendLettersMessage(lm); 
    }
	
	public static void setLettersOnBoard(Vector<String> stringPlacedLetters,Vector<Integer> locationLetters ) {
		Vector<Letter> letters = new Vector<Letter>();
		if(stringPlacedLetters != null && locationLetters != null) {
			for(String letterString: stringPlacedLetters) {
				String lower = letterString.toLowerCase();
				letters.add(new Letter(letterString,new ImageIcon("Images/"+lower+".png")));
			}
		}
		
		int i = 0;
		while (i < locationLetters.size()) {
			for (Letter l : letters) {
				l.updateRow(locationLetters.get(i));
				l.updateColumn(locationLetters.get(i+1));
				l.setTempOnBoard();
				i+=2;
			}
		}
		for (Letter l : letters) {
			l.setDisabledIcon(l.getIcon());
			l.setEnabled(false);
			
			squaresArray[l.getRow()][l.getColumn()].add(l, BorderLayout.CENTER);
			squaresArray[l.getRow()][l.getColumn()].setLetter(l);
			squaresArray[l.getRow()][l.getColumn()].revalidate();
			squaresArray[l.getRow()][l.getColumn()].repaint();
			squaresArray[l.getRow()][l.getColumn()].setLetter(l);          
		}
    }

	
	public void initializeLetterActionListeners() {		
		for (int i = 0; i < letterBag.totalBagSize(); i++) {
			Letter eachLetter = letterBag.getLetterAt(i);
			LetterActionListener(eachLetter);
		}
	}
	
	public void LetterActionListener(Letter eachLetter) {
		eachLetter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(eachLetter.getLetter()+ " CLICKED!");
				if (!swapIsActive && !eachLetter.tempOnBoard) {
					currentLetterSelected = eachLetter;
					//System.out.println(currentLetterSelected.getLetter() + " at location " + currentLetterSelected.getPlaceOnRack() + " is selected");
				}
				else if (swapIsActive) {
					int oldLetterSpot = eachLetter.getPlaceOnRack();
					eachLetter.changePlaceOnRack(7);
					letterBag.ReAddLetter(eachLetter);
					Letter newLetter = letterBag.drawLetter();
					newLetter.changePlaceOnRack(oldLetterSpot);
					swapRack.setLetterAt(oldLetterSpot, newLetter);
					swapRack.remove(eachLetter);
					for (int i = 0; i < 7; i++) {
						swapRack.remove(swapRack.getLetterAt(i));
						if (i == oldLetterSpot) {
							swapRack.add(newLetter);
						}else {
							swapRack.add(swapRack.getLetterAt(i));
						}
					}
	        		swap.revalidate();
	        		swap.repaint();
				}
				else if (eachLetter.tempOnBoard) {
					for (int i = 0; i < 7; i++) {
						if (userRack.getLetterAt(i) != null) {
							userRack.remove(userRack.getLetterAt(i));
							userRack.revalidate();
							userRack.repaint();
						}
					}
					eachLetter.setTempOnBoard();
	        		squaresArray[eachLetter.getRow()][eachLetter.getColumn()].remove(eachLetter);
	        		squaresArray[eachLetter.getRow()][eachLetter.getColumn()].setLetter(null);
	        		GameBoardPanel.this.revalidate();
	        		GameBoardPanel.this.repaint();
					for (int i = 0; i < 7; i++) {
						if (userRack.getLetterAt(i) != null) {
							userRack.add(userRack.getLetterAt(i));
							userRack.revalidate();
							userRack.repaint();
						}
						else if (eachLetter.getPlaceOnRack() == i) {
							eachLetter.updateRow(-1);
							eachLetter.updateColumn(-1);
							userRack.add(eachLetter);
							userRack.revalidate();
							userRack.repaint();
							placedLetters.remove(eachLetter);
						}
					}
				}
			}
		});
	}
	
	public void setPlayFunction() {
		playButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (placedLetters.size() != 0) {
					if(firstWord){
						boolean isCentered = false;
						for (Letter l : placedLetters) {
							if (l.getRow() == 7 && l.getColumn() == 7) {
								isCentered = true;
							}
						}
						if(!isCentered){
							returnAllLetters();
						}
					}

					MoveChecker moveChecker = new MoveChecker(placedLetters, GameBoardPanel.this);
					if(moveChecker.isValidWord()){
						Vector<Vector<Letter>> validWords = moveChecker.getValidWords();
						calculateScore(validWords);
						if(firstWord) firstWord = false;
						simulateLetters(currentPlaying);
						userRack.replenishRack();
						for(int i =0 ; i< 7; i++){
							Letter l = userRack.getLetterAt(i);
							LetterActionListener(l);
							userRack.remove(l);
							userRack.add(l);
							userRack.revalidate();
							userRack.repaint();
						}
						iteratePlayers(false);
						for(int i=0; i<placedLetters.size(); i++){
							placedLetters.get(i).setDisabledIcon(placedLetters.get(i).getIcon());
							placedLetters.get(i).setEnabled(false);
						}
						placedLetters.removeAllElements();
						
					}
					else{
						returnAllLetters();
					}
				}
				else
				{
				
				}
			}
		});
	}
	
	private void calculateScore(Vector<Vector<Letter>> validWords){
		int wordScore = 0;
		if (validWords != null) {
			for (Vector<Letter> vL : validWords) {
				for (Letter l : vL) {
					int letterValue = l.getLetterValue();
					int scoreMultiplier = 1;
					if(tileArray[l.getRow()][l.getColumn()].getTileType() == TileType.DOUBLELETTER){
						letterValue *= 2;
					}
					else if(tileArray[l.getRow()][l.getColumn()].getTileType() == TileType.TRIPLELETTER){
						letterValue *= 3;
					}
					else if(tileArray[l.getRow()][l.getColumn()].getTileType() == TileType.TRIPLEWORD){
						scoreMultiplier = 3;
					}
					else if(tileArray[l.getRow()][l.getColumn()].getTileType() == TileType.DOUBLEWORD){
						scoreMultiplier = 2;
					}
					wordScore += letterValue;
					wordScore *= scoreMultiplier;
				}
			}
			user.updateScore(wordScore);
		}
		
	}
	
	public void setPassFunction() {
		passButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				iteratePlayers(true);
			}
		});
	}
	
	public void setSwapFunction() {
		swapRack = user.getLetterRack();
		swap = new JDialog();
		swap.setLayout(new GridBagLayout());
		swap.setSize(new Dimension(400,200));
		swap.setLocationRelativeTo(null);
		swap.setModal(true);
		swap.setTitle("Swap Window");
		GridBagConstraints gbc = new GridBagConstraints();
		JButton finishedSwapping = new JButton("Done");
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("images/Riffic.ttf")).deriveFont(14F);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finishedSwapping.setFont(font);
		finishedSwapping.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				swapIsActive = false;
				swap.setVisible(false);
				rackPanel.remove(userRack);
				rackPanel.add(userRack);
				rackPanel.revalidate();
				rackPanel.repaint();
			}
		});
		
		swapButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				swapIsActive = true;
				gbc.gridx = 0;
				gbc.gridy = 0;
				Font font = null;
				try {
					font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("images/Riffic.ttf")).deriveFont(14F);
				} catch (FontFormatException | IOException ioe) {
					// TODO Auto-generated catch block
					ioe.printStackTrace();
				}
				JLabel label = new JLabel("Select Letters to Swap");
				label.setFont(font);
				swap.add(label, gbc);
				
				gbc.gridy = 1;
				swap.add(swapRack, gbc);
				
				gbc.gridy = 2;
				swap.add(finishedSwapping, gbc);
				
				swap.setVisible(true);
			}
		});
	}
	
	public void setWordSearchFunction() {
		wordSearchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {		
				wordSearch = new JDialog();
				wordSearch.setLayout(new GridBagLayout());
				wordSearch.setSize(300,100);
				wordSearch.setLocationRelativeTo(null);
				wordSearch.setModal(true);
				
				GridBagConstraints gbc = new GridBagConstraints();
				
				Font font = null;

				try {
					font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("images/Riffic.ttf")).deriveFont(15F);

					JTextField wordSearchTF = new JTextField();
					wordSearchTF.setFont(font);
					JTextArea wordSearchTA = new JTextArea(100,90);
					wordSearchTA.setFont(font);
					wordSearchTA.setEditable(false);
					wordSearchTA.setBackground(null);
					wordSearch.setTitle("Word Search");
					JButton searchButton = new JButton("Search");
					searchButton.setFont(font);
					searchButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e)
						{
							String input = wordSearchTF.getText().toString();
							boolean isValid = dictionary.isValidWord(input);
							if (isValid == false)
							{
								wordSearchTA.setText("             " + input + " is an invalid word!");
								wordSearchTA.setForeground(Color.red);
								wordSearchTF.setText("");
							}
							else
							{
								wordSearchTA.setText("             " + input + " is a valid word!");
								wordSearchTA.setForeground(new Color(0,204,0));
								wordSearchTF.setText("");
							}
						}
					});
					
					JPanel topPanel = new JPanel();
					topPanel.setLayout(new BorderLayout());
					
					gbc.gridx = 0;
					gbc.gridy = 0;
					topPanel.add(wordSearchTF, BorderLayout.CENTER);
					gbc.gridx = 1;
					topPanel.add(searchButton, BorderLayout.EAST);			

					gbc.gridy = 0;
					gbc.fill = GridBagConstraints.HORIZONTAL;
					wordSearch.add(topPanel, gbc);
					gbc.gridy = 1;
					gbc.weightx = 1.0;
					gbc.fill = GridBagConstraints.HORIZONTAL;
					wordSearch.add(wordSearchTA, gbc);
				} catch (FontFormatException | IOException e1) {
					e1.printStackTrace();
				}
				wordSearch.setVisible(true);	
			}
		});
	}
	

	private void returnAllLetters() {
		for(int i=0; i<placedLetters.size(); i++){
			
			Letter l = placedLetters.get(i);
			int r = l.getRow();
			//System.out.println("rvalue: " + r);
			
			int c = l.getColumn();
			//System.out.println("cvalue: " + c);
			
			squaresArray[r][c].remove(l);
			squaresArray[r][c].revalidate();
			squaresArray[r][c].repaint();
			
			for (int k = 0; k < 7; k++) {
				if (userRack.getLetterAt(k) != null) {
					userRack.add(userRack.getLetterAt(k));
					userRack.revalidate();
					userRack.repaint();
				}
				else {
					l.updateRow(-1);
					l.updateColumn(-1);
					l.tempOnBoard = false;
					l.changePlaceOnRack(k);
					userRack.add(l);
					userRack.revalidate();
					userRack.repaint();
				}
			}
		}
		placedLetters.removeAllElements();
	}
	
	private class MouseListener extends MouseAdapter 
    {
        private int row, col;
        public MouseListener(int row, int col) 
        {
        	this.row = row;
            this.col = col;
        }
        @Override
        public void mouseClicked(MouseEvent e)
        {
        	//System.out.println(row + " " + col);
        	if (currentLetterSelected != null && !currentLetterSelected.tempOnBoard) {
            	//Checks to make sure that a letter has been selected
            	//System.out.println("currentLetterSelected is: " + currentLetterSelected.getLetter());

        		Letter l = currentLetterSelected;
        		placedLetters.add(l);
        		l.updateRow(this.row);
        		l.updateColumn(this.col);
        		l.setTempOnBoard();
        		squaresArray[row][col].add(l, BorderLayout.CENTER);
        		squaresArray[row][col].setLetter(l);
        		swapRack.setLetterAt(currentLetterSelected.getPlaceOnRack(), null);
        		squaresArray[row][col].revalidate();
        		squaresArray[row][col].repaint();
        	}
        }
    }
	
	public static void disableButtons() {
		swapButton.setEnabled(false);
		passButton.setEnabled(false);
		playButton.setEnabled(false);
	//	wordSearchButton.setEnabled(false);
	}
	
	public static void enableAllButtons() {
		swapButton.setEnabled(true);
		passButton.setEnabled(true);
		playButton.setEnabled(true);
		wordSearchButton.setEnabled(true);
	}
	
	public static void enableMostButtons() {
		swapButton.setEnabled(true);
		passButton.setEnabled(true);
		playButton.setEnabled(true);
		wordSearchButton.setEnabled(false);
	}
}
