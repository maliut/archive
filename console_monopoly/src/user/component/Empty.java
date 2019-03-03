package user.component;

import user.GameManager;
import kernel.GameObject;
import kernel.Resource;
import kernel.component.Component;

public class Empty extends Component {

	@Override
	public void receive(Component sender, Object... args) {
		GameObject player = (GameObject) args[0];
		if (!player.getPosition().equals(this.gameObject.getPosition())) return;  // 需要判断坐标
		GameManager.getInstance().endRound();
	}
	
	public String getDescription() {
		return Resource.get("vocab.empty");
	}

}
