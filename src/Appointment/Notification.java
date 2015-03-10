package Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import user.User;
import calendar.Database;

public class Notification extends Database {

	private int notificationID;
	private User receiver;
	private String subject;
	private String message;
	private Timestamp executed;
		
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	// Hente Notification
	public Notification(int notificationID) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select * from Notification where NotificationID=?");
			preparedStatement.setInt(1,notificationID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				this.receiver = new User(resultSet.getString("Receiver"));
				this.message = resultSet.getString("Message");
				this.subject = resultSet.getString("Subject");
				this.executed = resultSet.getTimestamp("Executed");
			}
		} finally {
			closeConn();
		}
	}
	
	// Opprette Notification
	public Notification(String subject, String message, User reciever) throws Exception {
		//TO DO: databaseting
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Notification values (default,?,?,?,default,0)", PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1,reciever.getUsername());
			preparedStatement.setString(2,subject);
			preparedStatement.setString(3,message);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				this.notificationID = resultSet.getInt(1);
			}			
			PreparedStatement ps = connect.prepareStatement("select Excecuted from Notification where NotificationID=?");
			ps.setInt(1,notificationID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				this.executed = rs.getTimestamp(1);				
			}
			
		} finally {
			closeConn();
		}
		
		this.subject = subject;
		this.message = message;
		this.receiver = reciever;
	}
	
	public void setSeen() throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("update Notification set Seen=1 where NotificationID=?");
			preparedStatement.setInt(1, getNotificationID());
			preparedStatement.executeQuery();
		} finally {
			closeConn();
		}
	}
	
	public int getNotificationID() {
		return notificationID;
	}

	public User getReceiver() {
		return receiver;
	}

	public String getSubject() {
		return subject;
	}

	public String getMessage() {
		return message;
	}

	public Timestamp getExecuted() {
		return executed;
	}
	
}
