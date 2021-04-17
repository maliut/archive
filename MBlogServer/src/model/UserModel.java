package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import tool.DesUtil;

public class UserModel {
		
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
	
	public static boolean addUser(String username, String password, String email) {
		try {
			String salt = createSalt();
			sql = String.format("INSERT INTO user (Username,Password,Salt,Email,RegisterTime,BlogName) VALUES ('%s',MD5('%s'),'%s','%s',NOW(),'%s')",username, password + salt, salt, email, username+"的博客");
			stm = connection.createStatement();
			stm.executeUpdate(sql);
			// 创建该用户的默认分类：“未分类”
			UserModel user = getUserFromUsername(username);
			while (user == null) {
				user = getUserFromUsername(username);
			}
			CategoryModel.addCategory("未分类", user.getUID());
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean checkUser(String username, String password) {
		try {
			String salt = getSalt(username);
			if (salt.equals("")) { // no username
				return false;
			}
			sql = String.format("SELECT * FROM user WHERE Username='%s' AND Password=MD5('%s')", username, password + salt);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean checkUsedUsername(String username) {
		try {
			sql = String.format("SELECT * FROM user WHERE Username='%s'", username);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static String getSalt(String username) {
		try {
			sql = String.format("SELECT * FROM user WHERE Username='%s'", username);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				return rs.getString("Salt");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	private static String createSalt() {
		String salt = "";
		for (int i = 0; i < 8; i++) {
			salt += Integer.toHexString((int) (Math.random() * 16));
		}
		return salt;
	}
	
	public static UserModel getUserFromCookie(Cookie[] c) {
		try {
			// get username from cookie
			String cdata = null;
			if (c == null || c.length < 1) return null;
			for (int i=0; i<c.length; i++){  
	        	if ("cdata".equals(c[i].getName())){  
	            	cdata = c[i].getValue() ;  
	        	}  
			}
			if (cdata != null) {  
				String[] decdata = DesUtil.decryptCookies(cdata);
				if (decdata != null && UserModel.checkUser(decdata[0], decdata[1])) {
					// get user from username
					sql = String.format("SELECT * FROM user WHERE Username='%s'", decdata[0]);
					stm = connection.createStatement();
					rs = stm.executeQuery(sql);
					if (rs.next()) {
						UserModel user = new UserModel();
						user.uid = rs.getInt("UserID");
						user.username = decdata[0];
						user.postNum = rs.getInt("PostNum");
						user.blogName = rs.getString("BlogName");
						user.blogDes = rs.getString("BlogDes");
						user.bannerColor = rs.getInt("BannerColor");
						return user;
					}
				}
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static UserModel getUserFromUID(int uid) {
		try {
			sql = String.format("SELECT * FROM user WHERE UserID=%d", uid);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				UserModel user = new UserModel();
				user.uid = uid;
				user.username = rs.getString("Username");
				user.postNum = rs.getInt("PostNum");
				user.blogName = rs.getString("BlogName");
				user.blogDes = rs.getString("BlogDes");
				user.bannerColor = rs.getInt("BannerColor");
				return user;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static UserModel getUserFromUsername(String username) {
		try {
			sql = String.format("SELECT * FROM user WHERE Username='%s'", username);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				UserModel user = new UserModel();
				user.uid = rs.getInt("UserID");
				user.username = username;
				user.postNum = rs.getInt("PostNum");
				user.blogName = rs.getString("BlogName");
				user.blogDes = rs.getString("BlogDes");
				user.bannerColor = rs.getInt("BannerColor");
				return user;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static List<UserModel> getHotUsers() {
		List<UserModel> hotUsers = new ArrayList<UserModel>();
		try {
			sql = String.format("SELECT * FROM user ORDER BY PostNum DESC LIMIT 0,5");
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				UserModel user = new UserModel();
				user.uid = rs.getInt("UserID");
				user.username = rs.getString("Username");
				user.postNum = rs.getInt("PostNum");
				//user.blogName = rs.getString("BlogName");
				//user.blogDes = rs.getString("BlogDes");
				//user.bannerColor = rs.getInt("BannerColor");
				hotUsers.add(user);
			}
			return hotUsers;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	private int uid, postNum, bannerColor;
	private String username, blogName, blogDes;
	
	public int getUID() {
		return uid;
	}

	public String getUsername() {
		return username;
	}

	public String getBlogName() {
		return blogName;
	}

	public void setBlogName(String blogName) {
		try {
			sql = String.format("UPDATE user SET BlogName='%s' WHERE UserID=%d", blogName.replaceAll("'", "''"), uid);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public String getBlogDes() {
		return blogDes;
	}

	public void setBlogDes(String blogDes) {
		try {
			sql = String.format("UPDATE user SET BlogDes='%s' WHERE UserID=%d", blogDes.replaceAll("'", "''"), uid);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public int getPostNum() {
		return postNum;
	}

	public void deltaPostNum(int deltaPostNum) {
		try {
			sql = String.format("UPDATE user SET PostNum=%d WHERE UserID=%d", postNum + deltaPostNum, uid);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public int getBannerColor() {
		return bannerColor;
	}

	public void setBannerColor(int bannerColor) {
		try {
			sql = String.format("UPDATE user SET BannerColor=%d WHERE UserID=%d", bannerColor, uid);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public void setPassword(String password) {
		try {
			String salt = getSalt(username);
			if (salt.equals("")) { // no username
				return;
			}
			sql = String.format("UPDATE user SET Password=MD5('%s') WHERE UserID=%d", password+salt, uid);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
