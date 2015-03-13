package view;

import user.*;
import Appointment.*;

public class AppointmentView {
	
	private Appointment appointment;
	
	public AppointmentView( Appointment appointment ) {
		this.appointment = appointment;
	}
	
	public String viewAppointment() {
		String view = "";
		view += "Title: " + appointment.getTitle() + "\n"
				+ "Time: " + appointment.getStartTime().toString() + " to " + appointment.getEndTime() + "\n"
				+ "Place: " + appointment.getRoom().getRoomName() + "\n" + "\n"
				+ "Description: " + appointment.getDescription();
		// Vise deltagere:
		for (User user : appointment.getParticipants()) {
			view += user.getFirstname() + " " + user.getLastname() + ", ";
		}
		view.substring(0, view.length() - 2);
		
		return view;
	}
	
}
