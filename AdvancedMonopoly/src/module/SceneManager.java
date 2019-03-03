package module;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import MTool.MFrame;
import MTool.MPanel;
import scene.*;
import scene.Character;

public class SceneManager {
	
	private static DataManager data = new DataManager();
	private static AudioManager au = new AudioManager();
	private static MFrame frame;
	private static List<MPanel> scenes = new ArrayList<MPanel>();
	public MoveThread mthread;
	
	public void run() throws FileNotFoundException {
		data.loadSystem();
		frame = new MFrame();
		addScene(new Title());
	}
	
	public void startGame() throws FileNotFoundException, IOException, BasicPlayerException {
		addScene(new Spots());     
		refresh();
		addScene(new PlayerInfo());
		refresh();
		addScene(new Character()); 
		refresh();
		addScene(new Message());
		refresh();
		addScene(new Bank());
		refresh();
		addScene(new Setting());
		refresh();

		addScene(new TargetSelection());
		refresh();
		addScene(new ItemSelection());
		refresh();
		addScene(new ItemShop());
		refresh();
		addScene(new SaveLoad(false));
		refresh();

		au.playBGM();
		getMessageScene().showMessage(0);
	}
	
	public void restart() {
		data.system().setTheme("default");
		frame.removeAll();
		scenes.clear();
		try {
			data.loadSystem();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addScene(new Title());
		refresh();
	}
	
	
	//=============================================
	public void addScene(MPanel scene) {
		scenes.add(scene);
		frame.add(scene);
		//frame.setComponentZOrder(scene, 0);
		frame.refresh();
	}

	public void addScene(MPanel scene, boolean visible) {
		scenes.add(scene);
		if (!visible) {
			scene.setVisible(false);
		}
		frame.add(scene);
		//frame.setComponentZOrder(scene, 0);
		frame.refresh();
	}
	
	public void switchScene(MPanel scene) {
		disposeScene();
		addScene(scene);
	}
	
	public void disposeScene() {
		//scenes.get(scenes.size()-1).setVisible(false);
		frame.remove(scenes.get(scenes.size()-1));
		scenes.remove(scenes.get(scenes.size()-1));
		frame.repaint();
	}
	
	public void setVisible(boolean visible) {
		scenes.get(scenes.size()-1).setVisible(visible);
	}
	
	public void update() {
		for (int i = 0; i < scenes.size(); i++) {
			if (scenes.get(i) instanceof PlayerInfo) {
				scenes.get(i).repaint();
			}
		}
	}
	
	public Message getMessageScene() {
		for (int i = 0; i < scenes.size(); i++) {
			if (scenes.get(i) instanceof Message) {
				return (Message) scenes.get(i);
			} 
		}
		return null;
	}
	
	public Bank getBankScene() {
		for (int i = 0; i < scenes.size(); i++) {
			if (scenes.get(i) instanceof Bank) {
				return (Bank) scenes.get(i);
			} 
		}
		return null;
	}
	
	public Setting getSettingScene() {
		for (int i = 0; i < scenes.size(); i++) {
			if (scenes.get(i) instanceof Setting) {
				return (Setting) scenes.get(i);
			} 
		}
		return null;
	}
	
	public SaveLoad getSaveScene() {
		for (int i = 0; i < scenes.size(); i++) {
			if (scenes.get(i) instanceof SaveLoad) {
				return (SaveLoad) scenes.get(i);
			} 
		}
		return null;
	}
	
	public TargetSelection getTargetSelectionScene() {
		for (int i = 0; i < scenes.size(); i++) {
			if (scenes.get(i) instanceof TargetSelection) {
				return (TargetSelection) scenes.get(i);
			} 
		}
		return null;
	}
	
	public ItemSelection getItemSelectionScene() {
		for (int i = 0; i < scenes.size(); i++) {
			if (scenes.get(i) instanceof ItemSelection) {
				return (ItemSelection) scenes.get(i);
			} 
		}
		return null;
	}
	
	public ItemShop getItemShopScene() {
		for (int i = 0; i < scenes.size(); i++) {
			if (scenes.get(i) instanceof ItemShop) {
				return (ItemShop) scenes.get(i);
			} 
		}
		return null;
	}
	
	public Spots getSpotsScene() {
		for (int i = 0; i < scenes.size(); i++) {
			if (scenes.get(i) instanceof Spots) {
				return (Spots) scenes.get(i);
			} 
		}
		return null;
	}
	
	public MFrame getFrame() {
		return frame;
	}
	
	// use when necessary
	public void refresh() {
		frame.setComponentZOrder(scenes.get(scenes.size() - 1), 0);
		frame.refresh();
	}

}
