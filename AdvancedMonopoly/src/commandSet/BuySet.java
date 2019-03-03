package commandSet;

import game.Interpreter;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import module.DataManager;
import module.SceneManager;
import MTool.MButton;
import MTool.MPanel;

public class BuySet extends MPanel {
	
	private static DataManager data = new DataManager();
	private static SceneManager sm = new SceneManager();
	private static Interpreter interpreter = new Interpreter();

	private MButton jbtBuy = new MButton("ok");
	private MButton jbtLevelUp = new MButton("ok");
	private MButton jbtCancel = new MButton("cancel");
	
	public BuySet(int type) {
		setBounds(22*32-140,16*32-120,120,100);
		setLayout(new GridLayout(2,1));		
		jbtBuy.setMnemonic(KeyEvent.VK_Z);
		jbtLevelUp.setMnemonic(KeyEvent.VK_Z);
		jbtCancel.setMnemonic(KeyEvent.VK_X);
		jbtCancel.addActionListener(new ButtonListener());
		
		if (type == 0) {
			jbtBuy.addActionListener(new ButtonListener());			
			add(jbtBuy);
		} else if (type == 1) {
			jbtLevelUp.addActionListener(new ButtonListener());			
			add(jbtLevelUp);
		}
		add(jbtCancel);
	}
	
	class ButtonListener implements ActionListener {		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jbtBuy) {
				data.players().getCurrent().buy();
			} else if (e.getSource() == jbtCancel) {
				sm.setVisible(false);
				interpreter.switchPlayer();
			} else if (e.getSource() == jbtLevelUp) {
				data.players().getCurrent().levelUp();
			}
			

		}
		
	}

	
}
