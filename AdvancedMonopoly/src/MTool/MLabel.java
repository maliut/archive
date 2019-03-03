package MTool;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MLabel extends JLabel {
	
	private static MIO mio = new MIO();

	public MLabel(String filename) {
		super(mio.readImageIcon(filename));
		setLayout(null);
		setVerticalAlignment(SwingConstants.TOP);
		//setHorizontalAlignment(SwingConstants.RIGHT);
		setVisible(true);
	}
	
	public void switchIcon(String filename) {
		setIcon(mio.readImageIcon(filename));
	}

}
