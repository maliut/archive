package game;

import java.util.ArrayList;

import module.AI;
import module.DataManager;
import module.SceneManager;
import module.Vocab;

public class Interpreter {
	
	private static DataManager data = new DataManager();
	private static SceneManager sm = new SceneManager();
	private static int round = 0;
	private static int[] code = null;
	private static int[][] codes = null;
	private static int[] targetCode = null;
	private static Object[] target = null;
	
	public void caseBank() {
		sm.getMessageScene().setVisible(false);
		sm.getMessageScene().setLocked(true);
		sm.getBankScene().refresh();
		sm.getBankScene().setVisible(true);
		//sm.refresh();
		if (data.players().getCurrent().isAI()) {
			AI.run(-1); // except 4,5
		}
	}
	
	public void caseLocation() {
		int cellIndex = data.players().getCurrent().getLocation();
		switch(data.map().getCellType(cellIndex)) {
		case 0:  // field
			fieldDeal();
			break;
		case 3:  // item shop
			//sm.getMessageScene().setVisible(false);
			//sm.getMessageScene().setLocked(true);			
			sm.getItemShopScene().setVisible(true);
			sm.getItemShopScene().refresh();
			break;
		case 4:  // lucky point
			luckyDeal();
			break;
		default:
			switchPlayer();
		}
	}
	
	private void fieldDeal() {
		Field field = (Field) data.map().getCellAt(data.players().getCurrent().getLocation());
		if (field.getOwnerIndex() == -1) { // can be sold
			sm.getMessageScene().showMessage(4);
		} else if (field.getOwnerIndex() == data.players().getCurrentIndex()) { // level up
			if (field.getLevel() < Field.MAX_LEVEL) {  // can level up
				sm.getMessageScene().showMessage(5);
			} else {
				switchPlayer();
			}
		} else { // pay the fee
			if (data.players().getCurrent().getFineFreeRound() > 0) {
				sm.getMessageScene().showMessage(Vocab.FineFreePrompt, 0, false);
			} else {
				sm.getMessageScene().showMessage(6);
			}
		}
		
	}

	private void luckyDeal() {
		switch ((int) (Math.random() * 4)) {
		case 0: // get cash
			int num1 = (int) (Math.random() * 1000) + 1;
			process(new int[] {1,0,0,0,0,0}, new int[][] {{1,0,num1}});
			sm.getMessageScene().showMessage(String.format(Vocab.GetPrompt, num1, data.system().getCurrencyVocab(0)), 3, false);
			break;
		case 1: // get deposit
			int num2 = ((int) (Math.random() * 3) + 1) * 10;
			process(new int[] {1,0,0,0,0,0}, new int[][] {{2,1,num2}});
			sm.getMessageScene().showMessage(String.format(Vocab.GetByPrompt, num2, data.system().getCurrencyVocab(1)), 3, false);
			break;
		case 2: // get coupon
			int num3 = (int) (Math.random() * 100) + 1;
			process(new int[] {1,0,0,0,0,0}, new int[][] {{3,0,num3}});
			sm.getMessageScene().showMessage(String.format(Vocab.GetPrompt, num3, data.system().getCurrencyVocab(2)), 3, false);
			break;
		case 3: // get item
			process(new int[] {1,0,0,0,0,0}, new int[][] {{4,0,1}});
			sm.getMessageScene().showMessage(String.format(Vocab.GetItemPrompt, ""), 3, false);
			break;
		}
	}
	
	public void switchPlayer() {
		data.players().next();
		sm.update();
		round++;
		if (round % data.players().size() == 0) {
			data.system().getDate().nextDay();
			sm.update();
			round = 0;
			if (data.system().getDate().isEndOfMonth()) {
				for (int i = 0; i < data.players().size(); i++) {
					data.players().get(i).addDepositBy(10);
				}
				sm.getMessageScene().showMessage(Vocab.BankAddDeposit, 5, false);
			} else {
				sm.getMessageScene().showMessage(0);
			}
		} else {
			sm.getMessageScene().showMessage(0);
		}
	}

	public void process(int[] targetCode, int[][] codes) {
		//Object[] target = null;
		Interpreter.targetCode = targetCode;
		Interpreter.codes = codes;
		switch (targetCode[0]) {
		case 1: // player
			target = getTargetPlayers().toArray();
			//if (target != null) {
				//for (int i = 0; i < target.length; i++) {
					//java.lang.System.out.print(((Player)target[i]).getActor().getName());
				//}
			//}
			//java.lang.System.exit(0);
			if (targetCode[5] == 0) { // needn't player choose
				exec();
			} else {  // need choose than use exec from message class
				sm.getTargetSelectionScene().refresh();
				sm.getTargetSelectionScene().setVisible(true);
			}
			break;
		case 2: // field
			target = getTargetFields().toArray();
			exec();
		}
		sm.update();
	}
	
