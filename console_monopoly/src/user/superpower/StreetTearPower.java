package user.superpower;

import java.util.Collection;

import user.GameManager;
import user.component.Field;

public class StreetTearPower extends Superpower {

	@Override
	public boolean execute() {
		Collection<Field> fields = getStreetAtPlayer(GameManager.getInstance().getPlayers().getCurrent());
		if (fields == null) return false;  // 玩家不在土地上，执行失败
		fields.stream().filter(f -> (f.getOwner() != null)).forEach(f -> f.setLevel(1));
		return true;
	}
	
	private static StreetTearPower power;
	
	public static StreetTearPower getInstance() {
		if (power == null) power = new StreetTearPower();
		return power;
	}
	
	private StreetTearPower() {
		
	}

}
