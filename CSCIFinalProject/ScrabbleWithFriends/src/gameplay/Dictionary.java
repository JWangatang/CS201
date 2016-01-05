package gameplay;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public class Dictionary {
	
	private FileReader fr;
	private BufferedReader br;
	
	//checks if the dictionary was correctly parsed so client does not try to access invalid dictionary
	private boolean isParsed = false;
	
	//Hash table storing vectors with their corresponding first letter
	private Hashtable<String, Vector<String>> hashTable = new Hashtable<String, Vector<String>>();
	
	//26 vectors for each letter of the alphabet, storing all words beginning with each letter
	//makes searching the dictionary more efficient
	public Vector<String> aWords, bWords, cWords, dWords, eWords, fWords, gWords, hWords, iWords, jWords, kWords,
	lWords, mWords, nWords, oWords, pWords, qWords, rWords, sWords, tWords, uWords, vWords, wWords, xWords, yWords, zWords;
	
	
	public Dictionary(){
		aWords = new Vector<String>(); bWords= new Vector<String>(); cWords= new Vector<String>(); dWords= new Vector<String>(); 
		eWords= new Vector<String>(); fWords= new Vector<String>(); gWords= new Vector<String>(); hWords= new Vector<String>(); 
		iWords= new Vector<String>(); jWords= new Vector<String>(); kWords= new Vector<String>();lWords= new Vector<String>(); 
		mWords= new Vector<String>(); nWords= new Vector<String>(); oWords= new Vector<String>(); pWords= new Vector<String>(); 
		qWords= new Vector<String>(); rWords= new Vector<String>(); sWords= new Vector<String>(); tWords= new Vector<String>(); 
		uWords= new Vector<String>(); vWords= new Vector<String>(); wWords= new Vector<String>(); xWords= new Vector<String>(); 
		yWords= new Vector<String>(); zWords = new Vector<String>();
		
		try {
			fr = new FileReader ("dictionary.txt");
			br = new BufferedReader(fr);
			
			String word = br.readLine();
			while(word!=null){
				
				String letter = word.substring(0, 1);
				
				if(letter.equals("A")) aWords.add(word);
				else if(letter.equals("B")) bWords.add(word);
				else if(letter.equals("C")) cWords.add(word);
				else if(letter.equals("D")) dWords.add(word);
				else if(letter.equals("E")) eWords.add(word);
				else if(letter.equals("F")) fWords.add(word);
				else if(letter.equals("G")) gWords.add(word);
				else if(letter.equals("H")) hWords.add(word);
				else if(letter.equals("I")) iWords.add(word);
				else if(letter.equals("J")) jWords.add(word);
				else if(letter.equals("K")) kWords.add(word);				
				else if(letter.equals("L")) lWords.add(word);
				else if(letter.equals("M")) mWords.add(word);
				else if(letter.equals("N")) nWords.add(word);
				else if(letter.equals("O")) oWords.add(word);			
				else if(letter.equals("P")) pWords.add(word);
				else if(letter.equals("Q")) qWords.add(word);
				else if(letter.equals("R")) rWords.add(word);
				else if(letter.equals("S")) sWords.add(word);
				else if(letter.equals("T")) tWords.add(word);
				else if(letter.equals("U")) uWords.add(word);
				else if(letter.equals("V")) vWords.add(word);
				else if(letter.equals("W")) wWords.add(word);
				else if(letter.equals("X")) xWords.add(word);
				else if(letter.equals("Y")) yWords.add(word);
				else zWords.add(word);
				
				word = br.readLine();
			}
			
			hashTable.put("A", aWords);
			hashTable.put("B", bWords);
			hashTable.put("C", cWords);
			hashTable.put("D", dWords);
			hashTable.put("E", eWords);
			hashTable.put("F", fWords);
			hashTable.put("G", gWords);
			hashTable.put("H", hWords);
			hashTable.put("I", iWords);
			hashTable.put("J", jWords);
			hashTable.put("K", kWords);
			hashTable.put("L", lWords);
			hashTable.put("M", mWords);
			hashTable.put("N", nWords);
			hashTable.put("O", oWords);
			hashTable.put("P", pWords);
			hashTable.put("Q", qWords);
			hashTable.put("R", rWords);
			hashTable.put("S", sWords);
			hashTable.put("T", tWords);
			hashTable.put("U", uWords);
			hashTable.put("V", vWords);
			hashTable.put("W", wWords);
			hashTable.put("X", xWords);
			hashTable.put("Y", yWords);
			hashTable.put("Z", zWords);
			
			isParsed = true;
			int totalWords = aWords.size() + bWords.size() +cWords.size() +dWords.size() +eWords.size() +fWords.size() +
					gWords.size() +hWords.size() +iWords.size() +jWords.size() +kWords.size() +lWords.size() +
					mWords.size() +nWords.size() +oWords.size() +pWords.size() +qWords.size() +rWords.size() +sWords.size() +
					tWords.size() +uWords.size() +vWords.size() +wWords.size() +xWords.size() +yWords.size() +zWords.size();
			System.out.println("Dictionary successfully parsed! Total words: " + totalWords);
			
		} catch (FileNotFoundException fnfe) {
			System.out.println("File not found error in Dictionary: " + fnfe.getMessage());
			isParsed = false;
		} catch (IOException ioe){
			System.out.println("IOE in reading in dictionary: " + ioe.getMessage());
			isParsed = false;
		}
	}
	
	//call after dictionary is instantiated to make sure dictionary was parsed correctly.
	public boolean isParsed(){
		return isParsed;
	}
	
	//Called to check word validity during game
	public boolean isValidWord(String word){
		word = word.toUpperCase();
		String firstLetter = word.substring(0,1);
		if(hashTable.get(firstLetter)==null){
			return false;
		}
		
		return binarySearch(hashTable.get(firstLetter), word);
	}
	
	public boolean binarySearch(Vector<String> vector, String w){
		int hi = vector.size()-1;
		int low = 0;
		int mid = (hi+low)/2;
		
		while(low <=hi && !vector.get(mid).equals(w)){
			
			if(vector.get(mid).compareTo(w)<0)
				low = mid+1;
			else
				hi = mid-1;
			
			mid = (hi+low)/2;
		}
		
		if(low > hi) return false;
		return true;
	}
}
