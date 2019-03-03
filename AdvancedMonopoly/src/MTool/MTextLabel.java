package MTool;

import java.awt.Color;

import javax.swing.JLabel;

public class MTextLabel extends JLabel {
	
	public MTextLabel(String text) {
		super(text);
		setOpaque(false);
		setBackground(new Color(0,0,0,0));
		setForeground(new Color(168,213,224,255));
	}

}
