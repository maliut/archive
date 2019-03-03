package user.component;

import user.GameManager;
import kernel.GameLoop;
import kernel.GameObject;
import kernel.MenuManager;
import kernel.Resource;
import kernel.component.Component;

public class ItemShop extends Component {

	public ItemShop(GameObject gameObject) {
		super(gameObject);
	}

	@Override
	public void receive(Component sender, Object... args) {
		GameObject player = (GameObject) args[0];
		if (!player.getPosition().equals(this.gameObject.getPosition())) return;  // 需要判断坐标
		MenuManager.getInstance().showMenu("itemShopMenu");
		GameLoop.next();
		GameManager.getInstance().endRound();
	}
	
	public String getDescription() {
		return Resource.get("vocab.itemShop");
	}

}
