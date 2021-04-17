package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TagModel {

	private static Connection connection;
	private static String sql;
	private static Statement stm;
	private static ResultSet rs;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");		// load jdbc
			connection = DriverManager.getConnection(Common.connectionString, Common.DBUsername, Common.DBUserPassword);  // connect database
		} catch (ClassNotFoundException e) {
			System.out.println("error when loading jdbc");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("error when connecting database ");
			e.printStackTrace();
		}
	}
	
	public static int existTag(String name, int userID) {
		try {
			sql = String.format("SELECT * FROM tag WHERE Name='%s' AND UserID=%d", name.replaceAll("'", "''"), userID);
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				return rs.getInt("TagID");
			} else {
				return -1;  // 不存在
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;  // 错误
	}
	
	public static int addTag(String name, int userID) {
		try {
			sql = String.format("INSERT INTO tag (Name,UserID) VALUES ('%s',%d)", name.replaceAll("'", "''"), userID);
			Statement stm = connection.createStatement();
			stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stm.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public static TagModel getTagFromTID(int tagID) {
		try {
			sql = String.format("SELECT * FROM tag WHERE TagID=%d", tagID);
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);   // 二次调用，必须新建一个
			if (rs.next()) {
				TagModel tag = new TagModel();
				tag.tagID = rs.getInt("TagID");
				tag.name = rs.getString("Name");
				tag.userID = rs.getInt("UserID");
				return tag;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static List<TagModel> getTagsFromUID(int userID) {
		List<TagModel> tags = new ArrayList<TagModel>();
		try {
			sql = String.format("SELECT * FROM tag WHERE UserID=%d", userID);
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				TagModel tag = new TagModel();
				tag.tagID = rs.getInt("TagID");
				tag.name = rs.getString("Name");
				tag.userID = rs.getInt("UserID");
				tags.add(tag);
			}
			return tags;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static List<TagModel> getTagsFromPID(int postID) {
		List<TagModel> tags = new ArrayList<TagModel>();
		try {
			sql = String.format("SELECT * FROM post_tag WHERE PostID=%d", postID);
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				int tagID = rs.getInt("TagID");
				tags.add(TagModel.getTagFromTID(tagID));
			}
			return tags;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static boolean deleteTag(int tagID) {
		try {
			sql = String.format("DELETE FROM post_tag WHERE TagID=%d", tagID);
			Statement stm = connection.createStatement();
			stm.executeUpdate(sql);
			
			sql = String.format("DELETE FROM tag WHERE TagID=%d", tagID);
			Statement stm2 = connection.createStatement();
			stm2.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean addPost_tag(int postID, int tagID) {
		try {
			sql = String.format("INSERT INTO post_tag (PostID,TagID) VALUES (%d,%d)", postID, tagID);
			Statement stm = connection.createStatement();
			stm.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deletePost_tag(int postID, int tagID) {
		try {
			sql = String.format("DELETE FROM post_tag WHERE TagID=%d AND PostID=%d", tagID, postID);
			Statement stm = connection.createStatement();
			stm.executeUpdate(sql);

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean existPost_tag(int postID, int tagID) {
		try {
			sql = String.format("SELECT * FROM post_tag WHERE PostID=%d AND TagID=%d", postID, tagID);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return false;
	}
	
	private int tagID, userID;
	private String name;
	
	public int getTagID() {
		return tagID;
	}

	public int getUserID() {
		return userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		try {
			sql = String.format("UPDATE tag SET Name='%s' WHERE TagID=%d", name.replaceAll("'", "''"), tagID);
			Statement stm = connection.createStatement();
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}




}
