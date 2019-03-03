package scene;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import module.DataManager;
import module.SceneManager;
import MTool.MPanel;

public class Character extends MPanel {
	
	private static DataManager data = new DataManager();
	private static SceneManager sm = new SceneManager();
	
	public Character() {
		for (int i = 0; i < data.players().size(); i++) {
			add(data.players().get(i).getActor().getCharLabel());
		}
		addMouseListener(new NewMouseListener());
	}

	class NewMouseListener extends MouseAdapter {
		
		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) { 
				if (!sm.getMessageScene().isLocked()) {
		            sm.getMessageScene().setVisible(true);
				}
	        }  
		}
	}
}
