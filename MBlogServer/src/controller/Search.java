package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PostModel;
import model.UserModel;

/**
 * Servlet implementation class Search
 */
@WebServlet("/search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
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
			String keyword = request.getParameter("keyword").trim();
			int range = Integer.parseInt(request.getParameter("range"));
			String username = request.getParameter("username").trim();
			String place = request.getParameter("place");
			String sort_by = request.getParameter("sort_by");
			String sort = request.getParameter("sort");
			// deal
			if (keyword.equals("")) {   // 服务端验证
				request.setAttribute("state", 2); // 没输入搜索内容
				request.getRequestDispatcher("search.jsp").forward(request, response);
			}
			keyword = keyword.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;");// 转义
			boolean useEscape = false;
			if (keyword.contains("%") || keyword.contains("_")) {  // sql查询转义
				keyword.replaceAll("%", "/%").replaceAll("_", "/_");
				useEscape = true;
			}
			String[] keywords = keyword.split("\\s+");
			String userLimit = "";
			if (range == 1) {  // 按用户搜索
				UserModel user = UserModel.getUserFromUsername(username);
				if (user == null) {  // 没有用户
					request.setAttribute("state", 3); 
					request.getRequestDispatcher("search.jsp").forward(request, response);
				} else {
					userLimit = "userID=" + user.getUID() + " AND ";
				}
			}
			place = (place.equals("title")) ? "Title" : ((place.equals("content")) ? "Content" : "Name");   // 防止恶意请求虽然并没有用
			sort_by = (sort_by.equals("time")) ? "PostID" : "ViewNum";
			sort = (sort.equals("d")) ? "DESC" : "ASC";   // 防止恶意请求虽然并没有用
			List<PostModel> posts;
			if (place.equals("Name")) {
				posts = PostModel.searchPostsByTag(place, keywords, useEscape, userLimit, sort_by, sort);
			} else {
				posts = PostModel.searchPosts(place, keywords, useEscape, userLimit, sort_by, sort);
			}
			if (posts == null || posts.size() == 0) {  // 无结果
				request.setAttribute("state", 1); 
			} else {  // 正常
				request.setAttribute("state", 0); 
				request.setAttribute("posts", posts); 
			}
			request.getRequestDispatcher("search.jsp").forward(request, response);
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
