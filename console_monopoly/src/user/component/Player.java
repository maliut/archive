package user.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import user.Card;
import user.GameManager;
import user.Log;
import kernel.GameObject;
import kernel.Resource;
import kernel.component.Component;
import kernel.component.Renderer;

/** 玩家数据相关的组件，事实上标记了这个游戏对象是一个玩家。 */
public class Player extends Component {

	public Player(GameObject gameObject) {
		super(gameObject);
	}
	
	private String name;
	private int cash, deposit, coupon;
	private transient Collection<GameObject> fields;
	private transient Collection<Card> items;
	private transient List<Integer> stockNums;  // 股票不像道具一个一个的，而是显示的时候和买卖一起，所以数量0也会显示。就用了下标的对应关系
	
	public void gg() {
		Log.log("log.gg", getDisplayName());
		//((Renderer) this.gameObject.getComponent("Renderer")).setEnabled(false);  // 不让他出现
		// 卖掉所有房产
		List<Field> copyOfFields = new ArrayList<Field>();
		this.fields.stream().map(e -> ((Field) e.getComponent("Field"))).forEach(f -> copyOfFields.add(f));
		copyOfFields.forEach(f -> f.onSellToGame());  // 不能直接在 fields 中移除，不然有并发修改的错误
		GameManager.getInstance().getPlayers().remove(this.gameObject);  // bye~
		GameObject.destroy(this.gameObject);  // 在游戏世界中消除这个对象（这样设置renderer也没什么用）
		if (GameManager.getInstance().getPlayers().size() == 1) {  // 只剩一个人
			GameManager.getInstance().endGame();  // 结束游戏
		}
	}
	
	public boolean hasItem() {
		return !items.isEmpty();
	}
	
	@Override
	public void receive(Component sender, Object... args) {

	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public void addCash(int cash) {
		this.cash += cash;
	}
	
	public void addCashBy(double rate) {
		addCash((int) (cash * rate));
	}
	
	public int getDeposit() {
		return deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}
	
	public void addDeposit(int deposit) {
		this.deposit += deposit;
	}

	public void addDepositBy(double rate) {
		addDeposit((int) (deposit * rate));
	}
	
	public int getCoupon() {
		return coupon;
	}

	public void setCoupon(int coupon) {
		this.coupon = coupon;
	}
	
	public void addCoupon(int coupon) {
		this.coupon += coupon;
	}
	
	public void addField(GameObject field) {  
		this.fields.add(field);
	}
	
	public boolean removeField(GameObject field) {
		return this.fields.remove(field);
	}
	
	public void addItem(Card card) {
		this.items.add(card);
	}
	
	public boolean removeItem(Card card) {
		return this.items.remove(card);
	}
	
	public int getFieldsValue() {
		int value = 0;
		Iterator<GameObject> it = fields.iterator();
		while (it.hasNext()) {
			value += ((Field) it.next().getComponent("Field")).getValue();
		}
		return value;
	}
	
	public int getTotalValue() {
		return cash + deposit + getFieldsValue();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		Renderer r = (Renderer) this.gameObject.getComponent("Renderer");
		return String.format("\33[%d;%dm%s\33[40;37m『%s』", r.getBackground(), r.getForeground(), Resource.get("icon.p"), getName());
	}
	
	public Collection<GameObject> getFields() {
		return fields;
	}

	public Collection<Card> getCards() {
		return items;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// 这里就相当于构造方法了
		// 如果是读档进来的，还要遍历 cells 设定这个东西。而 cells 的 owner 也是 GameObject 因此要做存档还需要在序列化方法里处理（比如存玩家id啥的）
		// 因为不做存档所以先算了
		Player player = (Player) super.clone();
		player.fields = new LinkedList<>();
		player.items = new LinkedList<>();
		player.stockNums = new ArrayList<>();
		for (int i = 0; i < 10; i++) player.stockNums.add(0);
		return player;
	}

	public int getStockNum(int index) {
		return stockNums.get(index);
	}

	public void addStockNums(int index, int num) {
		int old = this.stockNums.get(index);
		this.stockNums.set(index, old + num);
	}

}
