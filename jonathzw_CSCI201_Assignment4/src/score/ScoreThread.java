package score;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ScoreThread extends Thread{
	private Socket s;
	
	private PrintWriter pw = null;
	
	private File scores = new File("src/score/scores");
	
	public ScoreThread(Socket s, ScoreServer ss){
		this.s = s;
	}
	
	public void run(){
		try{
			pw = new PrintWriter(s.getOutputStream());

			Scanner sc;
			sc = new Scanner(scores);
				
			pw.println("HTTP/1.1 200 OK");
			pw.println("Content-Type: text/html");
			pw.println("\r\n");
			
			pw.println("<p><b><font size=\"32\"> Sorry! Top Score List </font></b></p>");
			

			pw.println("<table border=\"1\" style=\"width:15%\">");
			pw.println("<th>Name</th>");
			pw.println("<th>Scores</th>");

			while(sc.hasNext()){
				pw.println("<tr>");
				pw.println("<td>" + sc.next() + "</td>" + "<td>" + sc.nextInt() + "</td>");
				pw.println("</tr>");
			}
			pw.println("</table>");

			pw.flush();
			sc.close();
			
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		} catch(IOException ioe){
			System.out.println("IOE in ScoreThread constructor: " + ioe.getMessage());
		} finally{
			if(pw!=null) pw.close();
		}
	}
}
