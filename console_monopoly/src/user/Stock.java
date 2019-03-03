package user;

public class Stock {
	
	private String name;
	private int price, expectChange, realChange;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public void changePrice(int rate) {
		this.price += this.price * rate / 100;
	}

	public void updatePrice() {
		realChange = (expectChange == 0) ? (int) (Math.random() * 21) - 10 : expectChange;
		changePrice(realChange);
		expectChange = 0;
	}
	
	public int getExpectChange() {
		return expectChange;
	}

	public void setExpectChange(int expectChange) {
		this.expectChange = expectChange;
	}

	public int getRealChange() {
		return realChange;
	}


}
