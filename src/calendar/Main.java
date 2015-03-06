package calendar;

import Appointment.Room;
import user.User;
import calendar.*;

public class Main {
	public static void main(String[] args) throws Exception {
		
		Room Kantina = new Room("Kantina", 250, "A1-001");
		System.out.println(Kantina.getRoomName());
		
		//User user1 = new User("fornavn","etternavn","brukernavn","passord","mail");
		//System.out.println(user1.getFirstname());
	}
}
