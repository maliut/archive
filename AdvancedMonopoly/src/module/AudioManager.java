package module;

import java.io.File;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import MTool.Audio;

public class AudioManager {
	
	private static DataManager data = new DataManager();
	private static Audio bgm = new Audio();
	private static Audio voice = new Audio();
	private static Audio se = new Audio();
	
	public void playBGM() throws BasicPlayerException {
		String extendFilename = String.format("theme/%s/Audio/BGM/%s", data.system().getTheme(), data.system().getBGM());
		if (!(new File(extendFilename).exists())) {
			extendFilename = String.format("theme/default/Audio/BGM/%s", data.system().getBGM());
		} 
		bgm.play(extendFilename, true);
		bgm.setVolume(data.system().getBGMVolume());
	}
	
	public void setBGM(String filename) throws BasicPlayerException {
		data.system().setBGM(filename);
		playBGM();
	}
	
	public void setBGMVolume(int vol) throws BasicPlayerException {
		data.system().setBGMVolume(vol);
		bgm.setVolume(vol);
	}
	
	public void playVoice(int type) throws BasicPlayerException {
		// play
		voice.setVolume(data.system().getVoiceVolume());
	}
	
	public void setVoiceVolume(int vol) {
		data.system().setVoiceVolume(vol);
	}
	
	public void playSE(int type) throws BasicPlayerException {
		// play
		se.setVolume(data.system().getSEVolume());
	}
	
	public void setSEVolume(int vol) {
		data.system().setSEVolume(vol);
	}

}
