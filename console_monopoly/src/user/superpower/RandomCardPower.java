package user.superpower;

import user.GameManager;
import user.Log;

public class RandomCardPower extends Superpower {

	@Override
	public boolean execute() {
		Log.log("news.getRandomCard");
		getAllPlayers().forEach(p -> addItem(p, GameManager.getInstance().getRandomCard()));
		return true;
	}
	
	private static RandomCardPower power;
	
	public static RandomCardPower getInstance() {
		if (power == null) power = new RandomCardPower();
		return power;
	}
	
	private RandomCardPower() {
		
	}

}
