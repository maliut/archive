package game;

import java.awt.image.BufferedImage;

public class Cell {

	// can to json
	protected int x=0;
	protected int y=0;
	protected int iconX=0;
	protected int iconY=0;
	protected int location;
	
	transient protected BufferedImage icon;
	
	public Cell(int x,int y,int ix, int iy) {
		this.x = x;
		this.y = y;
		this.iconX = ix;
		this.iconY = iy;
	}
	
	public void setIcon(BufferedImage icon) {
		this.icon = icon;
	}
	
	public BufferedImage getIcon() {
		return icon;
	}
	
	public int getIconX() {
		return iconX;
	}
	
	public int getIconY() {
		return iconY;
	}
	
	public int getLocation() {
		return location;
	}
}
