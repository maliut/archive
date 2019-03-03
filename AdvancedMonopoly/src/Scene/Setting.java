package scene;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import module.AudioManager;
import module.DataManager;
import module.SceneManager;
import MTool.MButton;
import MTool.MLabel;
import MTool.MPanel;

public class Setting extends MPanel {
	
	private static final long serialVersionUID = 1L;
	private static DataManager data = new DataManager();
	private static SceneManager sm = new SceneManager();
	private static AudioManager au = new AudioManager();
	
	private MLabel back = new MLabel("System/Bank.png");
	private JSlider bgmSlider = new JSlider(JSlider.HORIZONTAL);
	private JSlider voiceSlider = new JSlider(JSlider.HORIZONTAL);
	private JSlider seSlider = new JSlider(JSlider.HORIZONTAL);
	private JCheckBox jchkType = new JCheckBox();
	private JCheckBox jchkDice = new JCheckBox();
	private MButton jbtOK = new MButton("ok");
	private MButton jbtCancel = new MButton("cancel");

	public Setting() {
		super(3*32, 3*32,16*32,12*32);		
		back.setBounds(0, 0, 16*32, 12*32);
		bgmSlider.setBounds(3*32, 5*32, 5*32, 32);
		bgmSlider.setMinimum(0);
		bgmSlider.setMaximum(100);
		bgmSlider.setOpaque(false);
		bgmSlider.addChangeListener(new SliderListener());
		voiceSlider.setBounds(3*32, 7*32, 5*32, 32);
		voiceSlider.setMinimum(0);
		voiceSlider.setMaximum(100);
		voiceSlider.setOpaque(false);
		seSlider.setBounds(3*32, 9*32, 5*32, 32);
		seSlider.setMinimum(0);
		seSlider.setMaximum(100);
		seSlider.setOpaque(false);
		jchkType.setOpaque(false);
		jchkType.setBounds(8*32, 5*32, 128, 32);
		jchkDice.setOpaque(false);
		jchkDice.setBounds(8*32, 7*32, 128, 32);
		jbtOK.setBounds(9*32, 9*32-8, 96, 32);
		jbtOK.setMnemonic(KeyEvent.VK_Z);
		jbtOK.addActionListener(new ButtonListener());
		jbtCancel.setBounds(9*32+96+8, 9*32-8, 96, 32);
		jbtCancel.setMnemonic(KeyEvent.VK_X);
		jbtCancel.addActionListener(new ButtonListener());
		add(jbtOK);
		add(jbtCancel);
		add(bgmSlider);
		add(voiceSlider);
		add(seSlider);
		add(jchkType);
		add(jchkDice);
		add(back);
		
		setVisible(false);		
	}
	
	public void refresh() {
		bgmSlider.setValue(data.system().getBGMVolume());
		voiceSlider.setValue(data.system().getVoiceVolume());
		seSlider.setValue(data.system().getSEVolume());
		jchkType.setSelected(data.system().isTypeEffect());
		jchkDice.setSelected(data.system().isDiceSlowEffect());
		sm.getMessageScene().setVisible(false);
	}
	
	class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			try {
				au.setBGMVolume(bgmSlider.getValue());
			} catch (BasicPlayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jbtOK) {
				data.system().setBGMVolume(bgmSlider.getValue());
				data.system().setVoiceVolume(voiceSlider.getValue());
				data.system().setSEVolume(seSlider.getValue());
				data.system().setTypeEffect(jchkType.isSelected());
				data.system().setDiceSlowEffect(jchkDice.isSelected());
			}
			setVisible(false);
			sm.getMessageScene().setVisible(true);
			sm.getMessageScene().setLocked(false);
		}		
	}
}
