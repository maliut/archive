package game;

import module.DataManager;
import module.SceneManager;
import module.Vocab;

public class ItemShop extends Cell {

	private static DataManager data = new DataManager();
	private static SceneManager sm = new SceneManager();
	
	public ItemShop(int x, int y, int ix, int iy) {
		super(x, y, ix, iy);
		// TODO Auto-generated constructor stub
	}

	public static void makeItem(int coupon, int cash) {
		int price = ((int) (Math.random() * 150 + 50)) * coupon;
		if (cash > 3) {
			price += (int) (Math.random() * Math.log(cash) * 100);
		}
		Item item = data.items().getNearestPriceTo(price);
		data.players().getCurrent().addItem(data.items().getIndexFromItem(item));
		sm.getMessageScene().showMessage(String.format(Vocab.GetItemPrompt, item.getName()), 3, false);
	}
	
}
