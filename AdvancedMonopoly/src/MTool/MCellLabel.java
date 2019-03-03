package MTool;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import game.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import module.DataManager;

public class MCellLabel extends JLabel {
	
	private static MIO mio = new MIO();
	private static DataManager data = new DataManager();
	private Timer timer = new Timer(200, new TimerListener());
	private Cell cell;
	//private boolean anime;
	private static ImageIcon[] icons = new ImageIcon[15];
	static {
		try {
			BufferedImage objectIconSet = mio.readBufferedImage("System/ObjectIconSet.png");
			for (int i = 0; i < icons.length; i++) {
				icons[i] = new ImageIcon(objectIconSet.getSubimage(32 * i, 0, 32, 32));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public MCellLabel(Cell cell) {
		this.cell = cell;
		//this.anime = anime;
		setBounds(cell.getIconX() * 32, cell.getIconY() * 32, 32, 32);
		refresh();
		setLayout(null);
		//setOpaque(true);
		setVisible(true);
	}
	
	public void refresh() {
		// set background
		if (cell instanceof Field) {
			//setOpaque(true);
			if (((Field) cell).getOwnerIndex() != -1) { // has owner
				//setBackground(data.system().getPlayerColor(((Field) cell).getOwnerIndex()));
				setBorder(BorderFactory.createLineBorder(data.system().getPlayerColor(((Field) cell).getOwnerIndex()),2));
			} else {
				//setBorder(BorderFactory.createLineBorder(new Color(255,255,255,255), 2));
				setBorder(null);
				//setBackground(new Color(0,0,0,0));
			}
		} 
		// set icon
		if (cell instanceof Field) {
			if (((Field) cell).getOwnerIndex() == -1) { // no owner
				setIcon(icons[0]);
			} else {  // has owner, show by level
				setIcon(icons[((Field) cell).getLevel()]);
			}
		} else if (cell instanceof StartPoint) {
			setIcon(icons[6]);
		} else if (cell instanceof Bank) {
			setIcon(icons[7]);
		} else if (cell instanceof ItemShop) {
			setIcon(icons[8]);
		} else if (cell instanceof LuckyPoint) {
			setIcon(icons[9]);
		} //else if (cell instanceof StartPoint) {
		//	setIcon(new ImageIcon(icons[6]));
		//}
		// set anime effect
		if (cell instanceof LuckyPoint) {
			timer.start();
		}
	}
	
	class TimerListener implements ActionListener {
		private int index = 0;
		@Override
		public void actionPerformed(ActionEvent e) {
			index++;
			if (index == 2) {
				index = 0;
			}
			if (cell instanceof LuckyPoint) {
				setIcon(icons[9 + index]);
			}
		}		
	}

}
