package danielstestpackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import user.Group;
import user.User;

public class DanielTestClass4 {
	
	private void run() throws Exception {
		User perOlsen = new User("Per", "Olsen", "perOlsen", "pass", "per.olsen@mail.com");
		User torNilsen = new User("Tor", "Olsen", "perOlsen", "pass", "per.olsen@mail.com");;
		Group gruppe1;
		Group gruppe2;
		
		
		gruppe1.addSubGroup(gruppe2);
		Group subGroup = gruppe1.getSubGroups().get(0);
		int gid = subGroup.getGroupID();
		assertNotNull(subGroup);
		assertEquals(gruppe2, subGroup);
		
		Group gruppe1copy = new Group(gid, perOlsen);
		// DEBUG START
		System.out.println(gruppe1copy.getSubGroups());
		// DEBUG END
		subGroup = gruppe1copy.getSubGroups().get(0);
		assertNotNull(subGroup);
		assertEquals(gruppe2, subGroup);
	}
	
	public static void main(String[] args) throws Exception {
		DanielTestClass4 a = new DanielTestClass4();
		a.run();
	}
}
