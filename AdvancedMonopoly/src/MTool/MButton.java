package MTool;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class MButton extends JButton {
	
	private static MIO mio = new MIO();
	//private ImageIcon icon;
	
	public MButton(String action) {  // type : 0 for text command, 1 for actor-selection image command
		//icon = mio.readImageIcon("system/start_off.png");
		super();

		setIcon(mio.readImageIcon(String.format("system/%s_off.png",action)));
		setRolloverIcon(mio.readImageIcon(String.format("system/%s_on.png",action)));
		setPressedIcon(mio.readImageIcon(String.format("system/%s_on.png",action)));

		setLayout(null);
		setBorderPainted(false); 
		setContentAreaFilled(false);
		setVisible(true);
	}
	
	public MButton(String action, boolean anime) {
		super();
		
		setIcon(mio.readImageIcon(String.format("system/%s.png",action)));
		setRolloverIcon(mio.readImageIcon(String.format("system/%s.png",action)));
		setPressedIcon(mio.readImageIcon(String.format("system/%s.png",action)));

		setLayout(null);
		setBorderPainted(false); 
		setContentAreaFilled(false);
		setVisible(true);
	}
}
