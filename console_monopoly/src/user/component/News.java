package user.component;

import user.GameManager;
import user.superpower.GetDepositPower;
import user.superpower.LoseDepositPower;
import user.superpower.PrizeMaxFieldsPower;
import user.superpower.PrizeMinFieldsPower;
import user.superpower.RandomCardPower;
import kernel.GameObject;
import kernel.Resource;
import kernel.component.Component;

public class News extends Component {

	public News(GameObject gameObject) {
		super(gameObject);
	}

	public void onNews(GameObject o) {
		int type = (int) (Math.random() * 5);
		switch (type) {
		case 0:
			PrizeMaxFieldsPower.getInstance().execute();
			break;
		case 1:
			PrizeMinFieldsPower.getInstance().execute();
			break;
		case 2:
			GetDepositPower.getInstance().execute();
			break;
		case 3:
			LoseDepositPower.getInstance().execute();
			break;
		case 4:
			RandomCardPower.getInstance().execute();
			break;
		}
	}
	
	@Override
	public void receive(Component sender, Object... args) {
		GameObject player = (GameObject) args[0];
		if (!player.getPosition().equals(this.gameObject.getPosition())) return;  // 需要判断坐标
		onNews(player);
		GameManager.getInstance().endRound();
	}
	
	public String getDescription() {
		return Resource.get("vocab.news");
	}

}
