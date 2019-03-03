package kernel.component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import kernel.GameObject;
import kernel.Resource;

/** 在命令行/窗口上输出图形 */
public class Renderer extends Component {

	public Renderer(GameObject gameObject) {
		super(gameObject);
	}

	private boolean enabled;
	// Icon content;
	private String content;
	private int height, width;
	private int background = 40, foreground = 37;  // black & white
	// Format
	private String formatterClassStr, formatterMethodStr;
	private transient Method formatterMethod;
	
	/** clear the render area */
	public void clear() {
		int x = gameObject.getPosition().getX();
		int y = gameObject.getPosition().getY();
		for (int i = 0; i < height; i++) {
			System.out.printf("\33[%d;%dH", y + i, x);
			for (int j = 0; j < width; j++) System.out.printf("\u3000");
		}
	}
	
	/** render content in the area */
	public void render() {
		if (!enabled) return;
		// split s to lines
		String tmp = (Resource.get(content) == null) ? content : Resource.get(content); // 如果是资源标识符，则去读取，否则输出本身
		// 像是 android 中 handler 的机制，如果之前被第三方方法处理过了则不处理，否则自己来处理（认为是个资源标识符）
		String[] lines = tmp.split("\\$n");
		// for every line, clear old content and print it
		int x = gameObject.getPosition().getX();
		int y = gameObject.getPosition().getY();
		System.out.printf("\33[%d;%dm", background, foreground);
		for (int i = 0; i < lines.length; i++) {
			System.out.printf("\33[%d;%dH%s", y + i, x, lines[i]);
		}
	}
	
	/** 强制刷新当前组件
	 *  对于大富翁，因为游戏主循环是每回合进行一次，而回合中间也有玩家位置的更新等
	 *  在更新后调用此方法强制刷新可以做出动画效果
	 */
	public void forceRender() {
		render();
	}

	@Override
	public void receive(Component sender, Object... args) {

	}

	// getters & setters
	public String getContent() {
		return content;
	}

	public void setContent(Object... content) {
		// format
		if (formatterMethodStr == null) { // 没有代理方法，不执行
			this.content = (String) content[0];
		} else {
			try {
				if (formatterMethod == null) {  // lazy load, 防止每次调用都要执行，因为大多数时间都是不变的
					formatterMethod = Class.forName("user.component." + formatterClassStr).getMethod(formatterMethodStr, List.class);
				}
				this.content = (String) formatterMethod.invoke(this.gameObject.getComponent(formatterClassStr), Arrays.asList(content));
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				System.exit(1);
			}	
		}
	}

	public int getBackground() {
		return background;
	}

	public void setBackground(int background) {
		this.background = background;
	}

	public int getForeground() {
		return foreground;
	}

	public void setForeground(int foreground) {
		this.foreground = foreground;
	}
	
	public void setFormatter(String formatter) {
		String[] s = formatter.split("\\.");
		formatterClassStr = s[0];
		formatterMethodStr = s[1];
		formatterMethod = null;  // 有变化时标记要重新读取
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
}
