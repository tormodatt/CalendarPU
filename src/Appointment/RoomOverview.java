package Appointment;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.util.StringTokenizer;
import java.sql.*;

import calendar.Database;

public class RoomOverview extends Database {
	
	private ArrayList<Room> allRooms; 
	
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	
	public RoomOverview() throws Exception {
		this.allRooms = new ArrayList<Room>();
		
		try {
			openConn();
			preparedStatement = connect.prepareStatement("SELECT * FROM Room" );
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.allRooms.add( new Room( resultSet.getString("Name") ) );
			}
			} finally {
				closeConn();
			}
		} 

	public ArrayList<Room> getAllRooms(){
		return this.allRooms; 
	}
	
	public ArrayList<Room> getFreeRooms(Timestamp startTime, Timestamp endTime, int minimumCapacity) throws Exception {
		
		
		ArrayList<Room> freeRooms = new ArrayList<Room>();
		// denne metoden kan ikke lages ferdig før vi har funnet ut av hvordan vi skal behandle tid
		
		try {
			openConn();
			preparedStatement = connect.prepareStatement( "SELECT Room_name From Appointment WHERE (((start > '"+startTime+"') and (start > '"+endTime+"')) or ((end < '"+startTime+"') and (end < '"+endTime+"')))");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("Room_name");
				for (Room room : allRooms) {
					if ( room.getRoomName().equals(name) && room.getCapacity() >= minimumCapacity ) {
						freeRooms.add(room);
					}
					
				}
			}
		} finally {
			closeConn();
		}
		return freeRooms;
	}
	
	public void addRoom(Room room) {
		this.allRooms.add(room);
	}
	
	
	public String toString() {
		String roomString = "";
		for (Room room : allRooms) {
			roomString = roomString + room.getRoomName() + " ";
		}
		roomString = roomString.substring(0, roomString.length() - 1);
		return roomString;
	}
	
	public static void main(String[] args) throws Exception {
		RoomOverview ov = new RoomOverview();
		System.out.println(ov);
		Timestamp start = Timestamp.valueOf("2010-01-01 12:55:00.000000");
		Timestamp end = Timestamp.valueOf("2010-01-02 10:00:00.000000");
		ov.getFreeRooms(start, end, 4);
	}

}
