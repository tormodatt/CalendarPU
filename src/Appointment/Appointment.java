package Appointment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.sql.*;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import calendar.Calendar;
import calendar.Database;
import user.User;

public class Appointment extends Database {

	private int appointmentID;
	private Calendar calendar;
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

	private ArrayList<User> participants;

	//Hente avtale
	public Appointment(int appointmentID) throws Exception {
		if (appointmentIdExists(appointmentID)) {
			try {
				openConn();
				preparedStatement = connect.prepareStatement("select * from Appointment WHERE AppointmentID=?");
				preparedStatement.setInt(1, appointmentID);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					this.calendar = new Calendar(resultSet.getInt("CalendarID"));
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

	//Opprette avtale
	public Appointment(Calendar calendar, User owner, String title, String start, String end,  Room room, int priority, String description, int maxPartisipants, String alarm) throws Exception {
		try {
			if(isValidTimestamp(start) && isValidTimestamp(end) && isValidTitle(title)){
			}else throw new IllegalArgumentException("Either the name or username is invalid");  

			openConn();
			preparedStatement = connect.prepareStatement("insert into Appointment values (default,?,?,?,?,?,?,?,?,?)");
			preparedStatement.setInt(1,calendar.getCalendarID());
			preparedStatement.setString(2,owner.getUsername());
			preparedStatement.setTimestamp(3,Timestamp.valueOf(start));
			preparedStatement.setTimestamp(4,Timestamp.valueOf(end));
			preparedStatement.setString(5,room.getRoomName());
			preparedStatement.setInt(6, priority);
			preparedStatement.setString(7, description);
			preparedStatement.setInt(8, maxPartisipants);
			preparedStatement.setTimestamp(9,Timestamp.valueOf(alarm));
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				this.appointmentID = resultSet.getInt("NotificationID");
			}
		} finally {
			closeConn();
		}
		this.calendar = calendar;
		calendar.addAppointment(this);
		this.owner = owner;
		this.title = title;
		this.start = Timestamp.valueOf(start);
		this.start = Timestamp.valueOf(end);
	}

	//Endre avtale
	public void updateAppointment(Calendar calendar, User owner, String title, String start, String end,  Room room, int priority, String description, int maxParticipants, String alarm) throws Exception {
		try {
			if(isValidTimestamp(start) && isValidTimestamp(end)&& isValidTitle(title)){
			}else throw new IllegalArgumentException("Either the name or username is invalid");  

			openConn();
			preparedStatement = connect.prepareStatement("update Appointment SET CalendarID=?,Username=?,Start=?,End=?,Room_name=?,Priority=?,Description=?,Max_participants=?,Alarm=? WHERE AppointmentID=?");
			preparedStatement.setInt(1,calendar.getCalendarID());
			preparedStatement.setString(2,owner.getUsername());
			preparedStatement.setTimestamp(3,Timestamp.valueOf(start));
			preparedStatement.setTimestamp(4,Timestamp.valueOf(end));
			preparedStatement.setString(5,room.getRoomName());
			preparedStatement.setInt(6, priority);
			preparedStatement.setString(7, description);
			preparedStatement.setInt(8, maxParticipants);
			preparedStatement.setTimestamp(9,Timestamp.valueOf(alarm));
			preparedStatement.setInt(10,getAppointmentID());
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}
		this.calendar.removeAppointment(this);
		calendar.addAppointment(this);
		this.calendar = calendar;
		this.owner = owner;
		this.title = title;
		this.start = Timestamp.valueOf(start);
		this.end = Timestamp.valueOf(end);
		this.room = room;
		this.priority = priority;
		this.description = description;
		this.maxParticipants = maxParticipants;
		this.alarm = Timestamp.valueOf(alarm);

		//Notify participants
		for (int i = 0; i < participants.size(); i++) {
			new Notification("Your appiontment has changed","The appointment "+getTitle()+" scheduled at "+getStartTime()+" has changed",participants.get(i));
		}
	}

	//Slette avtale
	public void deleteAppointment() throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("delete from Appointment where AppointmentID=?");
			preparedStatement.setInt(1,getAppointmentID());
			preparedStatement.executeUpdate();
			calendar.removeAppointment(this);
		}
		finally {
			closeConn();
		}
		//TO DO: Slette objekt
	}

