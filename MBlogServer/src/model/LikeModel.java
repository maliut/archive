package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LikeModel {

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
	
	public static boolean personalLiked(int postID, int userID) {
		try {
			sql = String.format("SELECT * FROM liked WHERE PostID=%d AND UserID=%d", postID, userID);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				int liked = rs.getInt("Liked");
				if (liked == 1) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static int changeLike(int postID, int userID) {
		int delta = 0;
		try {
			sql = String.format("SELECT * FROM liked WHERE PostID=%d AND UserID=%d", postID, userID);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {  // 有用户和该篇文章的点赞记录
				int liked = rs.getInt("Liked");
				delta = (liked == 1) ? -1 : 1;  // 之前是喜欢，那么这次就是取消点赞
				sql = String.format("UPDATE liked SET liked = liked + %d WHERE PostID=%d AND UserID=%d ",  delta, postID, userID);
				stm = connection.createStatement();
				stm.executeUpdate(sql);
			} else { // 没有点赞记录，需要新建一条,且必然是点赞
				sql = String.format("INSERT INTO liked (PostID,UserID,Liked) VALUES (%d,%d,1)", postID, userID);
				stm = connection.createStatement();
				stm.executeUpdate(sql);
				delta = 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return delta;
	}
	
	public static boolean deleteLike(int postID) {  // 用于删除文章时
		try {
			sql = String.format("DELETE FROM liked WHERE PostID=%d", postID);
			Statement stm = connection.createStatement();
			stm.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
