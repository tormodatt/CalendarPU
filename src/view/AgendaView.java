package view;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

import calendar.*;
import user.*;
import Appointment.*;
import calendar.*;

public class AgendaView extends Database {
	
	private User user;
	private ArrayList<calendar.Calendar> calendars = new ArrayList<calendar.Calendar>();
	private Timestamp weekStart;
	private Timestamp weekEnd;
	
	private PreparedStatement preparedStatement = null;
	private PreparedStatement ps = null;
	private ResultSet resultSet = null;
	
	Calendar cal = Calendar.getInstance(new Locale("en","GB"));

	public AgendaView(User user) {
		setStartEndOfWeek();
		this.user = user;
		calendars.add(user.getPersonalCalendar());
		for (int i = 0; i < user.getAdminGroups().size(); i++) {
			this.calendars.add(user.getAdminGroups().get(i).getCalendar());
		}
		for (int i = 0; i < user.getMemberGroups().size(); i++) {
			this.calendars.add(user.getMemberGroups().get(i).getCalendar());
		}
		viewAgenda();
	}
	public ArrayList<Appointment> returnAgenda(){
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();

		for (int i = 0; i < calendars.size(); i++) {
			for (int j = 0; j < calendars.get(i).getAppointments().size(); j++) {
				Appointment appointment = calendars.get(i).getAppointments().get(j);
				if ((!appointment.getStartTime().before(weekStart)&&appointment.getStartTime().before(weekEnd))||(!appointment.getEndTime().before(weekStart)&&appointment.getEndTime().before(weekEnd))) {
					appointments.add(appointment);
				}
			}
		}
		return appointments; 
	}
	public void viewAgenda() {
		
		ArrayList<Appointment> appointments = returnAgenda(); 

		System.out.println("This weeks appointments:\n\n\tTitle\t\tStart\t\t\tEnd");
		for (int i = 0; i < appointments.size(); i++) {
			System.out.println((i+1)+"\t"+appointments.get(i).getTitle()+"\t\t"+appointments.get(i).getStartTime().toString().substring(0,16)+"\t"+appointments.get(i).getEndTime().toString().substring(0, 16));
		}
	}
	
	public void setStartEndOfWeek() {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		weekStart = new Timestamp(cal.getTime().getTime());
		cal.add(Calendar.DAY_OF_WEEK, 7);
		weekEnd = new Timestamp(cal.getTime().getTime());
	}
	
	public void nextWeek() {
		cal.setTime(weekEnd);
		weekStart.setTime(cal.getTime().getTime());
		cal.add(Calendar.DAY_OF_WEEK, 7);
		weekEnd.setTime(cal.getTime().getTime());
		viewAgenda();
	}
	
	public void previousWeek() {
		cal.setTime(weekStart);
		weekEnd.setTime(cal.getTime().getTime());
		cal.add(Calendar.DAY_OF_WEEK, -7);
		weekStart.setTime(cal.getTime().getTime());
		viewAgenda();
	}
	
	public Timestamp getStartOfWeek() {
		return weekStart;
	}
	
	public Timestamp getEndOfWeek() {
		return weekEnd;
	}
}
