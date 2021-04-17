package controller;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CategoryModel;
import model.PostModel;
import model.UserModel;

/**
 * Servlet implementation class Home
 * Model of home.jsp
 * receive UserID & [page] and provide blog name, des, posts...
 */
@WebServlet("/home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			// get parameter
			int uid = Integer.parseInt(request.getParameter("u"));
			String pagetmp = request.getParameter("page");
			int page = (pagetmp == null) ? 1 : Integer.parseInt(pagetmp); 
			// pack necessary info
			request.setAttribute("user", UserModel.getUserFromUID(uid)); 
			request.setAttribute("categories", CategoryModel.getCategoriesFromUID(uid));
			/// main
			request.setAttribute("posts", PostModel.getPosts("UserID", uid, page));
			request.getRequestDispatcher("home.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
