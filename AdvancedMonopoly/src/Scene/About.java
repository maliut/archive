package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import module.SceneManager;
import MTool.MIO;
import MTool.MPanel;

public class About extends MPanel {
	
	private static SceneManager sm = new SceneManager();
	private static MIO mio = new MIO();
	private Image backImage;
	private Image titleImage;
	private Scanner input;
	private ArrayList<String> texts = new ArrayList<String>();
	
	public About() {
		super();
		backImage = mio.readImage("System/about.png");
		titleImage = mio.readImage("System/title.png");
		try {
			input = new Scanner(new File("readme.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (input.hasNext()) {
			texts.add(input.nextLine());
		}
		input.close();
		addMouseListener(new NewMouseListener());
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(titleImage, 0, 0,  22*32, 16*32, this);
		g.drawImage(backImage, 3*32, 2*32, 16*32, 12*32, this);
		g.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 22));
		g.setColor(new Color(255, 255, 255, 255));	
		for (int i = 0; i < texts.size(); i++) {
			g.drawString(texts.get(i), 3 * 32 + 16, (3 + i) * 32 + 16);
		}
	}

	class NewMouseListener extends MouseAdapter {
		
		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {  
	            sm.switchScene(new Title());
	        }  
		}
	}

}
