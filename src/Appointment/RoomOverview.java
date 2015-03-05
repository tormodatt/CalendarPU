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
	
	public ArrayList<Room> allRooms; 
	
	
	public RoomOverview() throws Exception {
		this.allRooms = new ArrayList<Room>();
		
		try {
			openConn();
			PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM Room" );
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				
				this.allRooms.add( new Room( getString("RoomName") ) );
			}
			} finally {
				closeConn();
			}
		} 

	
	
	private String getString(String string) {
		// TODO Auto-generated method stub
		return null;
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
