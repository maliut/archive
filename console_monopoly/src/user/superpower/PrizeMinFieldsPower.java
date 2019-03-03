package user.superpower;

import user.Log;
import user.component.Player;

public class PrizeMinFieldsPower extends Superpower {

	@Override
	public boolean execute() {
		Player target = getMinFieldsOne();
		int randomCash = 1000 + ((int) (Math.random() * 4)) * 1000;
		Log.log("news.prizeMinFields", target.getDisplayName(), randomCash);
		target.addCash(randomCash);
		return true;
	}
	
	private static PrizeMinFieldsPower power;
	
	public static PrizeMinFieldsPower getInstance() {
		if (power == null) power = new PrizeMinFieldsPower();
		return power;
	}
	
	private PrizeMinFieldsPower() {
		
	}

}
