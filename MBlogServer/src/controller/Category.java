package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CategoryModel;
import model.PostModel;
import model.UserModel;

/**
 * Servlet implementation class Category
 */
@WebServlet("/category")
public class Category extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Category() {
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
			int cid = Integer.parseInt(request.getParameter("c"));
			String pagetmp = request.getParameter("page");
			int page = (pagetmp == null) ? 1 : Integer.parseInt(pagetmp); 
			CategoryModel category = CategoryModel.getCategoryFromCID(cid);
			int uid = category.getUserID();
			// pack necessary info
			request.setAttribute("user", UserModel.getUserFromUID(uid)); 
			request.setAttribute("categories", CategoryModel.getCategoriesFromUID(uid));
			/// main
			request.setAttribute("category", category);
			request.setAttribute("posts", PostModel.getPosts("CategoryID", cid, page));
			request.getRequestDispatcher("category.jsp").forward(request, response);
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
