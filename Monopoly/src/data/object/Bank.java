package data.object;

import data.global.Game;
import data.module.*;

public class Bank {
	
	/** Show greeting and prompt player to choose */
	public void greeting() {
		Window.showCellGreeting(2);
		boolean flag = true;
		while (flag) {
			System.out.printf(Vocab.bankPrompt, Game.players[Game.currentPlayer].cash, Game.players[Game.currentPlayer].deposit);
			switch (Helper.getInt(0,2)) {
			case 0: save(); break;
			case 1: get();  break;
			case 2: flag = false;
			}	
		}
	}
	
	public void save() {
		while (true) {
			System.out.print(Vocab.bankSavePrompt);
			int n = Helper.getInt(0,2147483647);
			if (n <= Game.players[Game.currentPlayer].cash) {
				Game.players[Game.currentPlayer].cash -= n;
				Game.players[Game.currentPlayer].deposit += n;
				Window.showErrorInfo(Vocab.NoError);
				break;
			} else {
				Window.showErrorInfo(Vocab.LackOfCashError);
			}
		}
	}

	public void get() {
		while (true) {
			System.out.print(Vocab.bankGetPrompt);
			int n = Helper.getInt(0,2147483647);
			if (n <= Game.players[Game.currentPlayer].deposit) {
				Game.players[Game.currentPlayer].cash += n;
				Game.players[Game.currentPlayer].deposit -= n;
				Window.showErrorInfo(Vocab.NoError);
				break;
			} else {
				Window.showErrorInfo(Vocab.LackOfDepositError);
			}
		}
	}
	
	public void interest(int player) {
		int interest = Game.players[player].deposit / 10;
		Game.players[player].deposit += interest;
		Window.showGetInfo(player, interest + Vocab.PlayersInfoListHead[3] + "ÀûÏ¢");
	}
	
	public void tax(int player, int times) {
		int tax = Game.players[player].deposit / 10 * times;
		Game.players[player].deposit -= tax;
		Window.showLossInfo(player, 3, tax);
	}
}
