package scene;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import module.DataManager;
import module.SceneManager;
import MTool.MLabel;
import MTool.MPanel;
import MTool.MTextButton;
import MTool.MTextLabel;

public class ItemSelection extends MPanel {

	private static final long serialVersionUID = 1L;
	private static DataManager data = new DataManager();
	private static SceneManager sm = new SceneManager();
	
	private MLabel back = new MLabel("System/Bank.png");
	private ButtonPanel btPanel = new ButtonPanel();
	private MTextButton[] jbtItems;
	private MTextLabel text1 = new MTextLabel(" ");
	private MTextLabel text2 = new MTextLabel(" ");
	
	public ItemSelection() {
		super(3*32, 3*32,16*32,12*32);
		back.setBounds(0, 0, 16*32, 12*32);
		btPanel.setBounds(1*32, 4*32, 14*32, 7*32);
		text1.setBounds(4*32, 4*32, 14*32, 32);
		text2.setBounds(4*32, 5*32, 14*32, 32);
		
		add(btPanel);
		add(text1);
		add(text2);
		add(back);
		setVisible(false);
	}
	
	public void refresh() {
		jbtItems = new MTextButton[data.players().getCurrent().getItemSize()];
		if (jbtItems.length == 0) {
			setVisible(false);
			sm.getMessageScene().setLocked(false);
			sm.getMessageScene().setVisible(true);
		} else {
			btPanel.removeAll();
			for (int i = 0; i < jbtItems.length; i++) {
				jbtItems[i] = new MTextButton(data.items().get(data.players().getCurrent().getItem(i)).getName());
				jbtItems[i].addActionListener(new ButtonListener());
				if (data.players().getCurrent().getItem(i) != 0) {
					btPanel.add(jbtItems[i]);
				}
			}	
			for (int i = 0; i < jbtItems.length; i++) {
				if (data.players().getCurrent().getItem(i) != 0) {
					return;
				} 
			}
			// È«ÊÇ Ò£¿Ø÷»×Ó
			setVisible(false);
			sm.getMessageScene().setLocked(false);
			sm.getMessageScene().setVisible(true);
		}
	}
	
	class ButtonPanel extends MPanel {

		private static final long serialVersionUID = 1L;

		public ButtonPanel() {
			setLayout(new GridLayout(4,5));
		}
	}

	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < jbtItems.length; i++) {
				if (e.getSource() == jbtItems[i]) {
					setVisible(false);
					data.players().getCurrent().useItem(data.players().getCurrent().getItem(i));
					//sm.getMessageScene().setLocked(false);
					//sm.getMessageScene().setVisible(true);
				}
			}
		}
	}
}
