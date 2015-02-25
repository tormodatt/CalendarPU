package fellesprosjekt;

import java.util.ArrayList;

public class DeltagerListe {
	
	public ArrayList<Bruker> liste;
	
	public DeltagerListe() {
		this.liste = new ArrayList<Bruker>();
	}
	
	public ArrayList<Bruker> getListe() {
		return liste;
	}

	public void addDeltager(Bruker deltager) {
		if (liste.contains(deltager)) {
			throw new IllegalArgumentException("Brukeren er allerede deltager"); 
		} else  {
			liste.add(deltager);
		}
	}
	
	public void removeDeltager(Bruker deltager) {
		if (liste.contains(deltager)) {
			liste.remove(deltager);
		} else {
			throw new IllegalArgumentException("Brukeren er ikke deltager");
		}
	}

}
