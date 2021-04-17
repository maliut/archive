package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CommentModel {

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
	
	public static boolean addComment(String content, int userID, int postID) {
		try {
			sql = String.format("INSERT INTO comment (Content,PublishTime,UserID,PostID) VALUES ('%s',NOW(),%d,%d)", content.replaceAll("'", "''"), userID, postID);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static CommentModel getCommentFromCID(int commentID) {
		try {
			sql = String.format("SELECT * FROM comment WHERE CommentID=%d", commentID);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				CommentModel comment = new CommentModel();
				comment.commentID = commentID;
				comment.content = rs.getString("Content");
				comment.publishTime = rs.getString("PublishTime");
				comment.userID = rs.getInt("userID");
				comment.postID = rs.getInt("PostID");
				return comment;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static List<CommentModel> getCommentsFromPID(int postID) {
		List<CommentModel> comments = new ArrayList<CommentModel>();
		try {
			sql = String.format("SELECT * FROM comment WHERE PostID=%d", postID);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				CommentModel comment = new CommentModel();
				comment.commentID = rs.getInt("commentID");
				comment.content = rs.getString("Content");
				comment.publishTime = rs.getString("PublishTime");
				comment.userID = rs.getInt("userID");
				comment.postID = postID;
				comments.add(comment);
			}
			return comments;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static boolean deleteComment(int commentID) {
		try {
			// 文章的评论数 -1
			PostModel.getPostFromPID(CommentModel.getCommentFromCID(commentID).postID).deltaCommentNum(-1);
			sql = String.format("DELETE FROM comment WHERE CommentID=%d", commentID);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private int commentID, userID, postID;
	private String content, publishTime;
	
	public int getUserID() {
		return userID;
	}

	public String getContent() {
		return content;
	}

	public String getPublishTime() {
		return publishTime.split(" ")[0].replace('-', '/');
	}

	public int getCommentID() {
		return commentID;
	}

	public int getPostID() {
		return postID;
	}


}
