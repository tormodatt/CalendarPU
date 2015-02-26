package calendar;

import java.util.ArrayList;

public class User {
	
	public String name; 
	public String username; 
	public String password;
	public String mail; 
	public Calendar personalCalendar; 
	public ArrayList<Group> groups;
	
	public User(String name, String username, String password, String mail, Calendar personalCalendar) {
		this.name = name; 
		this.username = username; 
		this.password = password; 
		this.mail = mail; 
		this.personalCalendar = new Calendar(null); 
		this.groups = new ArrayList<Group>(); 
	}

	public String getName() {
		return name;
	}

	public void setNavn(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Calendar getPersonalCalendar() {
		return personalCalendar;
	}

	public ArrayList<Group> getGroup() {
		return groups;
	}

	public void addGroup(Group group) {
		if (groups.contains(group)) {
			throw new IllegalArgumentException("Allerede medlem i gruppe"); 
		} else {
			groups.add(group); 
		}
	}
	
	public void removeGroup(Group group) {
		if (groups.contains(group)) {
			groups.remove(group); 
		} else {
			throw new IllegalArgumentException("Ikke medlem i gruppe");  
		}
	}
	
	

}
