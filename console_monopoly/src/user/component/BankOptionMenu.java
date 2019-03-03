package user.component;

import user.GameManager;
import kernel.GameObject;
import kernel.NotificationCenter;
import kernel.component.Menu;
import kernel.component.Trigger;

public class BankOptionMenu extends Menu {

	public BankOptionMenu(GameObject gameObject) {
		super(gameObject);
	}

	@Override
	protected void dealSelectedItem(int item) {
		GameObject currentPlayer = GameManager.getInstance().getPlayers().getCurrent();
		NotificationCenter.getInstance().postNotification(
				this.gameObject, Trigger.TRIGGER_ON_PASS, currentPlayer, BankOptionMenu.class, item);
	}


}
