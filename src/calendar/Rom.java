package fellesprosjekt;

import java.util.ArrayList;

public class Rom {
	
	public String romkode; 
	public int antallPlasser;
	public Avtale avtale; 
	public ArrayList<Avtale> avtaler;
	
	public Rom(String romkode, int antallPlasser) {
		this.romkode = romkode;
		this.antallPlasser = antallPlasser; 
		this.avtaler = new ArrayList<Avtale>(); 
	}

	public ArrayList<Avtale> getAvtaler() {
		return avtaler;
	}
	
	public String getRomkode() {
		return romkode;
	}

	public int getAntallPlasser() {
		return antallPlasser;
	}

	public void setRomkode(String romkode) {
		this.romkode = romkode; 
	}
	
	public void setAntallPlasser(int antallPlasser) {
		this.antallPlasser = antallPlasser; 
	}
	
	public void addAvtale(Avtale avtale) {
		if (avtaler.contains(avtale)) {
			throw new IllegalArgumentException("Avtalen skal allerede finnes sted i rommet"); 
		} else {
			avtaler.add(avtale); 
		}
	}
	
	public void removeAvtale(Avtale avtale) {
		if (avtaler.contains(avtale)) {
			 avtaler.remove(avtale);
		} else {
			throw new IllegalArgumentException("Avtalen er ikke planlagt i dette rommet");
		}
	}
	
	
	
	

}
