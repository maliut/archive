import java.io.IOException;
import java.util.Scanner;

import user.GameManager;
import user.component.Player;
import kernel.GameLoop;
import kernel.GameObject;
import kernel.MenuManager;
import kernel.Resource;
import kernel.component.Renderer;

public class Monopoly {
	
	public static void main(String[] args) throws IOException {
		Resource.load();  // 读取系统资源
		createPlayers();  // 创建人物的场景，比较简单就单独了
		GameManager.getInstance().initialize();  // 初始化游戏
		MenuManager.getInstance().showMenu("mainMenu");  // 设置初始菜单为主菜单
		cheat();
		GameLoop.start();	// 进入游戏循环
	}
	
	private static void cheat() {
		GameManager.getInstance().getPlayers().stream().map(o -> ((Player) o.getComponent("Player")))
			.forEach(p -> {
				GameManager.getInstance().getCards().forEach(c -> {
					for (int i = 0; i < 10; i++) p.addItem(c);
				});
		});
	}

	private static void createPlayers() {
		// 初始化玩家
		// brute force !!!
		Scanner scanner = new Scanner(System.in);
		System.out.print("\n\u3000欢迎来到大富翁！\n\u3000请输入玩家1名字：");
		createAPlayer(scanner.nextLine(), 1);
		System.out.print("\u3000请输入玩家2名字：");
		createAPlayer(scanner.nextLine(), 2);
		for (int i = 3; i <= 4; i++) {
			System.out.print("\u3000请输入玩家" + i + "名字（s-跳过）：");
			String input = scanner.nextLine();
			if (input.trim().equals("s")) break;
			createAPlayer(input, i);
		}
		scanner.close();
		// clear screen
		System.out.println("\33[2J"); 
	}
	
	private static void createAPlayer(String name, int index) {
		GameObject player = GameObject.create("player");
		((Player) player.getComponent("Player")).setName(name);
		((Renderer) player.getComponent("Renderer")).setForeground(30 + index);
	}

}
