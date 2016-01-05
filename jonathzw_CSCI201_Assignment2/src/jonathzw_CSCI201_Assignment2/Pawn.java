package jonathzw_CSCI201_Assignment2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Pawn {
	private JPanel currPanel, prevPanel;
	private JPanel [][] board;
	private Color color;
	private BufferedImage bi;
	private JLabel pawnImage;
	private boolean isPlayer;
	
	public Pawn(Color c, JPanel[][] jp, boolean ip){
		color = c;
		board = jp;
		isPlayer = ip;
		prevPanel = null;
		if(color.equals(Color.RED)){
			currPanel = board[14][11];
			try{
				bi = ImageIO.read(new File("red.png"));
				pawnImage = new JLabel (new ImageIcon(bi));
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		else if(color.equals(Color.BLUE)){
			currPanel = board[11][1];
			try{
				bi = ImageIO.read(new File("blue.png"));
				pawnImage = new JLabel (new ImageIcon(bi));
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		else if(color.equals(Color.GREEN)){
			currPanel = board[4][14];	
			try{
				bi = ImageIO.read(new File("green.png"));
				pawnImage = new JLabel (new ImageIcon(bi));
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		else{
			currPanel = board[1][4];	
			try{
				bi = ImageIO.read(new File("yellow.png"));
				pawnImage = new JLabel (new ImageIcon(bi));	
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Color getColor(){
		return color;
	}
	
	public JPanel getPanel(){
		return currPanel;
	}
	
	public void setPanel(JPanel j){
		prevPanel = currPanel;
		currPanel = j;
		currPanel.setLayout(new BorderLayout());
		currPanel.add(pawnImage, BorderLayout.CENTER);
	}
	
	public JPanel getPrevPanel(){
		return prevPanel;
	}
	
	public void setPrevPanel(JPanel j){
		prevPanel = j;
	}
	
	public boolean isPlayer(){
		return isPlayer;
	}
}
