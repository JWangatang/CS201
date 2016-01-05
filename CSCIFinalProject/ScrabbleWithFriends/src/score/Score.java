package score;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Score {
	
	private static JTable table;
	private static DefaultTableModel dtm;
	
	private final static JDialog display;
	
	static {
		Object[] tableHeaders = new Object[] {"Name", "Score"};
		dtm = new DefaultTableModel(tableHeaders,0);
		
		try {
			Scanner sc = new Scanner(new File("src/score/scores.txt"));
			while(sc.hasNext()) {
				Object[] row = {sc.next() + " " + sc.next(), sc.nextInt()};
				dtm.addRow(row);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("images/Riffic.ttf")).deriveFont(14F);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table = new JTable(dtm);
		table.setFont(font);
		table.getTableHeader().setFont(font);
		table.setEnabled(false);
		
		display = new JDialog();
		display.setTitle("Scores");
		display.setModal(true);
		display.setSize(250, 200);
		JScrollPane jsp = new JScrollPane(table);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		display.add(jsp);
	}
	
	public static void add(String name, int score) {
		for(int i = 0; i < dtm.getRowCount(); i++) {
			if (dtm.getValueAt(i, 0).equals(name)) {
				Object[] row = {name,score};
				dtm.insertRow(i, row);
				break;
			}
			else{
				Object[] row = {name,score};
				dtm.addRow(row);
			}
		}
		save();
	}
	
	private static void save() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter("src/score/scores.txt", "UTF-8");
			for(int i = 0; i < dtm.getRowCount(); i++) {
				pw.println(dtm.getValueAt(i, 0));
				pw.println(dtm.getValueAt(i, 1));
			}
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println("File not found: " + e.getMessage());
			System.out.println("Could not save scores!");
		} finally {
			if(pw != null){
				pw.close();
			}
		}
	}

	public void show() {
		display.setLocationRelativeTo(null);
		display.setVisible(true);
	}
}
