package data.object;

import data.global.Game;
import data.module.Helper;
import data.module.Vocab;
import data.module.Window;

public class Player {
	
	public String name = Vocab.AvailableCell, icon = "\u3000\u3000";
	public int location = 0, direction = 1;
	public int cash = 5000, deposit = 0, coupon = 0, property = 0;
	public int totalAssets = 0; // totalAssets is counted every time when show
	// define states
	public int slowRound = 0, fineFreeRound = 0;//, cellRobRound = 0;
	// define cells[] items[]
	public boolean[] cells = new boolean[Map.length];
	public int[] items = new int[Item.ItemNum];
	
	/** Control player to move */
	public void move(int step) {
		// whether player can move?
		for (int i = 0; i < step; i++) {
			step();
			// whether bank
			if (Game.mapWithInfo.route[location].type == 2) {
				Game.bank.greeting();
			}
			// whether player is blocked by barrier
			if (Game.mapWithInfo.route[location].isBarrier) {
				Window.showBarrierBlock();
				Game.mapWithInfo.route[location].isBarrier = false;
				break;
			}
		}
	}
	
	/** Use items */
	public void useItem(int index) {
		if (items[index] > 0) {
			new Item(index);
		} else {
			Window.showErrorInfo(Vocab.LackOfItemError);
		}
	}
	
	public void buy() {   // only can buy cell at your current location
		if (cash >= Game.mapWithInfo.route[location].price * Game.mapWithInfo.route[location].level) {
			cash -= Game.mapWithInfo.route[location].price * Game.mapWithInfo.route[location].level;
			cells[location] = true;
			Game.mapWithInfo.route[location].owner = Game.currentPlayer;
			Game.mapWithInfo.route[location].icon = Vocab.CellIcon[6 + Game.currentPlayer];
			property += Game.mapWithInfo.route[location].price;
			Window.showErrorInfo(Vocab.NoError);
		} else {
			Window.showErrorInfo(Vocab.LackOfCashError);
		}
		Helper.getEnter();
	}
	
	public void levelUp() {
		if (cash >= Game.mapWithInfo.route[location].price / 2) {
			cash -= Game.mapWithInfo.route[location].price / 2;
			Game.mapWithInfo.route[location].level += 1;
			property += Game.mapWithInfo.route[location].price;
			Window.showErrorInfo(Vocab.NoError);
		} else {
			Window.showErrorInfo(Vocab.LackOfCashError);
		}
	}
	
	public void fined() {
		int sum = 0, current = 0, fine = 0, fineRemaining = 0;
		boolean isFailed = true;  // suppose true
		// Calculate value in all street
		for (int i = 0; i < Map.length; i++) {			
			if ((Game.mapWithInfo.route[i].street == Game.mapWithInfo.route[location].street) && (Game.mapWithInfo.route[i].owner == Game.mapWithInfo.route[location].owner)) {
				sum += Game.mapWithInfo.route[i].price * Game.mapWithInfo.route[i].level;
			}
		}
		// Calculate value in current cell
		current = Game.mapWithInfo.route[location].price * Game.mapWithInfo.route[location].level;
		fine = sum / 10 + current / 5;
		// fine
		if (cash >= fine) {
			cash -= fine;
			Window.showLossInfo(Game.currentPlayer, 2, fine);
		} else {  // cash not enough
			fineRemaining = fine - cash;
			Window.showLossInfo(Game.currentPlayer, 2, cash);
			cash = 0;
			// fine deposit
			if (deposit >= fineRemaining) {
				deposit -= fineRemaining;
				Window.showLossInfo(Game.currentPlayer, 3, fineRemaining);
			} else {  // deposit not enough
				fineRemaining -= deposit;
				Window.showLossInfo(Game.currentPlayer, 3, deposit);
				deposit = 0;
				// fine property
				int value = 0;
				for (int i = 0; i < Map.length; i++) {
					value = 0;
					if (cells[i]) {
						value = Game.mapWithInfo.route[i].price * Game.mapWithInfo.route[i].level;
						// sell cell[i]
						Game.mapWithInfo.route[i].owner = 0;
						cells[i] = false;
						Game.mapWithInfo.route[i].icon = Vocab.CellIcon[0];
						property -= value;
						if (value >= fineRemaining) {
							cash += value - fineRemaining;
							Window.showCellLossInfo(Game.currentPlayer, Game.mapWithInfo.route[i].street, Game.mapWithInfo.route[i].streetNo); // need +
							isFailed = false;
							break;
						} else {
							fineRemaining -= value;
						}
					}
				}
				if (isFailed) {  
					Helper.commitFail();
				}
			}
		}
		Game.players[3 - Game.currentPlayer].cash += fine;		// need rewrite
		Window.showGetInfo(3 - Game.currentPlayer, fine + Vocab.PlayersInfoListHead[2]);
	}
	
	public void getCash(int cashGet) {
		cash += cashGet;
		Window.showGetInfo(Game.currentPlayer, cashGet + Vocab.PlayersInfoListHead[2]);
	}
	
	public void getCoupon(int couponGet) {
		coupon += couponGet;
		Window.showGetInfo(Game.currentPlayer, couponGet + Vocab.PlayersInfoListHead[1]);
	}
	
	public void getItem(int itemGet) {
		items[itemGet] += 1;
		Window.showGetInfo(Game.currentPlayer, Vocab.ItemName[itemGet]);
	}
	
	public void calTotalAssets() {
		totalAssets = cash + deposit + property;
	}
	
	public void stateFadeOut() {
		if (slowRound > 0) {
			slowRound -= 1;
		}
		if (fineFreeRound > 0) {
			fineFreeRound -= 1;
		}
	}
	
	/** One step and deal with sth */
	private void step() {
		location += direction;
		location = Helper.ensure(location);
	}

}
