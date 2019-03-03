package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import game.Interpreter;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import module.DataManager;
import module.SceneManager;
import MTool.MButton;
import MTool.MLabel;
import MTool.MPanel;

public class ItemShop extends MPanel {

	private static final long serialVersionUID = 1L;
	private static DataManager data = new DataManager();
	private static SceneManager sm = new SceneManager();
	private static Interpreter interpreter = new Interpreter();
	
	private MLabel back = new MLabel("System/itemShop.png");
	private JSlider cashSlider = new JSlider(JSlider.HORIZONTAL);
	private JSlider couponSlider = new JSlider(JSlider.HORIZONTAL);
	private JLabel text = new JLabel();
	private MButton jbtOK = new MButton("ok");
	private MButton jbtCancel = new MButton("cancel");
	
	public ItemShop() {
		super(3*32, 3*32,16*32,12*32);

		back.setBounds(0, 0, 16*32, 12*32);
		text.setForeground(new Color(168,213,224,255));
		text.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 24));
		text.setBounds(1*32+8,128,512,32);
		cashSlider.setBounds(3*32, 5*32+16, 7*32, 32);
		cashSlider.setOpaque(false);
		cashSlider.addChangeListener(new SliderListener());
		couponSlider.setBounds(3*32, 7*32+16, 7*32, 32);
		couponSlider.setOpaque(false);
		couponSlider.addChangeListener(new SliderListener());
		jbtOK.setBounds(9*32, 9*32+16, 96, 32);
		jbtOK.setMnemonic(KeyEvent.VK_Z);
		jbtOK.addActionListener(new ButtonListener());
		jbtCancel.setBounds(9*32+96+8, 9*32+16, 96, 32);
		jbtCancel.setMnemonic(KeyEvent.VK_X);
		jbtCancel.addActionListener(new ButtonListener());
		
		add(jbtOK);
		add(jbtCancel);
		add(text);
		add(cashSlider);
		add(couponSlider);
		add(back);
		setVisible(false);
	}

	public void refresh() {
		if (data.players().getCurrent().getCoupon() <= 0) {
			setVisible(false);
			interpreter.switchPlayer();
		} else {
			cashSlider.setMinimum(0);
			cashSlider.setMaximum(data.players().getCurrent().getCash());
			couponSlider.setMinimum(1);
			couponSlider.setMaximum(data.players().getCurrent().getCoupon());
			text.setText(String.format("%s£º%d£¬%s£º%d", data.system().getCurrencyVocab(0), cashSlider.getValue(), data.system().getCurrencyVocab(2), couponSlider.getValue()));
		}
	}
	
	class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			text.setText(String.format("%s£º%d£¬%s£º%d", data.system().getCurrencyVocab(0), cashSlider.getValue(), data.system().getCurrencyVocab(2), couponSlider.getValue()));
		}
	}
	
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			if (e.getSource() == jbtOK) {
				data.players().getCurrent().addCash(-cashSlider.getValue());
				data.players().getCurrent().addCoupon(-couponSlider.getValue());
				sm.update();
				game.ItemShop.makeItem(couponSlider.getValue(), cashSlider.getValue());
			} else if (e.getSource() == jbtCancel) {
				interpreter.switchPlayer();				
			}

		}		
	}
}
