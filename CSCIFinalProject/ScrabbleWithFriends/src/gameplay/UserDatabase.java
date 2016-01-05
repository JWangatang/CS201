package gameplay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Set;

public class UserDatabase {
	
	Hashtable<String, String> registeredUsers;
	Hashtable<String, Integer> userScores;
	private FileReader fr;
	private FileWriter fw;
	private BufferedReader br;
	private PrintWriter pw;
	private boolean isParsed = false;
	
	public UserDatabase(){
		registeredUsers = new Hashtable<String, String>();
		userScores = new Hashtable<String, Integer>();
		try {
			fr = new FileReader (new File("accounts.txt"));
			br = new BufferedReader(fr);
			
			String un, pw;
			int score = -1;
			un = br.readLine();
			
			while(un!=null){
				pw = br.readLine();
				if(pw!=null){
					score = Integer.parseInt(br.readLine());
					registeredUsers.put(un, pw);
					userScores.put(un, score);
					un = br.readLine();
				}
				else{
					isParsed = false;
					System.out.println("Error in reading password");
				}
				 
			}
		}  catch (FileNotFoundException fnfe) {
			System.out.println("File not found error in user database: " + fnfe.getMessage());
			isParsed = false;
		} catch (IOException ioe){
			System.out.println("IOE in reading in user database: " + ioe.getMessage());
			isParsed = false;
		} catch (NumberFormatException nfe){
			System.out.println("Number Format Exception in reading in score");
			isParsed = false;
		} finally{
			if(fr!=null && br!=null){
				try {
					fr.close();
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	//what the login button calls to check login
	public boolean login(String un, String pw){
		if(registeredUsers.containsKey(un)){
			if(registeredUsers.get(un).equals(pw))
				return true;
		}	
		return false;
	}
	
	//returns String indicating if account was created or not
	//necessary because makes sure user does not create an account with same username
	public boolean addAccount(String un, String pw){
		if(!registeredUsers.containsKey(un)){
			registeredUsers.put(un, pw);
			userScores.put(un, 0);
			exportHighScores();
			return true;
		}
		return false;
	}
	
	public boolean isParsed(){
		return isParsed;
	}
	
	public int getHighScoreFor(String un){
		if(userScores.containsKey(un))
			return userScores.get(un);
		return -1;
	}
	
	public void recordHighScore(String un, int score){
		userScores.put(un, score);
	}
	
	public void exportHighScores(){
		try{
			fw = new FileWriter(new File("accounts.txt"));
			pw = new PrintWriter(fw);
			
			Set<String> keys = registeredUsers.keySet();
			for(String u : keys){
				System.out.println(u);
				pw.println(u);
				System.out.println(registeredUsers.get(u));
				pw.println(registeredUsers.get(u));
				System.out.println(userScores.get(u));
				pw.println(userScores.get(u));
				
				pw.flush();
			}
			
		} catch (IOException ioe){
			System.out.println("IOE in exporting high scores: " + ioe.getMessage());
		} finally{
			if(fw!=null && pw!=null){
				try {
					fw.close();
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
