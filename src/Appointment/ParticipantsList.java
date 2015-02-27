package Appointment;

import java.util.ArrayList;

import calendar.User;

public class ParticipantsList {
	
	public ArrayList<User> list;
	
	public ParticipantsList() {
		this.list = new ArrayList<User>();
	}
	
	public ArrayList<User> getListe() {
		return list;
	}

	public void addParticipant(User participant) {
		if (list.contains(participant)) {
			throw new IllegalArgumentException("Brukeren er allerede deltager"); 
		} else  {
			list.add(participant);
		}
	}
	
	public void removeDeltager(User participant) {
		if (list.contains(participant)) {
			list.remove(participant);
		} else {
			throw new IllegalArgumentException("Brukeren er ikke deltager");
		}
	}

}
