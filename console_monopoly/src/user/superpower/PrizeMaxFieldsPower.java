package user.superpower;

import user.Log;
import user.component.Player;

public class PrizeMaxFieldsPower extends Superpower {

	@Override
	public boolean execute() {
		Player target = getMaxFieldsOne();
		int randomCash = 1000 + ((int) (Math.random() * 4)) * 1000;
		Log.log("news.prizeMaxFields", target.getDisplayName(), randomCash);
		target.addCash(randomCash);
		return true;
	}
	
	private static PrizeMaxFieldsPower power;
	
	public static PrizeMaxFieldsPower getInstance() {
		if (power == null) power = new PrizeMaxFieldsPower();
		return power;
	}
	
	private PrizeMaxFieldsPower() {
		
	}

}
