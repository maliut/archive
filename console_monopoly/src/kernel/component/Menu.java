package kernel.component;

import java.awt.event.KeyEvent;

import kernel.GameObject;
import kernel.Input;
import kernel.Resource;
import kernel.util.Point;

public abstract class Menu extends Component {

	protected int itemNum;
	protected int cursor;
	protected Point cursorPoint, initialCursorPoint;
	private transient boolean isInputing;
	protected int requestInputType;
	
	public Menu(GameObject gameObject) {
		super(gameObject);
		//cursor = 0;
		//cursorPoint = new Point(gameObject.getPosition().getX(), gameObject.getPosition().getY());
	}

	// 主菜单 input 内容的接收类
	public void dealInput(int keyCode) throws CloneNotSupportedException {
		switch (keyCode) {
		case KeyEvent.VK_UP:
        case KeyEvent.VK_W:
        	if (cursor > 0) {  // 还可以往上
        		cursor--;
        		updateCursor(cursorPoint.getX(), cursorPoint.getY() - 1);
        	}
        	break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_S:
        	if (cursor < itemNum - 1) {  // 还可以往下
        		cursor++;
        		updateCursor(cursorPoint.getX(), cursorPoint.getY() + 1);
        	}
        	break;
        case KeyEvent.VK_ENTER:
        case KeyEvent.VK_SPACE:
        	// 处理事件
        	isInputing = false;
        	dealSelectedItem(cursor); 
        	// 恢复初始（不一定做，是否可以存储上次的信息？）
        	cursor = 0;
        	cursorPoint = (Point) initialCursorPoint.clone();
        	break;
        }
	}
	
	protected abstract void dealSelectedItem(int item);
	
	private void updateCursor(int x, int y) {
		// clear old
		System.out.printf("\33[%d;%dH%s", cursorPoint.getY(), cursorPoint.getX(), "  ");
		// set new
		cursorPoint = new Point(x, y);
		// render new
		System.out.printf("\33[%d;%dH%s", y, x, Resource.get("ui.cursor"));
	}
	
	@Override
	public void receive(Component sender, Object... args) {
		if (((int) args[0]) == Input.START_INPUT) {
			isInputing = true;
			if (this.requestInputType == Input.REQUEST_INT) return;  // 输入数字模式不需要光标
			// render current
			System.out.printf("\33[%d;%dH%s",cursorPoint.getY(), cursorPoint.getX(), Resource.get("ui.cursor"));
		} else if (isInputing) {
			if (this.requestInputType == Input.REQUEST_INT) {
				dealSelectedItem((int) args[0]);   // 数字模式直接交给子类处理
			} else {
				try {
					dealInput((int) args[0]);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	protected void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public int getRequestInputType() {
		return requestInputType;
	}

	public void setRequestInputType(int requestInputType) {
		this.requestInputType = requestInputType;
	}

	protected void setCursorPoint(Point cursorPoint) {
		this.cursorPoint = cursorPoint;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Menu m = (Menu) super.clone();
		m.initialCursorPoint = (Point) this.cursorPoint.clone();  // 保存初始光标位置
		return m;
	}

}
