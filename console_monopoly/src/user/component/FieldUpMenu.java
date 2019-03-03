package user.component;

import java.util.List;

import user.GameManager;
import kernel.GameObject;
import kernel.NotificationCenter;
import kernel.Resource;
import kernel.component.Menu;
import kernel.component.Trigger;

public class FieldUpMenu extends Menu {

	public FieldUpMenu(GameObject gameObject) {
		super(gameObject);
	}

	@Override
	protected void dealSelectedItem(int item) {
		GameObject currentPlayer = GameManager.getInstance().getPlayers().getCurrent();
		NotificationCenter.getInstance().postNotification(
				this.gameObject, Trigger.TRIGGER_ON_STAY, currentPlayer, FieldUpMenu.class, item);
	}
	
	public String format(List<Object> o) {
		Field field = (Field) o.get(0);
		return String.format(Resource.get("menu.fieldUp"), 
				Resource.get("vocab.street." + field.getStreet()), field.getValue() / 2);
	}

}
