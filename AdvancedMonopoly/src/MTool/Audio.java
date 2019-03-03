package MTool;

import java.io.File;
import java.util.Map;

import module.DataManager;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

public class Audio implements BasicPlayerListener {
	
	private static DataManager data = new DataManager();
	// Instantiate BasicPlayer.
	BasicPlayer player = new BasicPlayer();
	// BasicPlayer is a BasicController.
	BasicController control = (BasicController) player;
	
	String filename;
	boolean isLoop;
	
	public void play(String filename, boolean isLoop) throws BasicPlayerException {
		player.addBasicPlayerListener(this);
		this.filename = filename;
		this.isLoop = isLoop;
		control.open(new File(filename));
		control.play();
		//control.setGain(0.85);
		//control.setPan(0.0);
	}
	
	// only use for bgm because it is always on
	public void setVolume(int vol) throws BasicPlayerException {
		control.setGain((double) vol / 100.0);
	}
	
	@Override
	public void opened(Object arg0, Map arg1) {
	}

	@Override
	public void progress(int arg0, long arg1, byte[] arg2, Map arg3) {
	}

	@Override
	public void setController(BasicController arg0) {
	}

	@Override
	public void stateUpdated(BasicPlayerEvent e) {
		if (e.getCode() == BasicPlayerEvent.STOPPED) {
			if (isLoop) {
				try {
					control.play();
					control.setGain(data.system().getBGMVolume() / 100.0);
				} catch (BasicPlayerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
