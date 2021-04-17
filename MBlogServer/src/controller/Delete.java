package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CategoryModel;
import model.CommentModel;
import model.LikeModel;
import model.PostModel;
import model.TagModel;
import model.UserModel;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 使用ajax（前台js设置相应display=none） */
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			// get parameter
			String type = request.getParameter("type");
			int id = Integer.parseInt(request.getParameter("id"));
			int userID = UserModel.getUserFromCookie(request.getCookies()).getUID();
			if (type.equals("post")) {
				if (PostModel.getPostFromPID(id).getUserID() == userID) {  	// validate  // 防止非管理员人为构造路径删除东西
					PostModel.deletePost(id);
					LikeModel.deleteLike(id);
					List<CommentModel> comments = CommentModel.getCommentsFromPID(id);
					for (int i = 0; i < comments.size(); i++) {   // 删除对应文章的评论
						CommentModel.deleteComment(comments.get(i).getCommentID());
					}
				}
			} else if (type.equals("comment")) {
				if (PostModel.getPostFromPID(CommentModel.getCommentFromCID(id).getPostID()).getUserID() == userID) {  	// validate  // 防止非管理员人为构造路径删除东西
					CommentModel.deleteComment(id);
				}
			} else if (type.equals("category")) {
				if (CategoryModel.getCategoryFromCID(id).getUserID() == userID) {  	// 要改！！！  // 防止非管理员人为构造路径删除东西
					CategoryModel.deleteCategory(id);
					// 执行js
				}
			} else if (type.equals("tag")) { 
				if (TagModel.getTagFromTID(id).getUserID() == userID) {  	// 要改！！！  // 防止非管理员人为构造路径删除东西
					TagModel.deleteTag(id);
					// 执行js
				}
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
