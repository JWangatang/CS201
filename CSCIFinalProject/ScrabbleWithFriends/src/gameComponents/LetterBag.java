package gameComponents;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class LetterBag implements Serializable {
	private static final long serialVersionUID = 89345893L;
	
	private static final String filePath = "src/gameComponents/letters";
	
	private static Map<String, Integer> lettersInBag;
	private final List<Letter> letters;
	
	static{
		lettersInBag = new HashMap<String, Integer>();
		try{
			//iterates through file and puts letter and value in hash map
			Scanner s = new Scanner (new File(filePath));
			while(s.hasNext()){
				lettersInBag.put(s.next(), s.nextInt());
			}
			s.close();
		}catch(FileNotFoundException fnfe){
			System.out.println("File Not Found: " + fnfe.getMessage());
		}
	}
	
	{
		letters = new ArrayList<Letter>(100);
		for(Map.Entry<String, Integer> m : lettersInBag.entrySet()){
			if(m.getKey().equals("A")){
				for(int i = 0; i < 9; i++){
					letters.add(new Letter("A", new ImageIcon("Images/a.png")));
				}
			}
			else if(m.getKey().equals("B")){
				for(int i = 0; i < 2; i++){
					letters.add(new Letter("B", new ImageIcon("Images/b.png")));
				}
			}
			else if(m.getKey().equals("C")){
				for(int i = 0; i < 2; i++){
					letters.add(new Letter("C", new ImageIcon("Images/c.png")));
				}
			}
			else if(m.getKey().equals("D")){
				for(int i = 0; i < 4; i++){
					letters.add(new Letter("D", new ImageIcon("Images/d.png")));
				}
			}
			else if(m.getKey().equals("E")){
				for(int i = 0; i < 12; i++){
					letters.add(new Letter("E", new ImageIcon("Images/e.png")));
				}
			}
			else if(m.getKey().equals("F")){
				for(int i = 0; i < 2; i++){
					letters.add(new Letter("F", new ImageIcon("Images/f.png")));
				}
			}
			else if(m.getKey().equals("G")){
				for(int i = 0; i < 3; i++){
					letters.add(new Letter("G",new ImageIcon("Images/g.png")));
				}
			}
			else if(m.getKey().equals("H")){
				for(int i = 0; i < 2; i++){
					letters.add(new Letter("H",new ImageIcon("Images/h.png")));
				}
			}
			else if(m.getKey().equals("I")){
				for(int i = 0; i < 9; i++){
					letters.add(new Letter("I",new ImageIcon("Images/i.png")));
				}
			}
			else if(m.getKey().equals("J")){
				for(int i = 0; i < 1; i++){
					letters.add(new Letter("J",new ImageIcon("Images/j.png")));
				}
			}
			else if(m.getKey().equals("K")){
				for(int i = 0; i < 1; i++){
					letters.add(new Letter("K",new ImageIcon("Images/k.png")));
				}
			}
			else if(m.getKey().equals("L")){
				for(int i = 0; i < 4; i++){
					letters.add(new Letter("L",new ImageIcon("Images/l.png")));
				}
			}
			else if(m.getKey().equals("M")){
				for(int i = 0; i < 2; i++){
					letters.add(new Letter("M",new ImageIcon("Images/m.png")));
				}
			}
			else if(m.getKey().equals("N")){
				for(int i = 0; i < 6; i++){
					letters.add(new Letter("N",new ImageIcon("Images/n.png")));
				}
			}
			else if(m.getKey().equals("O")){
				for(int i = 0; i < 8; i++){
					letters.add(new Letter("O",new ImageIcon("Images/o.png")));
				}
			}
			else if(m.getKey().equals("P")){
				for(int i = 0; i < 2; i++){
					letters.add(new Letter("P",new ImageIcon("Images/p.png")));
				}
			}
			else if(m.getKey().equals("Q")){
				for(int i = 0; i < 1; i++){
					letters.add(new Letter("Q",new ImageIcon("Images/q.png")));
				}
			}
			else if(m.getKey().equals("R")){
				for(int i = 0; i < 6; i++){
					letters.add(new Letter("R",new ImageIcon("Images/r.png")));
				}
			}
			else if(m.getKey().equals("S")){
				for(int i = 0; i < 4; i++){
					letters.add(new Letter("S",new ImageIcon("Images/s.png")));
				}
			}
			else if(m.getKey().equals("T")){
				for(int i = 0; i < 6; i++){
					letters.add(new Letter("T",new ImageIcon("Images/t.png")));
				}
			}
			else if(m.getKey().equals("U")){
				for(int i = 0; i < 4; i++){
					letters.add(new Letter("U",new ImageIcon("Images/u.png")));
				}
			}
			else if(m.getKey().equals("V")){
				for(int i = 0; i < 2; i++){
					letters.add(new Letter("V",new ImageIcon("Images/v.png")));
				}
			}
			else if(m.getKey().equals("W")){
				for(int i = 0; i < 2; i++){
					letters.add(new Letter("W",new ImageIcon("Images/w.png")));
				}
			}
			else if(m.getKey().equals("X")){
				for(int i = 0; i < 1; i++){
					letters.add(new Letter("X",new ImageIcon("Images/x.png")));
				}
			}
			else if(m.getKey().equals("Y")){
				for(int i = 0; i < 2; i++){
					letters.add(new Letter("Y",new ImageIcon("Images/y.png")));
				}
			}
			else if(m.getKey().equals("Z")){
				for(int i = 0; i < 1; i++){
					letters.add(new Letter("Z",new ImageIcon("Images/z.png")));
				}
			}
			else if(m.getKey().equals("BLANK")){
				for(int i = 0; i < 2; i++){
					letters.add(new Letter("BLANK",new ImageIcon("Images/blank.png")));
				}
			}
		}
		Collections.shuffle(letters);
	}
	
	public Letter drawLetter(){
		Letter letter = letters.get(0);
		//removes the letter from the bag after getting it
		letters.remove(0);
		return letter;
	}
	
	public void ReAddLetter(Letter l) {
		letters.add(l);
		Collections.shuffle(letters);
	}
	
	public Letter getLetterAt(int i) {
		return letters.get(i);
	}
	
	public static Map<String, Integer> getLettersInBag() {
		return lettersInBag;
	}
	public int totalBagSize() {
		return letters.size();
	}
	
	public int bagLength()
	{
		return lettersInBag.size();
	}
	
}
