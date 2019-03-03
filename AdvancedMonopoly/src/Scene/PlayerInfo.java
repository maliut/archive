package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import module.DataManager;
import MTool.MIO;
import MTool.MPanel;

public class PlayerInfo extends MPanel {
	
	private BufferedImage[] iconSet = new BufferedImage[5];  // cash,deposit,coupon,finefree?,slow?
	private MIO mio = new MIO();
	private DataManager data = new DataManager();
	
	public PlayerInfo() throws FileNotFoundException, IOException {
		BufferedImage icons = mio.readBufferedImage("System/IconSets.png");
		for (int i = 0; i < iconSet.length; i++) {
			iconSet[i] = icons.getSubimage(i * 24, 0, 24, 24);
		}
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(0,0,0,128));
		g.fillRect(0,0, 22*32, 32);
		Color c = data.system().getPlayerColor(data.players().getCurrentIndex());
		g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 220));
		g.fillRect(8, 6, 20, 20);
		g.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		g.setColor(new Color(255,255,255,255));
		g.drawString(data.players().getCurrent().getActor().getName(), 40, 24);
		g.drawImage(iconSet[0], 120+8, 4, this);
		g.drawString(String.format("%7d",data.players().getCurrent().getCash()), 120+32, 24);
		g.drawImage(iconSet[1], 240+8, 4, this);
		g.drawString(String.format("%7d",data.players().getCurrent().getDeposit()), 240+32, 24);
		g.drawImage(iconSet[2], 360+8, 4, this);
		g.drawString(String.format("%7d",data.players().getCurrent().getCoupon()), 360+32, 24);
		if (data.players().getCurrent().getFineFreeRound() > 0) {
			g.drawImage(iconSet[3], 480+8, 4, this);
		}
		if (data.players().getCurrent().getSlowRound() > 0) {
			g.drawImage(iconSet[4], 510+8, 4, this);
		}
		g.drawString(data.system().getDate().toString(), 546, 24);
	}

}
