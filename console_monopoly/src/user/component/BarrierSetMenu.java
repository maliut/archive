package user.component;

import user.Card;
import user.GameManager;
import user.superpower.CellBlockPower;
import kernel.GameObject;
import kernel.MenuManager;
import kernel.component.Menu;
import kernel.component.Mover;
import kernel.util.Point;

public class BarrierSetMenu extends Menu {
	
	private Point position;

	public BarrierSetMenu(GameObject gameObject) {
		super(gameObject);
	}

	@Override
	protected void dealSelectedItem(int item) {
		if (item >= -8 && item <= 8) {
			GameObject currentPlayer = GameManager.getInstance().getPlayers().getCurrent();
			// 要取得放路障的具体位置
			position = currentPlayer.getPosition();
			dealPosition(item);	
			// 放路障
			Card card = GameManager.getInstance().getCard(CellBlockPower.getInstance());
			((CellBlockPower) card.getSuperpower()).setPosition(position);
			if (card.execute()) {
				((Player) GameManager.getInstance().getPlayers().getCurrent().getComponent("Player")).removeItem(card);
			}
			MenuManager.getInstance().showMenu("mainMenu");
		} else {
			MenuManager.getInstance().showMenu("itemMenu");  // 取消
		}

	}

	private void dealPosition(int step) {
		if (step == 0) return;  // 就在当前位置
		boolean faceFront = step > 0;  // 取得方向
		step = Math.abs(step);  // 取得绝对的步数
		GameObject it = GameObject.create("player");  // 借一个假想的玩家
		it.setPosition(position);  // 先设为当前位置
		for (int i = 0; i < step; i++) {
			((Mover) it.getComponent("Mover")).move(faceFront ? getCellAt(position).getDirection() : getCellAt(position).getOpdirection());
			position = it.getPosition();
		}
		GameObject.destroy(it);  // 还回去
	}

	private GameObject getCellAt(Point position) {
		return GameManager.getInstance().getMap().stream()
				.filter(e -> e.getPosition().equals(position))
				.findFirst().get();
	}

}
