package user.component;

import user.DiceCommand;
import kernel.GameObject;
import kernel.component.Component;

public class Barrier extends Component {

	@Override
	public void receive(Component sender, Object... args) {
		GameObject player = (GameObject) args[0];
		if (!player.getPosition().equals(this.gameObject.getPosition())) return;  // 需要判断坐标
		//　如果遇到了路障
		DiceCommand.getInstance().setBlocked(true);  // 设置打断标记
		GameObject.destroy(this.gameObject);  // 更好的方法是设置一个 路障池，这样可以复用，不过在路障不多的情况下就暴力了
	}

}
