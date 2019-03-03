package user.component;

import user.Card;
import user.GameManager;
import user.superpower.DiceControlPower;
import kernel.GameObject;
import kernel.MenuManager;
import kernel.component.Menu;

public class DiceControlMenu extends Menu {

	public DiceControlMenu(GameObject gameObject) {
		super(gameObject);
	}

	@Override
	protected void dealSelectedItem(int item) {
		if (item == 6) {  // cancel
			MenuManager.getInstance().showMenu("itemMenu");
		} else {
			Card card = GameManager.getInstance().getCard(DiceControlPower.getInstance());
			((DiceControlPower) card.getSuperpower()).setResult(item);
			if (card.execute()) {
				((Player) GameManager.getInstance().getPlayers().getCurrent().getComponent("Player")).removeItem(card);
			}
		}

	}

}
