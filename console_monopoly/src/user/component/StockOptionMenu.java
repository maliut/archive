package user.component;

import java.util.List;

import user.Card;
import user.GameManager;
import user.Stock;
import user.superpower.StockUpPower;
import user.superpower.StockDownPower;
import user.superpower.Superpower;
import kernel.GameObject;
import kernel.MenuManager;
import kernel.Resource;
import kernel.component.Menu;
import kernel.component.Renderer;

public class StockOptionMenu extends Menu {

	private Class<? extends Menu> intent;  // 调出这个菜单的目的：StockInputMenu-买卖;MainMenu-用卡
	private Superpower cardType;  
	
	public StockOptionMenu(GameObject gameObject) {
		super(gameObject);
	}

	@Override
	protected void dealSelectedItem(int item) {
		if (item == 10) {  // 写死。取消
			if (intent == MainMenu.class) {
				MenuManager.getInstance().showMenu("itemMenu");
			} else if (intent == StockInputMenu.class) {
				MenuManager.getInstance().showMenu("mainMenu");
			}			
		} else {
			if (intent == MainMenu.class) {
				Card card = GameManager.getInstance().getCard(cardType);
				if (cardType instanceof StockUpPower) {
					((StockUpPower) cardType).setTarget(GameManager.getInstance().getStocks().get(item));
				} else {
					((StockDownPower) cardType).setTarget(GameManager.getInstance().getStocks().get(item));
				}
				//((StockControlPower) card.getSuperpower()).setChange((cardType == 0) ? 10 : -10);  // 红卡：黑卡
				if (card.execute()) {
					((Player) GameManager.getInstance().getPlayers().getCurrent().getComponent("Player")).removeItem(card);
				}
				MenuManager.getInstance().showMenu("mainMenu");
			} else if (intent == StockInputMenu.class) {
				((StockInputMenu) MenuManager.getInstance().get("stockInputMenu").getComponent("Menu")).setStockIndex(item);
				MenuManager.getInstance().showMenu("stockInputMenu");
			}
		}
	}

	@Override
	public void update() {
		((Renderer) this.gameObject.getComponent("Renderer")).setContent(GameManager.getInstance().getStocks());
	}
	
	public String format(List<Object> o) {
		@SuppressWarnings("unchecked")
		List<Stock> stocks = (List<Stock>) o.get(0);
		Player current = ((Player) GameManager.getInstance().getPlayers().getCurrent().getComponent("Player"));
		String format = "   %-5s%10s%s%10s\33[37m%10s";
		String title = String.format(format, Resource.get("vocab.sellable.name"), Resource.get("vocab.sellable.price"), "", 
				Resource.get("vocab.sellable.change"), Resource.get("vocab.sellable.num"));
		String content = "";
		for (int i = 0; i < stocks.size(); i++) {
			Stock s = stocks.get(i);
			content += "$n" + String.format(format, s.getName(), "" + s.getPrice(), 
					getColorFromChange(s.getRealChange()), s.getRealChange() + "%",
					"" + current.getStockNum(i));
		}
		String cancel = "$n   " + Resource.get("vocab.cancel");
		return title + content + cancel;
	}
	
	private String getColorFromChange(int change) {
		if (change > 0) {
			return "\33[31m";  // red
		} else if (change < 0) {
			return "\33[32m";  // green
		} else {  // == 0
			return "\33[37m";  // white
		}
	}
	
	public void setIntent(Class<? extends Menu> intent) {
		this.intent = intent;
	}

	public void setCardType(Superpower cardType) {
		this.cardType = cardType;
	}

}
