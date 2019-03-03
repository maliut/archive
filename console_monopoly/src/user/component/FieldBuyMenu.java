package user.component;

import java.util.List;

import user.GameManager;
import kernel.GameObject;
import kernel.NotificationCenter;
import kernel.Resource;
import kernel.component.Menu;
import kernel.component.Trigger;

public class FieldBuyMenu extends Menu {

	public FieldBuyMenu(GameObject gameObject) {
		super(gameObject);
	}

	@Override
	protected void dealSelectedItem(int item) {
		GameObject currentPlayer = GameManager.getInstance().getPlayers().getCurrent();
		NotificationCenter.getInstance().postNotification(
				this.gameObject, Trigger.TRIGGER_ON_STAY, currentPlayer, FieldBuyMenu.class, item);
	}
	
	public String format(List<Object> o) {
		Field field = (Field) o.get(0);
		return String.format(Resource.get("menu.fieldBuy"), 
				Resource.get("vocab.street." + field.getStreet()), field.getPrice());
	}

	@Override
	public void update() {
		// 在买地时展示那块地的信息，在 field 里面做掉了
		// （field 里要调出这个菜单之前就要自己 set content）
	}
}
