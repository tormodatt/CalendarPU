package view;

import java.util.ArrayList;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;

import calendar.*;
import user.*;
import Appointment.*;
import calendar.*;

public class AgendaView {
	
	private User owner;
	private ArrayList<calendar.Calendar> calendars;
	private ArrayList<Appointment> appointments;
	private Timestamp currentTime;
	private Timestamp weekStart;
	private Timestamp weekEnd;
	private final long week = 604800000;
	
	public AgendaView(User owner) {
		this.owner = owner;
		//this.appointments = owner.getPersonalCalendar().getAppointment(); // mangler denne metoden
		for (int i = 0; i < owner.getGroups().size(); i++) {
			this.calendars.add(owner.getGroups().get(i).getCalendar());
		}
	}
	
	public String viewAgenda() {
		
		Date today = new Date();
		Timestamp currentTime = new Timestamp(today.getTime());
		
		String agendaView = "Appointments this week appointments:" + "\n";
		
		for (Appointment appointment : appointments) {
			if (appointment.getStartTime().after(currentTime)) {
				agendaView = agendaView + appointment.getStartTime().toString() + ": " + appointment.getTitle() + "\n";
			}
		}
		return agendaView;
	}
	
	public void setStartEndOfWeek() {
		// get today and clear time of day
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		// get start of this week in milliseconds
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		weekStart = new Timestamp(cal.getTime().getTime());
		weekEnd = new Timestamp(cal.getTime().getTime()+604800000);
	}
	
	public void nextWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(weekStart);
		cal.add(Calendar.DAY_OF_WEEK, 7);
		weekStart.setTime(cal.getTime().getTime());
		cal.setTime(weekEnd);
		cal.add(Calendar.DAY_OF_WEEK, 7);
		weekEnd.setTime(cal.getTime().getTime());
	}
	
	public void previousWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(weekStart);
		cal.add(Calendar.DAY_OF_WEEK, -7);
		weekStart.setTime(cal.getTime().getTime());
		cal.setTime(weekStart);
		cal.add(Calendar.DAY_OF_WEEK, -7);
		weekStart.setTime(cal.getTime().getTime());
	}

}
