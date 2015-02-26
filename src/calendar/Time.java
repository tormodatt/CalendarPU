package calendar;

import java.util.Date;

public class Time {
	
	public Date date; 
	public int startHour; 
	public int startMin; 
	public int duration; 
	
	public Time(Date date, int startHour, int startMin, int duration) {
		this.date = date;
		this.startHour = startHour;
		this.startMin = startMin; 
		this.duration = duration; 
	}
}
