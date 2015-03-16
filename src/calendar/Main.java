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

public class Main extends Database{
	
	private User user;
	private User member;
	private RoomOverview roomOverview; 
	private ArrayList<Notification> unseenNotifications; 
	private Appointment appointment;
	private Group group;
	private ArrayList<Appointment> appointments; 
	
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
		
	private void logIn() throws Exception {
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter username:");
		String username = input.next(); 
		User existingUser = new User(username);
		this.user = existingUser; 
		System.out.println("Please enter password:");
		while (input.next().equals(user.getPassword())==false) System.out.println("Wrong password! Try again");
		System.out.println("Successfully logged in!"); 
	}
	
	
	private void registerNew() throws Exception {
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
		System.out.println("User '" + user.getUsername() + "' is now added");
	}
	
	private void showCalendar() {
		//Kaller visning 
	}
	
	private void notSeen() throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select NotificationID from Notification WHERE Receiver=? and Seen=0");
			preparedStatement.setString(1, user.getUsername());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				unseenNotifications.add(new Notification(resultSet.getInt("NotificationID"))); 
			}
		} finally {
			closeConn();
		}
		if (unseenNotifications != null) {
			System.out.println("Unseen notifications:\n");
			
			for (int i = 0; i < unseenNotifications.size(); i++) {
				System.out.println("Subject: "+unseenNotifications.get(i).getSubject());
				System.out.println("Message: "+unseenNotifications.get(i).getMessage());
				System.out.println("Excecuted: "+unseenNotifications.get(i).getExcecuted()+"\n");
			}
			Scanner scan = new Scanner(System.in);
			System.out.println("Press any key to continue...");
			scan.next();
			user.setAllSeen(); 
		}
	}
	
	private void showChoices() throws Exception {
		Scanner scan = new Scanner(System.in);
		String answer;
		boolean flag = false; 
		while (! flag) {
			System.out.println("What do you want to do?");
			System.out.println(
			"1. Show my calendar\n" +
			"2. Update my credencials\n" +
			"3. Administrate my appointments\n"+
			"4. Show or administrate my groups\n");
			int choice = scan.nextInt();
			if (choice == 1) {
				//agendaView()
			} else if (choice==2) {
				System.out.println("What do you want to update?\n"+
				"1. Firstname\n"+
				"2. LastName\n"+
				"3. Password\n"+
				"4. Email\n");
				int choice2 = scan.nextInt();
				if (choice2==1) {
					System.out.println("Please enter Firstname\n");
					String newvalue = scan.next();
					user.updateFirstName(newvalue);;
				} else if (choice2==2) {
					System.out.println("Please enter Lastname\n");
					String newvalue = scan.next();
					user.updateLastName(newvalue);
				} else if (choice2==3) {
					System.out.println("Please enter Password\n");
					String newvalue = scan.next();
					user.updatePassword(newvalue);
				} else if (choice2==4) {
					System.out.println("Please enter mail\n");
					String newvalue = scan.next();
					user.updateMail(newvalue);
				} else {
					System.out.println("Not valid chioce!");
				}
			} else if (choice==3) {
				System.out.println("Do wan't to: \n"+
				"1. Add new appointment\n"+
				"2. Update appointment/book room\n" +
				"3. Delete appointment" +
				"4. Show free timeslots");
				choice = scan.nextInt();
				if (choice==1) {
					addAppointment();
				} else if (choice==2) {
					selectAppointment();
					System.out.println("What do you want to update in this appointment?\n" +
					"1. Title\n"+
					"2. Start\n"+
					"3. End\n"+
					"4. Room\n"+
					"5. Priority\n"+
					"6. Description\n"+
					"7. Maximum participants\n");
					choice = scan.nextInt();
					System.out.println("Please enter the new value:");
					answer = scan.next();
					if (choice==1) appointment.updateTitle(answer);
					else if (choice==2) appointment.updateStart(Timestamp.valueOf(answer));
					else if (choice==3) appointment.updateEnd(Timestamp.valueOf(answer));
					else if (choice==4) booking(appointment);
					else if (choice==5) appointment.updateDescription(answer);
					else if (choice==6) appointment.updateMaxParticipants(answer.toCharArray()[0]);
					else System.out.println("Not a valid choice!");
					System.out.println("The appointment has been updated!");
				} else if (choice==3) {
					selectAppointment();
					appointment.deleteAppointment();
					System.out.println("The appointment has been successfully deleted!");
				} else if (choice==4) {
					showFreeRooms();
				} else {
					System.out.println("Not valid chioce!");
				}
				flag = true; 
			} else if (choice == 4) {
				System.out.println("Do wan't to: \n"+
				"1. Show groups\n"+
				"2. Create a new group\n" +
				"3. Add member\n" +
				"4. Remove member\n"+
				"5. Delete group");
						int choice2 = scan.nextInt();
						if (choice2==1) {
							showGroups();
						} else if (choice==2) {
							createNewGroup();
						} else if (choice==3) {
							selectUser();
							selectGroup();
							member.addGroup(group);
							group.addMember(member);
						} else if (choice==4) {
							selectUser();
							selectGroup();
							member.removeGroup(group);
							group.removeMember(member);
						} else if (choice==5) {
							selectGroup();
							group.deleteGroup();
							user.removeGroup(group);
						}
						else {
							System.out.println("Not valid chioce!");
						} 
				flag = true; 
			} else {
				System.out.println("Not valid choice, try again!");
			}
		}
	}
	
	private void selectUser() {
		/* TO DO: usersView()
		 * users = getUsers();
		 * System.out.println("Which user?");
		 * for (int i = 0; i < users.size(); i++) {
				System.out.println((i+1) + ": "+ users.get(i).getFirstname()+ " "+user.get(i).getLastname());
			} 
		 * Scanner scan = new Scanner(System.in);
		 * member = users.get(scan.nextInt());
		 * */
	}
	
	private void selectAppointment() {
		/* TO DO: agendaView()
		 * appointments = agendaView().getAppointments()
		 * System.out.println("Which appointment?");
		 * for (int i = 0; i < appointments.size(); i++) {
				System.out.println((i+1) + ": "+ appointments.get(i).getTitle());
			} 
		 * Scanner scan = new Scanner(System.in);
		 * appointment = appointments.get(scan.nextInt());
		 * */		
	}
	
	private void selectGroup() throws Exception {
		 showGroups(); 
		
		 /* TO DO:* groups = user.getGroups();
		 * System.out.println("Which group?");
		 * for (int i = 0; i < groups.size(); i++) {
				System.out.println((i+1) + ": "+ groups.get(i).getName());
			} 
		 * Scanner scan = new Scanner(System.in);
		 * group = groups.get(scan.nextInt());
		 * */		
	}
	
	private void joinGroup() throws Exception {
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
	//denne sender ut error så fort gruppenavn er skrevet inn
	private void createNewGroup() throws Exception {
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
	
	private void showGroups() throws Exception {
		ArrayList<Group> groups = user.getGroups();
		if (groups==null) System.out.println("You aren't part of any group yet");
		else {
			System.out.println("Groups you are member of:\n");
			for (int i = 0; i < groups.size(); i++) {
				System.out.println((i+1) + ". "+groups.get(i).getName());
		}

		}
		 //Metode for Œ vise alle tilgjengelige grupper som man kan bli medlem i HER
		Scanner scanner = new Scanner(System.in); 
		boolean flag = false; 
		while (! flag) {
			System.out.println("Do you want to: " + "\n" + "1. Join a group" + "\n" 
					+ "2. Create new group" + "\n" + "3. Leave group"); //+ Evt. vise alle avtaler til en av gruppene
			int choice = scanner.nextInt();
			if (choice == 1) {
				joinGroup(); 
				flag = true; 
			} else if (choice == 2) {
				createNewGroup(); 
				flag = true; 
			} else if (choice == 3) {
				leaveGroup();
				flag = true; 
			} else {
				System.out.println("Not valid choice, try again!");
			}
		}
		
	}
	
	private void leaveGroup() {
		Scanner scan = new Scanner(System.in); 
		System.out.println("Please enter group ID: "); //Evt endre til gruppenavn
		int groupID = scan.nextInt(); 
		try {
			Group group = new Group(groupID); 
			this.user.removeGroup(group);
			group.removeMember(this.user);
		} catch (Exception e) {
			System.out.println("Not valid ID");
		}
		
	}

	private void showAppointments() throws Exception { //MŒ gj¿res noe 
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select AppointmentID from Appointment WHERE CalendarID=?");
			preparedStatement.setInt(1, this.user.getPersonalCalendar().getCalendarID());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				appointments.add(new Appointment(resultSet.getInt("AppointmentID"))); 
			}
		} finally {
			closeConn();
		}
		System.out.println("What do you want to do?" + "\n" + "1. Cancel appointment" + "\n" 
		+ "2. Change appointment information");
		Scanner scan = new Scanner(System.in); 
		//Fullf¿re her 
		
	}
	
	
	
	private void showFreeRooms() throws Exception {
		roomOverview = new RoomOverview(appointment.getStartTime(),appointment.getEndTime(),appointment.getMaxParticipants());
		ArrayList<Room> rooms = roomOverview.getFreeRooms();
		if (rooms.size()==0) System.out.println("No room are avalible at the time and capacity given!");
		else {
			System.out.println("These rooms are availible: \n");
			for (int i = 0; i < rooms.size(); i++) {
				System.out.println((i+1) + ": "+ rooms.get(i).getRoomName());
			}
		}
	}
	//denne metoden failer etter at man skal skrevet inn start og sluttidspunkt
	private void addAppointment() throws Exception {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter a title for your appointment \1"
				+ "n");
		String title = scan.next();
		System.out.println("Please enter start time in the format [YYYY-MM-DD HH:MM:SS.S] ");
		String start = scan.next(); 
		System.out.println("Please enter end time in the format [YYYY-MM-DD HH:MM:SS.S] ");
		String end = scan.next();
		System.out.println("Please enter priority:");
		int priority = scan.next().toCharArray()[0];
		System.out.println("Please enter a short description:");
		String description = scan.next();
		System.out.println("Please enter maximum number of participants: ");
		int capacity = scan.nextInt(); 
		appointment = new Appointment(user,title,Timestamp.valueOf(start),Timestamp.valueOf(end),null,priority,description,capacity);
		System.out.println("Appointment added! Do you wan't to book a room now?\n1. Yes!\n2. No, I'll do that later");
		int answer = scan.nextInt();
		if (answer==1) {
			booking(appointment);
		}
		
	}
	
	private void booking(Appointment appointment) throws Exception {
		roomOverview = new RoomOverview(appointment.getStartTime(),appointment.getEndTime(),appointment.getMaxParticipants());
		ArrayList<Room> rooms = roomOverview.getFreeRooms();
		if (rooms.size()==0) System.out.println("No room are avalible at the time and capacity given!");
		else {
			System.out.println("These rooms are availible: \n");
			for (int i = 0; i < rooms.size(); i++) {
				System.out.println((i+1) + ": "+ rooms.get(i).getRoomName());
			}
			Scanner scan = new Scanner(System.in);
			System.out.println("Please enter the number of the choosen room:");
			int roomnr = scan.nextInt();
			appointment.updateRoom(rooms.get(roomnr));
			System.out.println("The Room " + rooms.get(roomnr) + " is booked!");
		}
	}
			
		
	
	
	public static void main(String[] args) throws Exception {
		Main main = new Main(); 
		main.run(); //Logger inn med eksisterende bruker/oppretter ny 
		main.notSeen(); //Sjekker invitasjoner/notifications 
		//main.showCalendar(); //Visning av kalender 
		main.showChoices(); //Viser liste med mulige valg
	}
	
}
