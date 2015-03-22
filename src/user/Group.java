package user;

import java.util.ArrayList;

import Appointment.Notification;
import calendar.Calendar;
import calendar.Database;

import java.sql.*;

import user.User;


public class Group extends Database{
	
	private String name; 
	private ArrayList<User> members = new ArrayList<User>(); 
	private User leader;
	private Group mainGroup; 
	private int groupID;
	private ArrayList<Group> subGroups = new ArrayList<Group>();
	private Calendar calendar; 
	
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public Group(int groupID,User user) throws Exception {
		if (groupIDExists(groupID)) {
			try {
				openConn();
				preparedStatement = connect.prepareStatement("select * from `Group` where GroupID=?");
				preparedStatement.setInt(1,groupID);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					this.name = resultSet.getString("Name");
					String leaderUsername = resultSet.getString("Leader");
					if (leaderUsername.equals(user.getUsername())) this.leader = user;
					else this.leader = new User(leaderUsername);
				}
				this.groupID = groupID;
				preparedStatement = connect.prepareStatement("select * from Group_relation where Sub_group=?");
				preparedStatement.setInt(1,groupID);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					this.mainGroup = new Group(resultSet.getInt("Super_group"),user);
				}
				preparedStatement = connect.prepareStatement("select * from Group_relation where Super_group=?");
				preparedStatement.setInt(1,groupID);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					this.subGroups.add(new Group(resultSet.getInt("Sub_group"),user));
				}
			} finally {
				closeConn();
			}
			this.calendar = new Calendar(this); //Lager evt. en metode senere, setCalendar(), tar inn navn fra bruker
		} else {
			 System.out.println("Group does not exist!");
		}
		
		
	}
	
	public Group(String name, User leader) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into `Group` values (default,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1,name);
			preparedStatement.setString(2,leader.getUsername());
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				this.groupID = resultSet.getInt(1);
			} 
		} finally {
				closeConn();
			}
			this.name = name; 
			this.leader = leader;
			this.calendar = new Calendar(this, name + " calendar"); //Lager evt. en metode senere, setCalendar(), tar inn navn fra bruker
	}
	
	
	public boolean groupIDExists(int groupID) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select GroupID from `Group` WHERE GroupID=?");
			preparedStatement.setInt(1,groupID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return true;
			}
		} finally {
			closeConn();
		}
		return false;
	}
	
	public int getGroupID() {
		return groupID;
	}

	public void setName(String name) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Group (Name) values (?)");
			preparedStatement.setString(1,name);
			preparedStatement.executeUpdate();
			} finally {
			closeConn();
			}
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setLeader(User leader) throws Exception{
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Group (Leader) values (?)");
			preparedStatement.setString(1,leader.getUsername());
			preparedStatement.executeUpdate();
			} finally {
			closeConn();
			}
		this.leader = leader;
	}
	
	public User getLeader() {
		return this.leader; 
	}
	
	public void addMember(User user) throws Exception {
		new Notification("You've been added to a group", "You've been added to the group "+getName(), user);
		members.add(user);
	}
	
	
	public void removeMember(User user) throws Exception {
		new Notification("You've been removed from a group", "You've been removed from the group "+getName(), user);
		members.remove(user);
	}
	
	public ArrayList<User> getMembers() {
		return members;
	}

	
	public void setMainGroup(Group group) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("UPDATE Group_relation SET Super_group = ? where GroupID= ?");
			preparedStatement.setInt(1,group.getGroupID());
			preparedStatement.setInt(2,this.groupID);
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}		
		this.mainGroup = group;
	}
	
	
	public Group getMainGroup() {
		return mainGroup;
	}


	public void addSubGroup(Group group) throws Exception {
		if (this.groupID == group.getGroupID()) {
			throw new IllegalArgumentException("Kan ikke legge til seg selv som subgruppe.");
		}
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Group_relation (Sub_group, Super_group) values (?, ?)");
			preparedStatement.setInt(1,group.getGroupID());
			preparedStatement.setInt(2, this.getGroupID());
			preparedStatement.executeUpdate();
		} finally {
			closeConn();	
		}
		this.subGroups.add(group);
	}
	
	public ArrayList<Group> getSubGroups() { 
		return this.subGroups;
	}
	
	
	public Calendar getCalendar() {
		return this.calendar; 
	}
	
	public void deleteGroup() throws Exception {
		for (int i = 0; i < members.size(); i++) {
			members.get(i).removeMemberGroup(this);
			new Notification("A group you are member of has been deleted","The group "+getName()+" that you are a member of has been deleted.",members.get(i));
		}
		try {
			openConn();
			preparedStatement = connect.prepareStatement("delete from Appointment where CalendarID=?");
			preparedStatement.setInt(1,getCalendar().getCalendarID());
			preparedStatement.executeUpdate();
		}
		finally {
			closeConn();
		}
		//TO DO: Slette objekt
	}
	
}
