package game;

import module.DataManager;

public class Field extends Cell {

	private static DataManager data = new DataManager();
	
	private int price = 500;
	private int level = 1;
	private int ownerIndex = -1; 
	private String street = "";
	final static int MAX_LEVEL = 5;
	
	
	public Field(int x, int y, int ix, int iy,int lv) {
		super(x, y, ix, iy);
		setLevel(lv);
	}


	public int getOwnerIndex() {
		return ownerIndex;
	}


	public void setOwnerIndex(int ownerIndex) {
		this.ownerIndex = ownerIndex;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}
	
	public void levelUp() {
		this.level += 1;
		this.level = (this.level > MAX_LEVEL) ? MAX_LEVEL : this.level;
	}

	public void levelDown() {
		this.level -= 1;
		this.level = (this.level < 1) ? 1 : this.level;
	}
	
	public void resetLevel() {
		this.level = 1;
	}
	
	public int getInitialPrice() {
		return price;
	}
	
	public int getValue() {
		return price * level;
	}

	public String getStreet() {
		return street;
	}
	
	public int getFee() {
		int fee = getValue() * 3 / 10;
		for (int i = 0; i < data.map().length(); i++) {
			if (data.map().getCellAt(i) instanceof Field) {
				if (((Field) data.map().getCellAt(i)).getStreet().equals(street) && ((Field) data.map().getCellAt(i)).getOwnerIndex() == ownerIndex) {
					fee += ((Field) data.map().getCellAt(i)).getValue() / 10;
				}
			}
		}
		return fee;
	}

}
