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

	private PreparedStatement preparedStatement = null;
	private PreparedStatement ps = null;
	private ResultSet resultSet = null;

	private ArrayList<User> participants;

	//Hente avtale
	public Appointment(int appointmentID, User owner) throws Exception {
			try {
				openConn();
				preparedStatement = connect.prepareStatement("select * from Appointment WHERE AppointmentID=?");
				preparedStatement.setInt(1, appointmentID);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					this.calendar = owner.getPersonalCalendar();
					this.owner = owner;
					this.title = resultSet.getString("Title");
					this.start = resultSet.getTimestamp("Start");
					this.end = resultSet.getTimestamp("End");
					if (resultSet.getString("Room_name")!=null) this.room = new Room(resultSet.getString("Room_name"));
					this.priority = resultSet.getInt("Priority");
					this.description = resultSet.getString("Description");
					this.maxParticipants = resultSet.getInt("Max_participants");
				}
				preparedStatement = connect.prepareStatement("select User from Invited WHERE AppointmentID=?");
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

	//Opprette avtale
	public Appointment(User owner, String title, Timestamp start, Timestamp end, int priority, String description, int maxParticipants) throws Exception {
		try {
			if(isValidTimestamp(start) && isValidTimestamp(end) && isValidTitle(title)){
			}else throw new IllegalArgumentException("Either the name or username is invalid");  

			openConn();
			preparedStatement = connect.prepareStatement("insert into Appointment values (default,?,?,?,?,?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1,owner.getPersonalCalendar().getCalendarID());
			preparedStatement.setString(2,owner.getUsername());
			preparedStatement.setString(3, title);
			preparedStatement.setTimestamp(4,start);
			preparedStatement.setTimestamp(5,end);
			preparedStatement.setString(6,null);
			preparedStatement.setInt(7, priority);
			preparedStatement.setString(8, description);
			preparedStatement.setInt(9, maxParticipants);
			preparedStatement.setTimestamp(10, null);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				this.appointmentID = resultSet.getInt(1);
			}
		} finally {
			closeConn();
		}
		this.calendar = owner.getPersonalCalendar();
		calendar.addAppointment(this);
		this.owner = owner;
		this.title = title;
		this.start = start;
		this.end = end;
		this.priority = priority;
		this.description = description;
		this.maxParticipants = maxParticipants;
	}

	//Endre avtale
	public void updateAppointment(Calendar calendar, User owner, String title, Timestamp start, Timestamp end,  Room room, int priority, String description, int maxParticipants) throws Exception {
		try {
			if(isValidTimestamp(start) && isValidTimestamp(end)&& isValidTitle(title)){
			}else throw new IllegalArgumentException("Either the name or username is invalid");  

			openConn();
			preparedStatement = connect.prepareStatement("update Appointment SET CalendarID=?,Username=?,Start=?,End=?,Room_name=?,Priority=?,Description=?,Max_participants=? WHERE AppointmentID=?");
			preparedStatement.setInt(1,calendar.getCalendarID());
			preparedStatement.setString(2,owner.getUsername());
			preparedStatement.setString(3, title);
			preparedStatement.setTimestamp(4,start);
			preparedStatement.setTimestamp(5,end);
			preparedStatement.setString(6,room.getRoomName());
			preparedStatement.setInt(7, priority);
			preparedStatement.setString(8, description);
			preparedStatement.setInt(9, maxParticipants);
			preparedStatement.setInt(10, getAppointmentID());
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}
		this.calendar.removeAppointment(this);
		calendar.addAppointment(this);
		this.calendar = calendar;
		this.owner = owner;
		this.title = title;
		this.start = start;
		this.end = end;
		this.room = room;
		this.priority = priority;
		this.description = description;
		this.maxParticipants = maxParticipants;

		//Notify participants
		for (int i = 0; i < participants.size(); i++) {
			new Notification("Your appiontment has changed","The appointment "+getTitle()+" scheduled at "+getStartTime()+" has changed",participants.get(i));
		}
	}
	
	public void updateTitle(String title) throws Exception {
		try {
		openConn();
		ps = connect.prepareStatement("update Appointment set Title=? where AppointmentID=?");
		ps.setString(1, title);
		ps.setInt(2, getAppointmentID());
		ps.executeUpdate();
		} finally {
			closeConn();
		}
		this.title = title;
	}
	
	public void updateStart(Timestamp start) throws Exception {
		try {
		openConn();
		ps = connect.prepareStatement("update Appointment set Start=? where AppointmentID=?");
		ps.setTimestamp(1, start);
		ps.setInt(2, getAppointmentID());
		ps.executeUpdate();
		ps = connect.prepareStatement("update Invited set Confirmed=0 where AppointmentID=?");
		ps.setInt(2, getAppointmentID());
		ps.executeUpdate();
		} finally {
			closeConn();
		}
		this.start = start;
		for (int i = 0; i < participants.size(); i++) {
			new Notification("Your appiontment has changed","The appointment "+getTitle()+" scheduled at "+getStartTime()+" has changed its start time to "+getStartTime(),participants.get(i));
		}
	}
	
	public void updateEnd(Timestamp end) throws Exception {
		try {
		openConn();
		ps = connect.prepareStatement("update Appointment set End=? where AppointmentID=?");
		ps.setTimestamp(1, end);
		ps.setInt(2, getAppointmentID());
		ps.executeUpdate();
		ps = connect.prepareStatement("update Invited set Confirmed=0 where AppointmentID=?");
		ps.setInt(2, getAppointmentID());
		ps.executeUpdate();
		} finally {
			closeConn();
		}
		this.end = end;
		for (int i = 0; i < participants.size(); i++) {
			new Notification("Your appiontment has changed","The appointment "+getTitle()+" scheduled at "+getStartTime()+" has changed its end time to "+getStartTime(),participants.get(i));
		}
	}
	
	public void updateRoom(Room room) throws Exception {
		try {
		openConn();
		ps = connect.prepareStatement("update Appointment set Room_name=? where AppointmentID=?");
		ps.setString(1, room.getRoomName());
		ps.setInt(2, getAppointmentID());
		ps.executeUpdate();
		ps = connect.prepareStatement("update Invited set Confirmed=0 where AppointmentID=?");
		ps.setInt(1, getAppointmentID());
		ps.executeUpdate();
		} finally {
			closeConn();
		}
		this.room = room;
		for (int i = 0; i < participants.size(); i++) {
			new Notification("Your appiontment has changed","The appointment "+getTitle()+" scheduled at "+getStartTime()+" has moved to "+room.getRoomName(),participants.get(i));			
		}
	}
	public void updatePriority(int priority) throws Exception {
		try {
		openConn();
		ps = connect.prepareStatement("update Appointment set Priority=? where AppointmentID=?");
		ps.setInt(1, priority);
		ps.setInt(2, getAppointmentID());
		ps.executeUpdate();
		} finally {
			closeConn();
		}
		this.priority = priority;
	}
	public void updateDescription(String description) throws Exception {
		try {
		openConn();
		ps = connect.prepareStatement("update Appointment set Description=? where AppointmentID=?");
		ps.setString(1, description);
		ps.setInt(2, getAppointmentID());
		ps.executeUpdate();
		} finally {
			closeConn();
		}
		this.description = description;
	}
	public void updateMaxParticipants(int maxParticipants) throws Exception {
		try {
		openConn();
		ps = connect.prepareStatement("update Appointment set Max_participants=? where AppointmentID=?");
		ps.setInt(1, maxParticipants);
		ps.setInt(2, getAppointmentID());
		ps.executeUpdate();
		} finally {
			closeConn();
		}
		this.maxParticipants = maxParticipants;
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

	public boolean isValidTimestamp(Timestamp time) throws java.text.ParseException {
		SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
		try {
			format.parse(time.toString());
			Pattern p = Pattern.compile("^\\d{4}[-]?\\d{1,2}[-]?\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}[.]?\\d{1,6}$");
			return p.matcher(time.toString()).matches();
		} catch (ParseException e) {
			return false;
		}
	}

	public boolean appointmentIdExists(int appointmentID) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select AppointmentID from Appointment WHERE AppointmentID=?");
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

	public ArrayList<User> getParticipants() {
		return participants;
	}

	//Settere
	/*
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
*/
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
			if((c >= 'a' && c <= 'z') || c == 'æ' || c == 'ø' || c == 'å'|| c <= '9' && c >= '0'); 
			else return false; 

		}return true; 
	}
}
