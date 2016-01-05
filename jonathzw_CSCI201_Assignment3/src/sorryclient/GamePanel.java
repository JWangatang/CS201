package sorryclient;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import game.GameHelpers;
import game.GameManager;
import game.Tile;

/*
 * GamePanel
 * Panel to hold the graphical game
 * */
public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1593344194657112518L;
	
	//A grid to hold all the tiles in the game
	private final static int boardSize = 16;
	private final TilePanel[][] tileGrid;
	
	//The card button for the game
	private final JButton cardButton;
	private final JLabel cardLabel;
	
	//The game manager that runs the actual logic
	private final GameManager mGameManager;
	
	//The way to exit the game menu
	private final ActionListener mQuitAction;
	
	private final Font labelFont;
	
	{
		labelFont = new Font("KenVector Future", Font.BOLD, 8);
		
		//Create and set-up the card button
		cardButton = new JButton() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
			    super.paintComponent(g);
			    g.drawImage(new ImageIcon("cardBack_red.png").getImage(), 0, 0, getWidth(), getHeight(), null);
			}
		};
		cardButton.setBorderPainted(false);
		cardButton.setOpaque(true);
		cardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mGameManager.drawCard();
				redraw();
			}
		});
		cardLabel = new JLabel("Cards:");
		cardLabel.setFont(labelFont);
	}
	
	public GamePanel(ActionListener inQuitAction, GameManager inGameManager){
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("cursorHand_blue.png").getImage(),
				new Point(0,0),"cursorHand_blue.png"));
		
		mQuitAction = inQuitAction;
		mGameManager = inGameManager;
		
		//Create the GUI to be a grid for all the tiles
		setLayout(new GridLayout(boardSize,boardSize));
		tileGrid = new TilePanel[boardSize][boardSize];
		
		//Create each grid square
		//Either give it a Start/Home label panel, or a TilePanel
		for(int y = 0; y < boardSize; ++y) {
			for(int x = 0; x < boardSize; ++x) {
				if(x == 4 && y == 2) {tileGrid[x][y] = new StartLabelPanel(GameHelpers.getIndexFromColor(Color.YELLOW));}
				else if (x == 2 && y == 7) {tileGrid[x][y] = new HomeLabelPanel(GameHelpers.getIndexFromColor(Color.YELLOW));}
				else if(x == 13 && y == 4) {tileGrid[x][y] = new StartLabelPanel(GameHelpers.getIndexFromColor(Color.GREEN));}
				else if(x == 8 && y == 2) {tileGrid[x][y] = new HomeLabelPanel(GameHelpers.getIndexFromColor(Color.GREEN));}
				else if(x == 11 && y == 13) {tileGrid[x][y] = new StartLabelPanel(GameHelpers.getIndexFromColor(Color.RED));}
				else if(x == 13 && y == 8) {tileGrid[x][y] = new HomeLabelPanel(GameHelpers.getIndexFromColor(Color.RED));}
				else if(x == 2 && y == 11) {tileGrid[x][y] = new StartLabelPanel(GameHelpers.getIndexFromColor(Color.BLUE));}
				else if(x == 7 && y == 13) {tileGrid[x][y] = new HomeLabelPanel(GameHelpers.getIndexFromColor(Color.BLUE));}
				else {tileGrid[x][y] = new TilePanel(mGameManager.getTile(x,y));}
				add(tileGrid[x][y]);
			}
		}
		
		//Set in the card
		TilePanel cardLabelTile = tileGrid[boardSize/2-1][boardSize/2-1];
		cardLabelTile.setLayout(new GridLayout(1,1));
		cardLabelTile.add(cardLabel);
		
		TilePanel cardButtonTile = tileGrid[boardSize/2][boardSize/2-1];
		cardButtonTile.setLayout(new GridLayout(1,1));
		cardButtonTile.add(cardButton);
		
		//This is used to make sure the GameManager can redraw the GUI
		inGameManager.setGamePanel(this);
		
		redraw();
	}
	
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(new ImageIcon("sorry.png").getImage(), getWidth()/3, getHeight()/4, 6*getWidth()/16, 3*getHeight()/16, null);
	}
	
	public void redraw() {
		for(TilePanel row[] : tileGrid) {
			for(TilePanel tp : row) {
				tp.update();
			}
		}
		revalidate();
		repaint();
	}
	
	//Each tile is a square in the grid, it can be null to hold a blank square
	class TilePanel extends JPanel {
		private static final long serialVersionUID = -9071191204545371340L;
		
		private Tile mTile;
		private final Stack<Component> components;
		private boolean highlighted = false;
		private boolean justClicked = false;
		
		private Tile selectedTile;
		
		private JPanel pawn = new JPanel(){
			private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g) {
			    super.paintComponent(g);
			    if(mTile!=null && mTile.getPawn()!=null){
				    if(mTile.getPawnColor().equals(Color.RED)){
				    	g.drawImage(new ImageIcon("red_pawn.png").getImage(), 0, 0, getWidth(), getHeight(), null);
				    }
				    else if (mTile.getPawnColor().equals(Color.BLUE)){
				    	g.drawImage(new ImageIcon("blue_pawn.png").getImage(), 0, 0, getWidth(), getHeight(), null);
				    }
				    else if (mTile.getPawnColor().equals(Color.GREEN)){
				    	g.drawImage(new ImageIcon("green_pawn.png").getImage(), 0, 0, getWidth(), getHeight(), null);
				    }
				    else{
				    	g.drawImage(new ImageIcon("yellow_pawn.png").getImage(), 0, 0, getWidth(), getHeight(), null);
				    }
			    }
			} 	
		};
		
		private boolean pawnDisplayed = false;
		
		TilePanel(Tile tile) {
			mTile = tile;
			//Used to keep track what component should be displayed
			components = new Stack<Component>();
						
			//If we are a meaningful tile in the game
			if(mTile != null) {
				//Set any special looks based on the tiles properties
				
				if(mTile.doesSlide()){
					components.push(new JLabel());
				}
				if(mTile.isStart()){
					JLabel jl = new JLabel("Start");
					jl.setFont(labelFont);
					jl.setVerticalTextPosition(JLabel.CENTER);
					jl.setOpaque(false);
					components.push(jl);
				}
				if(mTile.isHome()){
					JLabel jl = new JLabel("Home");
					jl.setFont(labelFont);
					jl.setVerticalTextPosition(JLabel.CENTER);
					jl.setOpaque(false);
					components.push(jl);
				}
				
				//PAWN LISTENER
				pawn.addMouseListener(new MouseAdapter() {	
					@Override
					public void mouseClicked(MouseEvent me) {
						
						mGameManager.tileClicked(mTile, mGameManager.getMainPlayer());
					}
				});
				
				//If the tile is clicked by the user...
				addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseClicked(MouseEvent me) {
						
						if(mTile!=null){
							if(mTile.isStart())
								mGameManager.tileClicked(mTile,mGameManager.getMainPlayer());
							else{
								if(SwingUtilities.isRightMouseButton(me)){
									highlighted = false;
									Tile temp = mTile;
									mTile = selectedTile;
									repaint();
									mTile = temp;
								}
								else if(mTile.isOccupied() && mTile.getPawnColor().equals(mGameManager.getMainPlayer().getColor())){
									selectedTile = mTile;
									highlighted = true;
									justClicked = true;
									repaint();	
								}
							}	
						}
					}
					
					
					public void mouseEntered(MouseEvent me){
						if(mTile!=null && mTile.isOccupied() && mTile.getPawnColor()==mGameManager.getMainPlayer().getColor()){
							highlighted = true;
							repaint();
						}
					}
					
					public void mouseExited(MouseEvent me){
						if(!justClicked && mTile!=null && mTile.isOccupied() && mTile.getPawnColor()==mGameManager.getMainPlayer().getColor()){
							highlighted = false;
							repaint();
						}
					}
				});
			} else {
				setBackground(new Color(0,0,0,0));
				setOpaque(true);
			}
		}
		
		//Update the tile based on its properties
		protected void update() {
			if(mTile == null) return;
			if(mTile.isOccupied() && !pawnDisplayed) {
				pawnDisplayed = true;
				components.push(pawn);
			}
			if(!mTile.isOccupied() && pawnDisplayed) {
				pawnDisplayed = false;
				components.pop();
			}
			removeAll();
			if(!components.isEmpty())add(components.peek());
		}
		
		@Override
		protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		   
		    if(mTile!=null){
		    	if(highlighted && mTile.isOccupied()){
					if(mTile.getPawnColor()==Color.RED){
						g.drawImage(new ImageIcon("red_panel.png").getImage(), 0, 0, getWidth(), getHeight(), null);
					}
					else if(mTile.getPawnColor()==Color.BLUE){
						g.drawImage(new ImageIcon("blue_panel.png").getImage(), 0, 0, getWidth(), getHeight(), null);
					}
					else if(mTile.getPawnColor()==Color.GREEN){
						g.drawImage(new ImageIcon("green_panel.png").getImage(), 0, 0, getWidth(), getHeight(), null);
					}
					else{
						g.drawImage(new ImageIcon("yellow_panel.png").getImage(), 0, 0, getWidth(), getHeight(), null);
					}
				}
		    	else{
			    	if(mTile.getColor().equals(Color.RED)){
			    		if(mTile.isHome() || mTile.isStart())
			    			g.drawImage(new ImageIcon("red_panel.png").getImage(), 0, 0, getWidth(), getHeight(), null);
			    		else if(mTile.doesSlide()){
				    		g.drawImage(new ImageIcon("red_tile.png").getImage(), 0, 0, getWidth(), getHeight(), null);
				    		if(!mTile.isOccupied())
				    			g.drawImage(new ImageIcon("red_slide.png").getImage(), getWidth()/3, getHeight()/3, getWidth()/3, getHeight()/3, null);
			    		}
			    		else
				    		g.drawImage(new ImageIcon("red_tile.png").getImage(), 0, 0, getWidth(), getHeight(), null);
					}
			    	
					else if(mTile.getColor().equals(Color.BLUE)){		
			    		if(mTile.isHome() || mTile.isStart())
			    			g.drawImage(new ImageIcon("blue_panel.png").getImage(), 0, 0, getWidth(), getHeight(), null);
			    		else if(mTile.doesSlide()){
				    		g.drawImage(new ImageIcon("blue_tile.png").getImage(), 0, 0, getWidth(), getHeight(), null);
				    		if(!mTile.isOccupied())
				    			g.drawImage(new ImageIcon("blue_slide.png").getImage(), getWidth()/3, getHeight()/3, getWidth()/3, getHeight()/3, null);
			    		}
			    		else
				    		g.drawImage(new ImageIcon("blue_tile.png").getImage(), 0, 0, getWidth(), getHeight(), null);
					}
			    	
					else if(mTile.getColor().equals(Color.GREEN)){
						if(mTile.isHome() || mTile.isStart())
			    			g.drawImage(new ImageIcon("green_panel.png").getImage(), 0, 0, getWidth(), getHeight(), null);
						else if(mTile.doesSlide()){
				    		g.drawImage(new ImageIcon("green_tile.png").getImage(), 0, 0, getWidth(), getHeight(), null);
				    		if(!mTile.isOccupied())
				    			g.drawImage(new ImageIcon("green_slide.png").getImage(), getWidth()/3, getHeight()/3, getWidth()/3, getHeight()/3, null);
			    		}
			    		else
				    		g.drawImage(new ImageIcon("green_tile.png").getImage(), 0, 0, getWidth(), getHeight(), null);
					}
			    	
					else if (mTile.getColor().equals(Color.YELLOW)){
			    		if(mTile.isHome() || mTile.isStart())
			    			g.drawImage(new ImageIcon("yellow_panel.png").getImage(), 0, 0, getWidth(), getHeight(), null);
			    		else if(mTile.doesSlide()){
				    		g.drawImage(new ImageIcon("yellow_tile.png").getImage(), 0, 0, getWidth(), getHeight(), null);
				    		if(!mTile.isOccupied())
				    			g.drawImage(new ImageIcon("yellow_slide.png").getImage(), getWidth()/3, getHeight()/3, getWidth()/3, getHeight()/3, null);
			    		}
			    		else
				    		g.drawImage(new ImageIcon("yellow_tile.png").getImage(), 0, 0, getWidth(), getHeight(), null);
					}
					else{
							g.drawImage(new ImageIcon("grey_tile.png").getImage(), 0, 0, getWidth(), getHeight(), null);
					}
		    	}	
		    }			    
		}
	}
	
	//Used for the start counter display
	class StartLabelPanel extends TilePanel{
		private static final long serialVersionUID = -9166979703140637366L;
		private final int mPlayerNum;
		JLabel mLabel;
		{
			mLabel = new JLabel();
			mLabel.setFont(labelFont);
			add(mLabel);
		}
		StartLabelPanel(int numPlayer) {
			super(null);
			mPlayerNum = numPlayer;
		}
		@Override
		protected void update() {
			mLabel.setText(mGameManager.getPlayerStartCount(mPlayerNum));
		}
	}
	
	//Used for the home counter display
	class HomeLabelPanel extends TilePanel{
		private static final long serialVersionUID = -9166979703540637366L;
		private final int mPlayerNum;
		JLabel mLabel;
		{
			mLabel = new JLabel();
			mLabel.setFont(labelFont);
			add(mLabel);
		}
		HomeLabelPanel(int numPlayer) {
			super(null);
			mPlayerNum = numPlayer;
		}
		@Override
		protected void update() {
			mLabel.setText(mGameManager.getPlayerHomeCount(mPlayerNum));
		}
	}

	public void endGame(String winnerName) {
		JOptionPane.showMessageDialog(
				null, 
				mGameManager.getWinner() + " player won!", 
				"Sorry!", 
				JOptionPane.NO_OPTION
			);
		
		String input = JOptionPane.showInputDialog("Please enter your name!");
		int score = mGameManager.calculatePlayerScore();
		
		JOptionPane.showMessageDialog(null, "Your score is: " + score, "Score", JOptionPane.NO_OPTION);
		
		SorryClientWindow.getRecords().put(input, score);
		
		FileWriter fw = null;
		PrintWriter pw = null;
		try{
			fw = new FileWriter("user_records.txt", true);
			pw = new PrintWriter(fw);
			pw.println(input);
			pw.println(score);
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
		
		//Quit out if over
		JButton exit = new JButton("");
		exit.addActionListener(mQuitAction);
		exit.doClick();
	}
}
