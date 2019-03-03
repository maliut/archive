package kernel.component;

import kernel.GameObject;

public abstract class Component implements Cloneable {
	
	protected transient GameObject gameObject;
	
	public Component() {
		
	}
	
	public Component(GameObject gameObject) {
		this.setGameObject(gameObject);
	}

	public void broadcast(Object... args) {
		gameObject.getComponents().forEach(e -> e.receive(this, args));
	}
	
	public abstract void receive(Component sender, Object... args);
	
	public void update() {
		
	}

	public GameObject getGameObject() {
		return gameObject;
	}

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
