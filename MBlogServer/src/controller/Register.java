package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.DesUtil;
import model.UserModel;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get method used in ajax deciding used username
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		// get parameter
		String username = request.getParameter("username").trim();
		// validate
		if (UserModel.checkUsedUsername(username)) {
			response.getWriter().println("该用户名已被占用"); 
		} else {
			response.getWriter().println("该用户名可用"); 
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		// get parameter
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		String rePassword = request.getParameter("rePassword").trim();
		String email = request.getParameter("email").trim();
		String fromPath = request.getParameter("fromPath");
		// validate
		List<String> list = new ArrayList<String>();
		if (username == null || username.trim().equals("")) {
			list.add("用户名不能为空！");
		}
		if (username.length() > 30) {
			list.add("用户名不能超过10字符！");
		}
		if (UserModel.checkUsedUsername(username)) {
			list.add("用户名已被占用！");
		}
		if (password == null || password.trim().equals("")) {
			list.add("密码不能为空！");
		}
		if (password.length() > 20 || !password.matches("^\\w+$")) {
			list.add("密码必须只包含数字字母，长度不超过20字符！");
		}
		if (!password.equals(rePassword)) {
			list.add("密码和确认密码不一致！");
		}
		if (email == null || email.trim().equals("")) {
			list.add("邮箱不能为空！");
		}
		if (!email.matches("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$")) {
			list.add("邮箱格式不正确！");
		}
		// continue
		if (list.isEmpty()) {
			UserModel.addUser(username, password, email);
			// set cookies			
			Cookie cdata = new Cookie("cdata", DesUtil.encryptCookies(username,password));
			response.addCookie(cdata);
			response.getWriter().print("<script type='text/javascript'>window.location.href='index.jsp';</script>");
			return;
		} else {
			String errorPrompt = "";
			for (String str: list) {
				errorPrompt += str + "\\n";
			}
		    response.getWriter().print("<script type='text/javascript'>alert('" + errorPrompt + "');window.location.href='"+fromPath+"';</script>");
		    return;
		}
	}

}
