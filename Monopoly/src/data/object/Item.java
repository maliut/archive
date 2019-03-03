package data.object;

import data.global.Game;
import data.module.Helper;
import data.module.Vocab;
import data.module.Window;

public class Item {
	public static final int ItemNum = 11;  // 0 for cancel
	public static final int[] Price = {0,20,20,20,30,20,20,20,30,30,30};
	
	/** new Item(x) to use item */
	public Item(int type) {
		switch(type) {
		case 1: useTurnAround(); break;
		case 2: useSlow(); break;
		case 3: useBarrier(); break;
		case 4: useRobBuy(); break;
		case 5: useExtraTax(); break;
		case 6: useAvgCash(); break;
		case 7: useTearDown(); break;
		case 8: useMonster(); break;
		case 9: useCashGod(); break;
		case 10: useLuckyGod(); break;
		case 11: news(); break;
		case 12: lottery(); break;
		}
	}

	// Following methods define using different cards
	private void useTurnAround() {
		boolean[] targetPlayers = findPlayers(5, true);
		System.out.println("«Î—°‘Ò£∫");
		Window.showPlayerSelectList(targetPlayers);
		int n = Helper.getInt(0, Game.players.length);
		while (n != 0 && !targetPlayers[n]) {
			Window.showErrorInfo(Vocab.InputError);
			n = Helper.getInt(0, Game.players.length);
		} 
		if (n != 0) {
			turnAround(n);
			Game.players[Game.currentPlayer].items[1] -= 1;
			Window.showErrorInfo(Vocab.NoError);
		} 
	}

	private void useSlow() {
		boolean[] targetPlayers = findPlayers(5, true);
		System.out.println("«Î—°‘Ò£∫");
		Window.showPlayerSelectList(targetPlayers);
		int n = Helper.getInt(0, Game.players.length);
		while (n != 0 && !targetPlayers[n]) {
			Window.showErrorInfo(Vocab.InputError);
			n = Helper.getInt(0, Game.players.length);
		} 
		if (n != 0) {
			slow(n, 3);
			Game.players[Game.currentPlayer].items[2] -= 1;
			Window.showErrorInfo(Vocab.NoError);
		} 
	}
	
	private void useBarrier() {
		Window.showBarrierSetPrompt();
		int n = Helper.getInt(-8, 8);
		int targetLocation = Helper.ensure(Game.players[Game.currentPlayer].location + n);
		setBarrier(targetLocation);
		Game.players[Game.currentPlayer].items[3] -= 1;
		Window.showErrorInfo(Vocab.NoError);
	}

	private void useRobBuy() {
		int oldOwner = Game.mapWithInfo.route[Game.players[Game.currentPlayer].location].owner;
		if (oldOwner == Game.currentPlayer) {
			Window.showErrorInfo(Vocab.IllegalItemUseError);
		} else {			
			Game.players[Game.currentPlayer].buy();
			if (Game.players[Game.currentPlayer].cells[Game.players[Game.currentPlayer].location]) { // buy succeed
				Game.players[oldOwner].cells[Game.players[Game.currentPlayer].location] = false;
				Game.players[oldOwner].property -= Game.mapWithInfo.route[Game.players[Game.currentPlayer].location].price * Game.mapWithInfo.route[Game.players[Game.currentPlayer].location].level;
				Game.players[Game.currentPlayer].items[4] -= 1;
			}
		}
	}
	
	private void useExtraTax() {
		boolean[] targetPlayers = findPlayers(5, false);
		System.out.println("«Î—°‘Ò£∫");
		Window.showPlayerSelectList(targetPlayers);
		int n = Helper.getInt(0, Game.players.length);
		while (n != 0 && !targetPlayers[n]) {
			Window.showErrorInfo(Vocab.InputError);
			n = Helper.getInt(0, Game.players.length);
		} 
		if (n != 0) {
			Game.bank.tax(n, 3);
			Game.players[Game.currentPlayer].items[5] -= 1;
			Window.showErrorInfo(Vocab.NoError);
		} 
	}

	private void useAvgCash() {
		averageCash();
		Game.players[Game.currentPlayer].items[6] -= 1;
		Window.showErrorInfo(Vocab.NoError);
	}

	private void useTearDown() {
		if (Game.mapWithInfo.route[Game.players[Game.currentPlayer].location].street == 0) {
			Window.showErrorInfo(Vocab.IllegalItemUseError);
		} else {
			tearDown();
			Game.players[Game.currentPlayer].items[7] -= 1;
			Window.showErrorInfo(Vocab.NoError);
		}
	}
	
	private void useMonster() {
		if (Game.mapWithInfo.route[Game.players[Game.currentPlayer].location].street == 0) {
			Window.showErrorInfo(Vocab.IllegalItemUseError);
		} else {
			monster();
			Game.players[Game.currentPlayer].items[8] -= 1;
			Window.showErrorInfo(Vocab.NoError);
		}
	}
	
	private void useCashGod() {
		cashGod();
		Game.players[Game.currentPlayer].items[9] -= 1;
		Window.showErrorInfo(Vocab.NoError);
	}
	
	private void useLuckyGod() {
		luckyGod();
		Game.players[Game.currentPlayer].items[10] -= 1;
		Window.showErrorInfo(Vocab.NoError);
	}
	
	// Following is methods that needed to form useXXX methods 
	private void turnAround(int targetPlayer) {
		Game.players[targetPlayer].direction = 0 - Game.players[targetPlayer].direction;
	}
	
	private void slow(int targetPlayer, int round) {
		Game.players[targetPlayer].slowRound += round;
	}
	
	private void setBarrier(int location) {
		Game.mapWithInfo.route[location].isBarrier = true;
	}
	
