package user.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import user.Card;
import user.GameManager;
import user.Log;
import user.superpower.CellBlockPower;
import user.superpower.DiceControlPower;
import user.superpower.StockDownPower;
import user.superpower.StockUpPower;
import kernel.GameObject;
import kernel.MenuManager;
import kernel.Resource;
import kernel.component.Menu;
import kernel.component.Renderer;
import kernel.util.Point;

public class ItemMenu extends Menu {
	
	private transient List<Card> cardTypeList;

	public ItemMenu(GameObject gameObject) {
		super(gameObject);
	}

	@Override
	protected void dealSelectedItem(int item) {
		if (item == cardTypeList.size()) {  // 取消
			MenuManager.getInstance().showMenu("mainMenu");
		} else {
			Card card = cardTypeList.get(item);
			// 有二级菜单的卡片要到二级菜单里面去发动
			if (card.getSuperpower().equals(DiceControlPower.getInstance())) {  // 遥控骰子
				MenuManager.getInstance().showMenu("diceControlMenu");
			} else if (card.getSuperpower().equals(CellBlockPower.getInstance())) {  // 路障
				MenuManager.getInstance().showMenu("barrierSetMenu");
			} else if (card.getSuperpower().equals(StockUpPower.getInstance()) || 
					card.getSuperpower().equals(StockDownPower.getInstance())) { 
				if (((Calendar) GameManager.getInstance().getCalendar().getComponent("Calendar")).isWeekend()) {
					Log.log("log.stockClose");
				} else {
					StockOptionMenu menu = (StockOptionMenu) MenuManager.getInstance().get("stockOptionMenu").getComponent("Menu");
					menu.setIntent(MainMenu.class);
					menu.setCardType(card.getSuperpower()); 
					MenuManager.getInstance().showMenu("stockOptionMenu");
				}
			} else {
				if (card.execute()) {  // 发动成功
					((Player) GameManager.getInstance().getPlayers().getCurrent().getComponent("Player")).removeItem(card);
				} else {
					Log.log("log.invalidItem");
				}
			}
		}
	}
	
	@Override
	public void update() {
		((Renderer) this.gameObject.getComponent("Renderer")).setContent(  /* 更新菜单内容以供使用 */
				((Player) GameManager.getInstance().getPlayers().getCurrent().getComponent("Player")).getCards());
		super.itemNum = cardTypeList.size() + 1;  // 动态设定菜单项
		super.cursor = 0;
		super.cursorPoint = new Point(gameObject.getPosition().getX(), gameObject.getPosition().getY());  // 指针归位
	}
	
	public String format(List<Object> o) {
		// 要把一个玩家的一堆道具分类整合成一个 “xx： n个”这样的列表供玩家选择
		cardTypeList = new ArrayList<>();
		List<Integer> cardNumList = new ArrayList<>();
		Map<Card, Integer> cardListIndex = new HashMap<>();  // 记录每个 card 在 typelist中的下标
		@SuppressWarnings("unchecked")
		Collection<Card> items = (Collection<Card>) o.get(0);
		items.forEach(i -> { 
			if (cardTypeList.contains(i)) {  // 之前已经遇到过了，那么这个卡的数量+1
				int index = cardListIndex.get(i);
				cardNumList.set(index, cardNumList.get(index) + 1);
			} else {  // 之前没遇到过，那么添加一项
				cardTypeList.add(i);
				cardNumList.add(1);
				cardListIndex.put(i, cardListIndex.size());
			}
		});
		List<String> ret = new ArrayList<>();
		for (int i = 0; i < cardTypeList.size(); i++) {
			ret.add(String.format("   %s x%d :%s", cardTypeList.get(i).getName(), cardNumList.get(i), cardTypeList.get(i).getDescription()));
		}  // 生成选择菜单
		ret.add("   " + Resource.get("vocab.cancel"));
		return String.join("$n", ret);
	}

}
