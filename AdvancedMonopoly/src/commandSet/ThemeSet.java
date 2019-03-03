package commandSet;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

public class ThemeSet extends JTabbedPane {
	
	private String[] themes ;

	
	public ThemeSet() {
		//super(JTabbedPane.LEFT);
		setBounds(64,64+32,18*32,12*32);
		themes = new File("theme/").list();
		for (int i = 0; i < themes.length; i++) {
			if (!(themes[i].equals("default"))) {
				addTab(themes[i], new JLabel(new ImageIcon("theme/" + themes[i] + "/theme.png")));
			}
		}
		
	}

}
