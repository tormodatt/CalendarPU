package view;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.lang.Object;
import java.util.Calendar;


import Appointment.*;
import calendar.*;
import user.*;

public class CalendarView {
	
	private User owner;
	private Group ownerGroup;
	Calendar now = Calendar.getInstance();
	
	private ArrayList<Appointment> appointments;
	
	private static Date today = new Date();
	private Timestamp currentTime = new Timestamp(today.getTime());
	
	public CalendarView(User owner) {
		this.owner = owner;
		this.appointments = owner.getPersonalCalendar().getAppointments();
		this.ownerGroup = null;
	}
	
	public CalendarView(Group ownerGroup) {
		this.ownerGroup = ownerGroup;
		this.appointments = ownerGroup.getCalendar().getAppointments();
		this.owner = null;
	}
	
	
	public void sortAppointments() {
		
	}
	
	public static String getWeekDay(Date date){
		int n = date.getDay();
		switch (n) {
		case 1 : return "Monday";
		case 2 : return "Tuesday";
		case 3 : return "Wednesday";
		case 4 : return "Thursday";
		case 5 : return "Friday";
		case 6 : return "Saturday";
		case 7 : return "Sunday";
		default : return "Unknown day";	
		}
	}
	
	public static String getMonth(Date date) {
		int n = date.getMonth();
		switch (n) {
		case 0 : return "January";
		case 1 : return "February";
		case 2 : return "March";
		case 3 : return "April";
		case 4 : return "May";
		case 5 : return "June";
		case 6 : return "July";
		case 7 : return "August";
		case 8 : return "September";
		case 9 : return "October";
		case 10 : return "November";
		case 11 : return "December";
		default : return "Unknown month";
		}
	}
	
	
	public String viewCalendar() {
		
		String table = "|";
		for (int i = 0; i < 7; i++) {
			table += "";
		}
		return table;
	}
	
	public static void main(String[] args) {
		
	}
	

}
