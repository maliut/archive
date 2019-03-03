package user.component;

import user.GameManager;
import kernel.GameObject;
import kernel.MenuManager;
import kernel.component.Menu;
import kernel.component.Renderer;

public class ReturnMenu extends Menu {

	public ReturnMenu(GameObject gameObject) {
		super(gameObject);
	}

	@Override
	protected void dealSelectedItem(int item) {
		GameManager.getInstance().getPrimitiveMap().forEach(e -> ((Renderer) e.getComponent("Renderer")).setEnabled(false));
		GameManager.getInstance().getMap().forEach(e -> ((Renderer) e.getComponent("Renderer")).setEnabled(true));
		GameManager.getInstance().getPlayers().forEach(e -> ((Renderer) e.getComponent("Renderer")).setEnabled(true));
		GameObject.find("barrier").forEach(e -> ((Renderer) e.getComponent("Renderer")).setEnabled(true));
		MenuManager.getInstance().showMenu("mainMenu");
	}

}
