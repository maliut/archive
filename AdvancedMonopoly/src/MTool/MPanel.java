package MTool;

import java.awt.Color;

import javax.swing.JPanel;

public class MPanel extends JPanel {

	public MPanel(int x, int y, int w, int h) {
		super();
		setBounds(x, y, w, h);

		setOpaque(false);
		setLayout(null);
		setBackground(new Color(0,0,0,0));
		setVisible(true);
		
	}
	
	public MPanel() {
		this(0,0,22*32,16*32);
	}

}