	public void hideAppointment(User user) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("update Invited set Hidden=1 where User=? and AppointmentID=?");
			preparedStatement.setString(1,user.getUsername());
			preparedStatement.setInt(2,appointmentID);
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}
	}
	//TO DO: Gjemme avtale

	public void addParticipant(User user) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Invited values (?,?,null,0,null)");
			preparedStatement.setString(1,user.getUsername());
			preparedStatement.setInt(2,appointmentID);
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}
		participants.add(user);
	}

	public void removeParticipant(User user, boolean owner) throws Exception {
		if (!owner) {
			String name = user.getFirstname()+" "+user.getLastname();
			new Notification("A user has declined your appointment","The user "+name+" is no longer availible to your event "+getTitle()+" shcedueled at "+getStartTime(),getOwner());
			for (int i = 0; i < participants.size(); i++) {
				if (participants.get(i) != user) {
					new Notification("A participant opted out form an appointment you are going to","The user, "+name+" is no longer participating at the event "+ getTitle() + " schedueled at " +getStartTime(),participants.get(i));
				}
			}
		};
		try {
			openConn();
			preparedStatement = connect.prepareStatement("delete from Invited where Username=?,AppointmentID=?");
			preparedStatement.setString(1,user.getUsername());
			preparedStatement.setInt(2,appointmentID);
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}
		participants.remove(user);
	}

	public String getParticipantsStatus() throws Exception {
		int unseen=0,rejected=0,confirmed=0;

		try {
			openConn();
			preparedStatement = connect.prepareStatement("select sum(Confirmed=1) as Confirmed, sum(Confirmed=0) as Rejected, sum(Confirmed is null) as Unseen from Invited where AppointmentID=?");
			preparedStatement.setInt(1, getAppointmentID());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				unseen = resultSet.getInt("Unseen");
				rejected = resultSet.getInt("Rejected");
				confirmed = resultSet.getInt("Confirmed");
			}
		} finally {
			closeConn();
		}
		return "Unseen: "+unseen+"\nRejected: "+rejected+"\nConfirmed: "+confirmed;
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
	public Calendar getCalendar() {
		return calendar;
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
	public int getMaxParticipants() {
		return maxParticipants;
	}
	public Timestamp getAlarm() {
		return alarm;
	}
	public ArrayList<User> getParticipants() {
		return participants;
	}

	//Settere
	public void setAlarm(String alarm, User user) throws Exception {
		if (owner.getUsername()==user.getUsername()) {
			try {
				openConn();
				preparedStatement = connect.prepareStatement("update Appointment set Alarm=? where AppointmentID=?");
				preparedStatement.setTimestamp(1, Timestamp.valueOf(alarm));
				preparedStatement.setInt(2,getAppointmentID());
				preparedStatement.executeQuery();
			} finally {
				closeConn();
			}
		} else {
			try {
				openConn();
				preparedStatement = connect.prepareStatement("update Invited set Alarm=? where AppointmentID=? and Username=?");
				preparedStatement.setTimestamp(1, Timestamp.valueOf(alarm));
				preparedStatement.setInt(2,getAppointmentID());
				preparedStatement.setString(3, user.getUsername());
				preparedStatement.executeQuery();
			} finally {
				closeConn();
			}
		}
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
		this.participants = participants;
	}
	private boolean isValidTitle(String title){
		title = title.toLowerCase(); 
		for (int i = 0 ; i <title.length(); ++i){
			char c = title.charAt(i); 
			if((c >= 'a' && c <= 'z') || c == '�' || c == '�' || c == '�'|| c <= '9' && c >= '0'); 
			else return false; 

		}return true; 
	}
}