	private void averageCash() {
		int sum = 0;
		for (int i = 1; i < Game.players.length; i++) {
			sum += Game.players[i].cash;
		}
		for (int i = 1; i < Game.players.length; i++) {
			Game.players[i].cash = sum / Game.players.length;
		}
	}
	
	private void tearDown() {
		int location = Game.players[Game.currentPlayer].location;
		for (int i = 0; i < Map.length; i++) {
			if (Game.mapWithInfo.route[i].street == Game.mapWithInfo.route[location].street) {
				int oldOwner = Game.mapWithInfo.route[i].owner;
				Game.players[oldOwner].cells[i] = false;
				Game.players[oldOwner].cash += Game.mapWithInfo.route[i].price * Game.mapWithInfo.route[i].level * 3 / 2;
				Game.players[oldOwner].property -= Game.mapWithInfo.route[i].price * Game.mapWithInfo.route[i].level;
				Game.mapWithInfo.route[i].owner = 0;
				Game.mapWithInfo.route[i].icon = Vocab.CellIcon[0];
			}
		}
	}
	
	private void monster() {
		int location = Game.players[Game.currentPlayer].location;
		for (int i = 0; i < Map.length; i++) {
			if (Game.mapWithInfo.route[i].street == Game.mapWithInfo.route[location].street) {
				int oldOwner = Game.mapWithInfo.route[i].owner;
				int oldLevel = Game.mapWithInfo.route[i].level;
				Game.mapWithInfo.route[i].level = 0;
				Game.players[oldOwner].property -= Game.mapWithInfo.route[i].price * (oldLevel - Game.mapWithInfo.route[i].level);
			}
		}
	}
	
	private void cashGod() {
		Game.players[Game.currentPlayer].cash += 10000;
		Game.players[Game.currentPlayer].fineFreeRound += 8;
	}
	
	private void luckyGod() {
		Game.players[Game.currentPlayer].getItem(Helper.rand(10) + 1);
		Game.players[Game.currentPlayer].fineFreeRound += 8;
	}
	
	/** Lottery cell */
	private void lottery() {
		int n = Helper.rand(10);
		if (n == 0) {
			Window.showLotteryInfo(1);
			Game.players[Game.currentPlayer].cash += 10000;
			Window.showGetInfo(Game.currentPlayer, 10000 + Vocab.PlayersInfoListHead[2]);
		} else if (1 <= n && n <= 2) {
			Window.showLotteryInfo(2);
			Game.players[Game.currentPlayer].cash += 4000;
			Window.showGetInfo(Game.currentPlayer, 4000 + Vocab.PlayersInfoListHead[2]);
		} else if (3 <= n && n <= 5) {
			Window.showLotteryInfo(3);
			Game.players[Game.currentPlayer].cash += 2500;
			Window.showGetInfo(Game.currentPlayer, 2500 + Vocab.PlayersInfoListHead[2]);
		} else {
			Window.showLotteryInfo(0);
		}
	}
	
	/** News cell */
	private void news() {
		int n = Helper.rand(Vocab.News.length);
		switch (n) {
		case 0: rewardMaxCellPlayer(); break;
		case 1: rewardMinCellPlayer(); break;
		case 2: interestAll(); break;
		case 3: taxAll(); break;
		case 4: itemAll(); break;
		default: 
		}
	}
	
	// Following methods define different news
	private void rewardMaxCellPlayer() {
		int reward = 100 * (Helper.rand(13) + 8);
		int player = findMaxCellPlayer();
		Game.players[player].cash += reward;
		Window.showNewsReport(0, player, reward);
	}

	private void rewardMinCellPlayer() {
		int reward = 100 * (Helper.rand(13) + 8);
		int player = findMinCellPlayer();
		Game.players[player].cash += reward;
		Window.showNewsReport(1, player, reward);
	}

	private void interestAll() {
		Window.showNewsReport(2, 0, 0);
		for (int i = 1; i < Game.players.length; i++) {
			Game.bank.interest(i);
		}
	}

	private void taxAll() {
		Window.showNewsReport(3, 0, 0);
		for (int i = 1; i < Game.players.length; i++) {
			Game.bank.tax(i,1);
		}
	}
	
	private void itemAll() {
		Window.showNewsReport(4, 0, 0);
		for (int i = 1; i < Game.players.length; i++) {
			int randItem = Helper.rand(10) + 1;
			Game.players[i].items[randItem] += 1;
			Window.showGetInfo(i, Vocab.ItemName[randItem]);
		}
	}
	
	// Following is methods that needed to form all former methods 
	private boolean[] findPlayers(int radius, boolean includeSelf) {
		int location = 0;
		boolean[] playersExist = new boolean[Game.players.length];
		for (int i = - radius; i < radius + 1; i++) {
			location = Helper.ensure(Game.players[Game.currentPlayer].location + i);
			for (int j = 1; j < Game.players.length; j++) {
				if (Game.players[j].location == location) {
					playersExist[j] = true;
				}
			}
		}
		if (includeSelf) {
			playersExist[Game.currentPlayer] = false;
		}
		return playersExist;
	}
	
	private int findMinCellPlayer() {
		int player = 1;
		for (int i = 1; i < Game.players.length; i++) {
			if (Game.players[i].property < Game.players[player].property) {
				// if 2 players equal,return first player
				player = i;
			}
		}
		return player;
	}
	
	private int findMaxCellPlayer() {
		int player = 1;
		for (int i = 1; i < Game.players.length; i++) {
			if (Game.players[i].property >= Game.players[player].property) {
				// if 2 players equal,return last player
				player = i;
			}
		}
		return player;
	}
}
