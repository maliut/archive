package commandSet;

import MTool.MButton;
import MTool.MIO;

public class ImageCommand extends MButton {
	
	private MIO mio = new MIO();
	private static int count = 0;
	
	public ImageCommand(String filename) {
		super(filename);
		setBounds(64,64+100*count,18*32,100);
		count++;
		//setIcon(mio.readImageIcon(filename));
	}

}
