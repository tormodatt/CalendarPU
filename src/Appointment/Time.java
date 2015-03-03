package Appointment;

import java.util.Date;

public class Time {
	
	public Date dateC; 
	public int startHour; 
	public int startMin; 
	public int duration; 
	
	public Time(int year, int month, int date, int startHour, int startMin, int duration) {
		setDate(year, month, date, startHour, startMin); 
		this.duration = duration; 
	}

	public void setDate(int year, int month, int date, int hours, int minutes) {
			this.dateC = new Date(year, month, date, hours, minutes); 
	}

	public Date getDate() {
		return dateC;
	}

	public int getDuration() {
		return duration;
	}
	
	public String toString() {
		return "Date: " + this.getDate() + "\n" + "Duration: " + this.getDuration() + " min"; 
	}
	
	public static void main(String[] args) {
		Time time = new Time(2015, 03, 23, 17, 30, 60);
		System.out.println(time);
	}
}
