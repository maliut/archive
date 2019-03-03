package commandSet;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import module.SceneManager;
import MTool.MButton;
import MTool.MPanel;

public class TitleSet extends MPanel {
	
	private SceneManager sm = new SceneManager();
	
	// create buttons
	private MButton jbtStart    = new MButton("start");
	private MButton jbtContinue = new MButton("continue");
	private MButton jbtAbout    = new MButton("about");
	private MButton jbtExit     = new MButton("exit");
	
	public TitleSet() {
		// create title panel
		super((22*32-200)/2,9*32,200,200);
		// add buttons
		setLayout(new GridLayout(4,1));
		add(jbtStart);
		add(jbtContinue);
		add(jbtAbout);
		add(jbtExit);		
		// create listener
		ButtonListener listener = new ButtonListener();
		// add listener
		jbtStart.addActionListener(listener);
		jbtContinue.addActionListener(listener);
		jbtAbout.addActionListener(listener);
		jbtExit.addActionListener(listener);
		// set this panel
		setOpaque(false);
	}
	
	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jbtStart) {
				sm.switchScene(new scene.ThemeSelection());
			} else if (e.getSource() == jbtContinue) {
				sm.switchScene(new scene.SaveLoad(true));
			} else if (e.getSource() == jbtAbout) {
				sm.switchScene(new scene.About());
			} else if (e.getSource() == jbtExit) {
				System.exit(0);
			}
		}
		
	}

}
