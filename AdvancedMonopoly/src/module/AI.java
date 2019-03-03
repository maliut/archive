package module;

import game.Field;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class AI {
	
	private static DataManager data = new DataManager();
	private static SceneManager sm = new SceneManager();
	private static Robot robot;
	static {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void run(int type) {
		switch (type) {
		//case 0:
			//robot.delay(1000);
			//okay();
			//break;
		case 4:  // buy
			if (buyField()) {
				okay();//okay();okay();
			} else {
				cancel();//cancel();cancel();
			}
			break;
		case 5:
			if (levelUpField()) {
				okay();//okay();okay();
			} else {
				cancel();//cancel();cancel();
			}
		default:
			okay();//okay();okay();
		}
	}
	
	private static boolean buyField() {
		Field field = (Field) data.map().getCellAt(data.players().getCurrent().getLocation());
		return (data.players().getCurrent().getCash() >= field.getValue()) ? true : false;
	}
	
	private static boolean levelUpField() {
		Field field = (Field) data.map().getCellAt(data.players().getCurrent().getLocation());
		return (data.players().getCurrent().getCash() >= field.getValue() / 2) ? true : false;
	}
	
	private static void okay() {
		//robot.delay(100);
		sm.getFrame().setFocusableWindowState(true);
		sm.getFrame().setExtendedState(JFrame.NORMAL);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_Z);
		robot.keyRelease(KeyEvent.VK_Z);
		robot.keyRelease(KeyEvent.VK_ALT);		
	}
	
	private static void cancel() {
		//robot.delay(100);
		sm.getFrame().setFocusableWindowState(true);
		sm.getFrame().setExtendedState(JFrame.NORMAL);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_X);
		robot.keyRelease(KeyEvent.VK_X);
		robot.keyRelease(KeyEvent.VK_ALT);		
	}
}
