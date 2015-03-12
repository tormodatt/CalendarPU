package calendar;

import Appointment.Appointment;
import Appointment.Notification;
import Appointment.Room;
import Appointment.RoomOverview;
import user.Group;
import user.User;
import tests.*;
import calendar.Calendar;
import calendar.Database;
import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.sql.*;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import java.util.Scanner; 

public class Main {
	
	private User user;
	private RoomOverview roomOverview; 
	private ArrayList<Notification> unseenNotifications; 
	
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public void run() throws Exception {
		Scanner scan = new Scanner(System.in);  
		boolean status = false; 
		while (! status) { 
			System.out.println("Hello! Do you want to:" + "\n" + "1. Log on" + "\n" + "2. Register new user" + "\n" + "3. Close");
			int choice = scan.nextInt();
			if (choice == 1) {
				logIn(); 
				status = true; 
			} else if (choice == 2) {
				registerNew(); 
				status = true;
			} else if (choice == 3) {
				System.out.println("Program closing, welcome back!"); //Legge inn noe mer? eks. fjerne databasetilgang++? 
				status = true; 
			} else {
				System.out.println("Not valid choice, try again");
			}
		}
	}
		
	public void logIn() throws Exception {
		Scanner input_logIn = new Scanner(System.in);
		System.out.println("Please enter username");
		String username = input_logIn.next(); 
		User existingUser = new User(username);
		this.user = existingUser; 
	}
	
	
	public void registerNew() throws Exception {
		Scanner input_logIn = new Scanner(System.in); 
		System.out.println("Please enter firstname"); 
		String firstName = input_logIn.next(); 
		System.out.println("Please enter lastname");
		String lastName = input_logIn.next(); 
		System.out.println("Please enter username");
		String userName = input_logIn.next(); 
		System.out.println("Please enter password");
		String password = input_logIn.next(); 
		System.out.println("Please enter email");
		String email = input_logIn.next(); 
		User newUser = new User(firstName, lastName, userName, password, email); 
		this.user = newUser; 
		System.out.println("User '" + this.user + "' is now added");
	}
	
	public void showCalendar() {
		//Kaller visning 
	}
	
	public void notSeen() throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select * from Notification WHERE Receiver=?");
			preparedStatement.setString(1, this.user.getUsername());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				// Notification notification = 
				// if (unseen)
				unseenNotifications.add(notification); 
			}
		} finally {
			closeConn();
		}
	}
	
	public void showChoices() throws Exception {
		Scanner scanner = new Scanner(System.in);
		boolean flag = false; 
		while (! flag) {
			System.out.println("What do you want to do?");
			System.out.println("1. Show your groups" + "\n" + "2. Show your appointments"
			+ "\n" + "3. Show free timeslots" + "\n" + "4. Book room");
			int choice = scanner.nextInt();
			if (choice == 1) {
				showGroups(); 
				flag = true; 
			} else if (choice == 2) {
				showAppointments(); 
				flag = true; 
			} else if (choice == 3) {
				showFreeRooms();
				flag = true; 
			} else if (choice == 4) {
				booking(); 
				flag = true; 
			} else {
				System.out.println("Not valid choice, try again!");
			}
		}
	}
	
	public void joinGroup() throws Exception {
		Scanner scan = new Scanner(System.in); 
		System.out.println("Please enter group ID: "); //Evt endre til gruppenavn
		int groupID = scan.nextInt(); 
		try {
			Group group = new Group(groupID); 
			this.user.addGroup(group);
			group.addMember(this.user);
		} catch (Exception e) {
			System.out.println("Not valid ID");
		}
	}
	
	public void createNewGroup() throws Exception {
		System.out.println("Create new group!");
		Scanner scan = new Scanner(System.in); 
		try { 
			System.out.println("Please enter prefered group name: ");
			String groupName = scan.next(); 
			Group newGroup = new Group(groupName, this.user); 
			this.user.addGroup(newGroup);
			newGroup.addMember(this.user);
			System.out.println("Group created, you are the leader of the group.");
		} catch (Exception e) {
			System.out.println("Error"); 
		}
		
	}
	
	public void showGroups() {
		 //Evt. metode for Œ vise alle tilgjengelige grupper som man kan bli medlem i. 
	}
	
	public void showAppointments() {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select * from Appointment WHERE CalendarID=?");
			preparedStatement.setInt(1, this.user.getPersonalCalendar().getCalendarID());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				// Appointment appointment = 
				unseenNotifications.add(appointment); 
			}
		} finally {
			closeConn();
		}
	}
	
	public void showFreeRooms() throws Exception {
		Scanner scanner = new Scanner(System.in); 
		System.out.println("Choose period you want to show free rooms." + "\n" + "Please enter start time: ");
		Timestamp startTime = Timestamp.valueOf(scanner.next()); 
		System.out.println("Please enter end time: ");
		Timestamp endTime = Timestamp.valueOf(scanner.next());  
		System.out.println("Please enter room capacity: ");
		int minimumCapacity = scanner.nextInt(); 
		roomOverview.getFreeRooms(startTime, endTime, minimumCapacity);
	}
	
	public void booking() throws Exception {
		Scanner scan = new Scanner(System.in);
		try {
			System.out.println("Add new appointment!");
			System.out.println("Please enter appointment name: ");
			String name = scan.next(); 
			System.out.println("Please enter start time: ");
			String start = scan.next();
			System.out.println("Please enter end time: ");
			String end = scan.next();
			showFreeRooms(); 
			System.out.println("Please enter room name: ");
			String roomName = scan.next(); 
			Room bookRoom = new Room(roomName);
			System.out.println("Please enter priority: ");
			int pri = scan.nextInt(); 
			System.out.println("Please enter short description: ");
			String des = scan.next(); 
			System.out.println("Please enter maxium partisipants: ");
			int maxParti = scan.nextInt(); 
			Appointment appointment = new Appointment(this.user.getPersonalCalendar(), this.user, name, start, end, bookRoom, pri, des, maxParti);
			this.user.getPersonalCalendar().addAppointment(appointment); 
			} catch (Exception e) {
				System.out.println("Not valid input");
			}
		}
			
		
	
	
	public static void main(String[] args) throws Exception {
		Main main = new Main(); 
		//main.run(); //Logger inn med eksisterende bruker/oppretter ny 
		//main.notSeen(); //Sjekker invitasjoner/notifications 
		//main.showCalendar(); //Visning av kalender 
		//main.showChoices(); //Viser liste med mulige valg
		main.booking();
	}
	
}