	private ArrayList<Player> getTargetPlayers() {
		ArrayList<Player> target = new ArrayList<Player>();
		// get all players
		for (int i = 0; i < data.players().size(); i++) {
			target.add(data.players().get(i));
		}
		// who
		switch (targetCode[1]) {
		case 0:  // self
			target.clear();
			target.add(data.players().getCurrent());
			return target;
		case 1:  // all
			break;
		case 2:  // except self
			for (int i = 0; i < data.players().size(); i++) {
				if (data.players().get(i).equals(data.players().getCurrent())) {
					target.remove(data.players().getCurrent());
				}
			}
			break;
		}
		// where
		if (targetCode.length > 2) {
			switch (targetCode[2]) {
			case 0: // all map
				break;
			default: // radius x
				for (int i = 0; i < data.players().size(); i++) {
					if (data.players().getCurrent().getDistance(data.players().get(i)) > targetCode[2]) {
						target.remove(data.players().get(i));
					}
				}
				break;
			}
		}
		// what
		if (targetCode.length > 3) {
			ArrayList<Player> a = null;
			switch (targetCode[3]) {
			case 1: // cash
				a = data.players().getExtremaCash(targetCode[4]);
				break;
			case 2: // deposit
				a = data.players().getExtremaDeposit(targetCode[4]);
				break;
			case 3: // coupon
				a = data.players().getExtremaCoupon(targetCode[4]);
				break;
			case 4: // property
				a = data.players().getExtremaProperty(targetCode[4]);
				break;
			case 5: // assets
				a = data.players().getExtremaAssets(targetCode[4]);
				break;
			}
			if (targetCode[3] != 0) {
				for (int i = 0; i < target.size(); i++) {
					if (!a.contains(target.get(i))) {
						target.set(i, null);
					}
				}
				for (int i = 0; i < target.size(); i++) {
					target.remove(null);
				}
			}
		}
		// how
		return target;
	}
	
	private ArrayList<Field> getTargetFields() {
		ArrayList<Field> target = new ArrayList<Field>();
		switch (targetCode[1]) {
		case 0: // current
			if (data.map().getCellAt(data.players().getCurrent().getLocation()) instanceof Field) {
				target.add((Field) data.map().getCellAt(data.players().getCurrent().getLocation()));
			}
			break;
		case 1: // current street
			if (data.map().getCellAt(data.players().getCurrent().getLocation()) instanceof Field) {
				String name = ((Field) data.map().getCellAt(data.players().getCurrent().getLocation())).getStreet();
				for (int i = 0; i < data.map().length(); i++) {
					if (data.map().getCellAt(i) instanceof Field) {
						if (((Field) data.map().getCellAt(i)).getStreet().equals(name)) {
							target.add((Field) data.map().getCellAt(i));
						}
					}
				}
			}
			break;
		}
		return target;
	}
	
	public void exec() {
		if (target != null) {
			switch (targetCode[0]) {
			case 0:
				switch (targetCode[1]) {
				case 0:
					// ÷»×Ó
					break;
				case 1:
					// Â·ÕÏ
					break;
				}
				break;
			case 1: // player
				for (int i = 0; i < codes.length; i++) {
					code = codes[i];
					execPlayer();
				}
				break;
			case 2: // field
				for (int i = 0; i < codes.length; i++) {
					code = codes[i];
					execField();
				}
				break;
			}
		}
		sm.getMessageScene().setLocked(false);
		sm.getMessageScene().setVisible(true);
	}
	
	private void execPlayer() {
		switch (code[0]) {
		case 1: // cash
		case 2: // deposit
		case 3: // coupon
			switch (code[1]) {
			case 0: // add
				addTarget(code[2], code[0]);
				break;
			case 1: // add by
				addTargetBy(code[2], code[0]);
				break;
			case 2: // rob
				robTarget(code[2], code[0]);
				break;
			case 3: // average
				avgTarget(code[0]);
				break;
			}
			break;
		case 4: // item
			switch (code[1]) {
			case 0: // add
				addTargetItem(code[2]); 
				break;
			case 2: // rob
				robTargetItem(code[2]);
				break;
			}
			break;
		case 5: // slow round
		case 6: // fine free round
		case 7: // stop round
			addTargetState(code[2], code[0]);
			break;
		case 8: // turn around
			for (int i = 0; i < target.length; i++) {
				((Player) target[i]).reverseClockwise();
			}
			break;
		}
	}

