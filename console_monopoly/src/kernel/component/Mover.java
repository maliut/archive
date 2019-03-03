package kernel.component;

import kernel.GameObject;
import kernel.util.Direction;
import kernel.util.Point;

public class Mover extends Component {

	public Mover(GameObject gameObject) {
		super(gameObject);
	}

	public void move(Direction direction) {
		gameObject.setDirection(direction);
		int ox = gameObject.getPosition().getX();
		int oy = gameObject.getPosition().getY();
		switch (direction) {
		case UP:
			gameObject.setPosition(new Point(ox, oy - 2)); // 命令行的问题，原点是(1,1)			
			break;
		case DOWN:
			gameObject.setPosition(new Point(ox, oy + 2));
			break;
		case LEFT:
			gameObject.setPosition(new Point(ox - 4, oy));
			break;
		case RIGHT:
			gameObject.setPosition(new Point(ox + 4, oy));
			break;
		}	
	}
	
	@Override
	public void receive(Component sender, Object... args) {
		// TODO Auto-generated method stub

	}

}
