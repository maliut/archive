package game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import module.DataManager;
import module.SceneManager;
import module.Vocab;

public class Player {
	
	private static DataManager data = new DataManager();
	private static SceneManager sm = new SceneManager();
	private static Interpreter interpreter = new Interpreter();
	// can to json
	private int location, clockwise;
	private int cash, deposit, coupon; // property , assets is auto-calculated when get
	private ArrayList<Integer> fieldsIndex = new ArrayList<Integer>();
	private ArrayList<Integer> itemsIndex = new ArrayList<Integer>();
	private int fineFreeRound, slowRound, stopRound;
	private boolean ai;
	
    private Actor actor = new Actor();
	
	// use to set things cannot be convert to json
	public void init() throws FileNotFoundException, IOException {
		actor.init();
		actor.getCharLabel().setBounds(32 * data.map().getCellAt(location).x, 32 * data.map().getCellAt(location).y, 32, 32);
		actor.getCharLabel().setIcon(actor.getCharactersFaceTo(data.map().getFaceDirectionAt(location, clockwise)));
	}

	public void buy() {
		Field field = (Field) data.map().getCellAt(location);
		if (cash >= field.getValue()) {
			cash -= field.getValue();
			field.setOwnerIndex(data.players().getCurrentIndex());
			fieldsIndex.add(location);
			sm.getSpotsScene().getCellLabel(location).refresh();
			sm.setVisible(false);
			interpreter.switchPlayer();
		} else {
			sm.getMessageScene().showMessage(String.format(Vocab.CashNotEnough, data.system().getCurrencyVocab(0)), 0);
		}
	}
	
	public void levelUp() {
		Field field = (Field) data.map().getCellAt(location);
		if (cash >= field.getValue() / 2) {
			cash -= field.getValue() / 2;
			field.levelUp();
			sm.getSpotsScene().getCellLabel(location).refresh();
			sm.setVisible(false);
			interpreter.switchPlayer();
		} else {
			sm.getMessageScene().showMessage(String.format(Vocab.CashNotEnough, data.system().getCurrencyVocab(0)), 0);
		}
	}
	
	public void payTo(int amount, Player target) {
		int remaining = amount;
		if (cash >= remaining) {  // pay with cash
			//java.lang.System.out.print(1);
			cash -= remaining;
			target.addCash(remaining);
			sm.getSpotsScene().getCellLabel(location).refresh();
			sm.getMessageScene().showMessage(2);
			return;
		} else { // cash not enough
			//java.lang.System.out.print(2);
			target.addCash(cash);
			remaining -= cash;
			cash = 0;
			if (deposit >= remaining) {
				//java.lang.System.out.print(3);
				deposit -= remaining;
				target.addCash(remaining);
				sm.getSpotsScene().getCellLabel(location).refresh();
				sm.getMessageScene().showMessage(2);
				return;
			} else { // deposit not enough
				//java.lang.System.out.print(4);
				target.addCash(deposit);
				remaining -= deposit;
				deposit = 0;
				// calculate sold list
				if (fieldsIndex.isEmpty()) { // no fields
					//java.lang.System.out.print(5);
					fail();
				} else {
					//java.lang.System.out.print(6);
					String soldList = "";int value = 0;		
					// test
					//for (int i = 0; i < fieldsIndex.size(); i++) {
					//	java.lang.System.out.println("fields:" + fieldsIndex.get(i));
					//}
					//
					for (int i = 0; i < fieldsIndex.size(); i++) {
						((Field) data.map().getCellAt(fieldsIndex.get(i))).setOwnerIndex(-1);
						soldList += " " + ((Field) data.map().getCellAt(fieldsIndex.get(i))).getStreet();
						value += ((Field) data.map().getCellAt(fieldsIndex.get(i))).getValue();
						sm.getSpotsScene().getCellLabel(fieldsIndex.get(i)).refresh();
						fieldsIndex.remove(i);
						if (value >= remaining) { // enough
							java.lang.System.out.print(7);
							cash = value - remaining;
							target.addCash(remaining);
							sm.getMessageScene().showMessage(String.format(Vocab.FieldSoldPrompt, soldList), 2);
							return;
						}
						if (fieldsIndex.isEmpty()) { // no fields
							java.lang.System.out.print(8);
							target.addCash(value);
							fail();
							return;
						}
					}
				}
			}
		}
	}
	
	public void fail() {
		if (data.players().size() == 2) {   // remaining one
			data.players().next();
			sm.getMessageScene().showMessage(3);
		} else {
			data.players().delete(data.players().getCurrentIndex());
			sm.getMessageScene().showMessage(actor.getName() + "ÆÆ²ú", 0);
		}
	}
	
	public void step() {
		while (true) {
			if (!actor.getCharLabel().getTimer().isRunning()) {	
				actor.setFaceTo(data.map().getFaceDirectionAt(location, clockwise));
				actor.getCharLabel().getTimer().start();	
				location += clockwise;
				location = ensure(location);
				break;
			}
		}
	}
	
