package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CategoryModel;
import model.PostModel;
import model.TagModel;
import model.UserModel;

/**
 * Servlet implementation class Tag
 */
@WebServlet("/tag")
public class Tag extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Tag() {
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
			int tid = Integer.parseInt(request.getParameter("t"));
			String pagetmp = request.getParameter("page");
			int page = (pagetmp == null) ? 1 : Integer.parseInt(pagetmp); 
			TagModel tag = TagModel.getTagFromTID(tid);
			int uid = tag.getUserID();
			// pack necessary info
			request.setAttribute("user", UserModel.getUserFromUID(uid)); 
			request.setAttribute("categories", CategoryModel.getCategoriesFromUID(uid));
			/// main
			request.setAttribute("tag", tag);
			request.setAttribute("posts", PostModel.getPostFromTID(tid, page));
			request.getRequestDispatcher("tag.jsp").forward(request, response);
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
