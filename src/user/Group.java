package user;

import java.util.ArrayList;

public class Group {
	
	public String name; 
	public ArrayList<User> members; 
	public User leader;
	public ArrayList<Group> mainGroups; 
	public ArrayList<Group> subGroups; 
	
	
	public Group(String name) {
		setName(name); 
		this.members = new ArrayList<User>(); 
		this.mainGroups = new ArrayList<Group>(); 
		this.subGroups = new ArrayList<Group>(); 
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<User> getMembers() {
		return members;
	}

	public String getLeaderName() {
		return this.leader.getName();
	}

	public void setLeader(User leader) {
		this.leader = leader;
	}
	
	public void addMember(User user) {
		if (members == null) {
			this.members.add(user);
			setLeader(user); 						//F¿rst deltager (den som oppretter gruppen) blir leder
		}
		if (members.contains(user)) {
			throw new IllegalArgumentException("User already member");
		} else {
			this.members.add(user); 
		}
	}
	
	public void removeMember(User user) {
		if (members.contains(user)) {
			this.members.remove(user); 
		} else {
			throw new IllegalArgumentException("User not member of group");
		}
	}

	public ArrayList<Group> getMainGroups() {
		return mainGroups;
	}
	
	public String getMainGroupsToString() {
		String res = ""; 
		for (Group group : mainGroups) {
			res = res + (group.getName()) + "\n"; 
		}
		return res; 
	}
	
	public ArrayList<Group> getSubGroups() {
		return subGroups;
	}
	
	public String getSubGroupsToString() {
		String res = ""; 
		for (Group group : subGroups) {
			res = res + (group.getName()) + "\n"; 
		}
		return res; 
	}


	//MŒ avgj¿re om vi ¿nsker at en gruppe kan v¾re medlem i flere grupper 
	public void addMainGroup(Group group) {
		if (mainGroups.contains(group)) {
			throw new IllegalArgumentException("Already set as maingroup"); 
		} else {
			this.mainGroups.add(group); 
		}
	}

	public void addSubGroup(Group group) {
		if (subGroups.contains(group)) {
			this.subGroups.add(group); 
		} else {
			throw new IllegalArgumentException("Already set as subgroup");
		}
	}
	
	public String toString() { 
		return "Group name: " + this.getName() + "\n" + "Group leader: " + this.getLeaderName() + "\n" + "Members: " +
				this.getMembers() + "\n" + "Main groups: " + this.getMainGroups() + "\n" + "Sub groups: " + this.getSubGroups(); 
	}
	
	public static void main(String[] args) {
		Group NTNU = new Group("NTNU"); 
		User gunnar = new User()
		
	}
	
	
	
	

}
