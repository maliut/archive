package kernel;

import java.awt.event.KeyEvent;
import java.util.List;
import kernel.component.Renderer;

public class GameLoop {
	
	private static Input input = new Input();
	static int[] inputTmp = new int[1];  // 用于和 Input 之间多线程交换数据
	
	public static void start() {
		while (true) next();		// the game loop
	}
	
	public static void next() {
		update(); //每个对象renderer要set content
		render();
		processInput(MenuManager.getInstance().getNextMenuRequestInputType());		
	}
	
	private static void update() {
		// update 要做的事：每个组件 update
		// 在这个机制中，每次菜单输入为一次循环
		// 循环间玩家数据等会发生变化
		// 因此需要为依赖于变化数据的 Renderer 在 update 方法中 set content
		// 参见 PlayerInfo 等
		GameObject.findAll().forEach(e -> e.getComponents().forEach(c -> c.update()));
		NotificationCenter.getInstance().update();
	}

	private static void processInput(int type) {
		input.setInputMode(type);
		onGetInput(type);
	}

	private static void onGetInput(int type) {
		input.requestInput();  // 请求输入开始
		while (true) {  // 无限循环，因玩家输入光标移动指令后并不代表选择结束
			synchronized (inputTmp) {
				try {
					inputTmp.wait();  // 等待输入
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} // after notify			 
			/*  通知组件按键的内容
			 *  这里不用 notify 机制的原因是 menu 作为比较特殊的东西，一次只会有1个 menu 是 active 的，
			 *  而且隔壁 MenuManager 的实现机制也要保存这个活动的 menu，因此可以直接向它发消息
			 *  采用原来的机制浪费性能不说，还会引起 bug
			 *  注：将游戏循环从递归改成循环后买彩票只有第一次正常，后续好像发给彩票菜单消息后不知道哪里还调用了显示主菜单跳到下一轮
			 */
			/*
			 * 注：虽然这里看起来有一点代码冗余，但是事实上必须先判断 if 语句再去分发消息进行逻辑处理
			 * 因为在过路事件比如银行中，处理过路事件是手动调用 next() 的一次性循环
			 * 在大循环中相当于递归，但是这个时候把上一层的 inputTmp 给改掉了，导致递归返回时下一步判断出错
			 */
			if (type == Input.REQUEST_INT || inputTmp[0] == KeyEvent.VK_ENTER || inputTmp[0] == KeyEvent.VK_SPACE) {
				MenuManager.getInstance().getMenuOnShow().getComponent("Menu").receive(null, inputTmp[0]);
				break;
			} else {
				MenuManager.getInstance().getMenuOnShow().getComponent("Menu").receive(null, inputTmp[0]);
			}
		}
	}

	private static void render() {
		List<GameObject> all = GameObject.findAll();
		all.sort((e1, e2) -> (e1.getLayer() - e2.getLayer()));
		System.out.println("\33[2J");  // 对每个做 clear 似乎闪屏很厉害
		all.stream().filter(e -> (e.getComponent("Renderer") != null))
			.forEach(e -> ((Renderer) e.getComponent("Renderer")).render());
	}

}
