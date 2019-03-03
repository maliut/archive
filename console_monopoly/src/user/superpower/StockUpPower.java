package user.superpower;

import user.Stock;

public class StockUpPower extends Superpower {
	
	private Stock target;
	private int change;

	@Override
	public boolean execute() {
		target.setExpectChange(change);
		return true;
	}

	public void setTarget(Stock target) {
		this.target = target;
	}

	public void setChange(int change) {
		this.change = change;
	}
	
	private static StockUpPower power;
	
	public static StockUpPower getInstance() {
		if (power == null) power = new StockUpPower();
		return power;
	}
	
	private StockUpPower() {
		
	}

}
