package fellesprosjekt;

import java.util.ArrayList;

public class RomOversikt {
	
	public ArrayList<Rom> alleRom; 
	public Rom rom; 
	
	public RomOversikt() {
		this.alleRom = new ArrayList<Rom>(); 		
	}
	
	public ArrayList<Rom> getRom(){
		return this.alleRom; 
	}

	public void addRom(Rom rom) {
		if (alleRom.contains(rom)) {
			throw new IllegalArgumentException("Rom er allerede lagt til i oversikten"); 
		} else {
			alleRom.add(rom); 
		}
	}
	
	public void removeRom(Rom rom) {
		if (alleRom.contains(rom)) {
			alleRom.remove(rom); 
		} else {
			throw new IllegalArgumentException("Rom finnes ikke i oversikten");
		}
	}
	

}
