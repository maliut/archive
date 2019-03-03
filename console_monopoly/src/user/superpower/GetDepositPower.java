package user.superpower;

import user.Log;

public class GetDepositPower extends Superpower {

	@Override
	public boolean execute() {
		Log.log("news.getDeposit");
		getAllPlayers().forEach(p -> p.addDepositBy(0.1));
		return true;
	}
	
	private static GetDepositPower power;
	
	public static GetDepositPower getInstance() {
		if (power == null) power = new GetDepositPower();
		return power;
	}
	
	private GetDepositPower() {
		
	}

}
