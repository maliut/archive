package MTool;

import game.LuckyPoint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;


// 在actor类内部使用
public class MCharLabel extends JLabel {
	
	private Timer timer = new Timer(5, new TimerListener());
	private char direction = 'n';
	
	public MCharLabel(String name) {
		setLayout(null);
		setVisible(true);
	}
	
	class TimerListener implements ActionListener {
		private int index = 0;
		@Override
		public void actionPerformed(ActionEvent e) {
			index++;
			if (index == 3) {
				index = 0;
			}
			//setIcon();
		}		
	}

}
