package commandSet;

import game.Field;
import game.Interpreter;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import module.DataManager;
import module.SceneManager;
import MTool.MButton;
import MTool.MPanel;

public class ContinueSet extends MPanel {
	
	private static final long serialVersionUID = 1L;
	private static SceneManager sm = new SceneManager();
	private static DataManager data = new DataManager();
	private static Interpreter interpreter = new Interpreter();
	private MButton jbtNull = new MButton("null");
	
	public ContinueSet(int type) {
		setBounds(0,16*32-160,22*32,160);
		setLayout(new GridLayout());
		jbtNull.setMnemonic(KeyEvent.VK_Z);
		jbtNull.addActionListener(new ButtonListener(type));
		
		add(jbtNull);
	}
	
	class ButtonListener implements ActionListener {
		
		private int type;
		
		public ButtonListener(int type) {
			this.type = type;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (type) {
			case 0:  // next player
				sm.setVisible(false);
				interpreter.switchPlayer();
				break;
			case 1: // before pay the fee
				Field field = (Field) data.map().getCellAt(data.players().getCurrent().getLocation());
				data.players().getCurrent().payTo(field.getFee(), data.players().get(field.getOwnerIndex()));
				break;
			case 2: // field sold prompt
				sm.getMessageScene().showMessage(2);
				break;
			case 3: // get prompt
				sm.getMessageScene().showMessage(1);
				break;
			case 4: // win
				//sm.restart();
				//break;
				System.exit(0);
			case 5: // simply add a message before dice, use when bank add deposit
				sm.getMessageScene().showMessage(0);
				break;
			} 
		}		
	}

}
