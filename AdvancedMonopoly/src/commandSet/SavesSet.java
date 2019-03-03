package commandSet;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

public class SavesSet extends JTabbedPane {

	private String[] saves;
	
	public SavesSet() {
		super(JTabbedPane.LEFT);
		setBounds(64,64+32,18*32,12*32);
		saves = new File("theme/default/Saves/").list();
		for (int i = 0; i < saves.length; i++) {
			addTab("save" + saves[i], new JLabel(new ImageIcon("theme/default/Saves/" + saves[i] + "/screenshot.png")));
		}
	}
	
}
