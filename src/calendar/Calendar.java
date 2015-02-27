package calendar;

import java.util.ArrayList;

import user.User;
import Appointment.Appointment;

public class Calendar {

	public ArrayList<Appointment> appointment;
	private User user;
    private int asdf;
	
	public Calendar(User user) {
		this.user = user; 
		this.appointment = new ArrayList<Appointment>(); 
	}
	
	
	

}
