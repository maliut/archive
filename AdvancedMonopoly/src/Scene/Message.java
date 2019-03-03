package scene;

import game.Field;
import game.Interpreter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import commandSet.BuySet;
import commandSet.ContinueSet;
import commandSet.DiceSet;
import module.AI;
import module.DataManager;
import module.SceneManager;
import module.Vocab;
import MTool.MLabel;
import MTool.MPanel;

public class Message extends MPanel {

	private static final long serialVersionUID = 1L;
	private static DataManager data = new DataManager();
	private static SceneManager sm = new SceneManager();
	private static Interpreter interpreter = new Interpreter();
	
	private MLabel bodyImage = new MLabel("System/emptyImage.png");
	private MLabel messageBack = new MLabel("System/MessageBack.png");
	private TypePanel typePanel = new TypePanel();
	private DiceSet diceSet = new DiceSet();
	private BuySet buySet = new BuySet(0);
	private ContinueSet continueSet = new ContinueSet(0);
	private boolean locked = false;
	
	public Message() {
		super(0,0,22*32,16*32);		
		messageBack.setBounds(0, 11*32, 22*32, 16*32);
		typePanel.setBounds(0,0,22*32,16*32);
		bodyImage.setBounds(0, 0, 22*32, 16*32);
		diceSet.setVisible(false);
		buySet.setVisible(false);
		continueSet.setVisible(false);

		add(typePanel);
		add(messageBack);
		add(bodyImage);		
		addMouseListener(new NewMouseListener());
	}

	public void showMessage(int eventID) {
		resetTypePanel();
		setVisible(true);
		typePanel.setVisible(false);
		typePanel.setVisible(true);
		switch (eventID) {
		case 0: // dice and go
			if (data.players().getCurrent().getStopRound() > 0) {
				if (data.map().getCellType(data.players().getCurrent().getLocation()) == 2) { // bank
					interpreter.caseBank();
				} else {
					interpreter.caseLocation();
				}
			} else {
				resetDiceSet();
				resetBodyImage();
				typePanel.showMessage(data.players().getCurrent().getActor().getScript(eventID), true);
				sm.getMessageScene().setLocked(false);
			}
			break;
		case 1: // get something			
		case 2: // lost something
			resetContinueSet(0);
			resetBodyImage();
			typePanel.showMessage(data.players().getCurrent().getActor().getScript(eventID), true);
			break;
		case 3: // win the game
			resetContinueSet(4);
			resetBodyImage();
			typePanel.showMessage(data.players().getCurrent().getActor().getScript(eventID), true);
			break;
		case 4: // buy prompt
			resetBuySet(0);
			bodyImage.setVisible(false);
			Field field1 = (Field) data.map().getCellAt(data.players().getCurrent().getLocation());
			typePanel.showMessage(String.format(Vocab.BuyPrompt, field1.getStreet(), field1.getValue()), false);
			break;
		case 5: // level up prompt
			resetBuySet(1);
			bodyImage.setVisible(false);
			Field field2 = (Field) data.map().getCellAt(data.players().getCurrent().getLocation());
			typePanel.showMessage(String.format(Vocab.LevelUpPrompt, field2.getStreet(),  field2.getValue() / 2), false);
			break;
		case 6: // pay the fee prompt // showMessage(plaintext) need to show body image...
			Field field3 = (Field) data.map().getCellAt(data.players().getCurrent().getLocation());
			bodyImage.setVisible(false);
			showMessage(String.format(Vocab.PayFeePrompt, field3.getStreet(), data.players().get(field3.getOwnerIndex()).getActor().getName(), field3.getFee()), 1);
			break;
		}
		if (data.players().getCurrent().isAI()) {
			AI.run(eventID);
		}
	}
	
	public void showMessage(String plainText, int type) {
		resetContinueSet(type);
		
		typePanel.showMessage(plainText, false);
		setVisible(true);
		typePanel.setVisible(false);
		typePanel.setVisible(true);
		if (data.players().getCurrent().isAI()) {
			AI.run(-1);
		}
	}
	
	public void showMessage(String plainText, int type, boolean showBodyImage) {
		bodyImage.setVisible(showBodyImage);
		showMessage(plainText, type);
		if (data.players().getCurrent().isAI()) {
			AI.run(-1);
		}
	}
	
	private void resetBodyImage() {
		bodyImage.switchIcon(String.format("Body/%s/happy.png", data.players().getCurrent().getActor().getName()));
		bodyImage.setVisible(true);
	}
	
	private void resetTypePanel() {
		remove(typePanel);
		typePanel = new TypePanel();
		add(typePanel);
		setComponentZOrder(typePanel, 0);
	}
	
	private void resetDiceSet() {
		remove(diceSet);
		remove(buySet);
		remove(continueSet);
		diceSet = new DiceSet();
		add(diceSet);
		setComponentZOrder(diceSet, 0);
		diceSet.setVisible(true);
	}
	
	private void resetBuySet(int type) {
		remove(diceSet);
		remove(buySet);
		remove(continueSet);
		buySet = new BuySet(type);
		add(buySet);
		setComponentZOrder(buySet, 0);
		buySet.setVisible(true);
	}
	
	private void resetContinueSet(int type) {
		remove(diceSet);
		remove(buySet);
		remove(continueSet);
		continueSet = new ContinueSet(type);
		add(continueSet);
		setComponentZOrder(continueSet, 0);
		continueSet.setVisible(true);
		setVisible(true);
		typePanel.setVisible(false);
		typePanel.setVisible(true);
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	class NewMouseListener extends MouseAdapter {
		
		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {  
				if (!locked) {
		            setVisible(false);
				}
	        }  
		}
	}
	
	class TypePanel extends MPanel {
		
		private static final long serialVersionUID = 1L;
		private DataManager data = new DataManager();
		Graphics g=getGraphics();
		
		private Timer timer = new Timer(100, new TimerListener());
		private String[] lines = {" "," "};
		private boolean typeEffect;
		private int length = 0;
		
		public void showMessage(String text, boolean typeEffect) {
			this.lines = text.split("/n");
			this.typeEffect = typeEffect;
			if (this.typeEffect && data.system().isTypeEffect()) {
				timer.start();
			}
		}

		protected void paintComponent(Graphics g) {
			this.g = getGraphics();
		    super.paintComponent(g);
			g.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 22));
			g.setColor(new Color(255, 255, 255, 255));		
			if (typeEffect && data.system().isTypeEffect()) {
				g.drawString(lines[0].substring(0, length), 16, 12 * 32 + 16);
				if (length == lines[0].length()) {
					timer.stop();
				}
			} else {
				for (int i = 0; i < lines.length; i++) {
					g.drawString(lines[i], 16, (12 + i) * 32 + 16);
				}
			}
		}

		class TimerListener implements ActionListener {			
			@Override
			public void actionPerformed(ActionEvent e) {
				length++;			
				if (g != null) paint(g);
			}
		}
	}
}
