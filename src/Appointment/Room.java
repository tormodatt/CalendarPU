package Appointment;

import java.util.ArrayList;

public class Room {
	
	public String roomName; // skal ikke kunne endres
	public int capacity;
	public ArrayList<Appointment> appointments;
	
	
	public Room(String roomName, int antallPlasser) {
		this.roomName = roomName;
		this.capacity = capacity; // antall plasser 
		this.appointments = new ArrayList<Appointment>(); // må bruke SQL til å finne alle avtalene som er relatert til rommet, og de legges inn i denne
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
	
	
	
	

}
