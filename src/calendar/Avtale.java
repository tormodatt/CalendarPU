package fellesprosjekt;

import java.util.ArrayList;
import java.util.Date;

public class Avtale {
	
	public String tittel; 
	public Tid tidspunkt;  
	public String beskrivelse; 
	public int prioritet; 
	public Rom sted; 
	public ArrayList<Bruker> deltagere;
	
	public Avtale(String tittel, Tid tidspunkt, String beskrivelse, int prioritet, Rom sted) {
	}
	
	public Tid getTidspunkt() {
		return this.tidspunkt;
	}

	public void setTidspunkt(Date dato, int startTime, int startMinutt, int varighet) {
		this.tidspunkt = new Tid(dato, startTime, startMinutt, varighet);
	}
	
	public String getTittel() {
		return tittel;
	}
	public void setTittel(String tittel) {
		this.tittel = tittel;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}
	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}
	public int getPrioritet() {
		return prioritet;
	}
	public void setPrioritet(int prioritet) {
		this.prioritet = prioritet;
	}
	public Rom getSted() {
		return sted;
	}
	public void setSted(Rom sted) {
		this.sted = sted;
	}
	public ArrayList<Bruker> getDeltagere() {
		return deltagere;
	}
	public void setDeltagere(ArrayList<Bruker> deltagere) {
		this.deltagere = deltagere;
	} 
	

}
