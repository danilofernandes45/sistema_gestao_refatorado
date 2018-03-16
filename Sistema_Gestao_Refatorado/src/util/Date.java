package util;
public class Date {
	private int month;
	private int day;
	private int year;
	
	public Date() {
		month = 1;
		day = 1;
		year = 2018;
	}
	
	public boolean setDate(String date) {
		
		boolean result = false;
		
		String[] dateS = date.split("/");
		
		if( dateS.length == 3 && isValid(date) ) {
			
			result = true;
			
			int day = Integer.valueOf( dateS[0] );
			int month = Integer.valueOf( dateS[1] );
			int year = Integer.valueOf( dateS[2] );
			
			int[] dayPerMonth = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
			
			if( month <= 0 || month > 12 || day <= 0 || day > dayPerMonth[month])
				result = false;
			if( day == 29 && month == 2 && year%4 == 0 && ( year%100 != 0 || year%400 == 0) )
				result = true;
			
			if(result) {
				this.month = month;
				this.day = day;
				this.year = year;
			}
			
		}
		
		return result;
		
	}
	
	public boolean isValid(String date) {
		
		for(int i=0; i<date.length(); i++) {
			if(date.charAt(i) < 47 || date.charAt(i) > 57)
				return false;
		}
		return true;
		
	}
	
	public int getMonth() {
		return month;
	}
	public int getDay() {
		return day;
	}
	public int getYear() {
		return year;
	}	
	
}
