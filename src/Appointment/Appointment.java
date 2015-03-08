package Appointment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.sql.*;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import calendar.Database;
import user.User;

public class Appointment extends Database {
	
	private int appointmentID;
	private int calendarID;
	private User owner;
	private String title;
	private Timestamp start;
	private Timestamp end;
	private Room room;
	private int priority;
	private String description; 
	private int maxParticipants;
	private Timestamp alarm;
	
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public ArrayList<User> participants;
		
	public Appointment(int appointmentID) throws Exception {
		if (appointmentIdExists(appointmentID)) {
			try {
				openConn();
				preparedStatement = connect.prepareStatement("select * from Appointment WHERE AppointmentID=?");
				preparedStatement.setInt(1, appointmentID);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					this.calendarID = resultSet.getInt("CalendarID");
					this.owner = new User(resultSet.getString("Owner"));
					this.title = resultSet.getString("Title");
					this.start = resultSet.getTimestamp("Start");
					this.end = resultSet.getTimestamp("End");
					this.room = new Room(resultSet.getString("Room_name"));
					this.priority = resultSet.getInt("Priority");
					this.description = resultSet.getString("Description");
					this.maxParticipants = resultSet.getInt("Max_participants");
					this.alarm = resultSet.getTimestamp("Alarm");
				}
				preparedStatement = connect.prepareStatement("select User from Invited WHERE appoinmentID=?");
				preparedStatement.setInt(1,appointmentID);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					participants.add(new User(resultSet.getString("Owner")));
				}
			} finally {
				closeConn();
			}
			this.appointmentID = appointmentID;
		}
		else System.out.println("Brukernavnet eksisterer ikke!");
	}
	
	public Appointment(int calendarID, User owner, String title, String start, String end,  Room room, int priority, String description, int maxPartisipants, String alarm) throws Exception {
		try {
			if(isValidTimestamp(start) && isValidTimestamp(end)){
			}else throw new IllegalArgumentException("Either the name or username is invalid");  

			openConn();
			preparedStatement = connect.prepareStatement("insert into Appointment values (default,?,?,?,?,?,?,?,?,?)");
			preparedStatement.setInt(1,calendarID);
			preparedStatement.setString(2,owner.getUsername());
			preparedStatement.setTimestamp(3,Timestamp.valueOf(start));
			preparedStatement.setTimestamp(4,Timestamp.valueOf(end));
			preparedStatement.setString(5,room.getRoomName());
			preparedStatement.setInt(6, priority);
			preparedStatement.setString(7, description);
			preparedStatement.setInt(8, maxPartisipants);
			preparedStatement.setTimestamp(9,Timestamp.valueOf(alarm));
			preparedStatement.executeUpdate();
			} finally {
				closeConn();
			}
			this.calendarID = calendarID;
			this.owner = owner;
			this.title = title;
			this.start = Timestamp.valueOf(start);
			this.start = Timestamp.valueOf(end);
	}
	
	public boolean isValidTimestamp(String time) throws java.text.ParseException {
	    SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
	       try {
	    	   format.parse(time);
	           Pattern p = Pattern.compile("^\\d{4}[-]?\\d{1,2}[-]?\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}[.]?\\d{1,6}$");
	           return p.matcher(time).matches();
	       } catch (ParseException e) {
	           return false;
	       }
	}
	
	public boolean appointmentIdExists(int appointmentID) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select AppointmentID from Appointment WHERE appoinmentID=?");
			preparedStatement.setInt(1,appointmentID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return true;
			}
		} finally {
			closeConn();
		}
		return false;
	}
	
	//Gettere
	public int getAppointmentID() {
		return appointmentID;
	}
	public int getCalendarID() {
		return calendarID;
	}
	public String getTitle() {
		return title;
	}
	public User getOwner() {
		return owner;
	}
	public Timestamp getStartTime() {
		return start;
	}
	public Timestamp getEndTime() {
		return end;
	}
	public Room getRoom() {
		return room;
	}
	public int getPriority() {
		return priority;
	}
	public String getDescription() {
		return description;
	}
	public int maxParticipants() {
		return maxParticipants;
	}
	public Timestamp getAlarm() {
		return alarm;
	}
	public ArrayList<User> getParticipants() {
		return participants;
	}
	
	//

	//Settere
	public void setDescription(String description) {
		this.description = description;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public void setParticipants(ArrayList<User> participants) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Invited values (?,?,null,0,null)");
			preparedStatement.setInt(2,appointmentID);
			for (int i = 0; i < participants.size(); i++) {
				preparedStatement.setString(1,participants.get(i).getUsername());
				preparedStatement.executeUpdate();
			}
		} finally {
			closeConn();	
		}
	}

	public void deleteAppointment() {
		
	}
}
