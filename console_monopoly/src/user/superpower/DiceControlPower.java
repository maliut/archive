package user.superpower;

import user.DiceCommand;

public class DiceControlPower extends Superpower {

	private int result;
	
	@Override
	public boolean execute() {
		DiceCommand.getInstance().execute(result);
		return true;
	}

	public void setResult(int result) {
		this.result = result + 1;  // 从 0-5 映射到 1-6
	}

	private static DiceControlPower power;
	
	public static DiceControlPower getInstance() {
		if (power == null) power = new DiceControlPower();
		return power;
	}
	
	private DiceControlPower() {
		
	}
}
