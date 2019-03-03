package game;

import module.DataManager;

public class Map {
	
	//private static DataManager data = new DataManager();
	
	// can to json
	private int length;
	transient private Cell[] cell;
	private int[] cellType = new int[62];
	private int[] startPoint = new int[2];
	
	public int length() {
		return length;
	}
	
	public Cell[] getCellArray() {
		// use only for save
		return cell;
	}
	
	public int getCellType(int location) {
		return cellType[ensure(location)];
	}
	
	public Cell getCellAt(int location) {
		return cell[ensure(location)];
	}
	
	public void setCellAt(int location, Cell cell) {
		if (this.cell == null) {
			this.cell = new Cell[this.length];
		}
		if (location >= 0 && location < cellType.length) {
			this.cell[location] = cell;
		} 
	}
	
	public int getStartPoint(int index) {
		// when player num > the set num, others will be default at loaction 0
		//return (index >= 0 && index < startPoint.length) ? startPoint[index] : 0;
		return startPoint[index % startPoint.length]; 
	}
	
	public int getFaceDirectionAt(int index, int clockwise) {
		switch (cell[ensure(index + clockwise)].x - cell[ensure(index)].x) {
		case 1:
			return 2; // face east
		case -1:
			return 1; // face west
		}
		switch (cell[ensure(index + clockwise)].y - cell[ensure(index)].y) {
		case 1:
			return 0; // face south
		case -1:
			return 3; // face north
		}
		return -1; // error
	}
	
	private int ensure(int location) {
		while (location < 0) {
			location += length;
		}
		while (location >= length) {
			location -= length;
		}
		return location;
	}
}
