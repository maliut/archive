package scene;

import game.Interpreter;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import module.DataManager;
import module.SceneManager;
import MTool.MButton;
import MTool.MLabel;
import MTool.MPanel;
import MTool.MTextButton;

public class Bank extends MPanel {

	private static final long serialVersionUID = 1L;
	private static DataManager data = new DataManager();
	private static SceneManager sm = new SceneManager();
	private static Interpreter interpreter = new Interpreter();
	
	private MLabel back = new MLabel("System/Bank.png");
	private JSlider slider = new JSlider(JSlider.HORIZONTAL);
	private JLabel text = new JLabel();
	private TextInput input = new TextInput();
	private MButton jbtOK = new MButton("ok");
	private MButton jbtCancel = new MButton("cancel");
	private int num;// = data.players().getCurrent().getCash();

	public Bank() {
		//super(0,0,22*32,16*32);	
		super(3*32, 3*32,16*32,12*32);
		//back.setBounds(3*32, 2*32, 16*32, 12*32);
		back.setBounds(0, 0, 16*32, 12*32);
		slider.setBounds(1*32, 7*32, 7*32, 32);
		slider.setOpaque(false);
		slider.addChangeListener(new SliderListener());
		text.setForeground(new Color(168,213,224,255));
		text.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 30));
		text.setBounds(1*32,5*32,256,32);
		jbtOK.setBounds(9*32, 9*32+16, 96, 32);
		jbtOK.setMnemonic(KeyEvent.VK_Z);
		jbtOK.addActionListener(new ButtonListener());
		jbtCancel.setBounds(9*32+96+8, 9*32+16, 96, 32);
		jbtCancel.setMnemonic(KeyEvent.VK_X);
		jbtCancel.addActionListener(new ButtonListener());
		add(jbtOK);
		add(jbtCancel);
		add(input);
		add(text);
		add(slider);
		add(back);
		setVisible(false);
		
	}
	
	public void refresh() {
		num = data.players().getCurrent().getCash();
		slider.setMinimum(0);
		slider.setMaximum(data.players().getCurrent().getCash() + data.players().getCurrent().getDeposit());
		slider.setValue(data.players().getCurrent().getCash());
		text.setText(data.system().getCurrencyVocab(0) + ":" + String.valueOf(data.players().getCurrent().getCash()));
	}
	
	class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			text.setText(data.system().getCurrencyVocab(0) + ":" + String.valueOf(slider.getValue()));
		}
	}
	
	class TextInput extends MPanel {
		
		private static final long serialVersionUID = 1L;
		private MTextButton[] jbts = new MTextButton[12];
		
		public TextInput() {
			super(10*32,2*32+32,3*48,4*48);
			setLayout(new GridLayout(4,3));
			for (int i = 0; i < jbts.length; i++) {
				jbts[i] = new MTextButton(String.valueOf(i));
				jbts[i].addActionListener(new ButtonListener());
			}
			jbts[10].setText("¡û");
			jbts[11].setText("C");
			add(jbts[7]);
			add(jbts[8]);
			add(jbts[9]);
			add(jbts[4]);
			add(jbts[5]);
			add(jbts[6]);
			add(jbts[1]);
			add(jbts[2]);
			add(jbts[3]);
			add(jbts[0]);
			add(jbts[10]);
			add(jbts[11]);
		}
		
		class ButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < jbts.length; i++) {
					if (e.getSource() == jbts[i]) {
						switch (i) {
						case 10:
							num /= 10;
							break;
						case 11:
							num = 0;
							break;
						default:
							num = Math.min(10 * num + i, slider.getMaximum());
						}
					}
				}
				slider.getModel().setValue(num);
				text.setText(data.system().getCurrencyVocab(0) + ":" + String.valueOf(num));
			}			
		}		
	}
	
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jbtOK) {
				data.players().getCurrent().setCash(slider.getValue());
				data.players().getCurrent().setDeposit(slider.getMaximum() - slider.getValue());
				sm.update();
			}
			setVisible(false);

			interpreter.caseLocation();
		}		
	}
}
