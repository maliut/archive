package user.superpower;

import user.Stock;

public class StockDownPower extends Superpower {
	
	private Stock target;

	@Override
	public boolean execute() {
		target.setExpectChange(-10);
		return true;
	}

	public void setTarget(Stock target) {
		this.target = target;
	}
	
	private static StockDownPower power;
	
	public static StockDownPower getInstance() {
		if (power == null) power = new StockDownPower();
		return power;
	}
	
	private StockDownPower() {
		
	}

}