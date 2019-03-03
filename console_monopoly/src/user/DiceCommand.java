package user;

import user.component.MainMenu;
import kernel.GameObject;
import kernel.NotificationCenter;
import kernel.component.Mover;
import kernel.component.Renderer;
import kernel.component.Trigger;
import kernel.util.Point;

public class DiceCommand {

	/*
	 * 用于连接发出行走指令和触发地图事件的类
	 */
	
	private boolean isBlocked;

	public void execute() {
		execute((int) (Math.random() * 6) + 1);
	}
	
	public void execute(int num) {
		Log.log("log.dice", num);
		// 得到当前玩家
		GameObject currentPlayer = GameManager.getInstance().getPlayers().getCurrent();
		// 得到当前玩家所在位置的格子
		Point currentPosition = currentPlayer.getPosition();
		// 这次行走没有遇到路障
		isBlocked = false;
		for (int i = 0; i < num; i++) {
			((Mover) currentPlayer.getComponent("Mover")).move(getCellAt(currentPosition).getDirection());  // 走一步
			refreshMap(currentPosition);  // 更新地图
			currentPosition = currentPlayer.getPosition();
			NotificationCenter.getInstance().postNotification(
					null, Trigger.TRIGGER_ON_PASS, currentPlayer, MainMenu.class);  
			// 发送过路事件，意为从 main menu进入的事件，触发者是 current player
			if (isBlocked) break;  
			// 判断是否有路障。因为路障要打破这个循环而不是继续，因此只能在这里进行判断，否则就可以和银行一样了
			// 但是之前路障的component还是能接收到 trigger_on_pass ，在那里它销毁自己
			try {
				Thread.sleep(400);  // 命令行应该是单线程，使用这个方法一般不会报错
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		NotificationCenter.getInstance().postNotification(
				null, Trigger.TRIGGER_ON_STAY, currentPlayer, MainMenu.class); // 发送到达事件
	}

	private void refreshMap(Point currentPosition) {  
		// 因为游戏循环每接收一次（非移动光标）输入进行一次，那么中间要改变图形必须强制渲染
		// 同时因为每个格子和玩家都是可以覆盖的所以事先不需要调用清除方法了
		//GameManager.getInstance().getMap().forEach(e -> ((Renderer) e.getComponent("Renderer")).forceRender());
		// 优化，只更新受玩家影响的格子
		((Renderer) getCellAt(currentPosition).getComponent("Renderer")).forceRender();
		GameManager.getInstance().getPlayers().forEach(e -> ((Renderer) e.getComponent("Renderer")).forceRender());
		((Renderer) GameManager.getInstance().getPlayers().getCurrent().getComponent("Renderer")).forceRender(); 
		// 当前角色在最上面，增强用户体验
		// 之所以要更新所有玩家而不是一个玩家，是因为玩家间可能存在重叠。这样被覆盖的玩家上面的玩家走了以后，下面会丢失
	}

	private GameObject getCellAt(Point position) {
		return GameManager.getInstance().getMap().stream()
				.filter(e -> e.getPosition().equals(position))
				.findFirst().get();
	}
	
	private static DiceCommand instance;
	
	public static DiceCommand getInstance() {
		if (instance == null) instance = new DiceCommand();
		return instance;
	}
	
	private DiceCommand() {
		
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
}
