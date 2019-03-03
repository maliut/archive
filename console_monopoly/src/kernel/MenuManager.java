package kernel;

import java.util.HashMap;
import java.util.Map;

import kernel.component.Menu;
import kernel.component.Renderer;
import kernel.component.Trigger;

public class MenuManager {

	/*
	 * 菜单管理器
	 * 菜单的特点是同一时间只显示一个菜单。本来想用栈来做，可以方便实现返回功能
	 * 但又因为每一个菜单是单例，所以也要维持每个菜单的引用
	 * 所以专门写个管理器
	 */
	private Map<String, GameObject> menus;
	private GameObject menuOnShow;
	private int nextMenuRequestInputType;
	
	public void put(String name, GameObject menu) {
		menus.put(name, menu);
	}
	
	public GameObject get(String key) {
		return menus.get(key);
	}
	
	public void showMenu(String key) {
		if (menuOnShow != null) {
			((Renderer) menuOnShow.getComponent("Renderer")).setEnabled(false);
			((Trigger) menuOnShow.getComponent("Trigger")).setEnabled(false);
		}
		menuOnShow = menus.get(key);
		((Trigger) menuOnShow.getComponent("Trigger")).setEnabled(true);
		((Renderer) menuOnShow.getComponent("Renderer")).setEnabled(true);
		nextMenuRequestInputType = ((Menu) menuOnShow.getComponent("Menu")).getRequestInputType();
		//GameLoop.next(((Menu) menuOnShow.getComponent("Menu")).getRequestInputType());  // 因为机制是接收一次输入过一遍循环
	}

	int getNextMenuRequestInputType() {
		return nextMenuRequestInputType;
	}

	GameObject getMenuOnShow() {
		return menuOnShow;
	}
	
	private static MenuManager instance;
	
	public static MenuManager getInstance() {
		if (instance == null) instance = new MenuManager();
		return instance;
	}
	
	private MenuManager() {
		menus = new HashMap<String, GameObject>();
	}

}
