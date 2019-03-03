package MTool;

import java.awt.Color;

import javax.swing.JButton;

public class MTextButton extends JButton {
	
	public MTextButton(String text) {
		super(text);
		setOpaque(false);
		setBackground(new Color(0,0,0,0));
		setForeground(new Color(168,213,224,255));
	}

}
