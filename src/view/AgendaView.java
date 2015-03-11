package view;

import java.util.ArrayList;

import calendar.*;
import user.*;
import Appointment.*;

public class AgendaView {
	
	ArrayList<Appointment> appointments;
	
	public AgendaView(ArrayList<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	public String viewAgenda() {
		String agendaView = "";
		for (Appointment appointment : appointments) {
			agendaView = agendaView + appointment.getStartTime().toString() + ": " + appointment.getTitle() + "\n";
		}
		return agendaView;
	}

}
