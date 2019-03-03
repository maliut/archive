package scene;

import java.awt.Graphics;
import java.awt.Image;
import MTool.*;

public class Title extends MPanel {
	
	private MIO mio = new MIO();
	
	private Image titleImage;
	
	public Title() {
		super();
		add(new commandSet.TitleSet());
		titleImage = mio.readImage("System/title.png");
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(titleImage, 0, 0,  22*32, 16*32, this);
	}


}
