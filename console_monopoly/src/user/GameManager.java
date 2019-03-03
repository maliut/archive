package user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import user.component.Calendar;
import user.component.Field;
import user.component.Player;
import user.superpower.Superpower;
import kernel.GameObject;
import kernel.MenuManager;
import kernel.NotificationCenter;
import kernel.Resource;
import kernel.component.Renderer;
import kernel.component.Trigger;
import kernel.util.Date;
import kernel.util.Direction;
import kernel.util.FileReader;
import kernel.util.LoopList;
import kernel.util.Point;

public class GameManager {
	
	private Collection<GameObject> map;   // 当前地图
	private Collection<GameObject> primitiveMap;   // 原始地图
	private LoopList<GameObject> players;  // 当前所有玩家
	private List<Card> cardPool;  // 卡池
	private List<Stock> stockPool;  // 股票
	private GameObject calendar;
	private static boolean hasInitialized = false;

	public void initialize() {
		if (hasInitialized) return;  // 只能初始化一次
		readMap();		// 初始化地图，根据 res/user/map.dat
		players = new LoopList<>(GameObject.find("player"));		// 同理取得对玩家的引用
		createCalendar();
		/*playerInfo = */GameObject.create("playerInfo");		// 载入状态窗口
		createMenus();		// 载入各类菜单
		createCards();		// 创建各类卡片
		createStocks();		// 创建股票
		System.out.print("\33[?25l"); // 隐藏光标
		hasInitialized = true;
	}

	public void endRound() {
		players.moveNext();
		if (players.getCurrent() == players.get(0)) {  // 回到了第一个玩家，说明进入下一天
			NotificationCenter.getInstance().postNotification(null, Trigger.TRIGGER_ON_DATE);
		}
		MenuManager.getInstance().showMenu("mainMenu");
	}
	
	public void endGame() {
		GameObject winner = players.get(0);
		Log.log("log.win", ((Player) winner.getComponent("Player")).getDisplayName());
		System.exit(0);
	}
	
	private void readMap() {
		try {
			FileReader.readLines("res/user/map.dat").stream().filter(e -> !e.trim().equals("")).forEach(e -> {
				String[] tmp = e.split("\\s+");
				GameObject o = GameObject.create(tmp[0]);
				o.setPosition(new Point(Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2])));
				o.setDirection(Direction.valueOf(tmp[3]));
				o.setOpdirection(Direction.valueOf(tmp[4]));
				if (tmp[0].equals("field")) {
					((Field) o.getComponent("Field")).setPrice(Integer.parseInt(tmp[5]));
					((Field) o.getComponent("Field")).setStreet(Integer.parseInt(tmp[6]));
				} else if (tmp[0].equals("bank")) {
					// 在银行的格子上复用 empty 格子捕获 stay 的 trigger
					GameObject o2 = GameObject.create("empty");
					o2.setPosition(new Point(Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2])));
					o2.setTag("");  // 不进入地图列表中
					o2.removeComponent("kernel.component.Renderer");  // 不显示
				}
			});
			map = new ArrayList<>(GameObject.find("cell"));		// 取得对地图的引用，防止多次调用 GameObject.find 的开销
			primitiveMap = new ArrayList<>();	// 原始地图
			map.forEach(r -> {
				try {
					GameObject r1 = (GameObject) r.clone();
					((Renderer) r1.getComponent("Renderer")).setEnabled(false);
					NotificationCenter.getInstance().removeTrigger(((Trigger) r1.getComponent("Trigger")));
					primitiveMap.add(r1);
				} catch (Exception e1) {
					System.err.println("初始化地图失败！");
					e1.printStackTrace();
					System.exit(1);
				}
			});
		} catch (IOException e) {
			System.err.println("初始化地图失败！");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void createCalendar() {
		calendar = GameObject.create("calendar");
		((Calendar) calendar.getComponent("Calendar")).setDate(new Date(2014,1,20));
		((Renderer) calendar.getComponent("Renderer")).setContent();  // 讲道理要传一个一样的日期进去，但是特殊情况
	}
	
	private void createMenus() {
		MenuManager menus = MenuManager.getInstance();
		// 根据以 Menu 结尾的 prefab 自动添加菜单进去，省去了每个菜单都要手动注册
		Resource.getPrefabs().keySet().stream().filter(e -> e.endsWith("Menu"))
			.forEach(e -> menus.put(e, GameObject.create(e)));
	}
	
	private void createCards() {
		cardPool = new ArrayList<>();
		try {
			FileReader.readLines("res/user/card.dat").stream().filter(e -> !e.trim().equals("")).forEach(e -> {
				Card card = new Card();
				String[] tmp = e.split("\\s+");
				card.setName(tmp[0]);
				card.setSuperpower(tmp[1]);
				card.setPrice(Integer.parseInt(tmp[2]));
				card.setDescription(tmp[3]);
				cardPool.add(card);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 给 item shop menu set content。因为不会变，所以只要做一次
		// 注意这个放在这里似乎不是太好（照理要每次update）。这对初始化的顺序产生的依赖。menu必须初始化在前
		((Renderer) MenuManager.getInstance().get("itemShopMenu").getComponent("Renderer")).setContent(cardPool);
	}
	
	private void createStocks() {
		stockPool = new ArrayList<>();
		try {
			FileReader.readLines("res/user/stock.dat").stream().filter(e -> !e.trim().equals("")).forEach(e -> {
				Stock stock = new Stock();
				String[] tmp = e.split("\\s+");
				stock.setName(tmp[0]);
				stock.setPrice(Integer.parseInt(tmp[1]));
				stockPool.add(stock);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Collection<GameObject> getMap() {
		return map;
	}

	public Collection<GameObject> getPrimitiveMap() {
		return primitiveMap;
	}

	public LoopList<GameObject> getPlayers() {
		return players;
	}

	public List<Card> getCards() {
		return cardPool;
	}
	
	public List<Stock> getStocks() {
		return stockPool;
	}
	
	public Card getCard(Superpower power) {
		return cardPool.stream().filter(c -> c.getSuperpower().equals(power)).findFirst().get();
	}
	
	public Card getRandomCard() {
		int index = (int) (Math.random() * cardPool.size());
		return (Card) Arrays.asList(cardPool.toArray()).get(index);
	}
	
	public GameObject getCalendar() {
		return calendar;
	}
	
	private static GameManager instance;
	
	public static GameManager getInstance() {
		if (instance == null) instance = new GameManager();
		return instance;
	}
	
	private GameManager() {
		
	}

}
