package calendar;

import Appointment.Appointment;
import Appointment.Notification;
import Appointment.Room;
import Appointment.RoomOverview;
import user.Group;
import user.User;
import view.AgendaView;
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
		System.out.println(user.getFirstname() + " " + user.getLastname() + " is now added");
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
		boolean flag1 = false; 
		while (!flag1) {
			System.out.println("What do you want to do?");
			System.out.println(
					"1. Show my calendar\n" +
							"2. Update my credencials\n" +
							"3. Administrate my appointments\n"+
					"4. Show or administrate my groups\n");
			int choice = scan.nextInt();
			if (choice == 1) {
				AgendaView av = new AgendaView(user);
				boolean exit = false;
				while (!exit){
					System.out.println("1. Next week\n2. Previous week\n3. Exit view");
					choice = scan.nextInt();
					if (choice==1) av.nextWeek();
					else if (choice==2) av.previousWeek();
					else if (choice==3) exit=true;
					else System.out.println("Not a valid choice! Try again...");
				}
			} else if (choice==2) {
				System.out.println("What do you want to update?\n"+
						"1. Firstname\n"+
						"2. LastName\n"+
						"3. Password\n"+
						"4. Email\n");
				int choice2 = scan.nextInt();
				if (choice2==1) {
					System.out.println("Please enter a new firstname:");
					String newvalue = scan.next();
					user.updateFirstName(newvalue);
					System.out.println("Your first name has been changed to " + newvalue+ "\n");
				} else if (choice2==2) {
					System.out.println("Please enter a new lastname:");
					String newvalue = scan.next();
					user.updateLastName(newvalue);
					System.out.println("Your last name has been changed to " + newvalue + "\n");
				} else if (choice2==3) {
					System.out.println("Please enter a new password:");
					String newvalue = scan.next();
					user.updatePassword(newvalue);
					System.out.println("Your password has been changed to " + newvalue + "\n");
				} else if (choice2==4) {
					System.out.println("Please enter a new mail:");
					String newvalue = scan.next();
					user.updateMail(newvalue);
					System.out.println("Your mail has been changed to " + newvalue + "\n");
				} else {
					System.out.println("Not valid chioce!");
				}
			} else if (choice==3) {
				System.out.println("What do you want to: \n"+
						"1. Add new appointment\n"+
						"2. Update appointment\n" +
						"3. Delete appointment\n" +
						"4. Show free timeslots\n" + 
						"5. Book room");
				choice = scan.nextInt();
				if (choice==1) {
					addAppointment();
					flag1 = true; 
				} else if (choice==2) {
					selectAppointment();
					System.out.println("What do you want to update in this appointment?\n" +
							"1. Title\n"+
							"2. Start\n"+
							"3. End\n"+
							"4. Priority\n"+
							"5. Description\n"+
							"6. Maximum participants\n");
					choice = scan.nextInt();
					System.out.println("Please enter the new value:");
					answer = scan.next();
					if (choice==1) appointment.updateTitle(answer);
					else if (choice==2) appointment.updateStart(Timestamp.valueOf(answer));
					else if (choice==3) appointment.updateEnd(Timestamp.valueOf(answer));
					else if (choice==4) appointment.updateDescription(answer);
					else if (choice==5) appointment.updateMaxParticipants(answer.toCharArray()[0]);
					else System.out.println("Not a valid choice!");
					System.out.println("The appointment has been updated!");
					flag1 = true; 
				} else if (choice==3) {
					selectAppointment();
					appointment.deleteAppointment();
					System.out.println("The appointment has been successfully deleted!");flag1 = true; 
				} else if (choice==4) {
					showFreeRooms();
				} else if (choice==5) {
					selectAppointment();
					booking(appointment);
				} else {
					System.out.println("Not valid chioce!");
				}
			} else if (choice == 4) {
				System.out.println("What do you want to: \n"+
						"1. Show groups\n"+
						"2. Create a new group\n" +
						"3. Add member\n" +
						"4. Remove member\n"+
						"5. Delete group");
				choice = scan.nextInt();
				if (choice==1) {
					showGroups(); 
				} else if (choice==2) {
					createGroup();
				} else if (choice==3) {
					selectUser();
					selectGroup();     
					System.out.println("The member will be added to the group");  
					//					member.addGroup(group);
					//					group.addMember(member);
				} else if (choice==4) {
					selectUser();
					selectGroup();
					System.out.println("this is not implemented yet. Sorry!2");
					//					member.removeGroup(group);
					//					group.removeMember(member);
				} else if (choice==5) {
					selectGroup();
					group.deleteGroup();
					user.removeGroup(group);
				}
				else {
					System.out.println("Not valid chioce!");
				} 
			} else {
				System.out.println("Not valid choice, try again!");
			}
		}
	}

	private void selectUser() {
		System.out.println("to pick a user is not implemented yet. Sorry!");

		/*TO DO: usersView()
		users = getUsers();
		System.out.println("Which user?");
		for (int i = 0; i < users.size(); i++) {
			System.out.println((i+1) + ": "+ users.get(i).getFirstname()+ " "+user.get(i).getLastname());
		} 
		Scanner scan = new Scanner(System.in);
		member = users.get(scan.nextInt());
		 */
	}

	private void selectAppointment() {
		AgendaView av = new AgendaView(this.user); 

		System.out.println("Which appointment?");
		for (int i = 0; i < appointments.size(); i++) {
			System.out.println((i+1) + ": "+ appointments.get(i).getTitle());
		} 
		// Scanner scan = new Scanner(System.in);
		//		 appointment = appointments.get(scan.nextInt());

	}

	private void selectGroup() throws Exception {
		; 
		ArrayList<Group> groups = user.getGroups();
		System.out.println("Please pick a group");
		for (int i = 0; i < groups.size(); i++) {
			System.out.println((i+1) + ": "+ groups.get(i).getName());
		} 
		Scanner scan = new Scanner(System.in);
		group = groups.get(scan.nextInt()-1);
		System.out.println("you have chosen " + group.getName());

	}

	private void createGroup() throws Exception {
		Scanner scan = new Scanner(System.in); 
		System.out.println("Please enter prefered group name: ");
		String groupName = scan.next(); 
		Group newGroup = new Group(groupName, this.user); 
		user.addGroup(newGroup);
		newGroup.addMember(user);
		System.out.println("Group created, you are the leader of the group.");		
	}

	private void showGroups() throws Exception {
		ArrayList<Group> groups = user.getGroups();
		if (groups.size() == 0) System.out.println("\nYou aren't part of any group yet!");
		else {
			System.out.println("\nGroups you are member of:");
			for (int i = 0; i < groups.size(); i++) {
				System.out.println((i+1) + ". "+groups.get(i).getName());
			}
		}		
	}

	private void showAppointments() throws Exception { //MŒ gj¿res noe 
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select AppointmentID from Appointment WHERE CalendarID=?");
			preparedStatement.setInt(1, this.user.getPersonalCalendar().getCalendarID());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				appointments.add(new Appointment(resultSet.getInt("AppointmentID"),user)); 
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

	public void addAppointment() throws Exception {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter a title for your appointment:");
		String title = scan.nextLine();
		System.out.println("Please enter start time in the format: [YYYY-MM-DD HH:MM:SS.S] ");
		String start = scan.nextLine(); 
		System.out.println("Please enter end time in the format: [YYYY-MM-DD HH:MM:SS.S] ");
		String end = scan.nextLine();
		System.out.println("Please enter priority: [1 is very important. 3 is the least important] :");
		int priority = Integer.parseInt(scan.nextLine());
		System.out.println("Please enter maximum number of participants: ");        
		int maxParticipants = scan.nextInt();  
		System.out.println("Please enter a short description:");
		String description = scan.next();


		appointment = new Appointment(this.user,title,Timestamp.valueOf(start),Timestamp.valueOf(end),priority,description,maxParticipants);
		System.out.println("Appointment added!"); 
		System.out.println("Do you wan't to book a room now?\n1. Yes!\n2. No, I'll do that later");

		int answer = scan.nextInt(); 
		if (answer==1) {
			booking(appointment);
		} else showChoices();
	}



	private void booking(Appointment appointment) throws Exception {
		roomOverview = new RoomOverview(appointment.getStartTime(),appointment.getEndTime(),appointment.getMaxParticipants());
		ArrayList<Room> rooms = roomOverview.getFreeRooms();
		if (rooms.size()==0){
			System.out.println("No rooms are avalible at the time and capacity given!");
			showChoices();
		}else {
			System.out.println("These rooms are availible: \n");
			for (int i = 0; i < rooms.size(); i++) {
				System.out.println((i+1) + ": "+ rooms.get(i).getRoomName());
			}
			Scanner scan = new Scanner(System.in);
			System.out.println("Please enter the number of the choosen room:");
			int roomnr = scan.nextInt();
			appointment.updateRoom(rooms.get(roomnr-1));
			System.out.println("The Room " + rooms.get(roomnr-1).getRoomName() + " is booked!");
			showChoices(); 
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
