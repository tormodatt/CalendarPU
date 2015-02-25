package fellesprosjekt;

import java.util.ArrayList;

public class Bruker {
	
	public String navn; 
	public String brukerNavn; 
	public String passord;
	public String mail; 
	public Kalender personligKalender; 
	public ArrayList<Gruppe> grupper;
	
	public Bruker(String navn, String brukerNavn, String passord, String mail, Kalender personligKalender) {
		this.navn = navn; 
		this.brukerNavn = brukerNavn; 
		this.passord = passord; 
		this.mail = mail; 
		this.personligKalender = new Kalender(); 
		this.grupper = new ArrayList<Gruppe>(); 
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getBrukerNavn() {
		return brukerNavn;
	}

	public String getPassord() {
		return passord;
	}

	public void setPassord(String passord) {
		this.passord = passord;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Kalender getPersonligKalender() {
		return personligKalender;
	}

	public ArrayList<Gruppe> getGrupper() {
		return grupper;
	}

	public void addGruppe(Gruppe gruppe) {
		if (grupper.contains(gruppe)) {
			throw new IllegalArgumentException("Allerede medlem i gruppe"); 
		} else {
			grupper.add(gruppe); 
		}
	}
	
	public void removeGruppe(Gruppe gruppe) {
		if (grupper.contains(gruppe)) {
			grupper.remove(gruppe); 
		} else {
			throw new IllegalArgumentException("Ikke medlem i gruppe");  
		}
	}
	
	

}
