package kernel.component;

import kernel.GameObject;
import kernel.NotificationCenter;

/** Trigger 表明这个 GameObject 可以在它的位置上触发事件（想象不带物理效果的碰撞体）<br>
 *  Trigger 是一个 Observer，可以触发 Trigger 的物体位置改变时通过 NotificationCenter 向其发送信息
 *  即调用 update 方法，Trigger 应检测是否触发了自己 */
public class Trigger extends Component {

	public static final int TRIGGER_ON_BEGIN = 0, TRIGGER_ON_PASS = 1, TRIGGER_ON_STAY = 2,
			TRIGGER_ON_DATE = 3, TRIGGER_ON_INPUT = 4;
	
	private int triggerType, triggerPriority;  // 触发优先级，给路障用，其他都是0
	private boolean enabled;
	
	public Trigger(GameObject gameObject, int triggerType) {
		super(gameObject);
		this.triggerType = triggerType;
		NotificationCenter.getInstance().registerTrigger(this);
	}

	public void onTrigger(GameObject sender, Object... args) {
		if (!enabled) return;
		this.broadcast(args);  // 把自己触发到的角色广播出去
	}

	@Override
	public void receive(Component sender, Object... args) {

	}

	public int getTriggerType() {
		return triggerType;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// 处理 Trigger
		// 因为游戏机制的原因，在游戏中得到一个对象是用 clone 方法来做的
		// 而 Trigger 的构造函数中包含了注册自己的逻辑，因此需要自己做一遍。
		Trigger ret = (Trigger) super.clone();
		//ret = new Trigger(ret.gameObject, ret.getTriggerType());
		NotificationCenter.getInstance().registerTrigger(ret);
		return ret;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getTriggerPriority() {
		return triggerPriority;
	}

	public void setTriggerPriority(int triggerPriority) {
		this.triggerPriority = triggerPriority;
	}

}
