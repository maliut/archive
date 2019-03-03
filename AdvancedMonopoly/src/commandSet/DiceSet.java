package commandSet;

import game.Interpreter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.Timer;

import module.DataManager;
import module.MoveThread;
import module.SceneManager;
import MTool.MButton;
import MTool.MLabel;
import MTool.MPanel;

public class DiceSet extends MPanel {
	
	private static DataManager data = new DataManager();
	private static SceneManager sm = new SceneManager();
	private static Interpreter interpreter = new Interpreter();
	
	private Timer timer = new Timer(25, new TimerListener());
	private MLabel dice = new MLabel("System/dice_on.png");
	private MButton jbtDice = new MButton("dice");
	private MButton jbtItem = new MButton("item");
	private MButton jbtCDice = new MButton("cdice");
	private MButton[] jbtsDices = new MButton[6];
	private MButton jbtSetting = new MButton("setting");
	private MButton jbtSave = new MButton("save");
	
	public int time = 0;	
	private int diceNum;
	
	public DiceSet() {
		setBounds(0,16*32-140,22*32,120);
		jbtDice.setBounds(22*32-140-32, 12, 100, 100);
		jbtDice.setMnemonic(KeyEvent.VK_Z);
		jbtDice.addActionListener(new ButtonListener());
		jbtItem.setBounds(642, 15, 40, 40);
		jbtItem.addActionListener(new ButtonListener());
		jbtCDice.setBounds(642, 71-2, 40, 40);
		jbtCDice.setEnabled(false);
		jbtCDice.addActionListener(new ButtonListener());
		jbtSetting.setBounds(300, 90, 96, 32);
		jbtSetting.addActionListener(new MenuListener());
		jbtSave.setBounds(410, 90, 96, 32);
		jbtSave.addActionListener(new MenuListener());
		dice.setBounds(22*32-140-32,12,100,100);
		dice.setVisible(false);
		
		if (data.players().getCurrent().hasCDice()) {
			jbtCDice.setEnabled(true);
		}
		
		add(jbtSave);
		add(jbtSetting);
		add(dice);
		add(jbtDice);
		add(jbtItem);
		add(jbtCDice);
		
		for (int i = 0; i < jbtsDices.length; i++) {
			jbtsDices[i] = new MButton(String.format("dice%ds", i + 1), false);
			jbtsDices[i].addActionListener(new DiceChooseListener());
			jbtsDices[i].setVisible(false);
			add(jbtsDices[i]);
		}
		jbtsDices[0].setBounds(642-150, 15, 40, 40);
		jbtsDices[1].setBounds(642-100, 15, 40, 40);
		jbtsDices[2].setBounds(642-50, 15, 40, 40);
		jbtsDices[3].setBounds(642-150, 69, 40, 40);
		jbtsDices[4].setBounds(642-100, 69, 40, 40);
		jbtsDices[5].setBounds(642-50, 69, 40, 40);
	}
	
	class ButtonListener implements ActionListener {		
		@Override
		public void actionPerformed(ActionEvent e) {
			sm.getMessageScene().setLocked(true);
			if (e.getSource() == jbtDice) {
				dice.setVisible(true);
				jbtDice.setVisible(false);
				jbtDice.setEnabled(false);
				jbtItem.setVisible(false);
				jbtCDice.setVisible(false);
				jbtSetting.setVisible(false);
				jbtSave.setVisible(false);
				if (data.players().getCurrent().getSlowRound() > 0) {
					diceNum = 1;
				} else {
					diceNum = (int) (Math.random() * 6 + 1);
				}
				time = 0;
				timer.start();
			} else if (e.getSource() == jbtCDice) {
				dice.setVisible(false);
				jbtDice.setVisible(false);
				jbtDice.setEnabled(false);
				jbtItem.setVisible(false);
				jbtCDice.setVisible(false);
				jbtSetting.setVisible(false);
				jbtSave.setVisible(false);
				for (int i = 0; i < jbtsDices.length; i++) {
					jbtsDices[i].setVisible(true);
				}
				data.players().getCurrent().useItem(0);
			} else if (e.getSource() == jbtItem) {
				sm.getMessageScene().setVisible(false);
				sm.getItemSelectionScene().setVisible(true);
				sm.getItemSelectionScene().refresh();
				sm.refresh();
			}
		}
		
	}

	class DiceChooseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < jbtsDices.length; i++) {
				if (e.getSource() == jbtsDices[i]) {
					sm.setVisible(false);
					dice.setVisible(false);
					sm.getMessageScene().setVisible(false);
					sm.mthread = new MoveThread(i + 1);
					sm.mthread.start();
				}
			}		
		}
	}
	
	class MenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jbtSetting) {
				sm.getMessageScene().setLocked(true);
				sm.getMessageScene().setVisible(false);
				//interpreter.process(new int[] {1,1,0,0,0,1},new int[][] {{1,0,1000}});
				sm.getSettingScene().setVisible(true);
				sm.getSettingScene().refresh();
			} else if (e.getSource() == jbtSave) {
				sm.getMessageScene().setVisible(false);
				sm.getSaveScene().createScreenShot();
				sm.getMessageScene().setVisible(true);
				sm.getSaveScene().refresh();
				sm.getSaveScene().setVisible(true);
			}
		}	
	}
	
	class TimerListener implements ActionListener {		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (time <= 25) {
				int num = (int) (Math.random() * 6 + 1);
				dice.switchIcon(String.format("System/dice%d.png", num));
				if (data.system().isDiceSlowEffect()) {
					timer.setDelay(25 * time);
				}
			} else {
				dice.switchIcon(String.format("System/dice%d.png", diceNum));
				if (time == 40) {
					timer.stop();
					sm.setVisible(false);
					dice.setVisible(false);
					sm.getMessageScene().setVisible(false);
					sm.mthread = new MoveThread(diceNum);
					sm.mthread.start();
				}
			}
			time++;
		}
		
	}
	
}
