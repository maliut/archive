package user.component;

import user.GameManager;
import user.Log;
import kernel.GameObject;
import kernel.MenuManager;
import kernel.component.Menu;

public class StockInputMenu extends Menu {
	
	private int stockIndex;

	public StockInputMenu(GameObject gameObject) {
		super(gameObject);
	}

	@Override
	protected void dealSelectedItem(int item) {
		Player current = (Player) GameManager.getInstance().getPlayers().getCurrent().getComponent("Player"); 
		if (item > 0) {
			buyStock(current, item);
		} else if (item < 0) {
			sellStock(current, -item);
		} else {  // item == 0, cancel
			MenuManager.getInstance().showMenu("stockOptionMenu");
		}

	}

	private void sellStock(Player player, int num) {
		if (player.getStockNum(stockIndex) < num) {
			Log.log("log.lackStock");
		} else {
			player.addCash(GameManager.getInstance().getStocks().get(stockIndex).getPrice() * num);
			player.addStockNums(stockIndex, -num);
			MenuManager.getInstance().showMenu("stockOptionMenu");
		}
	}

	private void buyStock(Player player, int num) {
		int cashNeed = GameManager.getInstance().getStocks().get(stockIndex).getPrice() * num;
		if (player.getCash() < cashNeed) {
			Log.log("log.lackCash");
		} else {
			player.addCash(-cashNeed);
			player.addStockNums(stockIndex, num);
			MenuManager.getInstance().showMenu("stockOptionMenu");
		}
	}

	public void setStockIndex(int stockIndex) {
		this.stockIndex = stockIndex;
	}
}
