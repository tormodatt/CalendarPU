package calendar;

import java.util.ArrayList;

public class Calendar {

	public ArrayList<Appointment> appointment;
	private User user; 
	
	public Calendar(User user) {
		this.user = user; 
		this.appointment = new ArrayList<Appointment>(); 
	}
	
	
	

}
