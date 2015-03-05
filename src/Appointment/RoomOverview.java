package Appointment;

import java.util.ArrayList;

public class RoomOverview {
	
	public ArrayList<Room> allRooms; 
	
	
	public RoomOverview() {
		this.allRooms = new ArrayList<Room>(); 		
	}
	
	
	public ArrayList<Room> getRoom(){
		return this.allRooms; 
	}
	
	public ArrayList<Room> getFreeRooms(Time time) {
		ArrayList<Room> freeRooms = new ArrayList<Room>();
		// Må skrive SQL som henter ut alle rom som er ledige i den aktuelle tidsperioden 
		return freeRooms; //returnerer liste med ledige rom 
	}

}
