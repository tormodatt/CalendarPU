package Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import user.User;
import calendar.Database;

public class Notification extends Database {

	private int notificationID;
	private User reciever;
	private String subject;
	private String message;
	private Timestamp executed;
		
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public Notification(int notificationID) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select * from Notification where NotificationID=?");
			preparedStatement.setInt(1,notificationID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				this.reciever = new User(resultSet.getString("Reciever"));
				this.message = resultSet.getString("Message");
				this.subject = resultSet.getString("Subject");
				this.executed = resultSet.getTimestamp("Executed");
			}
		} finally {
			closeConn();
		}
	}
	
	public Notification(String subject, String message, User reciever) throws Exception {
		//TO DO: databaseting
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Notification values (default,?,?,?,default,0)", PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1,reciever.getUsername());
			preparedStatement.setString(2,subject);
			preparedStatement.setString(3,message);
			preparedStatement.executeQuery();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				this.notificationID = resultSet.getInt("NotificationID");
				this.executed = resultSet.getTimestamp("Executed");
			}
		} finally {
			closeConn();
		}
		
		this.subject = subject;
		this.message = message;
		this.reciever = reciever;
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
}
