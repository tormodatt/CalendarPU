package calendar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import user.*;
import Appointment.*;

public class Calendar extends Database {

	private int calendarID;
	private String title;
	private User user;
	private Group group;
	private boolean ownerIsGroup;

	private ArrayList<Appointment> appointments = new ArrayList<Appointment>();

	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	//Opprette personlig kalender
	public Calendar(User user, String title) throws Exception {
		try {
			if(isValidTitle(title)){
				openConn();
				preparedStatement = connect.prepareStatement("insert into Calendar (Title, Username, GroupID) values (?,?,null)",PreparedStatement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1,title);
				preparedStatement.setString(2,user.getUsername());
				preparedStatement.executeUpdate();
				ResultSet rs = preparedStatement.getGeneratedKeys();
				while (rs.next()) {
					this.calendarID = rs.getInt(1);
				}
			}else throw new IllegalArgumentException("The calendars title is not valid"); 
		} finally {
			closeConn();
		}
		this.user = user; 
		this.title = title; 
		ownerIsGroup = false;
	}

	//Opprette gruppekalender
	public Calendar(Group group, String title) throws Exception {

		try {
			if(isValidTitle(title)){
				openConn();
				preparedStatement = connect.prepareStatement("insert into Calendar (Title, Username,GroupID) values (?,null,?)",PreparedStatement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1,title);
				preparedStatement.setInt(2,group.getGroupID());
				preparedStatement.executeUpdate();
				resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					this.calendarID = resultSet.getInt(1);
				}
			}else throw new IllegalArgumentException("The title is not valid");
		}finally {
			closeConn();
		}
		this.group = group; 
		this.title = title; 
		ownerIsGroup = true;
	}

	//Hente personlig kalender
	public Calendar(User user) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select * from Calendar WHERE Username=?");
			preparedStatement.setString(1,user.getUsername());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				this.calendarID = resultSet.getInt("CalendarID");
				this.title = resultSet.getString("Title");
			}
			this.user = user;
			ownerIsGroup = false;
			setAppointments();
		} finally {
			closeConn();
		}
	}

	//Hente gruppekalender
	public Calendar(Group group) throws Exception {
		this.group = group; 
		this.user = group.getLeader();
		ownerIsGroup = true;
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select * from Calendar WHERE GroupID=?");
			preparedStatement.setInt(1,group.getGroupID());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				this.calendarID = resultSet.getInt("CalendarID");
				this.title = resultSet.getString("Title");
			}
			setAppointments();
		} finally {
			closeConn();
		}
	}

	public Calendar(int calendarID) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select * from Calendar WHERE CalendarID=?");
			preparedStatement.setInt(1,calendarID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				this.calendarID = calendarID;
				this.title = resultSet.getString("Title");
				this.user = new User(resultSet.getString("Username"));
				this.group = new Group(resultSet.getInt("GroupID"),user);
			}
			setAppointments();
		} finally {
			closeConn();
		}
	}
	
	//Gettere
	public int getCalendarID() {
		return calendarID;
	}
	public String getTitle() {
		return title;
	}
	public User getUser() {
		return user;
	}
	public Group getGroup() {
		return group;
	}
	
	public boolean ownerIsGroup() {
		return ownerIsGroup;
	}
	
	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}

	//Settere
	public void setAppointments() throws Exception {
		preparedStatement = connect.prepareStatement("select AppointmentID from Appointment WHERE CalendarID=?");
		preparedStatement.setInt(1, getCalendarID());
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			this.appointments.add(new Appointment(resultSet.getInt("AppointmentID"),this));
		}
	}
	
	//asdf
	public void addAppointment(Appointment appointment) {
		appointments.add(appointment);
	}
	
	public void removeAppointment(Appointment appointment) {
		appointments.remove(appointment);
	}

	//Valideringsmetoder
	private static boolean isValidTitle(String title){
		String str = title.toLowerCase(); 
		int index = 0; 
		for (int i = 0; i < str.length();  ++i){
			char c = str.charAt(i); 
			if((c <= 'z' && c>='a') || (c >= '0' && c <= '9') || c == '�' || c == '�' || c== '�' || c == '-' || c == ' '){
				++index; 
			}else return false; 
		}
		if(index < 3) return false; 
		return true; 
	}

}
