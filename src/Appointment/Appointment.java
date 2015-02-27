package Appointment;

import java.util.ArrayList;
import java.util.Date;

import user.User;

public class Appointment {
	
	public String title; 
	public Time time;  
	public String description; 
	public int priority; 
	public Room place; 
	public ArrayList<User> participants;
	
	public Appointment(String title, Time time, String description, int priority, Room place) {
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
