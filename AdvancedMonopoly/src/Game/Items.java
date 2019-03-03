package game;

public class Items {
	
	private Item[] items;// = new Item[10];
	/*{
		for (int i = 0; i < items.length; i++) {
			items[i] = new Item();
		}
	}*/
	
	public Item get(int i) {
		return (i >=0 && i < items.length) ? items[i] : null;
	}
	
	public int getRandomIndex() {
		return (int) (Math.random() * items.length);
	}
	
	public int getIndexFromItem(Item item) {
		for (int i = 0; i < items.length; i++) {
			if (items[i].equals(item)) {
				return i;
			}
		}
		return -1;
	}
	
	public int size() {
		return items.length;
	}
	
	public Item getNearestPriceTo(int price) {
		Item target = items[0];
		for (int i = 0; i < items.length; i++) {
			if (Math.abs(items[i].getPrice() - price) < Math.abs(target.getPrice() - price)) {
				target = items[i];
			} else if (Math.abs(items[i].getPrice() - price) == Math.abs(target.getPrice() - price)) {
				if (Math.random() < 0.5) {
					target = items[i];
				}
			}
		}
		return target;
	}

}
