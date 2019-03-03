package user.component;

import user.GameManager;
import user.Log;
import kernel.GameObject;
import kernel.Resource;
import kernel.component.Component;

public class CouponAdd extends Component {

	public CouponAdd(GameObject gameObject) {
		super(gameObject);
	}

	@Override
	public void receive(Component sender, Object... args) {
		GameObject player = (GameObject) args[0];
		if (!player.getPosition().equals(this.gameObject.getPosition())) return;  // 需要判断坐标
		onCouponAdd(player);
		GameManager.getInstance().endRound();
	}

	private void onCouponAdd(GameObject o) {
		Player thisone = (Player) o.getComponent("Player");
		int coupon = ((int) (Math.random() * 5 + 1)) * 10;
		thisone.addCoupon(coupon);
		Log.log("log.gain", thisone.getDisplayName(), coupon, Resource.get("vocab.coupon"));
	}

	public String getDescription() {
		return Resource.get("vocab.couponAdd");
	}
}
