package kernel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import kernel.component.Trigger;

public class NotificationCenter {

	private Collection<Trigger> observers;
	private transient Collection<Trigger> tmpToRemove = new ArrayList<>();

	private NotificationCenter() {
		observers = new LinkedList<Trigger>();
	}

	public void registerTrigger(Trigger trigger) {
		observers.add(trigger);
	}
	
	public void removeTrigger(Trigger trigger) {
		tmpToRemove.add(trigger);
	}
	
	/** 可以向特定类型的触发器发送通知（以触发他们并传递参数） */
	public void postNotification(GameObject sender, int filter, Object... args) {
		observers.stream().filter(e -> (e.getTriggerType() == filter)).forEach(e -> e.onTrigger(sender, args));
	}
	
	// 更新方法，用于删除之前被标记的触发器。
	// 不能在 remove 中直接删除，这样会报同步修改的错
	public void update() {
		tmpToRemove.forEach(t -> observers.remove(t));
		tmpToRemove = new ArrayList<>();
	}
	
	private static NotificationCenter instance;
	
	public static NotificationCenter getInstance() {
		if (instance == null) {
			instance = new NotificationCenter();
		}
		return instance;
	}
	
}
