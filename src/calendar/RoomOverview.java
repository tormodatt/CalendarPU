package calendar;

import java.util.ArrayList;

public class RoomOverview {
	
	public ArrayList<Room> allRooms; 
	public Room room; 
	
	public RoomOverview() {
		this.allRooms = new ArrayList<Room>(); 		
	}
	
	public ArrayList<Room> getRoom(){
		return this.allRooms; 
	}

	public void addRoom(Room room) {
		if (allRooms.contains(room)) {
			throw new IllegalArgumentException("Rom er allerede lagt til i oversikten"); 
		} else {
			allRooms.add(room); 
		}
	}
	
	public void removeRom(Room room) {
		if (allRooms.contains(room)) {
			allRooms.remove(room); 
		} else {
			throw new IllegalArgumentException("Rom finnes ikke i oversikten");
		}
	}
	

}
