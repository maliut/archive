package kernel.util;

public class Point implements Cloneable {
	
	private int x, y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return (Point) super.clone();
	}
	
	@Override
	public boolean equals(Object other) {
		try {
			Point o = (Point) other;
			return (this.x == o.x && this.y == o.y) ? true : false;
		} catch (ClassCastException e) {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return String.format("(%d,%d)", x, y);
	}

}
