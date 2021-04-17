package controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.DesUtil;
import model.UserModel;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		// get parameter
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		// validate
		if (username.trim().equals("") || password.trim().equals("")) {
			response.getWriter().println("用户名或密码不能为空！");
			return;
		}
		if (!UserModel.checkUser(username, password)) {
			response.getWriter().println("用户名或密码错误！");
			return;
		} else {  // login succeed
			Cookie cdata = new Cookie("cdata", DesUtil.encryptCookies(username,password));
			response.addCookie(cdata);
			response.getWriter().print("登录成功");
			return;
		}
		
	}

}
