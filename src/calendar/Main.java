package calendar;

import user.User;

public class Main {
	public static void main(String[] args) throws Exception {
		User user = new User("ola");
		System.out.println(user.getPassword());
		user.setPassword("sdf");
		System.out.println(user.getPassword());
		
		//User user1 = new User("fornavn","etternavn","brukernavn","passord","mail");
		//System.out.println(user1.getFirstname());
	}
}
