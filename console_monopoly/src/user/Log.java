package user;

import kernel.Resource;

public class Log {
	/*
	 * 一个轻量级在屏幕上输出 log 的类
	 * 输出并暂停1秒
	 * 游戏中把 y = 21 这行给了 log
	 */
	public static final int MAX_LENGTH = 40;
	
	public static void log(String content, Object... args) {
		content = Resource.get(content) == null ? content : Resource.get(content);
		System.out.printf("\33[37m\33[22;5H" + content, args);
		try {
			Thread.sleep(1000);
			System.out.print("\33[37m\33[22;5H");  // 设置坐标和颜色
			for (int i = 0; i < MAX_LENGTH; i++) System.out.print("\u3000");  // 清空
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
