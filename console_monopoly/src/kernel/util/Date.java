package kernel.util;

import java.util.Calendar;

public class Date {
	
	Calendar calendar = Calendar.getInstance();
	String dateFormat = "%d年%2d月%2d日星期%s";

	public Date() {
		
	}
	
	public Date(int year, int month, int date) {
		setDate(year, month, date);
	}
	
	public void setDate(int year, int month, int date) {
		calendar.set(year, month - 1, date);
	}
	
	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}
	
	// month should + 1 to represent true month-num
	public int getMonth() {
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	public int getDay() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public boolean isEndOfMonth() {
		return getDay() == calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public boolean isEndOfYear() {
		return calendar.get(Calendar.DAY_OF_YEAR) == calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
	}
	
	public boolean isWeekend() {
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}
	
	public void nextDay() {
		calendar.add(Calendar.DAY_OF_YEAR, 1);
	}
	
	public String toString() {
		return String.format(dateFormat, getYear(), getMonth(), getDay(), getDayOfWeek());
	}

	private String getDayOfWeek() {
		return String.valueOf("日一二三四五六".charAt(calendar.get(Calendar.DAY_OF_WEEK) - 1));
	}
	

}
