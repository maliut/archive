package data.module;

import java.util.Calendar;
import data.global.Game;

public class Time {
	
	public static String startDate = "2014-2-27";
	public static String nowStr = startDate;
	
  	public static void nextDay() {
  		Game.dateCalendar.add(Calendar.DAY_OF_YEAR,1);
  		Game.date = Game.dateCalendar.getTime();
  	}
  	
  	public static boolean endOfMonth() {
  		return Game.dateCalendar.get(Calendar.DAY_OF_MONTH) == Game.dateCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) ? true : false;
  	}
  	
  	public static String getFormattedDate() {
  		return Game.dateFormat.format(Game.date);
  	}
  	
  	public static boolean isEndOfYear() {
  		return (Game.dateCalendar.get(Calendar.YEAR) == 2015);
  	}
}
