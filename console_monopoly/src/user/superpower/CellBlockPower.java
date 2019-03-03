package user.superpower;

import kernel.GameObject;
import kernel.util.Point;

public class CellBlockPower extends Superpower {

	private Point position;
	
	@Override
	public boolean execute() {
		GameObject o = GameObject.create("barrier");
		o.setPosition(position);
		return true;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
	
	private static CellBlockPower power;
	
	public static CellBlockPower getInstance() {
		if (power == null) power = new CellBlockPower();
		return power;
	}
	
	private CellBlockPower() {
		
	}

}
