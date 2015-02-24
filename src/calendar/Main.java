package calendar;

import calendar.Database;

public class Main {
	public static void main(String[] args) throws Exception {
		Database db = new Database();
		try {
			db.openConn();
			db.viewTable("venner");
		} catch (Exception e) {
			throw e;
		} finally {
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