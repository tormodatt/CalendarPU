package Appointment;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.util.StringTokenizer;
import java.sql.*;

import calendar.Database;

public class RoomOverview extends Database {
	
	private ArrayList<Room> allRooms; 
	
	
	public RoomOverview() throws Exception {
		this.allRooms = new ArrayList<Room>();
		
		try {
			openConn();
			PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM Room" );
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				
				this.allRooms.add( new Room( resultSet.getString("RoomName") ) );
			}
			} finally {
				closeConn();
			}
		} 

	public ArrayList<Room> getAllRooms(){
		return this.allRooms; 
	}
	
	public ArrayList<Room> getFreeRooms(Time time) throws Exception {
		
		ArrayList<Room> freeRooms = new ArrayList<Room>();
		// denne metoden kan ikke lages ferdig før vi har funnet ut av hvordan vi skal behandle tid
		
//		try {
//			openConn();
//			PreparedStatement preparedStatement = connect.preparedStatement(SELECT * From Appointment)
//			
//		} finally {
//			closeConn();
//		}
		// Må skrive SQL som henter ut alle rom som er ledige i den aktuelle tidsperioden. 
		return freeRooms;
	}
	
	
	public String toString() {
		String roomString = "";
		for (Room room : allRooms) {
			roomString = roomString + room.getRoomName() + " ";
		}
		roomString = roomString.substring(0, roomString.length() - 1);
		return roomString;
	}

}
