package commandSet;

import game.Actor;

import java.io.File;
import java.io.FileNotFoundException;

import module.DataManager;
import MTool.MIO;
import MTool.MLabel;
import MTool.MPanel;

public class ActorSet extends MPanel {
	
	private String[] actors;
	private int indexOnShow = 0;
	
	private DataManager data = new DataManager();
	private MIO mio = new MIO();
	private MLabel actorIcon ;
	
	
	public ActorSet() {
		super(64,0,18*32,16*32);
		setOpaque(false);

		actors = new File(String.format("theme/%s/Graphics/Body/", data.system().getTheme())).list();
		actorIcon = new MLabel("Body/" + actors[0] + "/happy.png");
		actorIcon.setBounds(0, 0, getWidth(), getHeight());
		//actorIcon.setToolTipText("sfsdgdgsgagdsgdgdfg");
		add(actorIcon);
	}
	
	public void nextActor() {
		indexOnShow++;
		if (indexOnShow >= actors.length) {
			indexOnShow = 0;
		}
		actorIcon.switchIcon("Body/" + actors[indexOnShow] + "/happy.png");
	}
	
	public void previousActor() {
		indexOnShow--;
		if (indexOnShow < 0) {
			indexOnShow = actors.length - 1;
		}
		actorIcon.switchIcon("Body/" + actors[indexOnShow] + "/happy.png");
	}

	public Actor getShowingActor() throws FileNotFoundException {
		return (Actor) mio.loadData(actors[indexOnShow] + ".mdat", Actor.class);
	}
}
