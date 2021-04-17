package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel {

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
	
	public static int addCategory(String name, int userID) {
		try {
			sql = String.format("INSERT INTO category (Name,UserID) VALUES ('%s',%d)", name.replaceAll("'", "''"), userID);
			stm = connection.createStatement();
			stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			rs = stm.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public static CategoryModel getCategoryFromCID(int categoryID) {
		try {
			sql = String.format("SELECT * FROM category WHERE CategoryID=%d", categoryID);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				CategoryModel category = new CategoryModel();
				category.categoryID = categoryID;
				category.name = rs.getString("Name");
				category.postNum = rs.getInt("PostNum");
				category.userID = rs.getInt("UserID");
				return category;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static CategoryModel getCategoryFromName(String name, int userID) {
		try {
			sql = String.format("SELECT * FROM category WHERE Name='%s' AND UserID", name, userID);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				CategoryModel category = new CategoryModel();
				category.categoryID = rs.getInt("CategoryID");
				category.name = name;
				category.postNum = rs.getInt("PostNum");
				category.userID = userID;
				return category;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static List<CategoryModel> getCategoriesFromUID(int userID) {
		List<CategoryModel> categories = new ArrayList<CategoryModel>();
		try {
			sql = String.format("SELECT * FROM category WHERE UserID=%d ORDER BY CategoryID DESC", userID);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				CategoryModel category = new CategoryModel();
				category.categoryID = rs.getInt("CategoryID");
				category.name = rs.getString("Name");
				category.postNum = rs.getInt("PostNum");
				category.userID = userID;
				categories.add(category);
			}
			return categories;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static boolean deleteCategory(int categoryID) {
		try {
			// 把该分类下所有文章移动到未分类
			List<PostModel> posts = PostModel.getAllPostsFromCID(categoryID);
			if (posts.size() > 0) {
				int userID = CategoryModel.getCategoryFromCID(categoryID).userID;
				List<CategoryModel> categories = CategoryModel.getCategoriesFromUID(userID);
				int defaultCategoryID = categories.get(categories.size() - 1).categoryID;
				for (int i = 0; i < posts.size(); i++) {
					posts.get(i).setCategoryID(defaultCategoryID);
					categories.get(categories.size() - 1).deltaPostNum(1);   // 默认分类文章数+1
				}
			}
			sql = String.format("DELETE FROM category WHERE CategoryID=%d", categoryID);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private int categoryID, postNum, userID;
	private String name;

	public int getCategoryID() {
		return categoryID;
	}

	public int getUserID() {
		return userID;
	}

	public int getPostNum() {
		return postNum;
	}

	public void deltaPostNum(int deltaPostNum) {
		try {
			sql = String.format("UPDATE category SET PostNum=%d WHERE CategoryID=%d", postNum + deltaPostNum, categoryID);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		try {
			sql = String.format("UPDATE category SET Name='%s' WHERE CategoryID=%d", name.replaceAll("'", "''"), categoryID);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	
	
}
