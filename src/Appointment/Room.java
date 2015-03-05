package Appointment;

import java.util.ArrayList;

public class Room {
	
	public String roomName; // skal ikke kunne endres
	public int capacity;
	public String location;
	public ArrayList<Appointment> appointments;
	
	
	public Room(String roomName) { //Konstruktør for å opprette et rom-objekt som allerede eksisterer i databasen 
		this.roomName = roomName;
		this.capacity = capacity; // hentes fra databasen
		this.location = location; // hentes fra databasen
	}
	
	public Room(String roomName, int capacity, String location) {
		this.roomName = roomName;
		this.capacity = capacity; // antall plasser 
		this.location = location;
		// Må ha kode som legger til rommet i databasen
	}

	public ArrayList<Appointment> getAppointment() {
		return appointments;
	}
	
	public String getRoomName() {
		return roomName;
	}

	public int getCapacity() {
		return capacity;
	}

	
	public void setCapacity(int capacity) {
		if (capacity > -1) {
			this.capacity = capacity;
		} 
		else {
			throw new IllegalArgumentException("Capacity must be a positive integer.");
		}
	}
	
	public void addAppointment(Appointment appointment) {
		if (appointments.contains(appointment)) {
			throw new IllegalArgumentException("This room is already related to this appointment"); 
		} else {
			appointments.add(appointment); 
		}
	}
	
	public void removeAppointment(Appointment appointment) {
		if (appointments.contains(appointment)) {
			 appointments.remove(appointment);
		} else {
			throw new IllegalArgumentException("Room and appointment is not related");
		}
	}
	
	public String getLocation() {
		// Skrive SQL-spørring som henter ut location for dette rommet
		String loc = null; // hentes fra databasen
		return loc;
	}
	
	
	
	

}
