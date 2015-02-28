package Appointment;

import user.User;
import user.NotificationInbox;

public class Notification {

	public String message;
	public String subject;
	public User sender;
	public User reciever;
	
	
	public Notification(String subject, String newMessage, User reciever) {
		this.message = newMessage;
		this.reciever = reciever;
	}
	
	public void notify() {
		reciever.notificationInbox.addNotification(this);
	}
}
