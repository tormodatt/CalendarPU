package fellesprosjekt;

import java.util.Date;

public class Tid {
	
	public Date dato; 
	public int startTime; 
	public int startMinutt; 
	public int varighet; 
	
	public Tid(Date dato, int startTime, int startMinutt, int varighet) {
		this.dato = dato;
		this.startTime = startTime;
		this.startMinutt = startMinutt; 
		this.varighet = varighet; 
	}
	

}
