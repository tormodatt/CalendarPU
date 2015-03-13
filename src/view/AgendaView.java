package view;

import java.util.ArrayList;

import java.sql.Timestamp;
import java.util.Date;

import calendar.*;
import user.*;
import Appointment.*;

public class AgendaView {
	
	private User owner;
	private ArrayList<Appointment> appointments;
	private Timestamp currentTime;
	
	public AgendaView(User owner) {
		this.owner = owner;
		this.appointments = owner.getPersonalCalendar().getAppointment(); // mangler denne metoden
	}
	
	public String viewAgenda() {
		
		Date today = new Date();
		Timestamp currentTime = new Timestamp(today.getTime());
		
		String agendaView = "Upcoming appointments:" + "\n";
		
		for (Appointment appointment : appointments) {
			if (appointment.getStartTime().after(currentTime)) {
				agendaView = agendaView + appointment.getStartTime().toString() + ": " + appointment.getTitle() + "\n";
			}
		}
		return agendaView;
	}

}
