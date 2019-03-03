package scene;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import commandSet.SavesSet;
import module.DataManager;
import module.SceneManager;
import MTool.MButton;
import MTool.MIO;
import MTool.MPanel;

public class SaveLoad extends MPanel {
	
	private static MIO mio = new MIO();
	private static DataManager data = new DataManager();
	private static SceneManager sm = new SceneManager();
	private static Robot robot;
	
	private Image titleImage;
	private SavesSet savesSet = new commandSet.SavesSet();
	private MButton jbtOK = new MButton("ok");
	private BufferedImage screenshot;
	
	private boolean isLoad;
	
	public SaveLoad(boolean isLoad) {
		super();
		this.isLoad = isLoad;
		add(savesSet);
		titleImage = mio.readImage("System/title.png");
		
		jbtOK.setBounds(512, 32, 150, 50);
		jbtOK.addActionListener(new ButtonListener());
		add(jbtOK);
		addMouseListener(new NewMouseListener());
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (!isLoad) {
			setVisible(false);
		}
	}

	public void refresh() {
		remove(savesSet);
		savesSet = new commandSet.SavesSet();
		add(savesSet);
	}
	
	public void createScreenShot() {
		Rectangle r = sm.getFrame().getBounds();
		r.setBounds((int) (r.getX() + 2), (int) (r.getY() + 24), 22*32-4, 16*32);
		screenshot = robot.createScreenCapture(r);
	}
	
	private void saveScreenShot() throws IOException {
		Image smallshot = screenshot.getScaledInstance(165 * 32 / 10, 12*32, Image.SCALE_DEFAULT);
	    BufferedImage smallbu = new BufferedImage(165 * 32 / 10, 12*32, BufferedImage.TYPE_3BYTE_BGR);
	    smallbu.getGraphics().drawImage(smallshot, 0, 0, null);
		File f = new File("theme/default/Saves/" + savesSet.getSelectedIndex() + "/screenshot.png");
		if (f.exists()) {f.delete();}
		ImageIO.write(smallbu, "png", f);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(titleImage, 0, 0,  22*32, 16*32, this);
	}
	
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (isLoad) {
				try {
					data.loadSaves(savesSet.getSelectedIndex());
					data.players().init();
					sm.switchScene(new Map());
					sm.startGame();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BasicPlayerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {  // save
				try {
					System.out.print(savesSet.getSelectedIndex());
					saveScreenShot();
					data.saveSaves(savesSet.getSelectedIndex());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);
			}
		}
		
	}
	
	class NewMouseListener extends MouseAdapter {
		
		public void mouseReleased(MouseEvent e) {
			if (isLoad && e.getButton() == MouseEvent.BUTTON3) {  
	            sm.switchScene(new Title());
	        }  
		}
	}

}
