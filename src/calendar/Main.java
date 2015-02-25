package calendar;

import calendar.Database;

public class Main {
	public static void main(String[] args) throws Exception {
		Database db = new Database();
		try {
			// Koble til databasen
			db.openConn();
			//Gj¿re det som skal gj¿res
			db.viewTable("venner");
		} catch (Exception e) {
			throw e;
		} finally {
			//Lukke koblingen
			db.closeConn();
		}
	}
}
/*
import calendar.MySQLAccess;

public class Main {
  public static void main(String[] args) throws Exception {
    MySQLAccess dao = new MySQLAccess();
    dao.readDataBase();
  }
} 
*/