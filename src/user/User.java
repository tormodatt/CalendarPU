package user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import calendar.Calendar;
import calendar.Database;

public class User extends Database{
	
	public String firstname; 
	public String lastname;
	public String username; 
	public String password;
	public String mail; 
	public Calendar personalCalendar; 
	public ArrayList<Group> groups;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public User(String username) throws Exception {
		if (userNameExists(username)) {
			this.username = username;
			try {
				openConn();
				preparedStatement = connect.prepareStatement("select * from all_s_gr46_calendar.User WHERE Username='"+username+"'");
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					this.firstname = resultSet.getString("First_name");
					this.lastname = resultSet.getString("Last_name");
					this.mail = resultSet.getString("Mail");
					this.password = resultSet.getString("Password");
				}
				} finally {
					closeConn();
				}
		}
		/*this.username = username; 
		this.password = password; 
		this.firstname = firstname; 
		this.mail = mail; 
		this.personalCalendar = new Calendar(null); 
		this.groups = new ArrayList<Group>(); 
		try {
		openConn();
		preparedStatement = connect.prepareStatement("insert into  all_s_gr46_calendar.User values (?,?, ?, ?, ?)");
		preparedStatement.setString(1,username);
		preparedStatement.setString(2,firstname);
		preparedStatement.setString(3,lastname);
		preparedStatement.setString(4,mail);
		preparedStatement.setString(5,password);
		preparedStatement.executeUpdate();
		} finally {
		closeConn();
		}
		*/
		
	}

	public String getName() throws Exception {
		/*try {
		openConn();
		preparedStatement = connect.prepareStatement("select First_name,Last_name from all_s_gr46_calendar.User WHERE Username='"+username+"'");
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			System.out.println("Fornavn: " + resultSet.getString("First_name") + "\nEtternavn: "+ resultSet.getString("Last_name"));
		}
		} finally {
			closeConn();
		}
		*/
		System.out.println("Fornavn: " + firstname + "\nEtternavn: " +lastname);
		return firstname;
	}

	public void setCredencials(String firstname, String lastname, String username, String password, String mail) throws Exception {
		try {
		openConn();
		preparedStatement = connect.prepareStatement("insert into  all_s_gr46_calendar.User values (?,?, ?, ?, ?)");
		preparedStatement.setString(1,username);
		preparedStatement.setString(2,firstname);
		preparedStatement.setString(3,lastname);
		preparedStatement.setString(4,mail);
		preparedStatement.setString(5,password);
		preparedStatement.executeUpdate();
		} finally {
		closeConn();
		}
	}
	
	public boolean userNameExists(String username) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select Username from all_s_gr46_calendar.User WHERE Username='"+username+"'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return true;
			}
			} finally {
				closeConn();
			}

			return false;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Calendar getPersonalCalendar() {
		return personalCalendar;
	}

	public ArrayList<Group> getGroup() {
		return groups;
	}

	public void addGroup(Group group) {
		if (groups.contains(group)) {
			throw new IllegalArgumentException("Allerede medlem i gruppe"); 
		} else {
			groups.add(group); 
		}
	}
	
	public void removeGroup(Group group) {
		if (groups.contains(group)) {
			groups.remove(group); 
		} else {
			throw new IllegalArgumentException("Ikke medlem i gruppe");  
		}
	}
	
	

}
