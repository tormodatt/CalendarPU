package calendar;

import Appointment.Room;
import user.User;
import calendar.*;
import tests.*;

import java.util.Scanner; 

public class Main {
	
	private User user; 
	
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
	
	public void notSeen() {
		
	}
	
	public void showChoices() {
		
	}
	
	
	public static void main(String[] args) throws Exception {
		Main main = new Main(); 
		main.run(); //Logger inn med eksisterende bruker/oppretter ny 
		//main.notSeen(); //Sjekker invitasjoner/notifications 
		//main.showCalendar(); //Visning av kalender 
		//main.showChoices(); //Viser liste med mulige valg
	}
	
}
