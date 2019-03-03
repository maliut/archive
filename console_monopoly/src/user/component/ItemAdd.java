package user.component;

import user.Card;
import user.GameManager;
import user.Log;
import kernel.GameObject;
import kernel.Resource;
import kernel.component.Component;

public class ItemAdd extends Component {

	public ItemAdd(GameObject gameObject) {
		super(gameObject);
	}

	private void onItemAdd(GameObject o) {
		Player player = (Player) o.getComponent("Player");
		Card card = GameManager.getInstance().getRandomCard();
		Log.log("log.gain", player.getDisplayName(), 1, card.getName());
		player.addItem(card);
	}
	
	@Override
	public void receive(Component sender, Object... args) {
		GameObject player = (GameObject) args[0];
		if (!player.getPosition().equals(this.gameObject.getPosition())) return;  // 需要判断坐标
		onItemAdd(player);
		GameManager.getInstance().endRound();
	}
	
	public String getDescription() {
		return Resource.get("vocab.itemAdd");
	}
}