	private void execField() {
		switch (code[0]) {
		case 1: // level up or down
			if (code[0] > 0) {
				for (int i = 0; i < code[0]; i++) {
					for (int j = 0; j < target.length; j++) {
						((Field) target[j]).levelUp();
					}
				}
			} else if (code[0] < 0) {
				for (int i = 0; i > code[0]; i--) {
					for (int j = 0; j < target.length; j++) {
						((Field) target[j]).levelDown();
					}
				}
			}
			break;
		case 2: // buy
			for (int i = 0; i < target.length; i++) {
				Field f = (Field) target[i];
				if (!(f.getOwnerIndex() == data.players().getCurrentIndex())) {
					if (data.players().getCurrent().getCash() >= f.getValue()) {
						data.players().getCurrent().addCash(-f.getValue());
						f.setOwnerIndex(data.players().getCurrentIndex());
						data.players().getCurrent().addField(f.getLocation());
					}
				}
			}
			break;
		case 3: // get
			for (int i = 0; i < target.length; i++) {
				Field f = (Field) target[i];
				f.setOwnerIndex(data.players().getCurrentIndex());
				data.players().getCurrent().addField(f.getLocation());
			}
			break;
		case 4: // destroy
			for (int i = 0; i < target.length; i++) {
				Field f = (Field) target[i];
				data.players().get(f.getOwnerIndex()).lostField(f.getLocation());
				data.players().get(f.getOwnerIndex()).addCash(f.getValue() * 3 / 2);
				f.setOwnerIndex(-1);
			}
			break;
		}
		for (int i = 0; i < target.length; i++) {
			Field f = (Field) target[i];
			sm.getSpotsScene().getCellLabel(f.getLocation()).refresh();
		}
	}
	
	private void addTargetItem(int num) {
		if (num > 0) {
			for (int i = 0; i < num; i++) {
				for (int j = 0; j < target.length; j++) {
					((Player) target[j]).addItem((int) (Math.random() * data.items().size()));
				}
			}
		} else if (num < 0) {
			for (int i = 0; i > num; i--) {
				for (int j = 0; j < target.length; j++) {
					if (((Player) target[j]).hasItem()) {
						((Player) target[j]).lostItem();
					}		
				}
			}
		}
	}

	private void robTargetItem(int num) {
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < target.length; j++) {
				if (((Player) target[j]).hasItem()) {
					data.players().getCurrent().addItem(((Player) target[j]).getItem(0));
					((Player) target[j]).lostItem();
				}		
			}
		}
	}

	private void addTarget(int num, int type) {
		switch (type) {
		case 1: // cash
			for (int i = 0; i < target.length; i++) {
				((Player) target[i]).addCash(num);
			}
			break;
		case 2: // deposit
			for (int i = 0; i < target.length; i++) {
				((Player) target[i]).addDeposit(num);
			}
			break;
		case 3: // coupon
			for (int i = 0; i < target.length; i++) {
				((Player) target[i]).addCoupon(num);
			}
			break;
		}
	}

	private void addTargetBy(int num, int type) {
		switch (type) {
		case 2: // deposit
			for (int i = 0; i < target.length; i++) {
				((Player) target[i]).addDepositBy(num);
			}
			break;
		}
	}

	private void robTarget(int num, int type) {
		switch (type) {
		case 1: // cash
			for (int i = 0; i < target.length; i++) {
				((Player) target[i]).addCash(-num);
			}
			data.players().getCurrent().addCash(num * target.length);
			break;
		case 2: // deposit
			for (int i = 0; i < target.length; i++) {
				((Player) target[i]).addDeposit(-num);
			}
			data.players().getCurrent().addDeposit(num * target.length);
			break;
		case 3: // coupon
			for (int i = 0; i < target.length; i++) {
				((Player) target[i]).addCoupon(-num);
			}
			data.players().getCurrent().addCoupon(num * target.length);
			break;
		}
	}

	private void avgTarget(int type) {
		switch (type) {
		case 1: // cash
			int sum1 = 0;
			for (int i = 0; i < target.length; i++) {
				sum1 += ((Player) target[i]).getCash();
			}
			for (int i = 0; i < target.length; i++) {
				((Player) target[i]).setCash(sum1 / target.length);
			}
			break;
		case 2: // deposit
			int sum2 = 0;
			for (int i = 0; i < target.length; i++) {
				sum2 += ((Player) target[i]).getDeposit();
			}
			for (int i = 0; i < target.length; i++) {
				((Player) target[i]).setDeposit(sum2 / target.length);
			}
			break;
		case 3: // coupon
			int sum3 = 0;
			for (int i = 0; i < target.length; i++) {
				sum3 += ((Player) target[i]).getCoupon();
			}
			for (int i = 0; i < target.length; i++) {
				((Player) target[i]).setCoupon(sum3 / target.length);
			}
			break;
		}
	}

	private void addTargetState(int num, int type) {
		switch (type) {
		case 5: // slow round
			for (int i = 0; i < target.length; i++) {
				((Player) target[i]).addSlowRound(num);
			}
			break;
		case 6: // fine free round
			for (int i = 0; i < target.length; i++) {
				((Player) target[i]).addFineFreeRound(num);
			}
			break;
		case 7: // stop round
			for (int i = 0; i < target.length; i++) {
				((Player) target[i]).addStopRound(num);
			}
			break;
		}
	}
	
	public static Object[] getTarget() {
		return target;
	}
	
	public static void setTarget(Object[] target) {
		Interpreter.target = target;
	}
	
}
