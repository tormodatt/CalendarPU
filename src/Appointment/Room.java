package Appointment;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import java.sql.*;
import java.util.ArrayList;

import calendar.Database;
import calendar.Calendar;

public class Room extends Database {

	private String roomName; // skal ikke kunne endres
	private int capacity;
	private String location;
	private ArrayList<Appointment> appointments;
	
	private PreparedStatement preparedStatement; // bruker dette feltet til å skrive til databasen
	private ResultSet resultSet;
	
	


	public Room(String roomName) throws Exception { //Konstruktør for å opprette et rom-objekt som allerede eksisterer i databasen 
		if (roomNameExists(roomName)) {
			try {
				openConn();
				preparedStatement = connect.prepareStatement("select * from Room WHERE Name='"+roomName+"'");
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					this.roomName = resultSet.getString("Name");
					this.capacity = resultSet.getInt("Capacity");
					this.location = resultSet.getString("Location");
				}
			} finally {
				closeConn();
			}
		} else {
			throw new IllegalArgumentException("The room " + roomName + " does not exist." );
		}
	}
	
	public boolean roomNameExists(String roomName) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select Name from all_s_gr46_calendar.Room WHERE Name = ?");
			preparedStatement.setString(1, roomName);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return true;
			}
		} finally {
			closeConn();
		}
		return false;
	}
		
	

	public Room(String roomName, int capacity, String location) throws Exception { // Konstruktør for å legge til et nytt rom i databasen
		if(isValidName(roomName) && ! roomNameExists(roomName) && isValidLocation(location) && isValidCapasity(capacity)){
			setCredencials(roomName, capacity, location);
			this.roomName = roomName;
			this.capacity = capacity;
			this.location = location;
			}  else throw new IllegalArgumentException("The input is not valid or roomName already exists");
		// Må ha kode som legger til rommet i databasen
	}
	
	public void setCredencials(String roomName, int capacity, String location) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Room values (?,?, ?)");
			preparedStatement.setString(1, roomName);
			preparedStatement.setInt(2, capacity);
			preparedStatement.setString(3, location);
			preparedStatement.executeUpdate();		
		} finally {
			closeConn();
		}
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
			if ((c <= 'z' && c >= 'a') || c == 'æ' || c == 'ø' || c == 'å' || (c <= 9 && c >=0)){ 
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
			if ((c <= 'z' && c >= 'a') || c == 'æ' || c == 'ø' || c == 'å' || (c <= 9 && c >=0) || c == '-'){ 
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
public static void main(String[] args) throws Exception{
	Room polse = new Room("polse##", 10, "U2-1"); 
	System.out.println(polse.getRoomName());
}

}
