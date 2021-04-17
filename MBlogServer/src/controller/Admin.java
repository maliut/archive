package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;

import model.CategoryModel;
import model.TagModel;
import model.UserModel;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 处理新增分类和标签,重命名等
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			String deed = request.getParameter("deed");
			if (deed.equals("add")) {
				doAdd(request, response);
			} else if (deed.equals("rename")) {
				doRename(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}

	private void doRename(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String type = request.getParameter("type");
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			if (type.equals("category")) {
				CategoryModel.getCategoryFromCID(id).setName(name);
			} else if (type.equals("tag")) {
				TagModel.getTagFromTID(id).setName(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}

	private void doAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String type = request.getParameter("type");
			int uid = Integer.parseInt(request.getParameter("uid"));
			String name = request.getParameter("name");
			if (type.equals("category")) {
				CategoryModel.addCategory(name, uid);
			} else if (type.equals("tag") && TagModel.existTag(name, uid) == -1) {
				TagModel.addTag(name, uid);
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
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			com.jspsmart.upload.SmartUpload su = new com.jspsmart.upload.SmartUpload();
			su.initialize(this.getServletConfig(), request, response);
			su.setMaxFileSize(15000000);
			su.setAllowedFilesList("jpg,jge,jpeg,png,gif,tif,tiff,bmp,dib");
			su.upload();
			// get parameters
			String type = su.getRequest().getParameter("type");
			if (type.equals("blog")) {
				changeBlog(su, request, response);
			} else if (type.equals("user")) {
				changeUser(su, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}

	private void changeUser(SmartUpload su, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			UserModel user = UserModel.getUserFromCookie(request.getCookies());
			// 保存图片
			// 注意：图片保存在eclipse的缓存路径当中，所以看不到
			// 可以在eclipse的workspace下.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps看到
			su.save("/image/avatar");
			com.jspsmart.upload.File file = su.getFiles().getFile(0);
			if (!file.isMissing()) {
				file.saveAs("/image/avatar/" + user.getUID(),SmartUpload.SAVE_VIRTUAL);
			}	// 会覆盖旧的文件所以没问题	
			// 其余事务
			// 处理密码更改
			String oldPassword = su.getRequest().getParameter("oldPassword");
			String newPassword = su.getRequest().getParameter("newPassword");
			String rePassword = su.getRequest().getParameter("rePassword");
			String fromPath = su.getRequest().getParameter("fromPath");
			if (!newPassword.trim().equals("")) {  // 要更改密码
				List<String> list = new ArrayList<String>();
				if (!UserModel.checkUser(user.getUsername(), oldPassword)) {
					list.add("当前密码错误！");
				} 
				if (newPassword.length() < 6) {
					list.add("密码必须大于6字符！");
				}
				if (newPassword.matches("^\\d+$")) {
					list.add("密码不能为纯数字！");
				}
				if (newPassword.length() > 20 || !newPassword.matches("^\\w+$")) {
					list.add("密码必须只包含数字字母，长度不超过20字符！");
				}
				if (newPassword.trim().equals("")) {
					list.add("密码不能为空！");
				}
				if (!newPassword.equals(rePassword)) {
					list.add("密码和确认密码不一致！");
				}
				if (newPassword.equals(oldPassword)) {
					list.add("新密码和当前密码不能相同！");
				}
				if (list.isEmpty()) {
					user.setPassword(newPassword);
					response.getWriter().print("<script type='text/javascript'>alert('更改成功！请重新登录！');window.location.href='index.jsp';</script>");
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
			response.getWriter().print("<script type='text/javascript'>alert('更改成功！');window.location.href='"+fromPath+"';</script>");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
		
	}

	private void changeBlog(SmartUpload su, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			UserModel user = UserModel.getUserFromCookie(request.getCookies());
			// 保存图片
			// 注意：图片保存在eclipse的缓存路径当中，所以看不到
			// 可以在eclipse的workspace下.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps看到
			su.save("/image/banner");
			com.jspsmart.upload.File file = su.getFiles().getFile(0);
			if (!file.isMissing()) {
				file.saveAs("/image/banner/" + user.getUID(),SmartUpload.SAVE_VIRTUAL);
			}	// 会覆盖旧的文件所以没问题		
			// 保存别的信息
			user.setBlogName(su.getRequest().getParameter("blogName").replaceAll(" ", "&nbsp;"));
			user.setBlogDes(su.getRequest().getParameter("blogDes").replaceAll(" ", "&nbsp;"));
			user.setBannerColor(Integer.parseInt(su.getRequest().getParameter("bannerColor")));
			String fromPath = su.getRequest().getParameter("fromPath");
			response.getWriter().print("<script type='text/javascript'>alert('更改成功！');window.location.href='"+fromPath+"';</script>");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}

	}

}
