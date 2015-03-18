package danielstestpackage;

import user.User;
import user.Group;

@SuppressWarnings("unused")
public class DanielTestClass {
	
	private void run() throws Exception {
		System.out.println("Start");
		User u1 = new User("perOlsen");
		// Group g1 = new Group("testgruppe", u1);
		// u1.addGroup(g1);
		// System.out.println(u1.getGroups());
		System.out.println("Ferdig");
		
//		u1.addGroup(new Group("testgruppe", u1));
		
		
//		System.out.println(c.u1.getFirstname());
		
//		Group g = new Group("", new User("perOlsen"));
		
		// DEBUG START
		// System.out.println("Group end");
		// DEBUG END
	}
	
	public static void main(String[] args) throws Exception {
		DanielTestClass c = new DanielTestClass();
		c.run();
		
	}
	
}
