package help;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Help {
	private JTextArea text;
	private JDialog display;
	
	public Help() {
		text = new JTextArea();
		text.setEditable(false);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setBackground(new Color(255, 229, 204));
		text.setTabSize(4);
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("images/Riffic.ttf")).deriveFont(14F);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		text.setFont(font);
		try {
			Scanner sc = new Scanner(new File("src/help/help"));
			while (sc.hasNext()) {
				text.append(sc.nextLine()+"\n");
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		display = new JDialog();
		display.setTitle("About");
		display.setModal(true);
		display.setSize(500, 250);
		display.setResizable(false);
		
		JScrollPane jsp = new JScrollPane(text);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		display.add(jsp);
	}

	public void show() {
		display.setLocationRelativeTo(null);
		display.setVisible(true);
	}
}
