package user.superpower;

import java.util.Collection;

import user.GameManager;
import user.component.Field;
import user.component.Player;

public class StreetResetPower extends Superpower {

	@Override
	public boolean execute() {
		Collection<Field> fields = getStreetAtPlayer(GameManager.getInstance().getPlayers().getCurrent());
		if (fields == null) return false;  // 玩家不在土地上，执行失败
		fields.stream().filter(f -> (f.getOwner() != null)).forEach(f -> {
			((Player) f.getOwner().getComponent("Player")).addCash((int) (f.getValue() * 1.5));
			f.onSellToGame();
		});
		return true;
	}

	private static StreetResetPower power;
	
	public static StreetResetPower getInstance() {
		if (power == null) power = new StreetResetPower();
		return power;
	}
	
	private StreetResetPower() {
		
	}
}
