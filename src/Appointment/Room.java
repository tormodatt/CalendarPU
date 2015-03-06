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
		if(isValidName(roomName) && isValidLocation(location) && isValidCapasity(capacity)){
			this.roomName = roomName;
			this.capacity = capacity; // antall plasser 
			this.location = location;
		}else throw new IllegalArgumentException("The input is not valid");
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

	private boolean isValidName(String name){ //sjekker at romnavn kune inneholder bokstaver og tall
		int index = 0;
		String str = name.toLowerCase(); 
		for (int i = 0 ; i < str.length(); ++i){
			char c = str.charAt(i); 
			if (c <= 'z' || c >= 'a' || c == 'æ' || c == 'ø' || c == 'å' || c <= 9 || c >=0){ 
				index++; 
			}
			else return false; 
		}
		if (index < 3) return false; 
		return true; 
	}
	private boolean isValidLocation(String location){ //sjekker at lokasjonen kun inneholder bokstaver, tall og bindestrek
		int index = 0; 
		String str = location.toLowerCase(); 
		for (int i = 0; i<str.length(); ++i){
			char c = str.charAt(i); 
			if (c <= 'z' || c >= 'a' || c == 'æ' || c == 'ø' || c == 'å' || c <= 9 || c >=0 || c == '-'){ 
				index++; 
			}
			else return false; 
		}
		if (index < 3) return false; 
		return true; 

	}
	private boolean isValidCapasity(int capasity){ //sjekker at kapasiteten ikke er negativ
		if (capasity < 0) return false; 
		return true; 
	}

}
