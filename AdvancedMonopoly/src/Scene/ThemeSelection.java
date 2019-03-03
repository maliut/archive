package scene;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import module.DataManager;
import module.SceneManager;
import commandSet.ThemeSet;
import MTool.MButton;
import MTool.MIO;
import MTool.MPanel;

public class ThemeSelection extends MPanel {
	
	private static final long serialVersionUID = 1L;
	private MIO mio = new MIO();
	private DataManager data = new DataManager();
	private SceneManager sm = new SceneManager();
	
	private Image titleImage;
	private ThemeSet themeSet = new commandSet.ThemeSet();
	private MButton jbtNext = new MButton("next");
	
	public ThemeSelection() {
		super();
		add(themeSet);
		titleImage = mio.readImage("System/title.png");
		
		jbtNext.setBounds(512, 32, 150, 50);
		add(jbtNext);
		ButtonListener listener = new ButtonListener();
		jbtNext.addActionListener(listener);

	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(titleImage, 0, 0,  22*32, 16*32, this);
	}
	
	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			data.system().setTheme(themeSet.getTitleAt(themeSet.getSelectedIndex()));
			try {
				data.loadData();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			sm.switchScene(new ActorSelection());
		}
		
	}


}
