package user.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import user.GameManager;
import user.Log;
import kernel.GameObject;
import kernel.MenuManager;
import kernel.Resource;
import kernel.component.Component;
import kernel.component.Renderer;

/** 表明是土地， 并处理土地买卖的事件 */
public class Field extends Component {

	private static final int MAX_LEVEL = 6;
	
	public Field(GameObject gameObject) {
		super(gameObject);
	}

	private int price, level;
	private int street;
	private transient GameObject owner;  // 通过 player 的列表反序列化后赋值。 然而事实上因为不做存档所以不用管了反正是 null
	
	private void onField(GameObject o) {
		if (owner == null) {
			((Renderer) MenuManager.getInstance().get("fieldBuyMenu").getComponent("Renderer")).setContent(this);
			MenuManager.getInstance().showMenu("fieldBuyMenu");
		} else if (o == owner) {
			((Renderer) MenuManager.getInstance().get("fieldUpMenu").getComponent("Renderer")).setContent(this);
			MenuManager.getInstance().showMenu("fieldUpMenu");
		} else {
			onFine(o);
		}
	}
	
	private void onBuy(GameObject o) {
		Player thisone = (Player) o.getComponent("Player");
		if (thisone.getCash() >= price) {
			thisone.addCash(-price);
			thisone.addField(this.gameObject);
			this.owner = o;
			this.level = 1;
		} else {
			Log.log("log.lackCash");
		}
	}

	private void onUpgrade(GameObject o) {
		if (this.level >= MAX_LEVEL) {
			Log.log("log.maxLevel");
		} else {
			Player thisone = (Player) o.getComponent("Player");
			int cost = getValue() / 2;
			if (thisone.getCash() >= cost) {
				thisone.addCash(-cost);
				this.level += 1;
			} else {
				Log.log("log.lackCash");
			}
		}
	}
	
	private void onFine(GameObject o) {
		Player thisone = (Player) o.getComponent("Player");
		Player ownerone = (Player) this.owner.getComponent("Player");
		int fine = calculateFine();
		Log.log("log.fine", fine);
		if (thisone.getTotalValue() < fine) {  // 付不起
			ownerone.addCash(thisone.getTotalValue());  // 所有钱
			thisone.gg();  // 输了
			GameManager.getInstance().getPlayers().movePrevious();  // 因为下一轮要move next
		} else {
			ownerone.addCash(fine);  // 要付的钱
			if (thisone.getCash() >= fine) {  // 现金即可
				thisone.addCash(-fine);
			} else {
				fine -= thisone.getCash();  // 剩下的罚款
				thisone.setCash(0);  // 现金扣光
				if (thisone.getDeposit() >= fine) { // 存款够用
					thisone.addDeposit(-fine);
				} else {
					fine -= thisone.getDeposit();
					thisone.setDeposit(0);  // 存款扣光
					// 开始扣房产
					int fieldValue = 0;
					List<GameObject> f2sell = new ArrayList<>();  // 保存要卖出的 field，不能遍历时直接删除
					List<Object> fields = Arrays.asList(thisone.getFields().toArray());  // 玩家持有的房产
					for (int i = 0; i < fields.size(); i++) {
						fieldValue += ((Field) ((GameObject) fields.get(i)).getComponent("Field")).getValue();
						f2sell.add((GameObject) fields.get(i));
						if (fieldValue >= fine) break;  // 够了。这里肯定会调用，因为之前已经判断了破产的情况。
					}
					// 扣掉房产
					f2sell.stream().map(e -> ((Field) e.getComponent("Field"))).forEach(f -> f.onSellToGame());
					// 剩余金钱加入现金
					thisone.addCash(fieldValue - fine); 
				}
			}
		}
		GameManager.getInstance().endRound();
	}

	public void onSellToGame() {
		((Player) this.owner.getComponent("Player")).removeField(this.gameObject);  // owner 不再拥有自己
		this.owner = null;
		this.level = 0;
	}
	
	private int calculateFine() {
		return (int) (getValue() * 0.2 + 
			GameManager.getInstance().getMap().stream()
			.filter(e -> (e.getComponent("Field") != null))
			.map(e -> ((Field) e.getComponent("Field")))
			.filter(f -> (f.street == this.street && f.owner == this.owner))
			.mapToDouble(f -> (f.getValue() * 0.1)).sum());
	}

	@Override
	public void receive(Component sender, Object... args) {
		GameObject player = (GameObject) args[0];
		if (!player.getPosition().equals(this.gameObject.getPosition())) return;  // 需要判断坐标
		if (args[1] == MainMenu.class) {  // 从 main menu 过来的，意为可能还要问玩家要不要买地
			onField(player);
		} else if (args[1] == FieldBuyMenu.class) {  // 从 field buy menu 过来的，意为选择好了买还是不买
			if (((int) args[2]) == 0) onBuy(player); 
			GameManager.getInstance().endRound();
		} else if (args[1] == FieldUpMenu.class) {  // 选择是否升级
			if (((int) args[2]) == 0) onUpgrade(player);
			GameManager.getInstance().endRound();
		}
	}

	@Override
	public void update() {
		((Renderer) this.gameObject.getComponent("Renderer")).setContent(this);
	}
	
	public String format(List<Object> o) {
		Field field = (Field) o.get(0);
		if (field.owner == null) {
			return Resource.get("icon.f.lv0");
		} else {
			Renderer playerR = ((Renderer) field.owner.getComponent("Renderer"));
			return String.format("\33[%dm%s", playerR.getForeground(), Resource.get("icon.f.lv" + field.level));
		}
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public void addLevel(int level) {
		this.level += level;
	}

	public int getStreet() {
		return street;
	}

	public void setStreet(int street) {
		this.street = street;
	}

	public GameObject getOwner() {
		return owner;
	}

	public void setOwner(GameObject owner) {
		this.owner = owner;
	}
	
	public int getValue() {
		return price * level;
	}
	
	public String getDescription() {
		// 光华大道[LV1] 价值：450  拥有者：？
		return String.format("%s%s\u3000价值：%d\u3000拥有者：%s", 
				Resource.get("vocab.street." + street),
				((level > 0) ? "[LV" + level + "]" : ""),
				this.getValue(),
				((owner == null) ? "<无主>" : ((Player) this.owner.getComponent("Player")).getDisplayName()));			
	}

}
