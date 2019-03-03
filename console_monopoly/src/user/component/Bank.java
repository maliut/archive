package user.component;

import user.Log;
import kernel.GameLoop;
import kernel.GameObject;
import kernel.MenuManager;
import kernel.Resource;
import kernel.component.Component;

public class Bank extends Component {

	private transient int option;  // 选择存款还是取款？
	
	public Bank(GameObject gameObject) {
		super(gameObject);
	}

	private void onSave(GameObject o, int cash) {
		Player player = (Player) o.getComponent("Player");
		if (player.getCash() < cash) {
			Log.log("log.lackCash");
			MenuManager.getInstance().showMenu("bankInputMenu");
			GameLoop.next();
		} else {
			player.addCash(-cash);
			player.addDeposit(cash);
		}
	}
	
	private void onFetch(GameObject o, int cash) {
		Player player = (Player) o.getComponent("Player");
		if (player.getDeposit() < cash) {
			Log.log("log.lackDeposit");
			MenuManager.getInstance().showMenu("bankInputMenu");
			GameLoop.next();
		} else {
			player.addCash(cash);
			player.addDeposit(-cash);
		}
	}
	
	@Override
	public void receive(Component sender, Object... args) {
		GameObject player = (GameObject) args[0];
		if (!player.getPosition().equals(this.gameObject.getPosition())) return;  // 需要判断坐标
		if (args[1] == MainMenu.class) {
			MenuManager.getInstance().showMenu("bankOptionMenu");
			GameLoop.next();
		} else if (args[1] == BankOptionMenu.class) {
			if (((int) args[2]) == 2) {
				//GameManager.getInstance().endRound();  // 不操作
			} else {
				option = (int) args[2];
				MenuManager.getInstance().showMenu("bankInputMenu");
				GameLoop.next();
			}
		} else if (args[1] == BankInputMenu.class) {
			if (option == 0) {  // 这里简单粗暴把负数也当成正数来处理
				onSave(player, Math.abs((int) args[2]));
			} else {  //if (option == 1)
				onFetch(player, Math.abs((int) args[2]));
			}
		}
	}
	
	public String getDescription() {
		return Resource.get("vocab.bank");
	}

}
