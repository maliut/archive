package user.superpower;

public class TestEmptyPower extends Superpower {

	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private static TestEmptyPower power;
	
	public static TestEmptyPower getInstance() {
		if (power == null) power = new TestEmptyPower();
		return power;
	}
	
	private TestEmptyPower() {
		
	}

}
