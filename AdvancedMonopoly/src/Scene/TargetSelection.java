package scene;

import game.Interpreter;
import game.Player;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import module.SceneManager;
import MTool.MLabel;
import MTool.MPanel;
import MTool.MTextButton;

public class TargetSelection extends MPanel {
	
	private static final long serialVersionUID = 1L;
	private static Interpreter interpreter = new Interpreter();
	private static SceneManager sm = new SceneManager();
	
	private MLabel back = new MLabel("System/Bank.png");
	private ButtonPanel btPanel = new ButtonPanel();
	private MTextButton[] jbtTargets;
	private Object[] target;
	
	public TargetSelection() {
		super(3*32, 3*32,16*32,12*32);
	//	this.setBounds(0, 0, 16*32, 12*32);
		this.setOpaque(false);
		back.setBounds(0, 0, 16*32, 12*32);
		btPanel.setBounds(32, 4*32, 14*32, 10*32);
		
		add(btPanel);
		add(back);
		setVisible(false);
		
	}
	
	public void refresh() {
		target = Interpreter.getTarget();
		if (target.length == 0) {
			setVisible(false);
			sm.getMessageScene().setLocked(false);
			sm.getMessageScene().setVisible(true);
		}
		jbtTargets = new MTextButton[target.length];
		btPanel.removeAll();
		for (int i = 0; i < target.length; i++) {
			jbtTargets[i] = new MTextButton(((Player) target[i]).getActor().getName());
			jbtTargets[i].addActionListener(new ButtonListener());
			btPanel.add(jbtTargets[i]);
		}
	}
	
	class ButtonPanel extends MPanel {

		private static final long serialVersionUID = 1L;

		public ButtonPanel() {
			setLayout(new GridLayout(6,2));
		}
	}

	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < jbtTargets.length; i++) {
				if (e.getSource() == jbtTargets[i]) {
					Interpreter.setTarget(new Object[] {target[i]});
					setVisible(false);
					interpreter.exec();
					//sm.getMessageScene().setLocked(false);
					//sm.getMessageScene().setVisible(true);
				}
			}
		}
	}
}
