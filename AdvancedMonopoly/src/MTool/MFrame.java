package MTool;

import javax.swing.JFrame;
import module.DataManager;

public class MFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static DataManager data = new DataManager();
	private static MIO mio = new MIO();
	
	public MFrame() { 
		setTitle(data.system().getTitle());
		setSize(22*32, 17*32-3);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(mio.readImage("System/icon.png"));
		setVisible(true);
	}
	
	public void refresh() {
		setVisible(true);
	}

}
