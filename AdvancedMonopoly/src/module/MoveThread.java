package module;

import game.Interpreter;

public class MoveThread extends Thread {

	private static DataManager data = new DataManager();
	private static SceneManager sm = new SceneManager();
	private static Interpreter interpreter = new Interpreter();
	
	private int times;
	private int initialLocation, finalLocation;
	
	public MoveThread(int times) {
		this.times = times;
	}
	
	public void run() {
		initialLocation = data.players().getCurrent().getLocation();
		for (int i = 0; i < times; i++) {
			data.players().getCurrent().step();
		}
		data.players().getCurrent().endStep();
		finalLocation = data.players().getCurrent().getLocation();
		if (bankPassed()) {
			interpreter.caseBank();
		} else {
			interpreter.caseLocation();
		}
    }
	
	private boolean bankPassed() {
		switch (data.players().getCurrent().getClockwise()) {
		case 1:
			if (finalLocation < initialLocation) {
				finalLocation += data.map().length();
			}
			for (int i = initialLocation + 1; i <= finalLocation; i++) {
				if (data.map().getCellType(ensure(i)) == 2) { // bank
					return true;
				}
			}
			break;
		case -1:
			if (finalLocation > initialLocation) {
				finalLocation -= data.map().length();
			}
			for (int i = finalLocation; i <= initialLocation - 1; i++) {
				if (data.map().getCellType(ensure(i)) == 2) { // bank
					return true;
				}
			}
			break;
		}
		return false;
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
