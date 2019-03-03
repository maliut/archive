package user.superpower;

import java.util.Collection;
import java.util.stream.Collectors;

import user.Card;
import user.GameManager;
import user.Log;
import user.component.Field;
import user.component.Player;
import kernel.GameObject;

public abstract class Superpower {

	/*
	 * 子类沙箱模式，由抽象基类提供共用的方法，子类使用此类方法和其他方法实现特殊能力
	 * 本来最好可以提供自定义的对应常量，可以在文件中实现自定义的组合效果，不过有点麻烦算了
	 * 用于卡片和新闻效果
	 */
	public abstract boolean execute();  // 返回是否成功
	
	// 得到目标的方法
	protected Collection<Field> getStreetAtPlayer(GameObject o) {
		GameObject currentCell = getCellAtPlayer(o);
		if (currentCell.getComponent("Field") == null) {
			return null;  // 玩家不在土地上
		} else {
			int street = ((Field) currentCell.getComponent("Field")).getStreet();
			return GameManager.getInstance().getMap().stream()
					.filter(e -> (e.getComponent("Field") != null))
					.map(e -> ((Field) e.getComponent("Field")))
					.filter(f -> (f.getStreet() == street))
					.collect(Collectors.toList());
		}
	}
	
	protected GameObject getCellAtPlayer(GameObject o) {
		return GameManager.getInstance().getMap().stream()
			.filter(e -> e.getPosition().equals(o.getPosition())).findFirst().get();
	}
	
	protected Player getMaxFieldsOne() {
		return GameManager.getInstance().getPlayers().stream()
				.map(e -> ((Player) e.getComponent("Player")))
				.max((p1, p2) -> p1.getFields().size() - p2.getFields().size()).get();
	}
	
	protected Player getMinFieldsOne() {
		return GameManager.getInstance().getPlayers().stream()
				.map(e -> ((Player) e.getComponent("Player")))
				.min((p1, p2) -> p1.getFields().size() - p2.getFields().size()).get();
	}

	protected Collection<Player> getAllPlayers() {
		return GameManager.getInstance().getPlayers().stream()
				.map(e -> ((Player) e.getComponent("Player")))
				.collect(Collectors.toList());
	}
	
	// 给目标得失的方法
	protected void addItem(Player p, Card card) {
		Log.log("log.gain", p.getDisplayName(), 1, card.getName());
		p.addItem(card);
	}

}
