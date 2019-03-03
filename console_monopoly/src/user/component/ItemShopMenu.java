package user.component;

import java.util.List;

import user.Card;
import user.GameManager;
import user.Log;
import kernel.GameLoop;
import kernel.GameObject;
import kernel.Resource;
import kernel.component.Menu;

public class ItemShopMenu extends Menu {

	public ItemShopMenu(GameObject gameObject) {
		super(gameObject);
	}

	@Override
	protected void dealSelectedItem(int item) {
		List<Card> cards = GameManager.getInstance().getCards();
		if (item == cards.size()) {
			// cancel
		} else {
			Card thiscard = cards.get(item);
			Player current = (Player) GameManager.getInstance().getPlayers().getCurrent().getComponent("Player");
			if (current.getCoupon() < thiscard.getPrice()) {
				Log.log("log.lackCoupon");
			} else {
				current.addCoupon(-thiscard.getPrice());
				current.addItem(thiscard);
			}
			GameLoop.next();
		}
	}
	
	public String format(List<Object> o) {
		@SuppressWarnings("unchecked")
		List<Card> cards = (List<Card>) o.get(0);
		String format = "   %-5s%5s    %s";
		String title = String.format(format, Resource.get("vocab.sellable.name"), Resource.get("vocab.sellable.price"), Resource.get("vocab.sellable.description"));
		String content = "";
		for (Card c:cards) {
			content += "$n" + String.format(format, c.getName(), c.getPrice() + "", c.getDescription());
		}
		String cancel = "$n   " + Resource.get("vocab.cancel");
		return title + content + cancel;
	}

}
