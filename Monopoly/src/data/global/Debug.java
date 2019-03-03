package data.global;

import java.util.Calendar;

import data.module.Helper;

public class Debug {
	
	public static int step = 0;
	
	public static void run() {
		String cmd = Helper.getStr();
		if (cmd.matches("cash@[0-9]+")) {
			Game.players[Game.currentPlayer].cash += Integer.parseInt(cmd.split("@")[1]);
		}
		if (cmd.matches("time@[0-9]{1,2}@[0-9]{1,2}")) {
			Game.dateCalendar.set(2014, Integer.parseInt(cmd.split("@")[1]) - 1, Integer.parseInt(cmd.split("@")[2]));
		}
		if (cmd.matches("fly@[0-9]+")) {
			step = Integer.parseInt(cmd.split("@")[1]);
		}
		if (cmd.matches("coupon@[0-9]+")) {
			Game.players[Game.currentPlayer].coupon = Integer.parseInt(cmd.split("@")[1]);
		}
	}
	
	public static void auto() {
		
		if (Game.players[Game.currentPlayer].name.equals("zh") && Game.dateCalendar.get(Calendar.MONTH) == 11 && Game.dateCalendar.get(Calendar.DAY_OF_MONTH) == 15) {
			Game.players[Game.currentPlayer].cash += 10000;
			System.out.println("Happy birthday!");
		}
	}
}
