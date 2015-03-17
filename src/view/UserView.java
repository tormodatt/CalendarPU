package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;




import calendar.Database;
import Appointment.Room;

public class UserView extends Database{
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	//vil returnere en liste med fornavn og etternavn til samtlige brukere i databasen.

	public void  userView() throws Exception{
		ArrayList<String> users = new ArrayList<String>(); 

		try {
			openConn();
			preparedStatement = connect.prepareStatement("SELECT Username, First_name, Last_name FROM User");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				users.add(resultSet.getString("Username") + "" + resultSet.getString("First_name") + "" + resultSet.getString("Last_name"));  
			}
		} finally {
			closeConn();
		}		

	}
}
