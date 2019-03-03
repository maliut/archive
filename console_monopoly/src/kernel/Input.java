package kernel;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JRootPane;

import kernel.component.Trigger;

public class Input extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static final int START_INPUT = -65536;
	public static final int REQUEST_KEYCODE = 0, REQUEST_INT = 1;
	private int inputMode;
	private int tmpInput = 0, tmpInputSize = 0;
	private boolean tmpInputMinus = false;
	
	Input() {
		this.setSize(0,0);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        this.setTitle("");  
        this.setUndecorated(true); // 去掉窗口的装饰
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE); //采用指定的窗口装饰风格
        this.addFocusListener(new FocusListener() {     	
        	@Override
            public void focusGained(FocusEvent arg0) {
                 
            }
        	
        	@Override
            public void focusLost(FocusEvent arg0) {
                requestFocus();
            }
        });
        
        this.addKeyListener(new KeyAdapter() {  
            public void keyPressed(KeyEvent e) {  
            	if (inputMode == Input.REQUEST_KEYCODE) {
    				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
    					setVisible(false);  // 结束选择
    					//dispose();
    				}
                	synchronized (GameLoop.inputTmp) {
                		GameLoop.inputTmp[0] = e.getKeyCode();
                		GameLoop.inputTmp.notify();
                	}
            	} else if (inputMode == Input.REQUEST_INT) {
            		//System.out.print("\33[?25h");  // 好像没用。因为焦点不在命令行
            		if ((e.getKeyCode() == KeyEvent.VK_MINUS || e.getKeyCode() == KeyEvent.VK_SUBTRACT) && tmpInputSize == 0) {  // 刚开始输入
            			System.out.print("-");
            			tmpInputMinus = true;
            			tmpInputSize++;
            		}
            		if (e.getKeyCode() >= KeyEvent.VK_0 && e.getKeyCode() <= KeyEvent.VK_9) {  // 输入数字
            			int thisInput = e.getKeyCode() - KeyEvent.VK_0;
            			System.out.print(thisInput);
            			tmpInputSize++;
            			tmpInput = 10 * tmpInput + thisInput;
            		} else if (e.getKeyCode() >= KeyEvent.VK_NUMPAD0 && e.getKeyCode() <= KeyEvent.VK_NUMPAD9) { // 小键盘
            			int thisInput = e.getKeyCode() - KeyEvent.VK_NUMPAD0;
            			System.out.print(thisInput);
            			tmpInputSize++;
            			tmpInput = 10 * tmpInput + thisInput;
            		} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && tmpInputSize > 0) {
            			System.out.print("\33[1D \33[1D");  // 左移一位，用空格覆盖后再左移一位
            			// 如果只输入了一位而且负数标志打开，那么说明输入的肯定是 "-"
            			if (tmpInputSize == 1 && tmpInputMinus) tmpInputMinus = false;
            			tmpInputSize--;
            			tmpInput /= 10;
            		} else if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
            			setVisible(false);  // 结束选择
            			if (tmpInputMinus) tmpInput = -tmpInput;  // 如果有负号那么取负数
                    	synchronized (GameLoop.inputTmp) {
                    		GameLoop.inputTmp[0] = tmpInput;
                    		GameLoop.inputTmp.notify();
                    	}
            		}
            	}
            }
        });  
	}
	
	public void requestInput() {
		setVisible(true);  		// 开始请求
		NotificationCenter.getInstance().postNotification(null, Trigger.TRIGGER_ON_INPUT, Input.START_INPUT);	// 通知 准备接收消息
		// 需要注意，这里的start_input不能和keycode冲突
	}

	public void setInputMode(int inputMode) {
		this.inputMode = inputMode;
		if (inputMode == Input.REQUEST_INT) { // init
    		tmpInput = 0; 
    		tmpInputSize = 0;
    		tmpInputMinus = false;
		}
	}

}
