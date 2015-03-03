package calendar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import user.*;
import calendar.*;
import Appointment.*;

public class Calendar extends Database {

	public ArrayList<Appointment> appointments;
	private User user;
	private Group group;
	private int calendarID;
	private String title;
	
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	//Opprette personlig kalender
	public Calendar(User user, String title) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Calendar (Title,User_Username) values (?,?)");
			preparedStatement.setString(1,title);
			preparedStatement.setString(2,user.getUsername());
			preparedStatement.executeUpdate();
			} finally {
			closeConn();
			}
		this.user = user; 
	}
	
	//Opprette gruppekalender
	public Calendar(Group group, String title) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Calendar (Title,Group_GroupID) values (?,?)");
			preparedStatement.setString(1,title);
			preparedStatement.setString(2,group.getGroupID());
			preparedStatement.executeUpdate();
			} finally {
			closeConn();
			}
		this.group = group; 
	}
	
	//Hente personlig kalender
	public Calendar(User user) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select * from Calendar WHERE User_Username=?");
			preparedStatement.setString(1,user.getUsername());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
					this.calendarID = resultSet.getInt("CalendarID");
					this.title = resultSet.getString("Title");
				}
			setAppointments();
		} finally {
				closeConn();
		}
		this.user = user;
	}

	//Hente gruppekalender
	public Calendar(Group group) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select * from Calendar WHERE Group_GroupID=?");
			preparedStatement.setString(1,group.getGroupID());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
					this.calendarID = resultSet.getInt("CalendarID");
					this.title = resultSet.getString("Title");
			}
			setAppointments();
		} finally {
				closeConn();
		}
		this.group = group; 
	}
	
	public void setAppointments() {
		preparedStatement = connect.prepareStatement("select AppointmentID from Appointment WHERE CalendarID = ?");
		preparedStatement.setInt(1, calendarID);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
				//Mangler skikkelig konstrukt¿r i Appointmentklassen
				this.appointments.add(new Appointment(resultSet.getInt("AppointmentID")));
		}
	}
	

}
