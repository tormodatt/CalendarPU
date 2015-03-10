package calendar;

import Appointment.Room;
import user.User;
import calendar.*;
import tests.*;

import java.util.Scanner; 

public class Main {
	
	private User user; 
	
	
	public void booking(User user) {
		
	}
	
	
	public void logOn() throws Exception {
		
	}
	
	public void registerNew() throws Exception {
		Scanner input_logOn = new Scanner(System.in); 
		System.out.println("Please enter firstname"); 
		String firstName = input_logOn.next(); 
		System.out.println("Please enter lastname");
		String lastName = input_logOn.next(); 
		System.out.println("Please enter username");
		String userName = input_logOn.next(); 
		System.out.println("Please enter password");
		String password = input_logOn.next(); 
		System.out.println("Please enter email");
		String email = input_logOn.next(); 
		User newUser = new User(firstName, lastName, userName, password, email); 
		this.user = newUser; 
	}
	
	public static String welcomeToString() {
		return "Hello! Do you want to:" + "\n" + "1. Log on" + "\n" + "2. Register new user";
	}
	
	public static void main(String[] args) throws Exception {
		Main run = new Main(); 
		welcomeToString();
		Scanner scan = new Scanner(System.in); 
		int choice = scan.nextInt(); 
		boolean status = false; 
		while (! status) { 
			if (choice == 1) {
			run.logOn(); 
			status = true; 
		} else if (choice == 2) {
			run.registerNew(); 
			status = true;
		} else {
			
		}
	
		
		//User user1 = new User("fornavn","etternavn","brukernavn","passord","mail");
		//System.out.println(user1.getFirstname());
	}
}
