package util;

public class Time {
	private int hour;
	private int minute;
	
	public Time() {
		hour = 0;
		minute = 0;
	}
	
	public boolean setTime(String time) {
		
		boolean result = false;
		String[] timeS = time.split(":");
		
		if(timeS.length == 2 && isValid(time)) {
			
			result = true;
			
			int hour = Integer.valueOf( timeS[0] );
			int minute = Integer.valueOf( timeS[1] );
			
			if(hour > 23 || hour < 0 || minute < 0 || minute > 59)
				result = false;
			
			else {
				this.hour = hour;
				this.minute = minute;
			}
			
		}
		
		return result;
		
	}
	
	public boolean isValid(String time) {
		
		for(int i=0; i<time.length(); i++) {
			if(time.charAt(i) < 48 || time.charAt(i) > 58)
				return false;
		}
		return true;
		
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

}
