package view;

import java.util.ArrayList;

import calendar.*;
import user.*;
import Appointment.*;

public class AgendaView {
	
	private User owner;
	private ArrayList<Appointment> appointments;
	
	public AgendaView(User owner) {
		this.owner = owner;
		this.appointments = owner.getPersonalCalendar(); // mangler denne metoden
	}
	
	public String viewAgenda() {
		String agendaView = "";
		for (Appointment appointment : appointments) {
			agendaView = agendaView + appointment.getStartTime().toString() + ": " + appointment.getTitle() + "\n";
		}
		return agendaView;
	}

}
