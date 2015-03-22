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
				+ "Time: " + appointment.getStartTime().toString() + " to " + appointment.getEndTime() + "\n"
				+ "Place: " + appointment.getRoom().getRoomName() + "\n" + "\n"
				+ "Description: " + appointment.getDescription();
		// Vise deltagere:
		for (User user : appointment.getParticipants()) {
			view += user.getFirstname() + " " + user.getLastname() + ", ";
			view.substring(0, view.length() - 2);

		}
		return view; 
	}

	public static void main(String[] args) throws Exception {

		User s = new User("Leo", "Ajkic", "leoleo", "kgdi123", "egefrabergen@nrk.no"); 
		Room r = new Room("k1"); 
		Appointment a = new Appointment(s, "Pifest", Timestamp.valueOf("2015-03-14 09:26:53.0"), Timestamp.valueOf("2015-03-14 23:00:00.0"), 2, "en fest for nerder flest", 550);
		a.updateRoom(r);
		System.out.println(a.getAppointmentID());
		AppointmentView av = new AppointmentView(a); 

	}
}
