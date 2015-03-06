package user;

import java.util.ArrayList;

import Appointment.Room;
import calendar.Database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.sql.*;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import calendar.Database;
import user.User;


public class Group extends Database{
	
	private String name; 
	private ArrayList<User> members; 
	private User leader;
	private Group mainGroup; 
	private int groupID;
	private ArrayList<Group> subGroups; 
	
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	
	
	public Group(String name, User leader) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Group values (default,?,?)");
			preparedStatement.setString(1,name);
			preparedStatement.setString(2,leader.getUsername());
			
			} finally {
				closeConn();
			}
			this.groupID = getGroupID();
			this.name = name; 
			this.leader = leader;
	}

	private int getGroupID() throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select GroupID from Group WHERE GroupID=?");
			preparedStatement.setInt(1,groupID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return resultSet.getInt("GroupID");
			}
		} finally {
			closeConn();
		}
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

	public ArrayList<User> getMembers() {
		return members;
	}

	public void setLeader(User leader) { //Finnes i database? 
		this.leader = leader;
	}
	
	public void addMember(User user) {
		if (members == null) {
			this.members.add(user);
			setLeader(user); 						//F¿rst deltager (den som oppretter gruppen) blir leder
		}
		if (members.contains(user)) {
			throw new IllegalArgumentException("User already member");
		} else {
			this.members.add(user); 
		}
	}
	
	public void removeMember(User user) {
		if (members.contains(user)) {
			this.members.remove(user); 
		} else {
			throw new IllegalArgumentException("User not member of group");
		}
	}

	public Group getMainGroups() {
		return mainGroup;
	}
	

	public ArrayList<Group> getSubGroups() {
		return subGroups;
	}

	//MŒ avgj¿re om vi ¿nsker at en gruppe kan v¾re medlem i flere grupper 
	public void setMainGroup(Group group) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("UPDATE Group_relation SET Super_group = ? where GroupID= ?");
			preparedStatement.setString(1,group.getName());
			preparedStatement.setInt(2,this.groupID);
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}		
		this.mainGroup = group;
	}

	public void addSubGroup(Group group) {
		if (subGroups.contains(group)) {
			this.subGroups.add(group); 
		} else {
			throw new IllegalArgumentException("Already set as subgroup");
		}
	}
	
	public String toString() { 
		return "Group name: " + this.getName() + "\n" + "Group leader: " + this.getLeaderName() + "\n" + "Members: " +
				this.getMembers() + "\n" + "Main groups: " + this.getMainGroups() + "\n" + "Sub groups: " + this.getSubGroups(); 
	}

	
	
	
	

}
