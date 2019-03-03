package user.component;

import user.GameManager;
import user.Log;
import kernel.GameObject;
import kernel.MenuManager;
import kernel.Resource;
import kernel.component.Component;

public class Lottery extends Component {

	public Lottery(GameObject gameObject) {
		super(gameObject);
	}

	private void onBoughtLottery(GameObject o, int number) {
		if (number <= 0 || number > 10) return;  // 取消
		Player player = (Player) o.getComponent("Player");
		if (player.getCash() < 100) {  // 买不起
			Log.log("log.lackCash");
		} else {
			player.addCash(-100);
			if (number / 2 == ((int) (Math.random() * 10 + 1)) / 2) {  // 两边除2，概率提高到20%
				Log.log("log.lotteryWin");
				player.addCash(5000);
			} else {
				Log.log("log.lotteryLose");
			}
		}
	}
	
	@Override
	public void receive(Component sender, Object... args) {
		GameObject player = (GameObject) args[0];
		if (!player.getPosition().equals(this.gameObject.getPosition())) return;  // 需要判断坐标
		if (args[1] == MainMenu.class) {
			MenuManager.getInstance().showMenu("lotteryMenu");
		} else if (args[1] == LotteryMenu.class) {
			onBoughtLottery(player, (int) args[2]);
			GameManager.getInstance().endRound();
		}
	}
	
	public String getDescription() {
		return Resource.get("vocab.lottery");
	}

}
