package Appointment;

import user.User;

public class Notification {

	public String message;
	public User sender;
	public User reciever;
	
	
	public Notification(String newMessage, User reciever) {
		this.message = newMessage;
		this.reciever = reciever;
	}
	
	public void notify() {
		reciever.inbox.add(this); //ikke opprettet denne klassen enda
	}
}
