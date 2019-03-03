package user.component;

import java.util.List;

import user.GameManager;
import kernel.GameObject;
import kernel.Resource;
import kernel.component.Component;
import kernel.component.Renderer;
import kernel.util.LoopList;

public class PlayerInfo extends Component {

	public PlayerInfo() {
		super();
	}
	
	public PlayerInfo(GameObject gameObject) {
		super(gameObject);
	}

	public String format(List<Object> o) {
		@SuppressWarnings("unchecked")
		LoopList<GameObject> players = (LoopList<GameObject>) o.get(0);
		String ret = "";
		for (int i = 0; i < players.size(); i++) {
			Player pi = (Player) players.get(i).getComponent("Player");
			//Renderer ri = (Renderer) players.get(i).getComponent("Renderer");
			String si = String.format("%s$n%s:%12d$n%s:%12d$n%s:%12d$n%s:%12d$n%s:%10d$n$n", 
					pi.getDisplayName(),
					Resource.get("vocab.cash"), pi.getCash(),
					Resource.get("vocab.deposit"), pi.getDeposit(),
					Resource.get("vocab.coupon"), pi.getCoupon(),
					Resource.get("vocab.fieldsValue"), pi.getFieldsValue(),
					Resource.get("vocab.totalValue"), pi.getTotalValue());
			ret += si;
		}
		return ret;
	}
	
	@Override
	public void update() {
		((Renderer) this.gameObject.getComponent("Renderer")).setContent(GameManager.getInstance().getPlayers());
	}
	
	@Override
	public void receive(Component sender, Object... args) {
		// TODO Auto-generated method stub

	}

}
