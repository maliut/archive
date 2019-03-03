package kernel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import kernel.component.Component;
import kernel.component.Trigger;
import kernel.util.Direction;
import kernel.util.Point;

public class GameObject {
	
	private String tag;  // 用于得到它
	private int layer;   // layer 越小，越早渲染，在越下面
	private Point position;
	private Direction direction, opdirection;
	
	private Map<String, Component> components;
	
	public GameObject() {
		components = new HashMap<String, Component>();
	}

	public Component getComponent(String componentClassName) {
		// 应该允许让编写游戏者以方便的方式得到组件，而不用写出全名。同时用户自定义的组件优先
		Component c;
		if (componentClassName.equals("Menu")) {  // 如果请求是 menu 要处理特殊情况
			String trueClassName = components.keySet().stream().filter(e -> e.matches(".+Menu")).findFirst().get();
			c = components.get(trueClassName);
		} else {
			c = components.get("user.component." + componentClassName);
		}
		return (c == null) ? components.get("kernel.component." + componentClassName) : c;	
	}
	
	public void addComponent(Component component) {
		components.put(component.getClass().getName(), component);
	}
	
	public void removeComponent(String componentClassName) {
		components.remove(componentClassName);
	}
	
	public Collection<Component> getComponents() {
		return components.values();
	}

	public void broadcast(Object args) {
		this.getComponents().forEach(e -> e.receive(null, args));
	}
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Direction getOpdirection() {
		return opdirection;
	}

	public void setOpdirection(Direction opdirection) {
		this.opdirection = opdirection;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		GameObject o = new GameObject();
		o.setPosition((Point) position.clone());// 复制位置
		o.setDirection(direction);
		o.setTag(tag);
		o.setLayer(layer);
		// 复制组件
		Iterator<Component> components = this.getComponents().iterator();
		while (components.hasNext()) {
			Component c = (Component) components.next().clone();
			c.setGameObject(o);  // 需要手动设置引用类型
			o.addComponent(c);
		}
		pool.add(o);  // 加入对象池中
		return o;
	}
	
	////////////////////////////////
	//                            //
	// static property and method //
	//                            //
	////////////////////////////////
	
	private static Collection<GameObject> pool = new LinkedList<GameObject>();
	
	public static List<GameObject> find(String tag) {
		return pool.stream().filter(e -> (e.getTag().equals(tag))).collect(Collectors.toList());
	}
	
	public static List<GameObject> findAll() {
		return pool.stream().collect(Collectors.toList());
	}
	
	public static GameObject create(String name) {
		GameObject model = Resource.getPrefab(name);  // 得到模板
		GameObject o;// = new GameObject();
		try {
			o = (GameObject) model.clone();
			//pool.add(o);  // 加入对象池中
			return o;
		} catch (Exception e1) {
			return null;
		}  
	}
	
	public static void destroy(GameObject o) {
		Trigger t = (Trigger) o.getComponent("Trigger");
		if (t != null) NotificationCenter.getInstance().removeTrigger(t);
		// 如果仅仅从 pool 里删除，那么 触发器 还留着引用，即使不渲染但还是能触发事件
		pool.remove(o);
	}

}
