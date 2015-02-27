package Appointment;

import java.util.ArrayList;

public class Room {
	
	public String roomID; 
	public int capacity;
	public Appointment appointment; 
	public ArrayList<Appointment> appointments;
	
	public Room(String roomID, int antallPlasser) {
		this.roomID = roomID;
		this.capacity = capacity; 
		this.appointments = new ArrayList<Appointment>(); 
	}

	public ArrayList<Appointment> getAppointment() {
		return appointments;
	}
	
	public String getRoomID() {
		return roomID;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID; 
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity; 
	}
	
	public void addAppointment(Appointment appointment) {
		if (appointments.contains(appointment)) {
			throw new IllegalArgumentException("Avtalen skal allerede finnes sted i rommet"); 
		} else {
			appointments.add(appointment); 
		}
	}
	
	public void removeAppointment(Appointment appointment) {
		if (appointments.contains(appointment)) {
			 appointments.remove(appointment);
		} else {
			throw new IllegalArgumentException("Avtalen er ikke planlagt i dette rommet");
		}
	}
	
	
	
	

}
