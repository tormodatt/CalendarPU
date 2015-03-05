package Appointment;

import java.util.ArrayList;
import java.sql.Timestamp;

import user.User;

public class Appointment {
	
	private int appointmentID;
	private int calendarID;
	private User leader;
	
	private String title; 
	private Time time;
	private Room room;
	private int priority;
	private String description; 
	private int maxPartisipants;
	private Date alarm;
	
	public ArrayList<User> participants;
	
	public Appointment(int calendarID, User leader, String title, Room room, int priority, String description, int maxPartisipants, Date alarm) {
	}
	
	public Time getTime() {
		return this.time;
	}

	public void setTime(Date date, int startHour, int startMin, int duration) {
		this.time = new Time(date, startHour, startMin, duration);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Room getPlace() {
		return place;
	}
	public void setPlace(Room place) {
		this.place = place;
	}
	public ArrayList<User> getParticipants() {
		return participants;
	}
	public void setParticipants(ArrayList<User> Participants) {
		this.participants = participants;
	} 
	

}
