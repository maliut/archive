package user.component;

import java.util.List;

import user.GameManager;
import user.Log;
import kernel.component.Component;
import kernel.component.Renderer;
import kernel.util.Date;

public class Calendar extends Component {

	private Date date;

	public boolean isWeekend() {
		return date.isWeekend();
	}
	
	@Override
	public void receive(Component sender, Object... args) {
		onNextDay();			// 收到意味着过了一天
	}

	private void onNextDay() {
		date.nextDay();
		((Renderer) this.gameObject.getComponent("Renderer")).setContent();  // 更新显示的内容
		if (!isWeekend()) GameManager.getInstance().getStocks().forEach(s -> s.updatePrice()); // 更新股票
		if (date.isEndOfMonth()) {  // 发利息
			Log.log("log.addDeposit");
			GameManager.getInstance().getPlayers().stream().map(e -> ((Player) e.getComponent("Player")))
				.forEach(p -> p.addDepositBy(0.1));
		}
	}
	
	public String format(List<Object> o) {
		return date.toString();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
