package user.superpower;

import user.Log;

public class LoseDepositPower extends Superpower {

	@Override
	public boolean execute() {
		Log.log("news.loseDeposit");
		getAllPlayers().forEach(p -> p.addDepositBy(-0.1));
		return true;
	}

	private static LoseDepositPower power;
	
	public static LoseDepositPower getInstance() {
		if (power == null) power = new LoseDepositPower();
		return power;
	}
	
	private LoseDepositPower() {
		
	}
}
