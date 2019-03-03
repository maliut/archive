package game;

import java.awt.Color;

import MTool.MDate;

public class System {
	
	private String title = "Monopoly";
	private String theme = "default";
	private String[] currencyVocab = {"现金", "存款", "点券"};
	private String bgm;
	private int bgmVolume = 85;
	private int voiceVolume = 85;
	private int seVolume = 85;
	private boolean typeEffect = true;
	private boolean diceSlowEffect = false;
	private boolean endRound = false;
	private MDate date = new MDate(2015,1,1);
	
	private Color[] playerColor = new Color[]{
			new Color(255,0,0,127),
			new Color(0,0,255,127),
			new Color(0,255,0,127),
			new Color(255,255,0,127),
			new Color(0,255,255,127),
			new Color(255,0,255,127)
	};

	public String getTheme() {
		return theme;
	}
	
	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	public String getTitle() {
		return title;
	}

	public MDate getDate() {
		return date;
	}
	
	public Color getPlayerColor(int playerIndex) {
		return playerColor[playerIndex];
	}

	public boolean isTypeEffect() {
		return typeEffect;
	}

	public void setTypeEffect(boolean typeEffect) {
		this.typeEffect = typeEffect;
	}

	public boolean isEndRound() {
		return endRound;
	}

	public void setEndRound(boolean endRound) {
		this.endRound = endRound;
	}

	public boolean isDiceSlowEffect() {
		return diceSlowEffect;
	}

	public void setDiceSlowEffect(boolean diceSlowEffect) {
		this.diceSlowEffect = diceSlowEffect;
	}

	public String getCurrencyVocab(int index) {
		return (index >= 0 && index < currencyVocab.length) ? currencyVocab[index] : "";
	}

	public String getBGM() {
		return bgm;
	}

	public void setBGM(String bgm) {
		this.bgm = bgm;
	}

	public int getBGMVolume() {
		return bgmVolume;
	}

	public void setBGMVolume(int bgmVolume) {
		this.bgmVolume = bgmVolume;
	}

	public int getVoiceVolume() {
		return voiceVolume;
	}

	public void setVoiceVolume(int voiceVolume) {
		this.voiceVolume = voiceVolume;
	}

	public int getSEVolume() {
		return seVolume;
	}

	public void setSEVolume(int seVolume) {
		this.seVolume = seVolume;
	}
}
