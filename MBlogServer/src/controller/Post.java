package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CategoryModel;
import model.CommentModel;
import model.LikeModel;
import model.PostModel;
import model.UserModel;

/**
  Servlet implementation class Post
 */
@WebServlet("/post")
public class Post extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Post() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			// get parameter
			int pid = Integer.parseInt(request.getParameter("p"));
			PostModel post = PostModel.getPostFromPID(pid);
			int uid = post.getUserID();
			// pack necessary info
			request.setAttribute("user", UserModel.getUserFromUID(uid)); 
			request.setAttribute("categories", CategoryModel.getCategoriesFromUID(uid));
			/// main
			request.setAttribute("post", post);
			request.setAttribute("category", CategoryModel.getCategoryFromCID(post.getCategoryID()));
			request.setAttribute("prevPost", PostModel.getPrevPost(pid, uid));
			request.setAttribute("nextPost", PostModel.getNextPost(pid, uid));
			request.setAttribute("comments", CommentModel.getCommentsFromPID(pid));
			request.getRequestDispatcher("post.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 处理点赞事件，使用ajax接收从like.js传来的参数// 点赞需要服务端验证！
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			// get parameter
			int postID = Integer.parseInt(request.getParameter("postID"));
			int userID = Integer.parseInt(request.getParameter("userID"));
			// verify user // 万一通过修改前台js参数导致利用他人的userID点赞
			if (UserModel.getUserFromCookie(request.getCookies()).getUID() == userID) {
				// 改变用户和赞的单条记录
				int delta = LikeModel.changeLike(postID, userID);
				// 改变文章的赞总数
				PostModel.getPostFromPID(postID).deltaLikeNum(delta);
				return;
			} else {  // 验证不通过， 简单粗暴报个错，活该
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
		return;
	}

}
