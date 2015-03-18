package danielstestpackage;

import user.Group;
import user.User;

public class DanielTestClass2 {

	private void run() throws Exception {
		User perOlsen = new User("perOlsen");
		Group gruppe1 = new Group(148, perOlsen);
//		Group gruppe2 = new Group(149);
//		gruppe1.addSubGroup(gruppe2);
		
//		System.out.println(gruppe1.getLeader());
//		System.out.println(gruppe2.getLeader());
		
//		gruppe1.addSubGroup(gruppe2);
//		Group subGroup = gruppe1.getSubGroups().get(0);
//		int gid = subGroup.getGroupID();
	//	
//		Group gruppe1copy = new Group(gid);
//		// DEBUG START
//		System.out.println(gruppe1copy.getSubGroups());
//		// DEBUG END
//		subGroup = gruppe1copy.getSubGroups().get(0);
		
	}
	
	
	public static void main(String[] args) throws Exception {
		DanielTestClass2 c = new DanielTestClass2();
		c.run();
	}
	
}
