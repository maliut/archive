package user;

import java.lang.reflect.InvocationTargetException;

import user.superpower.Superpower;
import user.superpower.TestEmptyPower;

public class Card {

	private String name, description;
	private transient Superpower superpower;
	private int price;
	
	public boolean execute() {
		return superpower.execute();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSuperpower(String superpowerStr) {
		try {  // 即 superpower = superpowerStr.getInstance();
			this.superpower = (Superpower) Class.forName("user.superpower." + superpowerStr).getMethod("getInstance").invoke(null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {		
			this.superpower = TestEmptyPower.getInstance();
		}
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Superpower getSuperpower() {
		return superpower;
	}

}
