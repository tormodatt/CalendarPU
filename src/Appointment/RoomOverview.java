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
	
	private ArrayList<Room> freeRooms;
	private Timestamp startTime;
	private Timestamp endTime;
	private int capacity;
	
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	
	public RoomOverview(Timestamp startTime, Timestamp Endtime, int capacity) throws Exception {
		this.freeRooms = new ArrayList<Room>();
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.capacity = capacity - 1;
		
		try {
			openConn();
			preparedStatement = connect.prepareStatement("SELECT Name FROM Room WHERE Name not in (SELECT Room_name FROM Appointment WHERE ('"+this.startTime+"' > Start and '"+this.startTime+"' < End) or ('"+this.endTime+"' > Start and '"+this.endTime+"' < End)) AND Capacity > '"+this.capacity+"'" );
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.freeRooms.add( new Room( resultSet.getString("Name") ) );
			}
			} finally {
				closeConn();
			}
		} 

	
	
	public String toString() {
		String roomString = "Free rooms with sufficient capacity: ";
		for (Room room : freeRooms) {
			roomString = roomString + room.getRoomName() + " ";
		}
		roomString = roomString.substring(0, roomString.length() - 1);
		return roomString;
	}
	
	public static void main(String[] args) throws Exception {
		
		Timestamp start = Timestamp.valueOf("2010-01-01 12:55:00.000000");
		Timestamp end = Timestamp.valueOf("2010-01-02 10:00:00.000000");

		RoomOverview ov = new RoomOverview(start, end, 20);
		System.out.println(ov);
	}

}
