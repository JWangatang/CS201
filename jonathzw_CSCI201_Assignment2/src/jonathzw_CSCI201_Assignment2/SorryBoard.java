package jonathzw_CSCI201_Assignment2;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SorryBoard extends JFrame {
	public static final long serialVersionUID =1;
	private Color playerColor, bot1Color, bot2Color, bot3Color;
	private int numPlayers;
	
	private JPanel mainPanel, selectedPanel, blueStartPanel, redStartPanel,greenStartPanel, 
	yellowStartPanel, blueHomePanel, redHomePanel, greenHomePanel, yellowHomePanel;
	
	private JPanel[][] board = new JPanel[16][16];
	private JPanel[] generalPath = new JPanel[60];
	private JPanel[] redPath = new JPanel[6];
	private JPanel[] bluePath = new JPanel[6];
	private JPanel[] greenPath = new JPanel[6];
	private JPanel[] yellowPath = new JPanel[6];
	
	private JLabel rHome, rStart, rHomeCounterLabel,rStartCounterLabel,
	gHome, gStart, gHomeCounterLabel, gStartCounterLabel,
	bHome, bStart, bHomeCounterLabel, bStartCounterLabel,
	yHome, yStart, yHomeCounterLabel, yStartCounterLabel;
	
	private JButton cardButton;
	
	private int rHomeCounter, rStartCounter, gHomeCounter, gStartCounter,
	bHomeCounter, bStartCounter, yHomeCounter, yStartCounter;
	
	private Deck deck;
	
	private Card currCard;
	
	private boolean isSelectingPawn;
	
	private Pawn[] p1Pawns, b1Pawns, b2Pawns, b3Pawns;
	
	private Pawn selectedPawn;
	
	public SorryBoard(int np, Color c){
		super("Sorry! - " + np + " players");
		playerColor = c;
		numPlayers = np;
		
		deck = new Deck();
		currCard = null;
		selectedPanel = null;
		isSelectingPawn = false;
			
		initializeComponents();
		createGUI();
		addEvents();
	}
	
	private void initializeComponents(){
		
		for(int i=0; i<16; i++){
			for(int j=0; j<16; j++){
				//Yellow arrows
				if(i==0 && ((j>0 && j<5) || (j>8 && j<14))){
					JPanel jp = new JPanel();
					JLabel jl = new JLabel(">");
					jp.add(jl);
					jp.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);
						}
					});
					board[i][j] = jp;
				}
				//Yellow spaces
				else if(j==2 && i>0 && i<6){
					JPanel jp = new JPanel();
					JLabel jl = new JLabel("");
					jp.add(jl);
					jp.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);
						}
					});
					board[i][j] = jp;
				}
				//Yellow home
				else if(i==6 && j==2){
					JPanel jp = new JPanel();
					yHome = new JLabel("Home");
					jp.add(yHome);
					jp.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);
						}
					});
					board[i][j] = jp;
				}
				//Yellow Start
				else if(i==1 && j==4){
					JPanel jp = new JPanel();
					yStart = new JLabel("Start");
					jp.add(yStart);
					jp.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);
						}
					});
					board[i][j] = jp;
					
				}
				//Yellow home counter
				else if(i==7 && j==2){
					yHomeCounter = 0;
					JPanel jp = new JPanel();
					yHomeCounterLabel = new JLabel("" + yHomeCounter);
					jp.add(yHomeCounterLabel);
					board[i][j] = jp;
				}
				//Yellow start counter
				else if(i==2 && j==4){
					yStartCounter = 4;
					JPanel jp = new JPanel();
					yStartCounterLabel = new JLabel("" + yStartCounter);
					jp.add(yStartCounterLabel);
					board[i][j] = jp;
				}
				
				//Green Arrows
				else if(j==15 && ((i>0 && i<5) ||(i>8 && i<14))){
					JPanel jp = new JPanel();
					JLabel jl = new JLabel("v");
					jp.add(jl);
					jp.setBorder(BorderFactory.createLineBorder(Color.GREEN));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);
						}
					});
					board[i][j] = jp;
				}
				//Green spaces
				else if(i==2 && j>9 && j<15){
					JPanel jp = new JPanel();
					JLabel jl = new JLabel("");
					jp.add(jl);
					jp.setBorder(BorderFactory.createLineBorder(Color.GREEN));
					board[i][j] = jp;
				}
				//Green home
				else if(i==2 && j==9){
					JPanel jp = new JPanel();
					gHome = new JLabel("Home");
					jp.add(gHome);
					jp.setBorder(BorderFactory.createLineBorder(Color.GREEN));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);
						}
					});
					board[i][j] = jp;
				}
				//Green start
				else if(i==4 && j==14){
					JPanel jp = new JPanel();
					gStart = new JLabel("Start");
					jp.add(gStart);
					jp.setBorder(BorderFactory.createLineBorder(Color.GREEN));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);
						}
					});
					board[i][j] = jp;
				}
				//Green home counter
				else if (i==2 && j==8){
					gHomeCounter = 0;
					JPanel jp = new JPanel();
					gHomeCounterLabel = new JLabel("" + gHomeCounter);
					jp.add(gHomeCounterLabel);
					board[i][j] = jp;
				}
				//Green start counter
				else if(i==4 && j==13){
					gStartCounter = 4;
					JPanel jp = new JPanel();
					gStartCounterLabel = new JLabel("" + gStartCounter);
					jp.add(gStartCounterLabel);
					board[i][j] = jp;
				}
				
				//Blue Arrows
				else if(j==0 && ((i>1 && i<7) ||(i>10 && i<15))){
					JPanel jp = new JPanel();
					JLabel jl = new JLabel("^");
					jp.add(jl);
					jp.setBorder(BorderFactory.createLineBorder(Color.BLUE));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);
						}
					});
					board[i][j] = jp;
				}
				//Blue spaces
				else if(i==13 && j>0 && j<6){
					JPanel jp = new JPanel();
					JLabel jl = new JLabel("");
					jp.add(jl);
					jp.setBorder(BorderFactory.createLineBorder(Color.BLUE));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);
						}
					});
					board[i][j] = jp;	
				}
				//Blue home
				else if(i==13 && j==6){
					JPanel jp = new JPanel();
					bHome = new JLabel("Home");
					jp.add(bHome);
					jp.setBorder(BorderFactory.createLineBorder(Color.BLUE));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);
						}
					});
					board[i][j] = jp;
				}
				//Blue start
				else if(i==11 && j==1){
					JPanel jp = new JPanel();
					bStart = new JLabel("Start");
					jp.add(bStart);
					jp.setBorder(BorderFactory.createLineBorder(Color.BLUE));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);
						}
					});
					board[i][j] = jp;
				}
				//Blue home counter
				else if (i==13 && j==7){
					bHomeCounter = 0;
					JPanel jp = new JPanel();
					bHomeCounterLabel = new JLabel("" + bHomeCounter);
					jp.add(bHomeCounterLabel);
					board[i][j] = jp;
				}
				//Blue start counter
				else if(i==11 && j==2){
					bStartCounter = 4;
					JPanel jp = new JPanel();
					bStartCounterLabel = new JLabel("" + bStartCounter);
					jp.add(bStartCounterLabel);
					board[i][j] = jp;
				}
				
				//Red arrows
				else if(i==15 && ((j>1 && j<7)|| (j>10 && j<15))){
					JPanel jp = new JPanel();
					JLabel jl = new JLabel("<");
					jp.add(jl);
					jp.setBorder(BorderFactory.createLineBorder(Color.RED));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);
						}
					});
					board[i][j] = jp;
				}
				//Red spaces
				else if(j==13 && i>9 && i<15){
					JPanel jp = new JPanel();
					JLabel jl = new JLabel("");
					jp.add(jl);
					jp.setBorder(BorderFactory.createLineBorder(Color.RED));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);
						}
					});
					board[i][j] = jp;
				}
				//Red home
				else if(i==9 && j==13){
					JPanel jp = new JPanel();
					rHome = new JLabel("Home");
					jp.add(rHome);
					jp.setBorder(BorderFactory.createLineBorder(Color.RED));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);						}
					});
					board[i][j] = jp;
				}
				//Red start
				else if(i==14 && j==11){
					JPanel jp = new JPanel();
					rStart = new JLabel("Start");
					jp.add(rStart);
					jp.setBorder(BorderFactory.createLineBorder(Color.RED));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);						}
					});
					board[i][j] = jp;
				}
				//Red home counter
				else if (i==8 && j==13){
					rHomeCounter = 0;
					JPanel jp = new JPanel();
					rHomeCounterLabel = new JLabel("" + rHomeCounter);
					jp.add(rHomeCounterLabel);
					board[i][j] = jp;
				}
				//Red start counter
				else if(i==13 && j==11){
					rStartCounter = 4;
					JPanel jp = new JPanel();
					rStartCounterLabel = new JLabel("" + rStartCounter);
					jp.add(rStartCounterLabel);
					board[i][j] = jp;
				}
				
				//Card Label
				else if(i==7 && j==7){
					JPanel jp = new JPanel();
					JLabel jl = new JLabel("Cards:");
					jp.add(jl);
					board[i][j] = jp;
				}
				//Card Button
				else if(i==7 && j==8){
					JPanel jp = new JPanel();
					cardButton = new JButton("");
					jp.add(cardButton);
					board[i][j] = jp;
				}
				
				//Other spaces
				else if(
						(i==0 && (j==0 || (j>4 && j<9) || j==14 || j==15)) 
					|| (j==0 && (i==1 || (i>6 && i<11) || (i==15)))
					|| (j==15 && (i==14 || i==15 || (i>4 && i<9)))
					|| (i==15 && (j==1 || (j>6 && j<11)))
					){
					
					JPanel jp = new JPanel();
					JLabel jl = new JLabel("");
					jp.add(jl);
					jp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					jp.addMouseListener(new MouseAdapter (){
						public void mousePressed(MouseEvent me){
							onClick(jp);
						}
					});
					board[i][j] = jp;
				}
				//Fill with empty JPanels
				else{
					board[i][j] = new JPanel();
				}	
			}
		}
		
		blueStartPanel = board[11][1];
		yellowStartPanel = board[1][4];
		greenStartPanel = board[4][14];
		redStartPanel = board[14][11];
		blueHomePanel = board[13][6];
		yellowHomePanel = board[6][2];
		greenHomePanel = board[2][9];
		redHomePanel = board[9][13];
		
		
		for(int i=0; i<16; i++){
			generalPath[i] = board[0][i];
			System.out.println("General Path at " + i + ": " + "0, " + i);
		}
		for(int i=1; i<16; i++){
			generalPath[15+i] = board[i][15];
			System.out.println("General Path at  "+ (15+i) + ": " + (i) + ", 15");
		}
		for(int i=0; i<15; i++){
			generalPath[31+i] = board[15][14-i];
			System.out.println("General Path at  "+ (31+i) + ": 15" + ", " + (14-i));
		}
		for(int i=0; i<14; i++){
			generalPath[46+i] = board[14-i][0];
			System.out.println("General Path at  "+ (46+i) + ": " + (14-i) + ", 0");
		}
		
		for(int i=0;i<6; i++){
			yellowPath[i]=board[i+1][2];
			bluePath[i]=board[13][i+1];
			redPath[i]=board[14-i][13];
			greenPath[i]=board[2][14-i];
		}
		
		if(playerColor.equals(Color.RED)){		
			bot1Color = Color.YELLOW;
			if(numPlayers>2){
				bot2Color = Color.GREEN;
				if(numPlayers>3)
					bot3Color = Color.BLUE;
				else
					bot3Color = null;
			}
			else{
				bot2Color = null;
				bot3Color = null;
			}
		}
		
		else if(playerColor.equals(Color.BLUE)){
			bot1Color = Color.GREEN;
			if(numPlayers>2){
				bot2Color = Color.YELLOW;
				if(numPlayers>3)
					bot3Color = Color.RED;
				else
					bot3Color = null;
			}
			else{
				bot2Color = null;
				bot3Color = null;
			}
		}
		else if(playerColor.equals(Color.GREEN)){
			bot1Color = Color.BLUE;
			if(numPlayers>2){
				bot2Color = Color.YELLOW;
				if(numPlayers>3)
					bot3Color = Color.RED;
				else
					bot3Color = null;
			}
			else{
				bot2Color = null;
				bot3Color = null;
			}
		}
		else{
			bot1Color = Color.RED;
			if(numPlayers>2){
				bot2Color = Color.GREEN;
				if(numPlayers>3)
					bot3Color = Color.BLUE;
				else
					bot3Color = null;
			}
			else{
				bot2Color = null;
				bot3Color = null;
			}
		}
		
		p1Pawns = new Pawn [4];
		for(int i=0; i<4; i++){
			p1Pawns[i] = new Pawn(playerColor, board, true);
		}
		
		if(numPlayers==2){
			b1Pawns = new Pawn [4];
			for(int i=0; i<4; i++){
				b1Pawns[i] = new Pawn(bot1Color, board, false);
			}
			b2Pawns = null;
			b3Pawns = null;
		}
		else if(numPlayers==3){
			b1Pawns = new Pawn [4];
			b2Pawns = new Pawn [4];
			for(int i=0; i<4; i++){
				b1Pawns[i] = new Pawn(bot1Color, board, false);
				b2Pawns[i] = new Pawn(bot2Color, board, false);
			}
			b3Pawns = null;
		}
		else if(numPlayers==4){
			b1Pawns = new Pawn [4];
			b2Pawns = new Pawn [4];
			b3Pawns = new Pawn [4];
			
			for(int i=0; i<4; i++){
				b1Pawns[i] = new Pawn(bot1Color, board, false);
				b2Pawns[i] = new Pawn(bot2Color, board, false);
				b3Pawns[i] = new Pawn(bot3Color, board, false);
			}
		}
	}
	
	private void createGUI(){
		setSize(960,640); 
		setLocation(400, 150);
		
		mainPanel = new JPanel();
		GridLayout gl = new GridLayout(16,16);
		mainPanel.setLayout(gl);
		
		for(int i=0; i<16; i++){
			for(int j=0; j<16; j++){
				mainPanel.add(board[i][j]);
			}
		}
		
		add(mainPanel);		
	}
	
	private void addEvents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cardButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				currCard = deck.pickCard();
				String message = currCard.getName() + " - " + currCard.getDescription();
				JOptionPane.showMessageDialog(null, message);
				isSelectingPawn = true;
				JOptionPane.showMessageDialog(null, "Please select a pawn to move.");
			}
		});
	}
	
	private boolean isPawn(){
		for(int i=0; i<4; i++){
			if(p1Pawns[i].getPanel().equals(selectedPanel)){
				selectedPawn = p1Pawns[i];
				return true;
			}
		}
		return false;
	}
	
	private void updateCounters(){
		//Call in actionListener with repaint()
		rHomeCounterLabel.setText("" + rHomeCounter);
		rStartCounterLabel.setText("" + rStartCounter);
		
		gHomeCounterLabel.setText("" + gHomeCounter);
		gStartCounterLabel.setText("" + gStartCounter);
		
		bHomeCounterLabel.setText("" + bHomeCounter);
		bStartCounterLabel.setText("" + bStartCounter);
		
		yHomeCounterLabel.setText("" + yHomeCounter);
		yStartCounterLabel.setText("" + yStartCounter);
	}
	
	private boolean isValidMove(Pawn p, JPanel jp){
		//***Check if there are valid moves
		Pawn[] botPawns = null;
		
		//Cannot move to same spot
		if(jp.equals(p.getPanel())){
			System.out.println("Same Spot.");
			return false;
		}
		
		//Cannot move to a spot with pawn from same team
		if(p.isPlayer()){
			
			for(int i=0; i<4; i++){
				if(p1Pawns[i].getPanel().equals(jp) && 
						(jp!=board[14][11]) && (jp!=board[11][1])
						&& (jp!=board[4][14]) && (jp!=board[1][4])
						&& (jp!=board[6][2]) && (jp!=board[2][9]) 
						&& (jp!=board[13][6]) && (jp!=board[9][13])){
					System.out.println("Cannot move to same team pawn's spot");
					return false;
				}
			}
		}
		else{
			
			if(p.getColor()==bot1Color){
				botPawns = b1Pawns;
			}
			else if(p.getColor()==bot2Color){
				botPawns = b2Pawns;
			}
			else if(p.getColor()==bot3Color){
				botPawns = b3Pawns;
			}
			
			for(int i=0; i<4; i++){
				if(botPawns[i].getPanel().equals(jp) && 
						(jp!=board[14][11]) && (jp!=board[11][1])
						&& (jp!=board[4][14]) && (jp!=board[1][4])
						&& (jp!=board[6][2]) && (jp!=board[2][9]) 
						&& (jp!=board[13][6]) && (jp!=board[9][13])){
					System.out.println("Cannot move to same team pawn's spot");
					return false;
				}
			}
		}
		
		//check if sliding through will land on same color pawn
		if(jp.equals(board[0][1]) && p.getColor()!=Color.YELLOW){
			if(p.isPlayer()){
				for(int i=0; i<4; i++){
					if(p1Pawns[i].getPanel().equals(board[0][5])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
			else{
				for(int i=0; i<4; i++){
					if(botPawns[i].getPanel().equals(board[0][5])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
		}
		else if(jp.equals(board[0][9])){
			if(p.isPlayer()){
				for(int i=0; i<4; i++){
					if(p1Pawns[i].getPanel().equals(board[0][14])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
			else{
				for(int i=0; i<4; i++){
					if(botPawns[i].getPanel().equals(board[0][14])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
		}
		else if(jp.equals(board[1][15]) && p.getColor()!=Color.GREEN){
			if(p.isPlayer()){
				for(int i=0; i<4; i++){
					if(p1Pawns[i].getPanel().equals(board[5][15])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
			else{
				for(int i=0; i<4; i++){
					if(botPawns[i].getPanel().equals(board[5][15])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
		}
		else if(jp.equals(board[9][15])){
			if(p.isPlayer()){
				for(int i=0; i<4; i++){
					if(p1Pawns[i].getPanel().equals(board[14][15])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
			else{
				for(int i=0; i<4; i++){
					if(botPawns[i].getPanel().equals(board[14][15])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
		}
		else if(jp.equals(board[15][14]) && p.getColor()!=Color.RED){
			if(p.isPlayer()){
				for(int i=0; i<4; i++){
					if(p1Pawns[i].getPanel().equals(board[15][10])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
			else{
				for(int i=0; i<4; i++){
					if(botPawns[i].getPanel().equals(board[15][10])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
		}
		else if(jp.equals(board[15][6])){
			if(p.isPlayer()){
				for(int i=0; i<4; i++){
					if(p1Pawns[i].getPanel().equals(board[15][1])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
			else{
				for(int i=0; i<4; i++){
					if(botPawns[i].getPanel().equals(board[15][1])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
		}
		else if(jp.equals(board[14][0]) && p.getColor()!=Color.BLUE){
			if(p.isPlayer()){
				for(int i=0; i<4; i++){
					if(p1Pawns[i].getPanel().equals(board[10][0])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
			else{
				for(int i=0; i<4; i++){
					if(botPawns[i].getPanel().equals(board[10][0])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
		}
		else if(jp.equals(board[6][0])){
			if(p.isPlayer()){
				for(int i=0; i<4; i++){
					if(p1Pawns[i].getPanel().equals(board[1][0])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
			else{
				for(int i=0; i<4; i++){
					if(botPawns[i].getPanel().equals(board[1][0])){
						System.out.println("Sliding will result in landing on same pawn.");
						return false;
					}
				}
			}
		}
		
		//Check if trying to move into another Color's Home
		for(int i=0; i<6; i++){
			if(p.getColor()==Color.RED && (jp.equals(bluePath[i]) || jp.equals(greenPath[i]) ||
					jp.equals(yellowPath[i]))){
				System.out.println("Trying to move into another Color's home");
				return false;
			}
			else if(p.getColor()==Color.BLUE && (jp.equals(redPath[i]) || jp.equals(greenPath[i]) ||
					jp.equals(yellowPath[i]))){
				System.out.println("Trying to move into another Color's home");

				return false;
			}
			else if(p.getColor()==Color.GREEN && (jp.equals(redPath[i]) || jp.equals(bluePath[i]) ||
					jp.equals(yellowPath[i]))){
				System.out.println("Trying to move into another Color's home");

				return false;
			}
			else if(p.getColor()==Color.YELLOW && (jp.equals(redPath[i]) || jp.equals(greenPath[i]) ||
					jp.equals(bluePath[i]))){
				System.out.println("Trying to move into another Color's home");

				return false;
			}
		}
		
		//Check Card values
		
		int currIndex = -1;
		int destIndex = -1;
		boolean inHome = false;
		boolean movingToHome = false;
		
		for(int i=0; i<60; i++){
			if(generalPath[i].equals(jp)){
				destIndex = i;
			}
			if(generalPath[i].equals(p.getPanel())){
				currIndex = i;
			}
		}
		
		for(int i=0; i<6; i++){
			if(redPath[i].equals(jp)){
				movingToHome = true;
				destIndex = i;
			}
			if(redPath[i].equals(p.getPanel())){
				inHome = true;
				currIndex = i;
			}
			
			if(bluePath[i].equals(jp)){
				movingToHome = true;
				destIndex = i;
			}
			if(bluePath[i].equals(p.getPanel())){
				inHome = true;
				currIndex = i;
			}
			if(greenPath[i].equals(jp)){
				movingToHome = true;
				destIndex = i;
			}
			if(greenPath[i].equals(p.getPanel())){
				inHome = true;
				currIndex = i;
			}
			if(yellowPath[i].equals(jp)){
				movingToHome = true;
				destIndex = i;
			}
			if(yellowPath[i].equals(p.getPanel())){
				inHome = true;
				currIndex = i;
			}
		}
		
		
		if(currIndex == -1 && destIndex == -1){
			System.out.println("Negative index");
			return false;
		}
		
		if(currCard.getName().equals("1")){
			if(movingToHome && inHome && currIndex>destIndex){
					return false;
			}
			else if(movingToHome && !inHome && (!p.getPanel().equals(board[0][2]) 
					|| !p.getPanel().equals(board[2][15]) ||!p.getPanel().equals(board[15][13])
					|| !p.getPanel().equals(board[13][0]))){
				return false;
			}
			else{
				if(p.getPanel().equals(blueStartPanel) && !jp.equals(board[11][0])){
					System.out.println("blue start issue");
					return false;
				}
				else if(p.getPanel().equals(yellowStartPanel) && !jp.equals(board[0][4])){
					System.out.println("yellow start issue");
					return false;
				}
				else if(p.getPanel().equals(greenStartPanel) && !jp.equals(board[4][15])){
					System.out.println("green start issue");

					return false;
				}
				else if(p.getPanel().equals(redStartPanel) && !jp.equals(board[15][11])){
					System.out.println("red start issue");

					return false;
				}
				else if(destIndex!=0 && currIndex!=59 && currIndex != -1 && 
						(destIndex-currIndex>1 || destIndex-currIndex<0)){
					System.out.println("other start issue");
					System.out.println("current index: " + currIndex + ", dest index: " + destIndex);
						return false;
				}
			}
		}
		
		else if(currCard.getName().equals("2")){
			if(movingToHome && inHome && currIndex>destIndex){
				return false;
			}
			else if(movingToHome && !inHome && ( (!p.getPanel().equals(board[0][2]) 
					|| !p.getPanel().equals(board[2][15]) || !p.getPanel().equals(board[15][13])
					|| !p.getPanel().equals(board[13][0]) || !p.getPanel().equals(board[0][1]) 
					|| !p.getPanel().equals(board[1][15]) ||!p.getPanel().equals(board[15][14]) 
					|| !p.getPanel().equals(board[14][0])))){
				return false;
			}
			else{
				if(p.getPanel().equals(blueStartPanel) && !jp.equals(board[10][0])){
					System.out.println("blue start issue");
					return false;
				}
				else if(p.getPanel().equals(yellowStartPanel) && !jp.equals(board[0][5])){
					System.out.println("yellow start issue");
					return false;
				}
				else if(p.getPanel().equals(greenStartPanel) && !jp.equals(board[5][15])){
					System.out.println("green start issue");
	
					return false;
				}
				else if(p.getPanel().equals(redStartPanel) && !jp.equals(board[15][10])){
					System.out.println("red start issue");
	
					return false;
				}
				else if(destIndex!=0 && currIndex!=59 && currIndex != -1 && 
						(destIndex-currIndex>2 || destIndex-currIndex<2)){
					System.out.println("other start issue");
					System.out.println("current index: " + currIndex + ", dest index: " + destIndex);
						return false;
				}
			}
		}
		else if(currCard.getName().equals("3")){
			if(movingToHome && inHome && currIndex>destIndex){
				return false;
			}
			else if(movingToHome && !inHome && (!p.getPanel().equals(board[0][2]) 
					|| !p.getPanel().equals(board[2][15]) || !p.getPanel().equals(board[15][13])
					|| !p.getPanel().equals(board[13][0]) || !p.getPanel().equals(board[0][1]) 
					|| !p.getPanel().equals(board[1][15]) || !p.getPanel().equals(board[15][14]) 
					|| !p.getPanel().equals(board[14][0]) || !p.getPanel().equals(board[0][0])
					|| !p.getPanel().equals(board[15][15]) || !p.getPanel().equals(board[15][0])
					|| !p.getPanel().equals(board[0][15]))){
				System.out.println("Moving into home issue");
				return false;
			}
			else if(destIndex!=0 && currIndex!=59 && 
						(destIndex-currIndex>3 || destIndex-currIndex<3)){
					System.out.println("other start issue");
					System.out.println("current index: " + currIndex + ", dest index: " + destIndex);
						return false;
			}
		}
		
		else if(currCard.getName().equals("4")){
			if(movingToHome && inHome && currIndex<destIndex){
				return false;
			}
			else if(movingToHome && !inHome && (!p.getPanel().equals(board[10][0]) 
					|| !p.getPanel().equals(board[11][0]) || !p.getPanel().equals(board[12][0])
					|| !p.getPanel().equals(board[13][0]) || !p.getPanel().equals(board[0][5]) 
					|| !p.getPanel().equals(board[0][4]) || !p.getPanel().equals(board[0][3]) 
					|| !p.getPanel().equals(board[0][2]) || !p.getPanel().equals(board[5][15])
					|| !p.getPanel().equals(board[4][15]) || !p.getPanel().equals(board[3][15])
					|| !p.getPanel().equals(board[2][15]) || !p.getPanel().equals(board[15][11])
					|| !p.getPanel().equals(board[15][12]) || !p.getPanel().equals(board[15][13])
					|| !p.getPanel().equals(board[15][14]))){
				System.out.println("Moving into home issue");
				return false;
			}
			else if(destIndex!=0 && currIndex!=59 && 
						(currIndex-destIndex>4 || currIndex-destIndex<4)){
					System.out.println("other start issue");
					System.out.println("current index: " + currIndex + ", dest index: " + destIndex);
						return false;
			}
		}
		else if(currCard.getName().equals("5")){
			if(movingToHome && inHome && currIndex>destIndex){
				return false;
			}
			else if(movingToHome && !inHome && (!p.getPanel().equals(board[0][2]) 
					|| !p.getPanel().equals(board[2][15]) || !p.getPanel().equals(board[15][13])
					|| !p.getPanel().equals(board[13][0]) || !p.getPanel().equals(board[0][1]) 
					|| !p.getPanel().equals(board[1][15]) || !p.getPanel().equals(board[15][14]) 
					|| !p.getPanel().equals(board[14][0]) || !p.getPanel().equals(board[0][0])
					|| !p.getPanel().equals(board[15][15]) || !p.getPanel().equals(board[15][0])
					|| !p.getPanel().equals(board[0][15]) || !p.getPanel().equals(board[15][1])
					|| !p.getPanel().equals(board[14][15]) || !p.getPanel().equals(board[0][14]) 
					|| !p.getPanel().equals(board[1][0]) || !p.getPanel().equals(board[2][0])
					|| !p.getPanel().equals(board[15][2]) || !p.getPanel().equals(board[13][15]) 
					|| !p.getPanel().equals(board[0][13]))){
				return false;
			}
			else if(destIndex!=0 && currIndex!=59 && 
						(destIndex-currIndex>5 || destIndex-currIndex<5)){
					System.out.println("other start issue");
					System.out.println("current index: " + currIndex + ", dest index: " + destIndex);
						return false;
			}
		}
		else if(currCard.getName().equals("7")){
			if(movingToHome && inHome && currIndex>destIndex){
				return false;
			}
			else if(movingToHome && !inHome && !((currIndex>39 && currIndex<46) || (currIndex>9 && currIndex<16)
					|| (currIndex>25 && currIndex<31) || (currIndex>54 && currIndex<60 && currIndex==0))){
				return false;
			}
			else if(destIndex!=0 && currIndex!=59 && 
						(destIndex-currIndex>7 || destIndex-currIndex<7)){
						return false;
			}
		}
		else if(currCard.getName().equals("8")){
			if(movingToHome && inHome && currIndex>destIndex){
				return false;
			}
			else if(movingToHome && !inHome && !((currIndex>39 && currIndex<46) || (currIndex>9 && currIndex<16)
					|| (currIndex>25 && currIndex<31) || (currIndex>54 && currIndex<60 && currIndex==0))){
				return false;
			}
			else if(destIndex!=0 && currIndex!=59 && 
						(destIndex-currIndex>8 || destIndex-currIndex<8)){
						return false;
			}
			
		}
		else if(currCard.getName().equals("10")){
			if(movingToHome && inHome && currIndex-1>destIndex){
				return false;
			}
			else if(movingToHome && !inHome && !((currIndex>39 && currIndex<46) || (currIndex>9 && currIndex<16)
					|| (currIndex>25 && currIndex<31) || (currIndex>54 && currIndex<60 && currIndex==0))){
				return false;
			}
			else if(destIndex!=0 && currIndex!=59 && 
						(destIndex-currIndex>10 || destIndex-currIndex<10)){
						return false;
			}
		}
		else if(currCard.getName().equals("11")){
			if(movingToHome && inHome && currIndex>destIndex){
				return false;
			}
			else if(movingToHome && !inHome && (!p.getPanel().equals(board[0][2]) 
					|| !p.getPanel().equals(board[2][15]) || !p.getPanel().equals(board[15][13])
					|| !p.getPanel().equals(board[13][0]) || !p.getPanel().equals(board[0][1]) 
					|| !p.getPanel().equals(board[1][15]) || !p.getPanel().equals(board[15][14]) 
					|| !p.getPanel().equals(board[14][0]) || !p.getPanel().equals(board[0][0])
					|| !p.getPanel().equals(board[15][15]) || !p.getPanel().equals(board[15][0])
					|| !p.getPanel().equals(board[0][15]) || !p.getPanel().equals(board[15][1])
					|| !p.getPanel().equals(board[14][15]) || !p.getPanel().equals(board[0][14]) 
					|| !p.getPanel().equals(board[1][0]) || !p.getPanel().equals(board[2][0])
					|| !p.getPanel().equals(board[15][2]) || !p.getPanel().equals(board[13][15]) 
					|| !p.getPanel().equals(board[0][13]))){
				return false;
			}
			else if(destIndex!=0 && currIndex!=59 && 
						(destIndex-currIndex>11 || destIndex-currIndex<11)){
						return false;
			}
			else{
				for(int i=0; i<4; i++){
					if(b1Pawns[i].getPanel().equals(generalPath[destIndex])){
						b1Pawns[i].setPanel(generalPath[currIndex]);
						p.setPanel(generalPath[destIndex]);
						return true;
					}
					if(numPlayers>2){
						if(b2Pawns[i].getPanel().equals(generalPath[destIndex])){
							b2Pawns[i].setPanel(generalPath[currIndex]);
							p.setPanel(generalPath[destIndex]);
							return true;
						}
					}
					if(numPlayers>3){
						if(b3Pawns[i].getPanel().equals(generalPath[destIndex])){
							b3Pawns[i].setPanel(generalPath[currIndex]);
							p.setPanel(generalPath[destIndex]);
							return true;
						}
					}
				}
				return false;
			}
		}
		else if(currCard.getName().equals("12")){
			if(movingToHome && inHome && currIndex>destIndex){
				return false;
			}
			else if(movingToHome && !inHome && (!p.getPanel().equals(board[0][2]) 
					|| !p.getPanel().equals(board[2][15]) || !p.getPanel().equals(board[15][13])
					|| !p.getPanel().equals(board[13][0]) || !p.getPanel().equals(board[0][1]) 
					|| !p.getPanel().equals(board[1][15]) || !p.getPanel().equals(board[15][14]) 
					|| !p.getPanel().equals(board[14][0]) || !p.getPanel().equals(board[0][0])
					|| !p.getPanel().equals(board[15][15]) || !p.getPanel().equals(board[15][0])
					|| !p.getPanel().equals(board[0][15]) || !p.getPanel().equals(board[15][1])
					|| !p.getPanel().equals(board[14][15]) || !p.getPanel().equals(board[0][14]) 
					|| !p.getPanel().equals(board[1][0]) || !p.getPanel().equals(board[2][0])
					|| !p.getPanel().equals(board[15][2]) || !p.getPanel().equals(board[13][15]) 
					|| !p.getPanel().equals(board[0][13]))){
				return false;
			}
			else if(destIndex!=0 && currIndex!=59 && 
						(destIndex-currIndex>12 || destIndex-currIndex<12)){
						return false;
			}
			
		}
		else if(currCard.getName().equals("Sorry!")){
			for(int i=0; i<4; i++){
				if(b1Pawns[i].getPanel().equals(generalPath[destIndex])){
					sendHome(b1Pawns[i]);
					return true;
				}
				if(numPlayers>2){
					if(b2Pawns[i].getPanel().equals(generalPath[destIndex])){
						sendHome(b2Pawns[i]);
						return true;
					}
				}
				if(numPlayers>3){
					if(b3Pawns[i].getPanel().equals(generalPath[destIndex])){
						sendHome(b3Pawns[i]);
						return true;
					}
				}
			}
			return false;
		}
		
		return true;
	}
	
	private void sendHome(Pawn p){
		if(p.getColor()==Color.YELLOW){
			p.setPanel(board[1][4]);
		}
		else if(p.getColor()==Color.GREEN){
			p.setPanel(board[4][14]);
		}
		else if(p.getColor()==Color.BLUE){
			p.setPanel(board[11][1]);
		}
		else{
			p.setPanel(board[14][11]);
		}
		
		p.setPrevPanel(null);
	}
	
	private void movePawn(Pawn p, JPanel jp){
		
		if(p.getPanel().equals(board[14][11])){
			rStartCounter--;
		}
		else if(p.getPanel().equals(board[11][1])){
			bStartCounter--;
		}
		else if(p.getPanel().equals(board[4][14])){
			gStartCounter--;
		}
		else if(p.getPanel().equals(board[1][4])){
			yStartCounter--;
		}
		
		if(jp.equals(board[14][11])){
			rStartCounter++;
		}
		else if(jp.equals(board[11][1])){
			bStartCounter++;
		}
		else if(jp.equals(board[4][14])){
			gStartCounter++;
		}
		else if(jp.equals(board[1][4])){
			yStartCounter++;
		}
		
		if(jp.equals(redHomePanel)){
			rHomeCounter++;
		}
		else if(jp.equals(blueHomePanel)){
			bHomeCounter++;
		}
		else if(jp.equals(greenHomePanel)){
			gHomeCounter++;
		}
		else if(jp.equals(yellowHomePanel)){
			yHomeCounter++;
		}	
		
		//implement slide function
		if(jp.equals(board[0][1]) && p.getColor()!=Color.YELLOW){
			p.setPanel(board[0][5]);
		}
		else if(jp.equals(board[0][9])){
			p.setPanel(board[0][14]);
		}
		else if(jp.equals(board[1][15]) && p.getColor()!=Color.GREEN){
			p.setPanel(board[5][15]);
		}
		else if(jp.equals(board[9][15])){
			p.setPanel(board[14][15]);
		}
		else if(jp.equals(board[15][14]) && p.getColor()!=Color.RED){
			p.setPanel(board[15][10]);
		}
		else if(jp.equals(board[15][6])){
			p.setPanel(board[15][1]);
		}
		else if(jp.equals(board[14][0]) && p.getColor()!=Color.BLUE){
			p.setPanel(board[10][0]);
		}
		else if(jp.equals(board[6][0])){
			p.setPanel(board[1][0]);
		}
		else{
			p.setPanel(jp);
		}
		revalidate();
		repaint();
		updateCounters();
		currCard = null;
		selectedPanel = null;
		
		for(int i=0; i<numPlayers-1; i++){
			currCard = deck.pickCard();
		}
		
		for(int i=0; i<60; i++){
			for(int j=0; j<4; j++){
				if(isValidMove(b1Pawns[j], generalPath[i])){
					movePawn(b1Pawns[j], generalPath[i]);
				}
				if(numPlayers >2 && isValidMove(b2Pawns[j], generalPath[i])){
					movePawn(b2Pawns[j], generalPath[i]);
				}
				if(numPlayers >3 && isValidMove(b3Pawns[j], generalPath[i])){
					movePawn(b3Pawns[j], generalPath[i]);
				}
			}
			
		}
	}
	
	private void onClick (JPanel jp){
		if(currCard!=null){
			selectedPanel = jp;
			if(isSelectingPawn){
				if(!isPawn()){
					JOptionPane.showMessageDialog(null, "Invalid pawn. "
							+ "Please select a valid pawn to move.");
				}
				else{
					isSelectingPawn = false;
					JOptionPane.showMessageDialog(null, "Please select a destination.");
				}
			}
			else{
				if(!isValidMove(selectedPawn, selectedPanel)){
					JOptionPane.showMessageDialog(null, "Invalid move. Please select a valid move.");
				}
				else{
					System.out.println("Moving Pawn!");		
					movePawn(selectedPawn, selectedPanel);
					if(rHomeCounter==4){
						JOptionPane.showMessageDialog(null, "Red Player has won!");
						StartScreen ss = new StartScreen();
						ss.setVisible(true);
						setVisible(false);
												
					}
					else if(gHomeCounter==4){
						JOptionPane.showMessageDialog(null, "Green Player has won!");
						StartScreen ss = new StartScreen();
						ss.setVisible(true);
						setVisible(false);

					}
					else if(bHomeCounter==4){
						JOptionPane.showMessageDialog(null, "Blue Player has won!");
						StartScreen ss = new StartScreen();
						ss.setVisible(true);
						setVisible(false);

					}
					else if(yHomeCounter==4){
						JOptionPane.showMessageDialog(null, "Yellow Player has won!");
						StartScreen ss = new StartScreen();
						ss.setVisible(true);
						setVisible(false);
					}
				}
			}
		}	
	}
}
