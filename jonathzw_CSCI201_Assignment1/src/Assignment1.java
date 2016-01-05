import java.util.Scanner;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Assignment1 {
	
	static final Scanner scan = new Scanner(System.in);
	
	public static void printMenu(){	
		System.out.println("Enter a command between 1-5");
		System.out.println("1. File report");
		System.out.println("2. Directory report");
		System.out.println("3. Compare two files");
		System.out.println("4. Compare two directories");
		System.out.println("5. Exit\n");
	}
	
	public static String fileReport(String fn, boolean save){
		
		String filename; 
		
		if(!fn.equals("")){
			filename = fn;
		}
		else{
		
			System.out.println("\nPlease enter a file name  (or \"exit\" to exit):");
			
			filename = scan.nextLine();
		}
			String report = "---" + filename + "---\n";
			
			
		if(filename.equals("exit") || filename.equals("Exit")){
			System.out.println("Exited!\n");
		}
		
		else{
			//Find the file size
			File f = new File(filename);
			long fileSize = f.length();
			
			int totalChar = 0;
			int totalWords = 0;
			int totalSentences = 0;
			String longestWord = "";
			String shortestWord = "";
			String longestSentence = "";
			String shortestSentence = "";
			Hashtable<Character, Integer> charHash = new Hashtable<Character, Integer>();
			Hashtable<String, Integer> wordHash = new Hashtable<String, Integer>();

			try{
				
				FileReader fr = new FileReader(filename);
				BufferedReader br = new BufferedReader(fr);
				String line = br.readLine();
				
				while(line!=null){
					
					line = line.toLowerCase();
					ArrayList<Character> parsedline = new ArrayList<>();
					char [] charray = line.toCharArray();
					
					for(int i=0; i<charray.length; i++){
						
						int ascii = (int)charray[i];
						
						//parsing all alphanumeric characters, ?s, !s, periods, and spaces
						if((ascii>46 && ascii<58) || (ascii>64 && ascii<91) || (ascii>96 && ascii<123) || 
								ascii==32 || ascii == 33 || ascii==46 || ascii==63){
							
							//for '/', substitute for a space
							if(ascii==47)
								parsedline.add(' ');
							else
								parsedline.add(charray[i]);
							
							//counting only numbers, upper case letters, and lower case letters
							if((ascii>47 && ascii<58) || (ascii>64 && ascii<91) || (ascii>96 && ascii<123)){
								totalChar++;
								//add/update character frequency
								if(charHash.containsKey(charray[i]))
									charHash.put(charray[i], charHash.get(charray[i])+1);
								else
									charHash.put(charray[i], 1);
							}
						}
					}
					
					char[] newline = new char[parsedline.size()];
					for(int i=0; i<parsedline.size(); i++){
						newline[i] = parsedline.get(i);
					}
					
					int lastpuncindex = -1;
					int beginningOfSentence = 0;
					shortestWord = new String(newline, 0, parsedline.size());
					shortestSentence = new String(newline, 0, parsedline.size());
					
					for(int i=0; i<parsedline.size(); i++){
						char curr = parsedline.get(i);
						if(curr==' ' || curr=='.' || curr=='!' || curr=='?'){
							
							if(i-lastpuncindex>1){
								
								totalWords++;
								String currWord = new String(newline, lastpuncindex+1, i-lastpuncindex-1);
								currWord = currWord.toLowerCase();
								
								//add/update word frequency
								if(wordHash.containsKey(currWord))
									wordHash.put(currWord, wordHash.get(currWord)+1);
								else
									wordHash.put(currWord, 1);
								
								//update longest/shortest word
								if(i-lastpuncindex-1 > longestWord.length())
									longestWord = currWord;
							
								if(i-lastpuncindex-1 < shortestWord.length())
									shortestWord = currWord;
							}
							lastpuncindex = i;
							
							
							if(curr!=' '){
								//finding longest/shortest sentence by number of characters
								//including punctuation/spaces
								totalSentences++;
								
								int sentencelength = i- beginningOfSentence;
								
								if(sentencelength > longestSentence.length()){
									longestSentence = new String(newline, beginningOfSentence, i-beginningOfSentence);
								}
								if(sentencelength < shortestSentence.length()){
									shortestSentence = new String(newline, beginningOfSentence, i-beginningOfSentence);
								}
								
								if(i+2 < parsedline.size())
									beginningOfSentence = i+2;
							}
						}
					}
					line = br.readLine();
				}
				
				br.close();
				
				int wfrequencies = 0;
				int cfrequencies = 0;
				String mostFreqWord = "";
				char mostFreqChar = '\0';
				
				Set<String> allwords = wordHash.keySet();
		        for(String k: allwords){
		        	if(wordHash.get(k)>wfrequencies){
		        		mostFreqWord = k;
		        		wfrequencies = wordHash.get(k);
		        	}
		        }
		        
		        Set<Character> allchars = charHash.keySet();
		        for(char c: allchars){
		        	if(charHash.get(c)>cfrequencies){
		        		mostFreqChar = c;
		        		cfrequencies = charHash.get(c);
		        	}
		        }
		        
		        int averageSentence = 0;
		        if(totalSentences!=0){
		        	averageSentence = totalChar/totalSentences;
		        }
				
				report = "---" + filename + "---\nSize of the file: " + fileSize + "\n" +
				"Total character count: " + totalChar + "\n" +
				"Most frequent character: " + mostFreqChar + "\n" +
				"Total word count: " + totalWords + "\n" +
				"Most frequent word: " + mostFreqWord + "\n" +
				"Average word length: " + totalChar/totalWords + "\n" +
				"Longest word: " + longestWord + "\n" +
				"Shortest word: " + shortestWord + "\n" +
				"Total sentence count: " + totalSentences + "\n" +
				"Average sentence length (valid characters): " + averageSentence + "\n" +
				"Longest sentence (by words): " + longestSentence + "\n" +
				"Shortest sentence (by words): " + shortestSentence + "\n";
				System.out.println(report);
				
			}
			catch (FileNotFoundException fnfe){
				System.out.println("FileNotFoundException: " + fnfe.getMessage());
			}
			catch (IOException ioe){
				System.out.println("IOException: " + ioe.getMessage());
			}
			
			if(save){
				System.out.println("Would you like to make a report for " + 
					filename+ "? (Enter \"y\", or anything else if No)");
				String command = scan.nextLine().toLowerCase();
				if(command.equals("y")){
					FileWriter fw = null;
					PrintWriter pw = null;
					try{
						Calendar c = Calendar.getInstance();
						java.util.Date now = c.getTime();
						java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
						String currTime = currentTimestamp.toString();
						
						fw = new FileWriter(f.getAbsoluteFile().getParent() + File.separator + currTime + ".txt");
						pw = new PrintWriter(fw);
						
						pw.println(report);
						
						pw.flush();
						System.out.println("Report created!\n");
					}
					catch(IOException ioe){
						System.out.println("IOException: " + ioe.getMessage());
					}
					finally{
						try{
							if(pw!=null)
								pw.close();
							if(fw!=null)
								fw.close();
						}
						catch(IOException ioe){
							System.out.println("IOException: " + ioe.getMessage());
						}
					}
				}
				else
					System.out.println("No report made.");
			}
		}	
		return report;
	}

	public static String directoryReport(String name, boolean save){
		
		String report = "";
		
		String directory = "";
		
		if(!name.equals("")){
			directory = name;
		}	
		else{
			System.out.println("\nPlease enter a directory name (or \"exit\" to exit):");
			directory = scan.nextLine();
		}
		
		if(directory.equals("exit") || directory.equals("Exit")){
			System.out.println("Exited!\n");
			return report;
		}
		
		else{
			
			//Find the directory size
			File f = new File(directory);
			
			if(!f.isDirectory()){
				System.out.println("Invalid Directory");
				directoryReport("", true);
			}
			else{
				System.out.println("\n---" + directory + "---");
				report+="\n---" + directory + "---\n";
				File [] fileList = f.listFiles();
				System.out.println("Number of files: " + fileList.length);
				report += "Number of files: " + fileList.length;
				String largest = "None";
				String smallest = "None";
				long totalBytes = 0;
				
				if(fileList.length>0){
					totalBytes+=fileList[0].length();
					largest = fileList[0].getName();
					smallest = fileList[0].getName();
					
					long most = fileList[0].length();
					long least = fileList[0].length();
					
					for(int i=1; i<fileList.length; i++){
						
						totalBytes+=fileList[i].length();
						
						if(fileList[i].length()>most){
							largest = fileList[i].getName();
							most = fileList[i].length();
						}
						
						if(fileList[i].length()<least){
							smallest = fileList[i].getName();
							least = fileList[i].length();
						}
					
					}
					
					System.out.println("Largest File: " + largest +
							"\nSmallest File: " + smallest +
							"\nTotal Bytes: " + totalBytes +	
							"\nAverage File Size: " + totalBytes/(fileList.length));
					
					report += "\nLargest File: " + largest +
					"\nSmallest File: " + smallest +
					"\nTotal Bytes: " + totalBytes +	
					"\nAverage File Size: " + totalBytes/(fileList.length);
				}
				
				if(save){
				
					System.out.println("Would you like to make a report for " + 
					directory+ "? (Enter \"y\", or anything else if No)");
					String command = scan.nextLine().toLowerCase();
					if(command.equals("y")){
						FileWriter fw = null;
						PrintWriter pw = null;
						try{
							fw = new FileWriter(directory + File.separator + f.getName() + "-files.txt");
							pw = new PrintWriter(fw);
							
							for(int i=0; i<fileList.length; i++){
								pw.println(fileReport(fileList[i].getName(), false));
							}
							
							pw.flush();
							System.out.println("Report created!");
						}
						catch(IOException ioe){
							System.out.println("IOException: " + ioe.getMessage());
						}
						finally{
							try{
								if(pw!=null)
									pw.close();
								if(fw!=null)
									fw.close();
							}
							catch(IOException ioe){
								System.out.println("IOException: " + ioe.getMessage());
							}
						}
					}
					else
						System.out.println("No report made.");
				}
			}	
		}
		return report;
	}
	
	public static String compareFiles(){
		
		String report = "";
		
		System.out.println("\nPlease enter the first file name (or \"exit\" to exit):");
		String fn1 = scan.nextLine();
		
		if(fn1.equals("exit") || fn1.equals("Exit")){
			System.out.println("Exitting!");
			return report;
		}
		
		System.out.println("\nPlease enter the second file name (or \"exit\" to exit):");
		String fn2 = scan.nextLine();
		
		if(fn2.equals("exit") || fn2.equals("Exit")){
			System.out.println("Exitting!");
			return report;
		}
		
		FileReader fr1 = null;
		BufferedReader br1 = null;
		FileReader fr2 = null;
		BufferedReader br2 = null;
		
		try{
			fr1 = new FileReader(fn1);
			br1 = new BufferedReader(fr1);
			fr2 = new FileReader(fn2);
			br2 = new BufferedReader(fr2);
			
			report += "\n" + fileReport(fn1, false) + "\n";
			report += fileReport(fn2, false) + "\n";
			
			String linereader1 = br1.readLine();
			String linereader2 = br2.readLine();
			
			ArrayList<String> lines1 = new ArrayList<String>();
			ArrayList<String> lines2 = new ArrayList<String>();
			
			//hash table of words from file 1, # of words
			Hashtable<String, Integer> ht1 = new Hashtable<String, Integer>();
			
			//hash table of words from file 2, # of words
			Hashtable<String, Integer> ht2 = new Hashtable<String, Integer>();

			while(linereader1!=null){
				
				linereader1 = linereader1.toLowerCase();
				lines1.add(linereader1);
				
				ArrayList<Character> parsedline = new ArrayList<>();
				char [] charray1 = linereader1.toCharArray();
				
				for(int i=0; i<charray1.length; i++){
					
					int ascii = (int)charray1[i];
					
					//parsing all alphanumeric characters, ?s, !s, periods, and spaces
					if((ascii>46 && ascii<58) || (ascii>64 && ascii<91) || (ascii>96 && ascii<123) || 
							ascii==32 || ascii == 33 || ascii==46 || ascii==63){
						
						//for '/', substitute for a space
						if(ascii==47)
							parsedline.add(' ');
						else
							parsedline.add(charray1[i]);
					}
				}
				
				char[] newline = new char[parsedline.size()];
				for(int i=0; i<parsedline.size(); i++){
					newline[i] = parsedline.get(i);
				}
				
				int lastpuncindex = -1;
				
				for(int i=0; i<parsedline.size(); i++){
					char curr = parsedline.get(i);
					if(curr==' ' || curr=='.' || curr=='!' || curr=='?'){
						
						if(i-lastpuncindex>1){

							String currWord = new String(newline, lastpuncindex+1, i-lastpuncindex-1);
							currWord = currWord.toLowerCase();
							
							//add/update word frequency
							if(ht1.containsKey(currWord))
								ht1.put(currWord, ht1.get(currWord)+1);
							else
								ht1.put(currWord, 1);
						}
						lastpuncindex = i;
					}
				}
				linereader1 = br1.readLine();

			}
			
			while(linereader2!=null){
				
				linereader2 = linereader2.toLowerCase();
				lines2.add(linereader2);
				
				ArrayList<Character> parsedline = new ArrayList<>();
				char [] charray2 = linereader2.toCharArray();
				
				for(int i=0; i<charray2.length; i++){
					
					int ascii = (int)charray2[i];
					
					//parsing all alphanumeric characters, ?s, !s, periods, and spaces
					if((ascii>46 && ascii<58) || (ascii>64 && ascii<91) || (ascii>96 && ascii<123) || 
							ascii==32 || ascii == 33 || ascii==46 || ascii==63){
						
						//for '/', substitute for a space
						if(ascii==47)
							parsedline.add(' ');
						else
							parsedline.add(charray2[i]);
					}
				}
				
				char[] newline = new char[parsedline.size()];
				for(int i=0; i<parsedline.size(); i++){
					newline[i] = parsedline.get(i);
				}
				
				int lastpuncindex = -1;
				
				for(int i=0; i<parsedline.size(); i++){
					char curr = parsedline.get(i);
					if(curr==' ' || curr=='.' || curr=='!' || curr=='?'){
						
						if(i-lastpuncindex>1){

							String currWord = new String(newline, lastpuncindex+1, i-lastpuncindex-1);
							currWord = currWord.toLowerCase();
							
							//add/update word frequency
							if(ht2.containsKey(currWord))
								ht2.put(currWord, ht2.get(currWord)+1);
							else
								ht2.put(currWord, 1);
						}
						lastpuncindex = i;
					}
				}
				linereader2 = br2.readLine();
			}
			
			report += "Lines in common: \n";
			
			System.out.println("Lines in common: \n");
			
			for(int i=0; i<lines1.size(); i++){
				for(int j=0; j<lines2.size(); j++){
					if(lines1.get(i).equals(lines2.get(j))){
						report+=lines1.get(i) + "\n";
						System.out.println(lines1.get(i) + "\n");
					}
				}
			}
			
			//hash table of common words from file 1 & 2, # of pairs
			Hashtable<String, Integer> commonhash = new Hashtable <String, Integer>();
			
			Set<String> commonwords = ht1.keySet();
	        for(String k: commonwords){
	        	if(ht2.containsKey(k)){
	        		commonhash.put(k, Math.min(ht1.get(k), ht2.get(k)));
	        	}
	        }
	        
	        //hash table of the remainders of the common words from file 1 & 2
			int highestRemainder = 0;
			int highestPairs = 0;
			String mostCommonWord = "";
	        
	        Set<String> finalwords = commonhash.keySet();
	        for(String k: finalwords){
	        	if(commonhash.get(k)>highestPairs){
	        		mostCommonWord = k;
	        		highestPairs = commonhash.get(k);
	        		highestRemainder = Math.abs(ht1.get(k)-ht2.get(k));
	        	}
	        	else if(highestPairs == commonhash.get(k)){
	        		if(highestRemainder< Math.abs(ht1.get(k)-ht2.get(k)) ){
	        			mostCommonWord = k;
	        			highestRemainder = Math.abs(ht1.get(k)-ht2.get(k));
	        		}
	        	}
	        }
			
			report += "Most common word: " + mostCommonWord;
			System.out.println("Most common word: " + mostCommonWord + "\n");
			
		}
		catch (FileNotFoundException fnfe){
			System.out.println("FileNotFoundException: " + fnfe.getMessage());
			compareFiles();
		}
		catch (IOException ioe){
			System.out.println("IOException: " + ioe.getMessage());
			compareFiles();
		}
		
		System.out.println("Would you like to make a report? (Enter \"y\", or anything else if No)");
		String command = scan.nextLine().toLowerCase();
		if(command.equals("y")){
			FileWriter fw = null;
			PrintWriter pw = null;
			try{
				Calendar c = Calendar.getInstance();
				java.util.Date now = c.getTime();
				java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
				String currTime = currentTimestamp.toString();
				File working = new File (fn1);
				fw = new FileWriter(working.getAbsoluteFile().getParent() + File.separator + currTime + ".txt");
				pw = new PrintWriter(fw);
				
				pw.println(report);
				
				pw.flush();
				System.out.println("Report created!");
			}
			catch(IOException ioe){
				System.out.println("IOException: " + ioe.getMessage());
			}
			finally{
				try{
					if(pw!=null)
						pw.close();
					if(fw!=null)
						fw.close();
				}
				catch(IOException ioe){
					System.out.println("IOException: " + ioe.getMessage());
				}
			}
		}
		else
			System.out.println("No report made.");
		
		return report;
	}
	
	public static String compareDirectories(){
		String report = "";
		
		System.out.println("\nPlease enter the first directory name (or \"exit\" to exit):");
		String dn1 = scan.nextLine();
		
		if(dn1.equals("exit") || dn1.equals("Exit")){
			System.out.println("Exited!\n");
			return report;
		}
		
		File f1 = new File(dn1);
		
		if(!f1.isDirectory()){
			System.out.println("Invalid Directory");
			compareDirectories();
		}
		
		System.out.println("\nPlease enter the second directory name (or \"exit\" to exit):");
		String dn2 = scan.nextLine();
		
		if(dn2.equals("exit") || dn2.equals("Exit")){
			System.out.println("Exitting!");
			return report;
		}
		File f2 = new File(dn2);
			
		if(!f2.isDirectory()){
			System.out.println("Invalid Directory");
			compareDirectories();
		}
		
		report += directoryReport(dn1, false) + "\n";
		report += directoryReport(dn2, false) + "\n";
		
		report += "\nCommon files: ";
		System.out.println("\nCommon files: ");
		
		File [] fileList1 = f1.listFiles();
		
		File [] fileList2 = f2.listFiles();
		
		for(int i=0; i<fileList1.length; i++){
			for(int j=0; j<fileList2.length; j++){
				if(fileList1[i].getName().equals(fileList2[j].getName())){
					report += fileList1[i].getName() + "\n";
					System.out.println(fileList1[i].getName());
				}
			}
		}	
		System.out.println("");
		
		System.out.println("Would you like to make a report? (Enter \"y\", or anything else if No)");
		String command = scan.nextLine().toLowerCase();
		if(command.equals("y")){
			FileWriter fw = null;
			PrintWriter pw = null;
			try{
				Calendar c = Calendar.getInstance();
				java.util.Date now = c.getTime();
				java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
				String currTime = currentTimestamp.toString();
				fw = new FileWriter(f1.getAbsoluteFile().getParent() + File.separator + currTime + ".txt");
				pw = new PrintWriter(fw);
				
				pw.println(report);
				
				pw.flush();
				System.out.println("Report created!\n");
			}
			catch(IOException ioe){
				System.out.println("IOException: " + ioe.getMessage());
			}
			finally{
				try{
					if(pw!=null)
						pw.close();
					if(fw!=null)
						fw.close();
				}
				catch(IOException ioe){
					System.out.println("IOException: " + ioe.getMessage());
				}
			}
		}
		else
			System.out.println("No report made.");
		return report;
	}
	
	public static void main(String[] args) {
				
		printMenu();
		
		while(scan.hasNextLine()){
				
			String input = scan.nextLine();
			
			if(input.equals("1")){
				System.out.println("File Report");
				fileReport("", true);
			}
			
			else if(input.equals("2")){
				System.out.println("Directory report");
				directoryReport("", true);
			}
			
			else if(input.equals("3")){
				System.out.println("Compare two files");
				compareFiles();
			}
			
			else if(input.equals("4")){
				System.out.println("Compare two directories");
				compareDirectories();
			}
			else if(input.equals("5"))
				break;
			else{
				System.out.println(input + " is not a valid command.");
			}
			
			printMenu();
		}
		scan.close();
		return;	
	}
}
