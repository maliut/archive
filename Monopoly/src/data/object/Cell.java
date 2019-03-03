package data.object;

import data.module.Vocab;


public class Cell {
	
	public int type = 0;
	public String icon = Vocab.CellIcon[type];
	public int street = 0, streetNo = 0;
	public int x = 0, y = 0;
	public int owner = 0;  // system
	public int level = 1, price = 0;
	public boolean isBarrier = false;	
	public static final int MAX_LEVEL = 6;

}
