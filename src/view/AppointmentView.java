package view;

import java.sql.Timestamp;

import calendar.Calendar;
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
				+ "Time: " + appointment.getStartTime().toString().substring(0, 16) + " to " + appointment.getEndTime().toString().substring(0, 16) + "\n"
				+ "Description: " + appointment.getDescription();
		if (appointment.getRoom()!=null) view += "\nPlace: " + appointment.getRoom().getRoomName();
		// Vise deltagere:
//		for (User user : appointment.getParticipants()) {
//			view += user.getFirstname() + " " + user.getLastname() + ", ";
//			view.substring(0, view.length() - 2);
//
//		}
		return view; 
	}
}
