package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostModel {
	
	static final int POST_PER_PAGE = 5;
	
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
	
	public static int addPost(String title, String content, int categoryID, int userID) {
		try {
			sql = String.format("INSERT INTO post (Title,Content,PublishTime,CategoryID,UserID) VALUES ('%s','%s',NOW(),%d,%d)", title.replaceAll("'", "''"), content.replaceAll("'", "''"), categoryID, userID);
			stm = connection.createStatement();
			stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			rs = stm.getGeneratedKeys();
			rs.next();
			CategoryModel.getCategoryFromCID(categoryID).deltaPostNum(1);
			UserModel.getUserFromUID(userID).deltaPostNum(1);   // 用户和分类拥有的文章数+1
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("=======insert post error======");
			e.printStackTrace();
		}
		return -1;
	}
	
	public static PostModel getPostFromPID(int postID) {
		try {
			sql = String.format("SELECT * FROM post WHERE PostID=%d", postID);
			stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				PostModel post = new PostModel();
				post.postID = postID;
				post.categoryID = rs.getInt("CategoryID");
				post.userID = rs.getInt("UserID");
				post.title = rs.getString("Title");
				post.content = rs.getString("Content");
				post.publishTime = rs.getString("PublishTime");
				post.modifyTime = rs.getString("ModifyTime");
				post.viewNum = rs.getInt("ViewNum");
				post.commentNum = rs.getInt("CommentNum");
				post.likeNum = rs.getInt("likeNum");
				post.tags = TagModel.getTagsFromPID(postID);
				return post;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static PostModel getPrevPost(int postID, int userID) {
		try {
			sql = String.format("SELECT * FROM post WHERE UserID=%d AND PostID<%d ORDER BY PostID DESC", userID, postID);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				PostModel post = new PostModel();
				post.postID = rs.getInt("PostID");
				//post.categoryID = rs.getInt("CategoryID");
				//post.userID = rs.getInt("UserID");
				post.title = rs.getString("Title");
				//post.content = rs.getString("Content");
				//post.publishTime = rs.getString("PublishTime");
				//post.modifyTime = rs.getString("ModifyTime");
				//post.viewNum = rs.getInt("ViewNum");
				//post.commentNum = rs.getInt("CommentNum");
				//post.likeNum = rs.getInt("likeNum");
				// post.tags = TagModel.getTagsFromPID(postID);
				return post;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;  // no prev
	}
	
	public static PostModel getNextPost(int postID, int userID) {
		try {
			sql = String.format("SELECT * FROM post WHERE UserID=%d AND PostID>%d ORDER BY PostID ASC", userID, postID);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				PostModel post = new PostModel();
				post.postID = rs.getInt("PostID");
				//post.categoryID = rs.getInt("CategoryID");
				//post.userID = rs.getInt("UserID");
				post.title = rs.getString("Title");
				//post.content = rs.getString("Content");
				//post.publishTime = rs.getString("PublishTime");
				//post.modifyTime = rs.getString("ModifyTime");
				//post.viewNum = rs.getInt("ViewNum");
				//post.commentNum = rs.getInt("CommentNum");
				//post.likeNum = rs.getInt("likeNum");
				// post.tags = TagModel.getTagsFromPID(postID);
				return post;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;  // no next
	}
	
	// 此方法和getPosts方法合作实现分页效果，此方法得到总页数
	/*public static int getMaxPage(String type, int id) {
		int maxPage = 0;
		try {
			sql = String.format("SELECT * FROM post WHERE %s=%d", type, id);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				maxPage += 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxPage;
	}*/
	
	public static List<PostModel> getPosts(String field, int id, int page) {  // field: UserID, , CategoryID
		List<PostModel> posts = new ArrayList<PostModel>();
		try {
			sql = String.format("SELECT * FROM post WHERE %s=%d ORDER BY PostID DESC LIMIT %d,%d", field, id, POST_PER_PAGE * (page - 1), POST_PER_PAGE);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				PostModel post = new PostModel();
				post.postID = rs.getInt("PostID");
				post.categoryID = rs.getInt("CategoryID");
				post.userID = rs.getInt("UserID");
				post.title = rs.getString("Title");
				post.content = rs.getString("Content");
				post.publishTime = rs.getString("PublishTime");
				post.modifyTime = rs.getString("ModifyTime");
				post.viewNum = rs.getInt("ViewNum");
				post.commentNum = rs.getInt("CommentNum");
				post.likeNum = rs.getInt("likeNum");
				post.tags = TagModel.getTagsFromPID(post.postID);
				posts.add(post);
			}
			return posts;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static List<PostModel> getAllPostsFromUID(int userID) {  // 在管理页面使用
		List<PostModel> posts = new ArrayList<PostModel>();
		try {
			sql = String.format("SELECT * FROM post WHERE UserID=%d ORDER BY PostID DESC", userID);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				PostModel post = new PostModel();
				post.postID = rs.getInt("PostID");
				post.categoryID = rs.getInt("CategoryID");
				post.userID = rs.getInt("UserID");
				post.title = rs.getString("Title");
				post.content = rs.getString("Content");
				post.publishTime = rs.getString("PublishTime");
				post.modifyTime = rs.getString("ModifyTime");
				post.viewNum = rs.getInt("ViewNum");
				post.commentNum = rs.getInt("CommentNum");
				post.likeNum = rs.getInt("likeNum");
				post.tags = TagModel.getTagsFromPID(post.postID);
				posts.add(post);
			}
			return posts;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static List<PostModel> getAllPostsFromCID(int categoryID) {  // 在管理页面使用
		List<PostModel> posts = new ArrayList<PostModel>();
		try {
			sql = String.format("SELECT * FROM post WHERE CategoryID=%d ORDER BY PostID DESC", categoryID);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				PostModel post = new PostModel();
				post.postID = rs.getInt("PostID");
				post.categoryID = rs.getInt("CategoryID");
				post.userID = rs.getInt("UserID");
				post.title = rs.getString("Title");
				post.content = rs.getString("Content");
				post.publishTime = rs.getString("PublishTime");
				post.modifyTime = rs.getString("ModifyTime");
				post.viewNum = rs.getInt("ViewNum");
				post.commentNum = rs.getInt("CommentNum");
				post.likeNum = rs.getInt("likeNum");
				post.tags = TagModel.getTagsFromPID(post.postID);
				posts.add(post);
			}
			return posts;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static List<PostModel> getPostFromTID(int id, int page) {
		List<PostModel> posts = new ArrayList<PostModel>();
		try {
			sql = String.format("SELECT * FROM post_tag WHERE TagID=%d ORDER BY PostID DESC LIMIT %d,%d", id, POST_PER_PAGE * (page - 1), POST_PER_PAGE);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				int postID = rs.getInt("PostID");
				posts.add(PostModel.getPostFromPID(postID));
			}
			return posts;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static List<PostModel> searchPosts(String place, String[] keywords, boolean useEscape, String userLimit, String sort_by, String sort) {
		List<PostModel> posts = new ArrayList<PostModel>();
		try {
			String searchword = place + " LIKE " + "'%" + keywords[0].replaceAll("'", "''") + "%'";
			if (keywords.length > 1) {  // 不止一个关键词
				for (int i = 1; i < keywords.length; i++) {
					searchword += " AND " + place + " LIKE " + "'%" + keywords[i].replaceAll("'", "''") + "%'";
				}
			}
			searchword += useEscape ? " ESCAPE '/'" : "";
			sql = String.format("SELECT * FROM post WHERE %s %s ORDER BY %s %s", userLimit, searchword, sort_by, sort);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				PostModel post = new PostModel();
				post.postID = rs.getInt("PostID");
				post.categoryID = rs.getInt("CategoryID");
				post.userID = rs.getInt("UserID");
				post.title = rs.getString("Title");
				post.content = rs.getString("Content");
				post.publishTime = rs.getString("PublishTime");
				post.modifyTime = rs.getString("ModifyTime");
				post.viewNum = rs.getInt("ViewNum");
				post.commentNum = rs.getInt("CommentNum");
				post.likeNum = rs.getInt("likeNum");
				post.tags = TagModel.getTagsFromPID(post.postID);
				posts.add(post);
			}
			return posts;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	

	public static List<PostModel> searchPostsByTag(String place, String[] keywords, boolean useEscape, String userLimit, String sort_by, String sort) {
		List<PostModel> posts = new ArrayList<PostModel>();
		List<Integer> tagsID = new ArrayList<Integer>();
		List<Integer> postsID = new ArrayList<Integer>();
		try {
			// 根据tagname查找tagid
			String searchword = place + " LIKE " + "'%" + keywords[0].replaceAll("'", "''") + "%'";
			if (keywords.length > 1) {  // 不止一个关键词
				for (int i = 1; i < keywords.length; i++) {
					searchword += " AND " + place + " LIKE " + "'%" + keywords[i].replaceAll("'", "''") + "%'";
				}
			}
			searchword += useEscape ? " ESCAPE '/'" : "";
			sql = String.format("SELECT * FROM tag WHERE %s %s", userLimit, searchword);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				tagsID.add(rs.getInt("TagID"));
			}
			if (tagsID.size() == 0) return null;
			// 根据tagid查找postid
			searchword = "TagID=" + tagsID.get(0);
			if (tagsID.size() > 1) {
				for (int i = 1; i < tagsID.size(); i++) {
					searchword += " OR TagID=" + tagsID.get(i);
				}
			}
			sql = String.format("SELECT * FROM post_tag WHERE %s", searchword);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("PostID");
				if (!postsID.contains(id)) {
					postsID.add(id);
				}
			}
			if (postsID.size() == 0) return null;
			// 根据postID查找post，主要是为了根据浏览量排序，不然可以省去一次
			searchword = "PostID=" + postsID.get(0);
			if (postsID.size() > 1) {
				for (int i = 1; i < postsID.size(); i++) {
					searchword += " OR PostID=" + postsID.get(i);
				}
			}
			sql = String.format("SELECT * FROM post WHERE %s ORDER BY %s %s", searchword, sort_by, sort);
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				PostModel post = new PostModel();
				post.postID = rs.getInt("PostID");
				post.categoryID = rs.getInt("CategoryID");
				post.userID = rs.getInt("UserID");
				post.title = rs.getString("Title");
				post.content = rs.getString("Content");
				post.publishTime = rs.getString("PublishTime");
				post.modifyTime = rs.getString("ModifyTime");
				post.viewNum = rs.getInt("ViewNum");
				post.commentNum = rs.getInt("CommentNum");
				post.likeNum = rs.getInt("likeNum");
				post.tags = TagModel.getTagsFromPID(post.postID);
				posts.add(post);
			}
			return posts;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}


	
	public static boolean deletePost(int postID) {
		try {
			// 用户文章数 -1， 分类文章数 -1
			UserModel.getUserFromUID(PostModel.getPostFromPID(postID).userID).deltaPostNum(-1);
			CategoryModel.getCategoryFromCID(PostModel.getPostFromPID(postID).categoryID).deltaPostNum(-1);
			// 删除关联的标签
			sql = String.format("DELETE FROM post_tag WHERE PostID=%d", postID);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
			// 删除
			sql = String.format("DELETE FROM post WHERE PostID=%d", postID);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static List<PostModel> getHotPosts() {  
		List<PostModel> posts = new ArrayList<PostModel>();
		try {
			sql = String.format("SELECT * FROM post ORDER BY likeNum DESC LIMIT 0,5");
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				PostModel post = new PostModel();
				post.postID = rs.getInt("PostID");
				//post.categoryID = rs.getInt("CategoryID");
				//post.userID = rs.getInt("UserID");
				post.title = rs.getString("Title");
				//post.content = rs.getString("Content");
				//post.publishTime = rs.getString("PublishTime");
				//post.modifyTime = rs.getString("ModifyTime");
				//post.viewNum = rs.getInt("ViewNum");
				//post.commentNum = rs.getInt("CommentNum");
				post.likeNum = rs.getInt("likeNum");
				//post.tags = TagModel.getTagsFromPID(post.postID);
				posts.add(post);
			}
			return posts;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static List<PostModel> getLatestPosts() {  
		List<PostModel> posts = new ArrayList<PostModel>();
		try {
			sql = String.format("SELECT * FROM post ORDER BY PostID DESC LIMIT 0,5");
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				PostModel post = new PostModel();
				post.postID = rs.getInt("PostID");
				//post.categoryID = rs.getInt("CategoryID");
				//post.userID = rs.getInt("UserID");
				post.title = rs.getString("Title");
				//post.content = rs.getString("Content");
				//post.publishTime = rs.getString("PublishTime");
				//post.modifyTime = rs.getString("ModifyTime");
				//post.viewNum = rs.getInt("ViewNum");
				//post.commentNum = rs.getInt("CommentNum");
				post.likeNum = rs.getInt("likeNum");
				//post.tags = TagModel.getTagsFromPID(post.postID);
				posts.add(post);
			}
			return posts;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	//public static boolean deletePost(int postID) {
		
	//}

	private int postID, categoryID, userID, viewNum, commentNum, likeNum;
	private String title, content, publishTime, modifyTime;
	private List<TagModel> tags;
	
	public int getPostID() {
		return postID;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		try {
			// 改变前后分类的文章数目
			CategoryModel.getCategoryFromCID(this.categoryID).deltaPostNum(-1);
			CategoryModel.getCategoryFromCID(categoryID).deltaPostNum(1);
			sql = String.format("UPDATE post SET CategoryID=%d WHERE PostID=%d", categoryID, postID);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public int getUserID() {
		return userID;
	}

	public int getViewNum() {
		return viewNum;
	}
	
	public void addViewNum() {
		try {
			sql = String.format("UPDATE post SET ViewNum=%d WHERE PostID=%d", viewNum + 1, postID);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public int getCommentNum() {
		return commentNum;
	}

	public void deltaCommentNum(int delta) {
		try {
			sql = String.format("UPDATE post SET CommentNum=%d WHERE PostID=%d", commentNum + delta, postID);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public int getLikeNum() {
		return likeNum;
	}

	public void deltaLikeNum(int delta) {
		try {
			sql = String.format("UPDATE post SET LikeNum=%d WHERE PostID=%d", likeNum + delta, postID);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		try {
			sql = String.format("UPDATE post SET Title='%s' WHERE PostID=%d", title.replaceAll("'", "''"), postID);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		try {
			sql = String.format("UPDATE post SET Content='%s' WHERE PostID=%d", content.replaceAll("'", "''"), postID);
			stm = connection.createStatement();
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public String getPublishTime() {
		return publishTime.split(" ")[0].replace('-', '/');
	}

	public String getModifyTime() {
		return modifyTime.split(" ")[0].replace('-', '/');
	}

	public List<TagModel> getTags() {
		return tags;
	}

	public void setTags(List<Integer> tagsID) {
		for (int i = 0; i < tagsID.size(); i++) {
			if(tagsID.get(i) != null) TagModel.addPost_tag(postID, tagsID.get(i));
		}
	}

}
