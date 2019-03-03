package user.component;

import java.util.List;

import user.DiceCommand;
import user.GameManager;
import user.Log;
import kernel.GameObject;
import kernel.MenuManager;
import kernel.Resource;
import kernel.component.Menu;
import kernel.component.Renderer;

public class MainMenu extends Menu {

	public MainMenu(GameObject gameObject) {
		super(gameObject);
	}

	protected void dealSelectedItem(int item) {
		switch (item) {
		case 0:  // 查看原始地图
			dealPrimitiveMap();	break;
		case 1:  // 查看指定地点信息
			dealCellMessage();	break;
		case 2:
			dealItem();			break;
		case 3:
			dealStock();		break;
		case 4:
			DiceCommand.getInstance().execute();	break;
		case 5:  // 认输
			dealGG();			break;
		}
	}
	
	private void dealCellMessage() {
		((Renderer) MenuManager.getInstance().get("cellMessageMenu").getComponent("Renderer")).setContent(
				String.format(Resource.get("menu.cellMessage"), "\u3000"));
		MenuManager.getInstance().showMenu("cellMessageMenu");
	}

	private void dealPrimitiveMap() {
		GameManager.getInstance().getPrimitiveMap().forEach(e -> {
			((Renderer) e.getComponent("Renderer")).setEnabled(true);
			((Renderer) e.getComponent("Renderer")).forceRender();
		});
		GameManager.getInstance().getMap().forEach(e -> ((Renderer) e.getComponent("Renderer")).setEnabled(false));
		GameManager.getInstance().getPlayers().forEach(e -> ((Renderer) e.getComponent("Renderer")).setEnabled(false));
		GameObject.find("barrier").forEach(e -> ((Renderer) e.getComponent("Renderer")).setEnabled(false));
		// 临时禁用了地图的渲染。需要在返回后改回去
		MenuManager.getInstance().showMenu("returnMenu");
	}

	private void dealStock() {
		if (((Calendar) GameManager.getInstance().getCalendar().getComponent("Calendar")).isWeekend()) {
			Log.log("log.stockClose");
		} else {
			((StockOptionMenu) MenuManager.getInstance().get("stockOptionMenu").getComponent("StockOptionMenu")).setIntent(StockInputMenu.class);
			MenuManager.getInstance().showMenu("stockOptionMenu");
		}
	}

	private void dealItem() {
		if (((Player) GameManager.getInstance().getPlayers().getCurrent().getComponent("Player")).hasItem()) {
			MenuManager.getInstance().showMenu("itemMenu");
		} else {
			Log.log("log.lackItem");
		}
	}
	
	private void dealGG() {
		((Player) GameManager.getInstance().getPlayers().getCurrent().getComponent("Player")).gg();
		GameManager.getInstance().getPlayers().movePrevious();  // 因为下一轮要move next
		GameManager.getInstance().endRound();
	}

	public String format(List<Object> o) {
		GameObject p = (GameObject) o.get(0);
		return String.format(Resource.get("menu.main"), ((Player) p.getComponent("Player")).getDisplayName());
	}
	
	@Override
	public void update() {
		((Renderer) this.gameObject.getComponent("Renderer")).setContent(GameManager.getInstance().getPlayers().getCurrent());
	}
}
