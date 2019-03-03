package game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import module.DataManager;

public class Players {
	
	private static DataManager data = new DataManager();
	
	private Player[] players;// = new Player[6]; //用于生成json数据 ，以后删掉
	/*{
		for (int i = 0; i<6;i++) {
			players[i] = new Player();
		}
	}*/ //用于生成json数据 ，以后删掉
	
	private int index = 0;
	
	public void init() throws FileNotFoundException, IOException {
		for (int i = 0; i < players.length; i++) {
			//players[i].setLocation(data.map().getStartPoint(i));
			//players[i].setDirection(data.map().getFaceDirectionAt(players[i].getLocation(), players[i].getDirection()));
			players[i].init();
		}
	}
	
	public int size() {
		return players.length;
	}
	
	public Player get(int i) {
		if (i >= 0 && i < players.length) {
			return players[i];
		} else {
			return null;
		}
	}
	
	public Player getCurrent() {
		return players[index];
	}
	
	public int getCurrentIndex() {
		return index;
	}

	public ArrayList<Player> getExtremaCash(int m) {
		ArrayList<Player> target = new ArrayList<Player>();
		target.add(get(0));
		for (int i = 1; i < size(); i++) {
			if (m == 0) {  // min
				if (get(i).getCash() < target.get(0).getCash()) {
					target.clear();
					target.add(get(i));
				} else if (get(i).getCash() == target.get(0).getCash()) {
					target.add(get(i));
				}
			} else if (m == 1) { // max
				if (get(i).getCash() > target.get(0).getCash()) {
					target.clear();
					target.add(get(i));
				} else if (get(i).getCash() == target.get(0).getCash()) {
					target.add(get(i));
				}
			}
		}
		return target;
	}
	
	public ArrayList<Player> getExtremaDeposit(int m) {
		ArrayList<Player> target = new ArrayList<Player>();
		target.add(get(0));
		for (int i = 1; i < size(); i++) {
			if (m == 0) {  // min
				if (get(i).getDeposit() < target.get(0).getDeposit()) {
					target.clear();
					target.add(get(i));
				} else if (get(i).getDeposit() == target.get(0).getDeposit()) {
					target.add(get(i));
				}
			} else if (m == 1) { // max
				if (get(i).getDeposit() > target.get(0).getDeposit()) {
					target.clear();
					target.add(get(i));
				} else if (get(i).getDeposit() == target.get(0).getDeposit()) {
					target.add(get(i));
				}
			}
		}
		return target;
	}
	
	public ArrayList<Player> getExtremaCoupon(int m) {
		ArrayList<Player> target = new ArrayList<Player>();
		target.add(get(0));
		for (int i = 1; i < size(); i++) {
			if (m == 0) {  // min
				if (get(i).getCoupon() < target.get(0).getCoupon()) {
					target.clear();
					target.add(get(i));
				} else if (get(i).getCoupon() == target.get(0).getCoupon()) {
					target.add(get(i));
				}
			} else if (m == 1) { // max
				if (get(i).getCoupon() > target.get(0).getCoupon()) {
					target.clear();
					target.add(get(i));
				} else if (get(i).getCoupon() == target.get(0).getCoupon()) {
					target.add(get(i));
				}
			}
		}
		return target;
	}
	
	public ArrayList<Player> getExtremaProperty(int m) {
		ArrayList<Player> target = new ArrayList<Player>();
		target.add(get(0));
		for (int i = 1; i < size(); i++) {
			if (m == 0) {  // min
				if (get(i).getProperty() < target.get(0).getProperty()) {
					target.clear();
					target.add(get(i));
				} else if (get(i).getProperty() == target.get(0).getProperty()) {
					target.add(get(i));
				}
			} else if (m == 1) { // max
				if (get(i).getProperty() > target.get(0).getProperty()) {
					target.clear();
					target.add(get(i));
				} else if (get(i).getProperty() == target.get(0).getProperty()) {
					target.add(get(i));
				}
			}
		}
		return target;
	}
	
	
	public ArrayList<Player> getExtremaAssets(int m) {
		ArrayList<Player> target = new ArrayList<Player>();
		target.add(get(0));
		for (int i = 1; i < size(); i++) {
			if (m == 0) {  // min
				if (get(i).getAssets() < target.get(0).getAssets()) {
					target.clear();
					target.add(get(i));
				} else if (get(i).getAssets() == target.get(0).getAssets()) {
					target.add(get(i));
				}
			} else if (m == 1) { // max
				if (get(i).getAssets() > target.get(0).getAssets()) {
					target.clear();
					target.add(get(i));
				} else if (get(i).getAssets() == target.get(0).getAssets()) {
					target.add(get(i));
				}
			}
		}
		return target;
	}
	
	public void next() {
		index++;
		if (index >= players.length) {
			index = 0;
		}
	}
	
	public void delete(int index) {
		Player[] temp = new Player[players.length - 1];
		int j = 0;
		for (int i = 0; i < players.length; i++) {
			if (i != index) {
				temp[j] = players[i];
				j++;
			}
		}
		players = temp;
	}

}
