package MTool;

import java.util.Calendar;

public class MDate {
	
	Calendar calendar = Calendar.getInstance();
	String dateFormat = "%dƒÍ%2d‘¬%2d»’";
	
	public MDate() {
		
	}
	
	public MDate(int year, int month, int date) {
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
	
	public void nextDay() {
		calendar.add(Calendar.DAY_OF_YEAR, 1);
	}
	
	public String toString() {
		return String.format(dateFormat, getYear(), getMonth(), getDay());
	}
	
}
