package game;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import MTool.MCharLabel;
import MTool.MIO;

public class Actor {
	
	transient private MIO mio = new MIO();
	
	private String name = "";			//	图片：name/happy,name/sad之类，声音：name/get之类
	private String description = "";
	private String[] script = new String[4];
			
	// 用于内部处理的  cannot to json
	transient private Image[] body = new Image[3];// happy normal sad
	transient private ImageIcon[][] characters = new ImageIcon[3][4];
	transient private MCharLabel charLabel;
	transient private int faceTo;
	
	public void init() throws FileNotFoundException, IOException {
		body[0] = mio.readImage(String.format("Body/%s/happy.png", name));
		body[1] = mio.readImage(String.format("Body/%s/normal.png", name));
		body[2] = mio.readImage(String.format("Body/%s/sad.png", name));
		
		BufferedImage characterSet = mio.readBufferedImage(String.format("Characters/%s.png", name));
		for (int i = 0; i < characters.length; i++) {
			for (int j = 0; j < characters[i].length; j++) {
				characters[i][j] = new ImageIcon(characterSet.getSubimage(32 * i, 32 * j, 32, 32));
			}
		}
		
		charLabel = new MCharLabel(name);
	}
	
	public String getName() {
		return name;
	}

	public ImageIcon getCharactersFaceTo(int direction) {
		return characters[1][direction];
	}
	
	public MCharLabel getCharLabel() {
		return charLabel;
	}

	public String getScript(int index) {
		if (index >= 0 && index < script.length) {
			return script[index];
		} else {
			return null;
		}
	}
	
	public void setFaceTo(int faceTo) {
		this.faceTo = faceTo;
		charLabel.setIcon(characters[1][faceTo]);
	}


	class MCharLabel extends JLabel {
		
		private static final long serialVersionUID = 1L;
		private Timer timer = new Timer(100, new TimerListener());
		
		public MCharLabel(String name) {
			setLayout(null);
			setVisible(true);
		}
		
		public void resetTimer() {
			timer = new Timer(100, new TimerListener());
		}
		
		public Timer getTimer() {
			return timer;
		}
		
		class TimerListener implements ActionListener {
			private int index = 0;
			private int velocity = 16;
			private int time = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				index++;time++;
				if (index == 4) {
					index = 0;
				}
				switch (index) {
				case 0:case 2:
					setIcon(characters[1][faceTo]);
					break;
				case 1:
					setIcon(characters[0][faceTo]);
					break;
				case 3:
					setIcon(characters[2][faceTo]);
					break;
				}
				switch (faceTo) {
				case 0: 
					setLocation(getX(), getY() + velocity);
					break;
				case 1:
					setLocation(getX() - velocity, getY());
					break;
				case 2:
					setLocation(getX() + velocity, getY());
					break;
				case 3:
					setLocation(getX(), getY() - velocity);
					break;
				}				
				if (velocity * time == 32) {
					timer.stop();
					time = 0;//index = 0;
				}
			}				
		}
	}
}
