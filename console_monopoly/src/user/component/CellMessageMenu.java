package user.component;

import java.lang.reflect.Method;

import user.GameManager;
import kernel.GameObject;
import kernel.MenuManager;
import kernel.Resource;
import kernel.component.Menu;
import kernel.component.Mover;
import kernel.component.Renderer;
import kernel.util.Point;

public class CellMessageMenu extends Menu {

	public CellMessageMenu(GameObject gameObject) {
		super(gameObject);
	}

	@Override
	protected void dealSelectedItem(int item) {
		if (item == 0) {
			MenuManager.getInstance().showMenu("mainMenu");
		} else {
			item %= GameManager.getInstance().getMap().size();
			GameObject o  = getCellAt(getPosition(item));
			o.getComponents().forEach(c -> {
				try {
					Method method = c.getClass().getMethod("getDescription");
					if (method != null) {
						String desc = (String) method.invoke(c);
						((Renderer) gameObject.getComponent("Renderer")).setContent(
								String.format(Resource.get("menu.cellMessage"), desc));
					}
				} catch (Exception e) {
					//e.printStackTrace();找不到方法会报错，这是正常现象
				}
			});
		}

	}

	private Point getPosition(int step) {
		Point position = GameManager.getInstance().getPlayers().getCurrent().getPosition();
		boolean faceFront = step > 0;  // 取得方向
		step = Math.abs(step);  // 取得绝对的步数
		GameObject it = GameObject.create("player");  // 借一个假想的玩家
		it.setPosition(position);  // 先设为当前位置
		for (int i = 0; i < step; i++) {
			((Mover) it.getComponent("Mover")).move(faceFront ? getCellAt(position).getDirection() : getCellAt(position).getOpdirection());
			position = it.getPosition();
		}
		GameObject.destroy(it);  // 还回去
		return position;
	}
	
	private GameObject getCellAt(Point position) {
		return GameManager.getInstance().getMap().stream()
				.filter(e -> e.getPosition().equals(position))
				.findFirst().get();
	}

}