	public void endStep() {
		while (true) {
			if (!actor.getCharLabel().getTimer().isRunning()) {
				actor.setFaceTo(data.map().getFaceDirectionAt(location, clockwise));
				break;
			}
		}
	}
	
	public void stateFadeOut() {
		if (fineFreeRound > 0) fineFreeRound -= 1;
		if (slowRound > 0) slowRound -= 1;
		if (stopRound > 0) stopRound -= 1;
	}
	
	public Actor getActor() {
		return actor;
	}
	
	public void setActor(Actor actor) {
		this.actor = actor;
	}
	
	public int getCash() {
		return cash;
	}
	
	public void setCash(int cash) {
		this.cash = cash;
		this.cash = (this.cash < 0) ? 0 : this.cash;
	}
	
	public void addCash(int cash) {
		this.cash += cash;
		this.cash = (this.cash < 0) ? 0 : this.cash;
	}
	
	public void emptyCash() {
		this.cash = 0;
	}
	
	public int getDeposit() {
		return deposit;
	}
	
	public void setDeposit(int deposit) {
		this.deposit = deposit;
		this.deposit = (this.deposit < 0) ? 0 : this.deposit;
	}
	
	public void addDeposit(int deposit) {
		this.deposit += deposit;
		this.deposit = (this.deposit < 0) ? 0 : this.deposit;
	}
	
	public void addDepositBy(int percent) {
		this.deposit += this.deposit * percent / 100;
		this.deposit = (this.deposit < 0) ? 0 : this.deposit;
	}
	
	public void emptyDeposit() {
		this.deposit = 0;
	}
	
	public int getCoupon() {
		return coupon;
	}
	
	public void addCoupon(int coupon) {
		this.coupon += coupon;
		this.coupon = (this.coupon < 0) ? 0 : this.coupon;
	}

	public void setCoupon(int coupon) {
		this.coupon = coupon;
		this.coupon = (this.coupon < 0) ? 0 : this.coupon;
	}
	
	public int getProperty() {
		int property = 0;
		for (int i:fieldsIndex) {
			Field field = (Field) data.map().getCellAt(i);
			property += field.getValue();
		}
		return property;
	}
	
	public int getAssets() {
		return cash + deposit + getProperty();
	}
	
	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = ensure(location);
	}

	public int getClockwise() {
		return clockwise;
	}
	
	public void reverseClockwise() {
		clockwise = -clockwise;
	}
	
	public int getFineFreeRound() {
		return fineFreeRound;
	}

	public void addFineFreeRound(int fineFreeRound) {
		this.fineFreeRound += fineFreeRound;
		this.fineFreeRound = (this.fineFreeRound < 0) ? 0 : this.fineFreeRound;
	}

	public int getSlowRound() {
		return slowRound;
	}

	public void addSlowRound(int slowRound) {
		this.slowRound += slowRound;
		this.slowRound = (this.slowRound < 0) ? 0 : this.slowRound;
	}

	public int getStopRound() {
		return stopRound;
	}

	public void addStopRound(int stopRound) {
		this.stopRound += stopRound;
		this.stopRound = (this.stopRound < 0) ? 0 : this.stopRound;
	}

	public void addItem(int index) {
		itemsIndex.add(index);
	}
	
	public int getItem(int index) {
		return itemsIndex.get(index);
	}
	
	public int getItemSize() {
		return itemsIndex.size();
	}
	
	public void lostItem() {
		itemsIndex.remove(0);
	}
	
	public boolean hasItem() {
		return !itemsIndex.isEmpty();
	}
	
	public boolean hasCDice() {
		if (hasItem()) {
			for (int i = 0; i < itemsIndex.size(); i++) {
				if (itemsIndex.get(i) == 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void useItem(int item) {
		if (item != 0) {  // ³ý Ò£¿Ø÷»×Ó
			interpreter.process(data.items().get(item).getTarget(), data.items().get(item).code());
		}
		for (int i = 0; i < itemsIndex.size(); i++) {
			if (itemsIndex.get(i) == item) {
				itemsIndex.remove(i);
				break;
			}
		}
	}
	
	public void addField(int index) {
		fieldsIndex.add(index);
	}
	
	public void lostField(int index) {
		for (int i = 0; i < fieldsIndex.size(); i++) {
			if (fieldsIndex.get(i) == index) {
				fieldsIndex.set(i, null);
			}
		}
		fieldsIndex.remove(null);
	}
	
	public boolean isAI() {
		return ai;
	}

	public void setAI(boolean ai) {
		this.ai = ai;
	}

	public int getDistance(Player p) {
		int d = Math.abs(location - p.getLocation());
		return Math.min(d, Math.abs(d - data.map().length()));
	}
	
	private int ensure(int location) {
		while (location < 0) {
			location += data.map().length();
		}
		while (location >= data.map().length()) {
			location -= data.map().length();
		}
		return location;
	}
}
