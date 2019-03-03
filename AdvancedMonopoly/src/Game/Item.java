package game;

public class Item {
	
	private String name = "fff";
	private int price = 100;
	private String description = " ";
	private int[] target;
	private int[][] code = new int[2][5];
	private boolean sellable = false;
	
	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public int[][] code() {
		return code;
	}

	public int[] getTarget() {
		return target;
	}

	public boolean isSellable() {
		return sellable;
	}

}
